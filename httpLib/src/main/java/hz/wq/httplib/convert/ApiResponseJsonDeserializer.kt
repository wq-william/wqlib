package hz.wq.httplib.convert

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import hz.wq.httplib.bean.ApiResponse
import hz.wq.httplib.bean.HttpResponse
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResponseJsonDeserializer<T> : JsonDeserializer<ApiResponse<T>> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ApiResponse<T> {
        val jsonObject = json.asJsonObject

        //http相关数据解析
        val httpStatusCode = when {
            jsonObject.has("httpStatusCode") -> jsonObject.get("httpStatusCode").asInt
            else -> -1
        }
        val httpMessage = when {
            jsonObject.has("httpMessage") -> jsonObject.get("httpMessage").asString
            else -> ""
        }
        val headsType = object : TypeToken<Map<String, List<String>>>() {}.type
        val httpHeaders: Map<String, List<String>>? = when {
            jsonObject.has("httpHeaders") -> try {
                val headsStr = jsonObject.getAsJsonObject("httpHeaders")
                Gson().fromJson<Map<String, List<String>>>(headsStr, headsType)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

            else -> null
        }
        val httpRawContent = when {
            jsonObject.has("httpRawContent") -> jsonObject.get("httpRawContent").asString
            else -> ""
        }

        //正式解析T
        return if (httpRawContent.isNullOrEmpty()) {
            ApiResponse("-11001", "未找到响应数据", null, HttpResponse(httpStatusCode, httpMessage, httpHeaders, httpRawContent))
        } else {
            try {
                val actualTypeArgument = (typeOfT as ParameterizedType).actualTypeArguments[0]

                val rawElement = JsonParser().parse(httpRawContent).asJsonObject

                val code = when {
                    rawElement.has("code") -> rawElement.get("code").asString
                    rawElement.has("cd") -> rawElement.get("cd").asString
                    rawElement.has("resultCode") -> rawElement.get("resultCode").asString
                    else -> ""
                }
                val message = when {
                    rawElement.has("msg") -> rawElement.get("msg").asString
                    rawElement.has("message") -> rawElement.get("message").asString
                    else -> ""
                }
                val dataElement = when {
                    rawElement.has("data") -> rawElement.get("data")
                    rawElement.has("result") -> rawElement.get("result")
                    else -> null
                }
//            val dataStr :String? = dataElement.toString()

                val data: T? = when {
                    dataElement?.isJsonNull == true -> null
                    actualTypeArgument == String::class.java -> dataElement?.asString as T
                    else -> {
                        try {
                            context.deserialize<T>(dataElement, typeOfT.actualTypeArguments[0])
                        } catch (e: Exception) {
                            e.printStackTrace()
                            null
                        }
                    }
                }
                ApiResponse(code, message, data, HttpResponse(httpStatusCode, httpMessage, httpHeaders, httpRawContent))
            } catch (e: Exception) {
                e.printStackTrace()
                ApiResponse("-11002", "解析数据异常", null, HttpResponse(httpStatusCode, httpMessage, httpHeaders, httpRawContent))
            }
        }


    }
}
