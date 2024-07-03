package hz.wq.httplib.interceptor

import hz.wq.common.log.LogUtils.wqLog
import hz.wq.httplib.interfaces.IDataProcessing
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import java.net.URLDecoder

/**
 * 创建者: W~Q
 * 描述: 数据加工拦截器
 */
class DataProcessingInterceptor(private val dataProcessing: IDataProcessing?) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest: Request = interceptRequest(request)
        return doRequest(chain, newRequest)
    }

    /**
     * 拦截请求
     */
    private fun interceptRequest(request: Request): Request {
        val newRequest: Request =
            if (dataProcessing == null) {
                request
            } else if (request.body() is FormBody) {//FormBody方式
                val formBody = request.body() as FormBody
                val formBuilder = FormBody.Builder()


                // 遍历原始表单参数并添加到新的构建器中
                for (i in 0 until formBody.size()) {
                    val originalContent = formBody.encodedValue(i)
                    val decodedString = URLDecoder.decode(originalContent, "UTF-8")
                    "WqApi FormBody 请求数据 (加工前)：$decodedString".wqLog()
                    val afterProcessingContent = dataProcessing.processingBeforeRequest(formBody.encodedName(i), decodedString)
                    "WqApi FormBody 请求数据(加工后)：$afterProcessingContent".wqLog()
                    formBuilder.add(formBody.encodedName(i), afterProcessingContent)
                }
                val newRequestBody = formBuilder.build()
                // 创建新的加密后的请求
                request.newBuilder()
                    .method(request.method(), newRequestBody)
                    .build()

            } else {
                // 雪峰加密请求体
                val originalBody = request.body()
                if (originalBody != null) {
                    val buffer = Buffer()
                    originalBody.writeTo(buffer)
                    val originalContent = buffer.readUtf8()
                    "WqApi RequestBody 请求数据(加工前)：$originalContent".wqLog()
                    val afterProcessingContent = dataProcessing.processingBeforeRequest(null, originalContent)
                    "WqApi RequestBody 请求数据(加工后)：$afterProcessingContent".wqLog()
                    val encryptedRequestBody = RequestBody.create(originalBody.contentType(), afterProcessingContent)
                    // 创建新的加密后的请求
                    request.newBuilder()
                        .method(request.method(), encryptedRequestBody)
                        .build()
                } else {
                    val encryptedRequestBody = RequestBody.create(MediaType.parse("application/json"), "")
                    // 创建新的加密后的请求
                    request.newBuilder()
                        .method(request.method(), encryptedRequestBody)
                        .build()
                }
            }
        return newRequest
    }

    /**
     * 拦截response
     */
    private fun doRequest(chain: Interceptor.Chain, request: Request?): Response {
        val response = chain.proceed(request)
        val decryptedResponseBody: ResponseBody? = interceptResponseBody(response, request)

        // 返回新的响应，包含解密后的响应体
        return response.newBuilder()
            .body(decryptedResponseBody)
            .build()
    }

    /**
     * 拦截responseBody
     */
    private fun interceptResponseBody(response: Response, request: Request?): ResponseBody? {
        // 解密响应体
        val responseBody = response.body()
        return if (dataProcessing == null) {
            responseBody
        } else {
            if (response.isSuccessful && responseBody != null) {
                val original = responseBody!!.string()
                "WqApi response数据(加工前)：$original".wqLog()
                var decryptedContent = dataProcessing.processingAfterResponse(original)
                "WqApi response数据(加工后)：$decryptedContent".wqLog()
                ResponseBody.create(responseBody!!.contentType(), decryptedContent)
            } else {
                responseBody// 如果响应失败或没有响应体，直接返回原始响应体
            }
        }

    }
}