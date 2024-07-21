package hz.wq.httplib.convert

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import hz.wq.common.util.log.LogUtils.wqLog
import hz.wq.httplib.bean.ApiResponse
import hz.wq.httplib.bean.HttpResponse
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResponseStringDeserializer : JsonDeserializer<String> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): String {
        "ApiResponseStringDeserializer".wqLog()
        val jsonObject = json.asJsonObject
        val httpRawContent = when {
            jsonObject.has("httpRawContent") -> jsonObject.get("httpRawContent").asString
            else -> ""
        }
        "ApiResponseStringDeserializer：$httpRawContent".wqLog()
        return httpRawContent
    }
}
