package hz.wq.httplib.wqweb

import hz.wq.common.log.LogUtils
import hz.wq.common.log.LogUtils.wqLog
import hz.wq.httplib.bean.ApiResponse
import hz.wq.httplib.utils.HttpUtil
import hz.wq.httplib.wqweb.bean.param.UpdatePassword
import hz.wq.httplib.wqweb.bean.param.UpdateUserBean
import hz.wq.httplib.wqweb.bean.result.UserBean
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

class UserTest {
    init {
        LogUtils.isAndroidLog = false
    }

    fun <T> ApiResponse<T>.isSuccess(): Boolean {
        return code == "1"
    }

    val wqWebDoMain = "http://127.0.0.1"
    val userName = "wq123456"
    val password = "123456"

    var tokenStr = ""

    interface ApiWqWebService {
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
        suspend fun updateUserInfoPassword(@Body param: UpdatePassword): ApiResponse<String>
    }

    init {
        LogUtils.isAndroidLog = false
    }

    @Test
    fun 注册() = runTest {
        try {
            val apiService = HttpUtil.getApiService(wqWebDoMain, ApiWqWebService::class.java)
            var result = apiService.register(userName, password)
            result.toString().wqLog()
            result.httpResponse.getResponseString().wqLog()
            assert(true)
        } catch (e: Exception) {
            e.printStackTrace()
            assert(false)
        }
    }

    @Test
    fun 登录() = runTest {
        try {
            val apiService = HttpUtil.getApiService(wqWebDoMain, ApiWqWebService::class.java)
            var result = apiService.login(userName, password)
            result.toString().wqLog()
            result.httpResponse.getResponseString().wqLog()
            result.data?.let {
                tokenStr = it
            }

            assert(result.isSuccess())
        } catch (e: Exception) {
            e.printStackTrace()
            assert(false)
        }
    }

    private suspend fun getTokenStr(): String {
        val apiLoginService = HttpUtil.getApiService(wqWebDoMain, ApiWqWebService::class.java)
        var result = apiLoginService.login(userName, password)
        result.toString().wqLog()
        result.httpResponse.getResponseString().wqLog()
        if (result.isSuccess() && result.data != null) {
            return result.data!!
        }
        throw Exception("获取token异常")
    }

    suspend fun getHeadMap(): Map<String, String> {
        val headMap = mapOf("Authorization" to getTokenStr())
        return headMap
    }

    @Test
    fun 获取用户信息() = runTest {
        try {
            val userApi = HttpUtil.getApiService(wqWebDoMain, ApiWqWebService::class.java, getHeadMap())
            val userBeanApiResponse = userApi.getUserInfo()
            userBeanApiResponse.toString().wqLog()
            userBeanApiResponse.httpResponse.getResponseString().wqLog()
            if (userBeanApiResponse.isSuccess()) {
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
    fun 更新用户信息() = runTest {
        try {
            val userApi = HttpUtil.getApiService(wqWebDoMain, ApiWqWebService::class.java, getHeadMap())
            val result = userApi.updateUserInfo(UpdateUserBean("415906626@qq.com", "111222333"))
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
    fun 更新用户头像() = runTest {
        try {
            val userApi = HttpUtil.getApiService(wqWebDoMain, ApiWqWebService::class.java, getHeadMap())
            val result = userApi.updateUserInfoAvatarUrl("https://unitTest")
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
    fun 更新用户密码() = runTest {
        try {
            val userApi = HttpUtil.getApiService(wqWebDoMain, ApiWqWebService::class.java, getHeadMap())
            val result = userApi.updateUserInfoPassword(
                UpdatePassword(
                    password,
                    password,
                    password,
                )
            )
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