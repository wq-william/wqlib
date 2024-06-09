package hz.wq.httplib.convert

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import hz.wq.httplib.bean.ApiResponse
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResponseJsonDeserializer<T> : JsonDeserializer<ApiResponse<T>> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ApiResponse<T> {
        val jsonObject = json.asJsonObject

        val code = when {
            jsonObject.has("code") -> jsonObject.get("code").asString
            jsonObject.has("cd") -> jsonObject.get("cd").asString
            jsonObject.has("resultCode") -> jsonObject.get("resultCode").asString
            else -> "unknown"
        }

        val message = when {
            jsonObject.has("msg") -> jsonObject.get("msg").asString
            jsonObject.has("message") -> jsonObject.get("message").asString
            else -> "unknown"
        }
        val httpStatusCode = when {
            jsonObject.has("httpStatusCode") -> jsonObject.get("httpStatusCode").asInt
            else -> -1
        }
        val httpMessage = when {
            jsonObject.has("httpMessage") -> jsonObject.get("httpMessage").asString
            else -> "unknown"
        }
        val headsType = object : TypeToken<Map<String, List<String>>>() {}.type
        val httpHeaders: Map<String, List<String>>? = when {
            jsonObject.has("httpHeaders") -> try {
                Gson().fromJson(jsonObject.get("httpHeaders").toString(), headsType)
            } catch (e: Exception) {
                null
            }

            else -> null
        }
        val httpRawContent = when {
            jsonObject.has("httpRawContent") -> jsonObject.get("httpRawContent").asString
            else -> "unknown"
        }

        val dataElement = jsonObject.get("data") ?: jsonObject.get("result")
        val actualTypeArgument = (typeOfT as ParameterizedType).actualTypeArguments[0]

        val data: T? = if (actualTypeArgument == String::class.java) {
            if (dataElement == null) {
                null
            } else {
                dataElement.toString() as T
            }
        } else {
            context.deserialize(dataElement, (typeOfT as ParameterizedType).actualTypeArguments[0])
        }

        return ApiResponse(code, message, data, httpStatusCode, httpMessage, httpHeaders, httpRawContent)
    }
}
