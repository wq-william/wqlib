package hz.wq.httplib.convert

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import hz.wq.httplib.utils.wqLog
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class CustomStringConverterFactory private constructor(private val gson: Gson) : Converter.Factory() {
    companion object {
        fun create(): CustomStringConverterFactory {
            return create(Gson())
        }

        fun create(gson: Gson): CustomStringConverterFactory {
            return CustomStringConverterFactory(gson)
        }
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        if (type == String::class.java) {
            "CustomStringConverterFactory String".wqLog()
            val adapter: TypeAdapter<*> = gson.getAdapter(TypeToken.get(type))
            return GsonResponseBodyConverter(gson, adapter)
        } else {
            return null
        }
    }

    private class GsonResponseBodyConverter<T>(
        private val gson: Gson,
        private val adapter: TypeAdapter<T>
    ) : Converter<ResponseBody, T> {
        override fun convert(value: ResponseBody): T? {
            value.use {
                val jsonReader = gson.newJsonReader(it.charStream())
                return adapter.read(jsonReader)
            }
        }
    }
}

