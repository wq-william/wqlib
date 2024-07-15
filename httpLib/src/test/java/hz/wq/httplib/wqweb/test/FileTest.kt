package hz.wq.httplib.wqweb.test

import hz.wq.common.log.LogUtils.wqLog
import hz.wq.httplib.helper.HttpHelper
import hz.wq.httplib.wqweb.Config.isSuccess
import hz.wq.httplib.wqweb.Config.wqWebDoMain
import hz.wq.httplib.wqweb.interfaces.FileInterface
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.Buffer
import okio.BufferedSink
import org.junit.Test
import java.io.File

class FileTest {
    class ProgressRequestBody(private val delegate: RequestBody, private val progressListener: ProgressListener) : RequestBody() {

        override fun contentType(): MediaType? {
            return delegate.contentType()
        }

        override fun contentLength(): Long {
            return delegate.contentLength()
        }


        override fun writeTo(sink: BufferedSink) {
            val buffer = Buffer()
            delegate.writeTo(buffer)

            val totalBytesRead = buffer.size()
            sink.write(buffer, totalBytesRead)

            progressListener.onProgress(totalBytesRead, contentLength())
        }

        interface ProgressListener {
            fun onProgress(bytesWritten: Long, contentLength: Long)
        }
    }

    @Test
    fun 上传文件_有进度() = runTest {
        try {
            val file = File("C:\\Users\\Administrator\\Desktop\\aaabbb.txt")
            val requestFile = ProgressRequestBody(RequestBody.create(MediaType.parse("*/*"), file), object : ProgressRequestBody.ProgressListener {
                override fun onProgress(bytesWritten: Long, contentLength: Long) {
                    "bytesWritten：$bytesWritten".wqLog()
                    "contentLength：$contentLength".wqLog()
                    // 更新UI显示上传进度
                    val progress = (bytesWritten * 100 / contentLength).toInt()
                    "上传进度：$progress%".wqLog()
                }
            })
            val multipartBody = MultipartBody.Part.createFormData("file", file.name, requestFile)
            val api = HttpHelper.getApiService(wqWebDoMain, FileInterface::class.java, UserTest().getHeadMap())
            var result = api.uploadFile(multipartBody)
            result.toString().wqLog()
            result.httpResponse.getResponseString().wqLog()
            if (result.isSuccess()) {
                assert(true)
            } else {
                assert(false)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            assert(false)
        }
    }

    @Test
    fun 上传文件() = runTest {
        try {
            val file = File("C:\\Users\\Administrator\\Desktop\\aaabbb.txt")
            val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
            val multipartBody = MultipartBody.Part.createFormData("file", file.name, requestBody)
            val api = HttpHelper.getApiService(wqWebDoMain, FileInterface::class.java, UserTest().getHeadMap())
            var result = api.uploadFile(multipartBody)
            result.toString().wqLog()
            result.httpResponse.getResponseString().wqLog()
            if (result.isSuccess()) {
                assert(true)
            } else {
                assert(false)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            assert(false)
        }
    }
}