package hz.wq.httplib.interceptor

import hz.wq.httplib.utils.wqLog
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import java.io.IOException

class WqHttpLogInterceptor(private val isNeedAllLog: Boolean ) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        // 打印请求信息
        printRequestInfo(request)
        val originalResponse = chain.proceed(request)

        // 缓存响应体以打印内容（注意：这可能会消耗响应体流，因此你需要决定是否这样做）
        val responseBodyString = originalResponse.body()?.string()
        val newBody = responseBodyString?.let { ResponseBody.create(originalResponse.body()!!.contentType(), it) }

        // 打印响应信息
        printResponseInfo(originalResponse, responseBodyString)

        // 返回新的响应（如果修改了响应体）
        return originalResponse.newBuilder().body(newBody).build()
    }

    private fun printRequestInfo(request: okhttp3.Request) {
        "WqApi ----------------------------------------------------------------------------------------------------------------------------------------------------------------------".wqLog()
        "WqApi 请求 Url：${request.url()}".wqLog()
        if (isNeedAllLog) {
            "WqApi 请求 Method：${request.method()}".wqLog()
            "WqApi 请求 Headers：${request.headers()}".wqLog()
        }
        request.url().queryParameterNames()?.forEach { paramName ->
            "WqApi 请求参数 Parameter：${paramName}:${request.url().queryParameter(paramName)}".wqLog()
        }

        // 尝试打印请求体
        request.body()?.let { body ->
            try {
                val buffer = Buffer()
                body.writeTo(buffer)

                "WqApi 请求参数 body：${buffer.readUtf8()}".wqLog()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (isNeedAllLog) {
            "WqApi --- End of Request ---".wqLog()
        }
    }

    private fun printResponseInfo(response: Response, responseBodyString: String?) {
        if (isNeedAllLog) {
            "WqApi response.code()：${response.code()}".wqLog()
            "WqApi response.headers()：${response.headers()}".wqLog()
        }
        responseBodyString?.let {
            "WqApi response body: $it".wqLog()
        }
        "WqApi ----------------------------------------------------------------------------------------------------------------------------------------------------------------------".wqLog()
    }
}