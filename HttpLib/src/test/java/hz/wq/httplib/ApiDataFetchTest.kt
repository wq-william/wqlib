package hz.wq.httplib

import com.blankj.utilcode.util.EncryptUtils
import com.google.gson.Gson
import hz.wq.httplib.bean.ApiResponse
import hz.wq.httplib.interfaces.IDataProcessing
import hz.wq.httplib.utils.Base64
import hz.wq.httplib.utils.HttpUtil
import hz.wq.otherlib.wqLog
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.RequestBody
import org.junit.Test
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

val domain = "http://xfdz-test.tpddns.cn:9000"
//val domain = "http://szxuefeng.com"

interface ApiService {
    @FormUrlEncoded
    @POST("/device/api/configure/v2/url")
    suspend fun getAllDomain(@FieldMap map: Map<String, String>): ApiResponse<String>

    @POST("/device/app/api/upgrade/v1/check")
    suspend fun checkUpdateApp(@Body body: RequestBody): ApiResponse<String>

    /**
     * 登录
     */
    @POST("/deviceauth/api/user/v1/login")
    suspend fun login(@Body body: RequestBody): ApiResponse<LoginResultBean>

}

data class CheckUpdateAppParamBean(
    val appVersion: String,
    val deviceType: String = "400000",
    val devid: String
)

data class LoginParamBean(
    var phone: String,//手机号
    var password: String,//密码

    var imei: String = "getIMEI()",//IMEI
    var devid: String = "",//起爆设备编号
    var appVersion: String = "AppUtils.getAppVersionName()"//软件版本
) {
    val deviceType: String = "400000"//设备类型代码（400000=起爆设备-普通型；410000=起爆设备-煤许型）
    val initiator: String = ""//控制卡号
}

