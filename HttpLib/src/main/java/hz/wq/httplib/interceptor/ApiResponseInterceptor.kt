package hz.wq.httplib.interceptor

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import hz.wq.httplib.bean.ApiResponse
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.ResponseBody
import okhttp3.Response

class ApiResponseInterceptor : Interceptor {

    val networkCodeErrorCode = -11001 //网络错误
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val originalResponse = chain.proceed(chain.request())
            val responseBody = originalResponse.body()
            val mediaType = responseBody?.contentType()

            // 获取原始响应体内容
            val content = responseBody?.string()
            val gson = Gson()
            var apiResponse: ApiResponse<Any>

            try {
                // 尝试解析为 JSON
                val jsonElement = JsonParser().parse(content)
                val type = object : TypeToken<ApiResponse<Any>>() {}.type
                apiResponse = gson.fromJson(jsonElement, type)
            } catch (e: Exception) {
                e.printStackTrace()
                // 如果解析失败，则认为是非 JSON 响应（如 HTML）
                apiResponse = ApiResponse(
                    code = "unknown",
                    message = "unknown",
                    data = content as Any,
                )
            }


            // 创建包含 HTTP 详细信息的 ApiResponse
            val detailedResponse = apiResponse.copy(
                httpStatusCode = originalResponse.code(),
                httpMessage = originalResponse.message(),
                httpHeaders = originalResponse.headers().toMultimap(),
                httpRawContent = content  // 设置原始响应体内容
            )

            // 将修改后的响应对象转换回 JSON
            val modifiedContent = gson.toJson(detailedResponse)
            val modifiedResponseBody = ResponseBody.create(mediaType, modifiedContent)

            return originalResponse.newBuilder()
                .body(modifiedResponseBody)
                .build()
        } catch (e: Exception) {
//            e.printStackTrace()
            // 处理网络错误
            val errorResponse = ApiResponse<Any>(
                code = "$networkCodeErrorCode",
                message = e.message ?: "Unknown network error",
                data = null,
                httpStatusCode = networkCodeErrorCode,
                httpMessage = e.message ?: "Unknown network error",
                httpHeaders = emptyMap(),
                httpRawContent = e.message ?: "Unknown network error"
            )
            val gson = Gson()
//            val mediaType = "application/json".toMediaTypeOrNull()
            val errorContent = gson.toJson(errorResponse)
            val errorResponseBody = ResponseBody.create(MediaType.parse("application/json"), errorContent)

            return Response.Builder()
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .code(200) // 自定义一个错误状态码
                .message(e.message ?: "Unknown network error",)
                .body(errorResponseBody)
                .build()
        }
    }
}