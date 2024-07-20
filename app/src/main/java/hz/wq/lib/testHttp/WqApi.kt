package hz.wq.lib.testHttp

import hz.wq.httplib.bean.ApiResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface WqApi {

    /**
     * 登录
     */
    @POST("/login")
    suspend fun login(@Body body: RequestBody): ApiResponse<LoginResultBean>

}