data class LoginResultBean(
    val factory: Boolean?,
    val idcard: String?,
    val mbdzlgUrl: String?,
    val name: String?,
    val phone: String?,
    val upgradeUrl: String?,
    val validity: Double?
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

    @Test
    fun fetchData_Test_login() = runTest {

        launch {
            val headMap = mutableMapOf(
                "isEncrypt" to "true"
            )
            val apiService = HttpUtil.getApiService(domain, ApiService::class.java, headMap)

            val paramBean = LoginParamBean(
                "18268020591", "12345678",
                imei = "A10000126F0C1B",
                devid = "F68C6240599",
                appVersion = "0.0.1",
            )
            val json = Gson().toJson(paramBean)
            val body = RequestBody.create(MediaType.parse("application/json"), json)
            var result = apiService.login(body)
            "原始数据：${result}".wqLog()
            "解析完成数据：${result.data}".wqLog()
        }
    }

    @Test
    fun fetchData_Test_sendApi_login() = runTest {

        launch {
            val paramBean = LoginParamBean(
                "18268020591", "12345678",
                imei = "A10000126F0C1B",
                devid = "F68C6240599",
                appVersion = "0.0.1",
            )
            val json = Gson().toJson(paramBean)
            val body = RequestBody.create(MediaType.parse("application/json"), json)
            var result = HttpUtil.sendApi(
                domain,
                ApiService::class.java,
                ApiService::login,
                body
            )
//            var result = apiService.login(body)
            "原始数据：${result}".wqLog()
            "解析完成数据：${result.data}".wqLog()
        }
    }

    private var isEncrypt = true
    private val headMap = mutableMapOf(
        "isEncrypt" to "$isEncrypt"
    )
    private var proData = object : IDataProcessing {
        val key = "vYjYGUennkefwObVKJI8h15WXpjXk5p5"
        override fun processingBeforeRequest(preSendKey: String?, preSendData: String): String {
            return if (isEncrypt) {
                val b = EncryptUtils.encryptAES(preSendData.toByteArray(), key.toByteArray(), "AES/ECB/PKCS5Padding", null)
                Base64.encodeToString(b, Base64.DEFAULT)
            } else {
                preSendData
            }

        }

        override fun processingAfterResponse(preResponseData: String): String {
            return if (isEncrypt) {
                val encryptBytes: ByteArray = Base64.decode(preResponseData, Base64.DEFAULT)
                val b = EncryptUtils.decryptAES(encryptBytes, key.toByteArray(), "AES/ECB/PKCS5Padding", null)
                String(b);
            } else {
                preResponseData
            }

        }
    }

    @Test
    fun 加密解密Test(): Unit = runBlocking {
//        val key = "jpfYm7u1LDYAGNQ5"
        val map: MutableMap<String, String> = HashMap()
        map["imei"] = "867709023723338"
        map["version"] = "39"

        val content = Gson().toJson(map)
        "加密前：$content".wqLog()

        val encrypt = proData.processingBeforeRequest("", content)
        "加密后：$encrypt".wqLog()

        val decrypt = proData.processingAfterResponse(encrypt)
        "解密后：$decrypt".wqLog()

        var s =
            "gJ6RP37QocZ40KAKbfAh5Rp9+ChgkUQzZ3AIBhCMk6CrtJwmgIEPay8/tinpVMqEoRyyrvrM2mZA93g+H9x5ON60Fn3Q7dPkgpFdJIvtO+f0+BRU3wQkqnDu8Yiv2x/2MG44bjMzZU0Fl7KIEfWR35GyhQD/J0b2wB8xYqbpboXIcbmrR6SYCb+CBeo+u/Mr"
        "解密后：${proData.processingAfterResponse(s)}".wqLog()
        s = "CjACj1TepnCuSPMm0H94Thp9+ChgkUQzZ3AIBhCMk6BqSNyQFy69TlyIL+s2CTHenW2Al5nnESYwkQ2zesAoq6JQsrFaUM7p04DX2wUZ839ApXqiDpPktHvBSEF+VQzVAaofUdYW8OBSHoi4j+LyISOTos9JFaq5f4DXD6o3dKg="
        "解密后11：${proData.processingAfterResponse(s)}".wqLog()
        s =
            "gJ6RP37QocZ40KAKbfAh5Rp9+ChgkUQzZ3AIBhCMk6CrtJwmgIEPay8/tinpVMqEoRyyrvrM2mZA93g+H9x5OCHX0505tPRQVxKVrnCO/3y1mRT9V5Zpg/qEcTnJeulqMG44bjMzZU0Fl7KIEfWR35GyhQD/J0b2wB8xYqbpboXIcbmrR6SYCb+CBeo+u/Mr"
        "解密后22：${proData.processingAfterResponse(s)}".wqLog()
        s =
            "gJ6RP37QocZ40KAKbfAh5Rp9+ChgkUQzZ3AIBhCMk6CrtJwmgIEPay8/tinpVMqEoRyyrvrM2mZA93g+H9x5OCHX0505tPRQVxKVrnCO/3yhw7BvfT5bVWAmltSwoGbTMG44bjMzZU0Fl7KIEfWR35GyhQD/J0b2wB8xYqbpboXIcbmrR6SYCb+CBeo+u/Mr"
        "解密后33：${proData.processingAfterResponse(s)}".wqLog()
    }

    @Test
    fun fetchData_Test_sendApi_login陈豪() = runTest {

        launch {
//            val paramBean = LoginParamBean(
//                "17572696697", "123456",
//                imei = "354738106513820",
//                devid = "F6800240611",
//                appVersion = "0.0.1",
//            )
            val paramBean = LoginParamBean(
                "17572696697", "123456",
                imei = "354738106513820",
                devid = "F38C7100486",
//                devid = "F6800240611",
                appVersion = "0.0.1",
            )
            val json = Gson().toJson(paramBean)

            val body = RequestBody.create(MediaType.parse("application/json"), json)
            var result = HttpUtil.sendApi(
                domain = domain,
                service = ApiService::class.java,
                serviceFunction = ApiService::login,
                param = body,
                headMap = headMap,
                dataProcessing = proData
            )
//            var result = apiService.login(body)
//            "原始数据：${result}".wqLog()
            "解析完成数据：${result.data}".wqLog()
        }
    }


}