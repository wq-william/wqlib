package hz.wq.httplib.interceptor

import hz.wq.common.log.LogUtils.wqLog
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HeadersInterceptor(private val headMap: Map<String, String>?) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()

        // 创建新的请求构建器，并添加自定义的头部参数
        val requestBuilder: Request.Builder = original.newBuilder().apply {
            headMap?.forEach { (key, value) ->
//                header("isEncrypt", "false")
                header(key, value)
                "添加头信息$key：$value".wqLog()
            }
        }

        // 构建新的请求
        val request: Request = requestBuilder.build()

        // 继续执行请求
        return chain.proceed(request)
    }
}