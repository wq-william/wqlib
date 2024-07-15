package hz.wq.httplib.helper

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
import hz.wq.httplib.interceptor.HeadersInterceptor
import hz.wq.httplib.interfaces.IDataProcessing
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Http util
 */
object HttpHelper {

    /**
     * 获取 Retrofit 对象
     *
     * @param T 业务层需要解析的bean
     * @param domain    域名
     * @param headMap   头
     * @param dataProcessing    数据加工
     * @param interceptors  拦截器list  -  扩展更多使用方式
     * @param converterFactories   转换工厂list  -  扩展更多使用方式
     * @param connectTimeout   连接超时 默认30
     * @param readTimeout   读取超时  默认30
     * @param writeTimeout   写超时  默认30
     * @return
     */
    private fun <T> getDefaultRetrofit(
        domain: String,
        headMap: Map<String, String>? = null,
        dataProcessing: IDataProcessing? = null,
        interceptors: Array<Interceptor>? = null,
        converterFactories: Array<Converter.Factory>? = null,
        isNeedAllLog: Boolean = false,
        connectTimeout: Long = 30,
        readTimeout: Long = 30,
        writeTimeout: Long = 30,

        ): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .run {
                connectTimeout(connectTimeout, TimeUnit.SECONDS)  // 连接超时
                readTimeout(readTimeout, TimeUnit.SECONDS)     // 读取超时
                writeTimeout(writeTimeout, TimeUnit.SECONDS)    // 写入超时
                interceptors?.forEach {
                    addInterceptor(it)
                }
                addInterceptor(HeadersInterceptor(headMap))
//                addInterceptor(HttpLoggingInterceptor().apply {
//                    level = HttpLoggingInterceptor.Level.BODY
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
                            .registerTypeAdapter(
                                String::class.java,
                                ApiResponseStringDeserializer()
                            )
                            .create()
                    )
                )

                //单独处理JSON
                addConverterFactory(
                    CustomGsonConverterFactory.create(
                        GsonBuilder()
                            .registerTypeAdapter(
                                ApiResponse::class.java,
                                ApiResponseJsonDeserializer<T>()
                            )
                            .create()
                    )
                )
                addConverterFactory(ScalarsConverterFactory.create())
                addConverterFactory(GsonConverterFactory.create())
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
        isNeedAllLog: Boolean = false,
        connectTimeout: Long = 30,
        readTimeout: Long = 30,
        writeTimeout: Long = 30,
    ): T {
        return getDefaultRetrofit<T>(
            domain,
            headMap,
            dataProcessing,
            interceptors,
            converterFactories,
            isNeedAllLog,
            connectTimeout,
            readTimeout,
            writeTimeout,

            ).create(service)
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
        isNeedAllLog: Boolean = false,
        connectTimeout: Long = 30,
        readTimeout: Long = 30,
        writeTimeout: Long = 30,

        ): ApiResponse<Result> {
        val apiService = getApiService(
            domain,
            service,
            headMap,
            dataProcessing,
            interceptors,
            converterFactories,
            isNeedAllLog,
            connectTimeout,
            readTimeout,
            writeTimeout,

            )
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
        isNeedAllLog: Boolean = false,
        connectTimeout: Long = 30,
        readTimeout: Long = 30,
        writeTimeout: Long = 30,
    ): ApiResponse<Result> {

        val apiService = getApiService(
            domain,
            service,
            headMap,
            dataProcessing,
            interceptors,
            converterFactories,
            isNeedAllLog,
            connectTimeout,
            readTimeout,
            writeTimeout,
        )
        val result = apiService.serviceFunction(param)
        return result
    }
}