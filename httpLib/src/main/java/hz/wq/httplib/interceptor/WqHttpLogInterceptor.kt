package hz.wq.httplib.interceptor

import hz.wq.common.util.log.LogUtils.wqLog
import hz.wq.httplib.bean.LogRequestBean
import hz.wq.httplib.bean.LogResponseBean
import hz.wq.httplib.helper.HttpLogCollector
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import retrofit2.HttpException
import java.io.IOException

class WqHttpLogInterceptor(private val isNeedAllLog: Boolean) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        // 打印请求信息
        printRequestInfo(request)

        val originalResponse =
            try {
                chain.proceed(request)
            } catch (e: Exception) {
                "http 访问异常：$e".wqLog()
                throw e
            }
        // 缓存响应体以打印内容（注意：这可能会消耗响应体流，因此你需要决定是否这样做）
        val responseBodyString = originalResponse.body()?.string()
        // 打印响应信息
        printResponseInfo(request, originalResponse, responseBodyString)

        val newBody = responseBodyString?.let { ResponseBody.create(originalResponse.body()!!.contentType(), it) }
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
        val params = mutableMapOf<String, String?>()
        request.url().queryParameterNames()?.forEach { paramName ->
            "WqApi 请求参数 Parameter：${paramName}:${request.url().queryParameter(paramName)}".wqLog()
            params[paramName] = request.url().queryParameter(paramName)
        }

        var bodyString: String? = null
        // 尝试打印请求体
        request.body()?.let { body ->
            try {
                val buffer = Buffer()
                body.writeTo(buffer)

                bodyString = buffer.readUtf8()
                "WqApi 请求参数 body：${bodyString}".wqLog()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        //RequestLog收集
//        runBlocking{
        GlobalScope.launch {
            HttpLogCollector.request(
                LogRequestBean(
                    url = request.url().toString(),
                    method = request.method(),
                    heads = request.headers().toMultimap(),
                    params = params,
                    body = bodyString,
                )
            )
        }

        if (isNeedAllLog) {
            "WqApi --- End of Request ---".wqLog()
        }
    }

    private fun printResponseInfo(request: okhttp3.Request, response: Response, responseBodyString: String?) {
        if (isNeedAllLog) {
            "WqApi response.code()：${response.code()}".wqLog()
            "WqApi response.headers()：${response.headers()}".wqLog()
        }
        if (!response.isSuccessful) {
            var error = when (response.code()) {
                in 400..499 -> "Client error , code: ${response.code()} ,msg: ${response.message()}"
                in 500..599 -> "Server error , code: ${response.code()} ,msg: ${response.message()}"
                else -> "response error , code: ${response.code()} ,msg: ${response.message()}"
            }
            error.wqLog()
        }

        responseBodyString?.let {
            "WqApi response body: $it".wqLog()
        }
        //RequestLog收集
//        runBlocking{
        GlobalScope.launch {
            HttpLogCollector.response(
                LogResponseBean(
                    url = request.url().toString(),
                    code = response.code(),
                    message = response.message(),
                    heads = response.headers().toMultimap(),
                    body = responseBodyString,
                )
            )
        }
        "WqApi ----------------------------------------------------------------------------------------------------------------------------------------------------------------------".wqLog()
    }
}