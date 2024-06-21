package hz.wq.httplib.utils

import com.google.gson.GsonBuilder
import hz.wq.httplib.bean.ApiResponse
import hz.wq.httplib.convert.ApiResponseJsonDeserializer
import hz.wq.httplib.convert.ApiResponseStringDeserializer
import hz.wq.httplib.convert.CustomGsonConverterFactory
import hz.wq.httplib.convert.CustomStringConverterFactory
import hz.wq.httplib.convert.NullOnEmptyConverterFactory
import hz.wq.httplib.interceptor.WqHttpLogInterceptor
import hz.wq.httplib.interceptor.HttpResponseInterceptor
import hz.wq.httplib.interceptor.DataProcessingInterceptor
import hz.wq.httplib.interceptor.ExceptionInterceptor
import hz.wq.httplib.interceptor.HeadersInterceptor
import hz.wq.httplib.interfaces.ApiResponseCallback
import hz.wq.httplib.interfaces.IDataProcessing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Http util
 */
object HttpUtil {

    /**
     * 获取 Retrofit 对象
     *
     * @param T 业务层需要解析的bean
     * @param domain    域名
     * @param headMap   头
     * @param dataProcessing    数据加工
     * @param interceptors  拦截器list  -  扩展更多使用方式
     * @param converterFactories   转换工厂list  -  扩展更多使用方式
     * @return
     */
    private fun <T> getDefaultRetrofit(
        domain: String,
        headMap: Map<String, String>? = null,
        dataProcessing: IDataProcessing? = null,
        interceptors: Array<Interceptor>? = null,
        converterFactories: Array<Converter.Factory>? = null,
        isNeedAllLog: Boolean = false
    ): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .run {
                connectTimeout(30, TimeUnit.SECONDS)  // 连接超时
                readTimeout(30, TimeUnit.SECONDS)     // 读取超时
                writeTimeout(30, TimeUnit.SECONDS)    // 写入超时
                interceptors?.forEach {
                    addInterceptor(it)
                }
                addInterceptor(HeadersInterceptor(headMap))
//                addInterceptor(HttpLoggingInterceptor().apply {
//                    level = HttpLoggingInterceptor.Level.BASIC
//                })
                addInterceptor(HttpResponseInterceptor())
                addInterceptor(DataProcessingInterceptor(dataProcessing))
                addInterceptor(WqHttpLogInterceptor(isNeedAllLog))

//                addInterceptor(ExceptionInterceptor())
            }
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(domain)
            .run {
                converterFactories?.forEach {
                    addConverterFactory(it)
                }
                addConverterFactory(NullOnEmptyConverterFactory())
                //单独处理String
                addConverterFactory(
                    CustomStringConverterFactory.create(
                        GsonBuilder()
                            .registerTypeAdapter(String::class.java, ApiResponseStringDeserializer())
                            .create()
                    )
                )
//                addConverterFactory(ScalarsConverterFactory.create())
                //单独处理JSON
                addConverterFactory(
                    CustomGsonConverterFactory.create(
                        GsonBuilder()
                            .registerTypeAdapter(ApiResponse::class.java, ApiResponseJsonDeserializer<T>())
                            .create()
                    )
                )
            }
            .client(okHttpClient)
            .build()
        return retrofit
    }

    /**
     * 获取ApiService
     *
     * @param domain    域名
     * @param service   Class: 例：ApiServiceXXX::class.java
     * @param headMap   头
     * @param dataProcessing    数据加工
     * @param interceptors  拦截器list  -  扩展更多使用方式
     * @param converterFactories   转换工厂list  -  扩展更多使用方式
     * @return  T
     */
    fun <T> getApiService(
        domain: String,
        service: Class<T>,
        headMap: Map<String, String>? = null,
        dataProcessing: IDataProcessing? = null,
        interceptors: Array<Interceptor>? = null,
        converterFactories: Array<Converter.Factory>? = null,
        isNeedAllLog: Boolean = false
    ): T {
        return getDefaultRetrofit<T>(domain, headMap, dataProcessing, interceptors, converterFactories,isNeedAllLog).create(service)
    }

    /**
     * 发送Api ，获取Result结果
     *
     * @param domain    域名
     * @param service   Class: 例：ApiServiceXXX::class.java
     * @param serviceFunction   ApiService的方法
     * @param param 参数
     * @param headMap   头
     * @param dataProcessing    数据加工
     * @param interceptors  拦截器list  -  扩展更多使用方式
     * @param converterFactories   转换工厂list  -  扩展更多使用方式
     */
    suspend fun <ServiceClas, Result> sendApiNoParam(
        domain: String,
        service: Class<ServiceClas>,
        serviceFunction: suspend ServiceClas.() -> ApiResponse<Result>, // 高阶函数，接受一个参数 Param
        headMap: Map<String, String>? = null,
        dataProcessing: IDataProcessing? = null,
        interceptors: Array<Interceptor>? = null,
        converterFactories: Array<Converter.Factory>? = null,
    ): ApiResponse<Result> {
        val apiService = getApiService(domain, service, headMap, dataProcessing, interceptors, converterFactories)
        val result = apiService.serviceFunction()
        return result
    }

    /**
     * 发送Api ，获取Result结果
     *
     * @param domain    域名
     * @param service   Class: 例：ApiServiceXXX::class.java
     * @param serviceFunction   ApiService的方法
     * @param param 参数
     * @param headMap   头
     * @param dataProcessing    数据加工
     * @param interceptors  拦截器list  -  扩展更多使用方式
     * @param converterFactories   转换工厂list  -  扩展更多使用方式
     */
    suspend fun <ServiceClas, Param, Result> sendApi(
        domain: String,
        service: Class<ServiceClas>,
        serviceFunction: suspend ServiceClas.(Param) -> ApiResponse<Result>, // 高阶函数，接受一个参数 Param
        param: Param,
        headMap: Map<String, String>? = null,
        dataProcessing: IDataProcessing? = null,
        interceptors: Array<Interceptor>? = null,
        converterFactories: Array<Converter.Factory>? = null,
    ): ApiResponse<Result> {

        val apiService = getApiService(domain, service, headMap, dataProcessing, interceptors, converterFactories)
        val result = apiService.serviceFunction(param)
        return result
    }
}