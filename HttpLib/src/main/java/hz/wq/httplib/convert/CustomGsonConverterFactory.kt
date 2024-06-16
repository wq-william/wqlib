package hz.wq.httplib.convert

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class CustomGsonConverterFactory private constructor(private val gson: Gson) : Converter.Factory() {
    companion object {
        fun create(): CustomGsonConverterFactory {
            return create(Gson())
        }

        fun create(gson: Gson): CustomGsonConverterFactory {
            return CustomGsonConverterFactory(gson)
        }
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val adapter: TypeAdapter<*> = gson.getAdapter(TypeToken.get(type))
        return GsonResponseBodyConverter(gson, adapter)
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

//class CustomGsonConverterFactory private constructor(private val gson: Gson) : Converter.Factory() {
//
//    companion object {
//        fun create(): CustomGsonConverterFactory {
//            return create(Gson())
//        }
//
//        fun create(gson: Gson): CustomGsonConverterFactory {
//            return CustomGsonConverterFactory(gson)
//        }
//    }
//
//    override fun responseBodyConverter(
//        type: Type,
//        annotations: Array<Annotation>,
//        retrofit: Retrofit
//    ): Converter<ResponseBody, *>? {
//        val adapter: TypeAdapter<*> = gson.getAdapter(TypeToken.get(type))
//        return GsonResponseBodyConverter(gson, adapter)
//    }
//
//    override fun requestBodyConverter(
//        type: Type,
//        parameterAnnotations: Array<Annotation>,
//        methodAnnotations: Array<Annotation>,
//        retrofit: Retrofit
//    ): Converter<*, RequestBody>? {
//        val adapter: TypeAdapter<*> = gson.getAdapter(TypeToken.get(type))
//        return GsonRequestBodyConverter(gson, adapter)
//    }
//}
//
//class GsonResponseBodyConverter<T>(private val gson: Gson, private val adapter: TypeAdapter<T>) :
//    Converter<ResponseBody, T> {
//    override fun convert(value: ResponseBody): T {
//        value.use { responseBody ->
//            val jsonReader = gson.newJsonReader(responseBody.charStream())
//            return adapter.read(jsonReader)
//        }
//    }
//}
//
//class GsonRequestBodyConverter<T>(private val gson: Gson, private val adapter: TypeAdapter<T>) :
//    Converter<T, RequestBody> {
//    override fun convert(value: T): RequestBody {
//        val buffer = Buffer()
//        val writer = JsonWriter(buffer.outputStream().writer(Charsets.UTF_8))
//        adapter.write(writer, value)
//        writer.close()
//        return RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), buffer.readByteString())
//    }
//}
