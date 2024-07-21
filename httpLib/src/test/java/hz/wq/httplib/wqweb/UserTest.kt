package hz.wq.httplib.wqweb

import hz.wq.common.util.log.LogUtils.wqLog
import hz.wq.httplib.api.service.UserInterface
import hz.wq.httplib.helper.HttpHelper
import hz.wq.httplib.api.param.UpdatePasswordBean
import hz.wq.httplib.api.param.UpdateUserBean
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UserTest {


    @Test
    fun 注册() = runTest {
        try {
            val apiService = HttpHelper.getApiService(wqWebDoMain, UserInterface::class.java)
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
            val apiService = HttpHelper.getApiService(wqWebDoMain, UserInterface::class.java)
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
        val apiLoginService = HttpHelper.getApiService(wqWebDoMain, UserInterface::class.java)
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
            val userApi = HttpHelper.getApiService(wqWebDoMain, UserInterface::class.java, getHeadMap())
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
            val userApi = HttpHelper.getApiService(wqWebDoMain, UserInterface::class.java, getHeadMap())
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
            val userApi = HttpHelper.getApiService(wqWebDoMain, UserInterface::class.java, getHeadMap())
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
            val userApi = HttpHelper.getApiService(wqWebDoMain, UserInterface::class.java, getHeadMap())
            val result = userApi.updateUserInfoPassword(
                UpdatePasswordBean(
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