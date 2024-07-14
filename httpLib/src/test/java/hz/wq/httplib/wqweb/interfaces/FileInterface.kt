package hz.wq.httplib.wqweb.interfaces

import hz.wq.httplib.bean.ApiResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface FileInterface {
    /**
     * 上传文件
     */
    @Multipart
    @POST("/upload")
    suspend fun uploadFile(@Part file: MultipartBody.Part): ApiResponse<String>
}