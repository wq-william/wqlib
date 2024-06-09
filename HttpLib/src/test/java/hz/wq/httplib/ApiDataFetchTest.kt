package hz.wq.httplib

import com.google.gson.Gson
import hz.wq.httplib.bean.ApiResponse
import hz.wq.httplib.utils.HttpUtil
import hz.wq.otherlib.wqLog
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.RequestBody
import org.junit.Test
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

//val domain = "http://xfdz-test.tpddns.cn:9000"
val domain = "http://szxuefeng.com"

interface ApiService {
    @FormUrlEncoded
    @POST("/device/api/configure/v2/url")
    suspend fun getAllDomain(@FieldMap map: Map<String, String>): ApiResponse<String>
    @POST("/deviceapp/api/upgrade/v1/check")
    suspend fun checkUpdateApp(@Body body: RequestBody): ApiResponse<String>
}
data class CheckUpdateAppParamBean(
    val appVersion: String,
    val deviceType: String="400000",
    val devid: String
)
class ApiDataFetchTest {
    @Test
    fun fetchData_Test_FormUrlEncoded() = runTest {

        launch {
            val apiService = HttpUtil.getApiService(domain, ApiService::class.java)
            val map = mapOf(
                "envCode" to "2",//（0=正式环境；1=测试环境内网；2=测试环境外网
                "deviceType" to "400000"
            )
            var result = apiService.getAllDomain(map)
            "测试result：$result".wqLog()
        }
    }
    @Test
    fun fetchData_Test_Body() = runTest {

        launch {
            val apiService = HttpUtil.getApiService(domain, ApiService::class.java)
            val map = mapOf(
                "envCode" to "2",//（0=正式环境；1=测试环境内网；2=测试环境外网
                "deviceType" to "400000"
            )

            val paramBean = CheckUpdateAppParamBean(
                devid = "F68C6240599",
                appVersion = "0.0.1",
            )
            val json = Gson().toJson(paramBean)
            val body = RequestBody.create(MediaType.parse("application/json"), json)
            var result = apiService.checkUpdateApp(body)
            "测试result：$result".wqLog()
        }
    }
}