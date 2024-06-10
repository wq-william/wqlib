package hz.wq.httplib.utils

import com.google.gson.GsonBuilder
import hz.wq.httplib.bean.ApiResponse
import hz.wq.httplib.convert.ApiResponseJsonDeserializer
import hz.wq.httplib.convert.CustomGsonConverterFactory
import hz.wq.httplib.convert.NullOnEmptyConverterFactory
import hz.wq.httplib.interceptor.WqHttpLogInterceptor
import hz.wq.httplib.interceptor.ApiResponseInterceptor
import hz.wq.httplib.interceptor.ExceptionInterceptor
import hz.wq.httplib.interceptor.HeadersInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * http请求 使用工具类
 */
object HttpUtil {

    /**
     * 获取retrofit
     */
    fun <T> getDefaultRetrofit(
        domain: String,
        interceptors: Array<Interceptor>? = null,
        factory: Array<Converter.Factory>? = null
    ): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .run {
                interceptors?.forEach {
                    addInterceptor(it)
                }
                addInterceptor(HeadersInterceptor())
                addInterceptor(ExceptionInterceptor())
//                addInterceptor(HttpLoggingInterceptor().apply {
//                    level = HttpLoggingInterceptor.Level.BODY
//                })
                addInterceptor(ApiResponseInterceptor())
                addInterceptor(WqHttpLogInterceptor())
            }
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(domain)
            .run {
                factory?.forEach {
                    addConverterFactory(it)
                }
                addConverterFactory(NullOnEmptyConverterFactory())
                addConverterFactory(ScalarsConverterFactory.create())
                val gson = GsonBuilder()
                    .registerTypeAdapter(ApiResponse::class.java, ApiResponseJsonDeserializer<T>())
                    .create()
                addConverterFactory(CustomGsonConverterFactory.create(gson))
            }
            .client(okHttpClient)
            .build()
        return retrofit
    }

    /**
     * 获取ApiServices
     */
    fun <T> getApiService(domain:String, service:Class<T>) :T{
        return getDefaultRetrofit<T>(domain).create(service)
    }

}