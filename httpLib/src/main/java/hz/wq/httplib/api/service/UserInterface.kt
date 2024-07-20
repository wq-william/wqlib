package hz.wq.httplib.api.service

import hz.wq.httplib.bean.ApiResponse
import hz.wq.httplib.api.param.UpdatePasswordBean
import hz.wq.httplib.api.param.UpdateUserBean
import hz.wq.httplib.api.result.UserBean
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserInterface {
    @POST("/register")
    suspend fun register(@Query("userName") userName: String, @Query("password") password: String): ApiResponse<String>

    @POST("/login")
    suspend fun login(@Query("userName") userName: String, @Query("password") password: String): ApiResponse<String>

    @GET("/getUserInfo")
    suspend fun getUserInfo(): ApiResponse<UserBean>

    @PUT("/updateUserInfo")
    suspend fun updateUserInfo(@Body param: UpdateUserBean): ApiResponse<String>


    @PATCH("/updateUserInfoAvatarUrl")
    suspend fun updateUserInfoAvatarUrl(@Query("avatarUrl") avatarUrl: String): ApiResponse<String>

    @PATCH("/updateUserInfoPassword")
    suspend fun updateUserInfoPassword(@Body param: UpdatePasswordBean): ApiResponse<String>
}
