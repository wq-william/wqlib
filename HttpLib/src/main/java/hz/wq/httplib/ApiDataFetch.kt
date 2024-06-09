package hz.wq.httplib

import com.google.gson.Gson
import hz.wq.otherlib.wqLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


/**
 * 处理请求
 */
class ApiDataFetch(
    val scope: CoroutineScope,
    var isNeedCallbackToMainThread: Boolean = true  //是否需要返回到主线程
) {

    val gson = Gson()

    /**
     * 发送请求body，获取数据
     */
    fun <T> fetchData(
        json: String,
        apiSuspend: (RequestBody) -> T,
        callback: (T?) -> Unit
    ) {
        scope.launch {
            try {
                try {
                    var str = apiSuspend.toString()
                    str.wqLog()
                    "WqApi 原始发送数据：$json".wqLog()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                val result = withContext(Dispatchers.IO) {
                    val body = RequestBody.create(MediaType.parse("application/json"), json)
                    apiSuspend(body)
                }
                "WqApi 接收数据：$result".wqLog()
                if (isNeedCallbackToMainThread) {
                    withContext(Dispatchers.Main) {
                        callback(result)
                    }
                } else {
                    callback(result)
                }
            } catch (e: Exception) {
                "WqApi 异常信息 ${e.message}".wqLog()
                e.printStackTrace()
//                if (T::class == ApiServiceResultBean::class) {
//                    val result = ApiServiceResultBean(ApiConfig.API_CODE_SERVICE_FAIL, e.message, false, null, null)
//                    callback(result as T)
//                } else if (T::class == String::class) {
//                    callback(e.message.toString() as T)
//                } else {
//                    callback(null)
//                }

            }
        }
    }


    /**
     * 发送请求Field，获取数据
     */
    inline fun <reified T> fetchDataField(
        params: Map<String, String>,
        noinline apiSuspend: suspend (Map<String, String>) -> T,
        noinline callback: (T?) -> Unit
    ) {
        scope.launch {
            try {
                try {
                    "WqApi 原始发送数据：$params".wqLog()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val result = withContext(Dispatchers.IO) {
                    apiSuspend(params)
                }
                "WqApi 接收数据：$result".wqLog()
                if (isNeedCallbackToMainThread) {
                    withContext(Dispatchers.Main) {
                        callback(result)
                    }
                } else {
                    callback(result)
                }
            } catch (e: Exception) {
                "WqApi 异常信息 ${e.message}".wqLog()
                e.printStackTrace()
//                if (T::class == ApiServiceResultBean::class) {
//                    val result = ApiServiceResultBean(ApiConfig.API_CODE_SERVICE_FAIL, e.message, false, null, null)
//                    callback(result as T)
//                } else if (T::class == String::class) {
//                    callback(e.message.toString() as T)
//                } else {
//                    callback(null)
//                }

            }
        }
    }

    /**
     * 发送请求file，获取数据
     */
    inline fun <reified T> fetchDataFile(
        params: Map<String, String>?,
        fileKey: String,
        fileName: String,
        fileContent: String,
        noinline apiSuspend: suspend (RequestBody) -> T,
        noinline callback: (T?) -> Unit
    ) {
        scope.launch {
            try {


                try {
                    "WqApi params：$params".wqLog()
                    "WqApi 原始发送数据 $fileKey：$fileName：$fileContent".wqLog()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val result = withContext(Dispatchers.IO) {
                    val multipartBody = MultipartBody.Builder().run {
                        params?.forEach {
                            addFormDataPart(it.key, it.value)
                        }
                        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), fileContent)
                        addFormDataPart(fileKey, fileName, requestBody)
                    }.build()
                    apiSuspend(multipartBody)
                }
                "WqApi 接收数据：$result".wqLog()
                if (isNeedCallbackToMainThread) {
                    withContext(Dispatchers.Main) {
                        callback(result)
                    }
                } else {
                    callback(result)
                }
            } catch (e: Exception) {
                "WqApi 异常信息 ${e.message}".wqLog()
                e.printStackTrace()
//                if (T::class == ApiServiceResultBean::class) {
//                    val result = ApiServiceResultBean(ApiConfig.API_CODE_SERVICE_FAIL, e.message, false, null, null)
//                    callback(result as T)
//                } else if (T::class == String::class) {
//                    callback(e.message.toString() as T)
//                } else {
//                    callback(null)
//                }

            }
        }
    }


}