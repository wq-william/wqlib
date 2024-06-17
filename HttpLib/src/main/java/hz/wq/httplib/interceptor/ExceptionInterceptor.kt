package hz.wq.httplib.interceptor

import com.google.gson.Gson
import hz.wq.httplib.bean.ApiResponse
import hz.wq.httplib.bean.HttpResponse
import hz.wq.httplib.utils.wqLog
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody

/**
 * 异常拦截器
 * 网络异常IO
 */
class ExceptionInterceptor : Interceptor {

    private val networkCodeErrorCode = -11001 //网络错误

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            return chain.proceed(chain.request())
        } catch (e: Exception) {
            e.printStackTrace()
            // 处理网络错误
            val errorResponse = ApiResponse<Any>(
                code = "$networkCodeErrorCode",
                message = "Unknown network error",
                data = null,
                HttpResponse(
                    httpStatusCode = networkCodeErrorCode,
                    httpMessage = "Unknown network error",
                    httpHeaders = emptyMap(),
                    httpRawContent = e.stackTraceToString() ?: "Unknown network error"
                )
            )
            val gson = Gson()
//            val mediaType = "application/json".toMediaTypeOrNull()
            val errorContent = gson.toJson(errorResponse)
            val errorResponseBody = ResponseBody.create(MediaType.parse("application/json"), errorContent)

            "错误返回数据：$errorContent".wqLog()
            return Response.Builder()
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .code(200) // 自定义一个错误状态码
                .message(e.message ?: "Unknown network error")
                .body(errorResponseBody)
                .build()
        }
    }
}