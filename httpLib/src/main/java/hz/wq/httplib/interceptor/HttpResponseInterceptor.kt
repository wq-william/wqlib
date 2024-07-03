package hz.wq.httplib.interceptor

import com.google.gson.Gson
import hz.wq.httplib.bean.ApiResponse
import hz.wq.httplib.bean.HttpResponse
import okhttp3.Interceptor
import okhttp3.ResponseBody
import okhttp3.Response

/**
 * 解析数据拦截器
 */
class HttpResponseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        val responseBody = originalResponse.body()
        val mediaType = responseBody?.contentType()

        // 获取原始响应体内容
        val content = responseBody?.string()

        // 创建包含 HTTP 详细信息的 ApiResponse
        val httpResponse = HttpResponse(
            httpStatusCode = originalResponse.code(),
            httpMessage = originalResponse.message(),
            httpHeaders = originalResponse.headers().toMultimap(),
            httpRawContent = content  // 设置原始响应体内容
        )

        // 将修改后的响应对象转换回 JSON
        val modifiedContent = Gson().toJson(httpResponse)
        val modifiedResponseBody = ResponseBody.create(mediaType, modifiedContent)
        return originalResponse.newBuilder()
            .body(modifiedResponseBody)
            .build()
    }
}
