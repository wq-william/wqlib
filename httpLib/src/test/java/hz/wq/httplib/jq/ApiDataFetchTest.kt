package hz.wq.httplib.jq

import com.blankj.utilcode.util.EncryptUtils
import com.google.gson.Gson
import hz.wq.common.util.log.LogUtils
import hz.wq.common.util.log.LogUtils.wqLog
import hz.wq.httplib.bean.ApiResponse
import hz.wq.httplib.interfaces.IDataProcessing
import hz.wq.httplib.utils.Base64
import hz.wq.httplib.helper.HttpHelper
import hz.wq.httplib.helper.HttpLogCollector
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.RequestBody
import org.junit.Test
import retrofit2.http.Body
import retrofit2.http.Field
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

    @FormUrlEncoded
    @POST("/emc/IRz")
    suspend fun deviceAuth(@Field("pack") pack: String): String

    /**
     * 登录
     */
    @POST("/deviceauth/api/user/v1/login")
    suspend fun login(@Body body: RequestBody): ApiResponse<LoginResultBean>

    @POST("/api/cc/data/add")
    suspend fun addOne(@Body body: RequestBody): String
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

public class ApiDataFetchTest {

    init {
        LogUtils.isAndroidLog = false

        GlobalScope.launch {
            HttpLogCollector.logRequestFlow.collect{
                it.getLogString(", ").wqLog()
            }
        }
        GlobalScope.launch {
            HttpLogCollector.logResponseFlow.collect{
                it.getLogString(", ").wqLog()
            }
        }

    }
    @Test
    fun fetchData_Test_FormUrlEncoded() = runTest {
        launch {
            val apiService = HttpHelper.getApiService(domain, ApiService::class.java)
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
            val apiService = HttpHelper.getApiService(domain, ApiService::class.java)
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
            val apiService =
                HttpHelper.getApiService(domain, ApiService::class.java, headMap, processingData)

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
            var result = HttpHelper.sendApi(
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

    @Test
    fun fetchData_Test_sendApi_login_java() {
        GlobalScope.launch {

            val paramBean = LoginParamBean(
                "18268020591", "12345678",
                imei = "A10000126F0C1B",
                devid = "F68C6240599",
                appVersion = "0.0.1",
            )
            val json = Gson().toJson(paramBean)
            val body = RequestBody.create(MediaType.parse("application/json"), json)
            var result = HttpHelper.sendApi(
                domain,
                ApiService::class.java,
                ApiService::login,
                body,
                headMap
            )
//            var result = apiService.login(body)
            "原始数据：${result}".wqLog()
            "解析完成数据：${result.data}".wqLog()
        }
    }

    private var isEncrypt = false
    private val headMap = mutableMapOf(
        "isEncrypt" to "$isEncrypt"
    )
    private var processingData = object : IDataProcessing {
        val key = "vYjYGUennkefwObVKJI8h15WXpjXk5p5"
        val transformation = "AES/ECB/PKCS5Padding"
        override fun processingBeforeRequest(preSendKey: String?, preSendData: String): String {
            return if (isEncrypt) {
                val b = EncryptUtils.encryptAES(
                    preSendData.toByteArray(),
                    key.toByteArray(),
                    transformation,
                    null
                )
                Base64.encodeToString(b, Base64.DEFAULT)
            } else {
                preSendData
            }
        }

        override fun processingAfterResponse(preResponseData: String): String {
            return if (isEncrypt) {
                val encryptBytes: ByteArray = Base64.decode(preResponseData, Base64.DEFAULT)
                val b =
                    EncryptUtils.decryptAES(encryptBytes, key.toByteArray(), transformation, null)
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

        val encrypt = processingData.processingBeforeRequest("", content)
        "加密后：$encrypt".wqLog()

        val decrypt = processingData.processingAfterResponse(encrypt)
        "解密后：$decrypt".wqLog()

        var s =
            "gJ6RP37QocZ40KAKbfAh5Rp9+ChgkUQzZ3AIBhCMk6CrtJwmgIEPay8/tinpVMqEoRyyrvrM2mZA93g+H9x5ON60Fn3Q7dPkgpFdJIvtO+f0+BRU3wQkqnDu8Yiv2x/2MG44bjMzZU0Fl7KIEfWR35GyhQD/J0b2wB8xYqbpboXIcbmrR6SYCb+CBeo+u/Mr"
        "解密后：${processingData.processingAfterResponse(s)}".wqLog()
        s =
            "CjACj1TepnCuSPMm0H94Thp9+ChgkUQzZ3AIBhCMk6BqSNyQFy69TlyIL+s2CTHenW2Al5nnESYwkQ2zesAoq6JQsrFaUM7p04DX2wUZ839ApXqiDpPktHvBSEF+VQzVAaofUdYW8OBSHoi4j+LyISOTos9JFaq5f4DXD6o3dKg="
        "解密后11：${processingData.processingAfterResponse(s)}".wqLog()
        s =
            "gJ6RP37QocZ40KAKbfAh5Rp9+ChgkUQzZ3AIBhCMk6CrtJwmgIEPay8/tinpVMqEoRyyrvrM2mZA93g+H9x5OCHX0505tPRQVxKVrnCO/3y1mRT9V5Zpg/qEcTnJeulqMG44bjMzZU0Fl7KIEfWR35GyhQD/J0b2wB8xYqbpboXIcbmrR6SYCb+CBeo+u/Mr"
        "解密后22：${processingData.processingAfterResponse(s)}".wqLog()
        s =
            "gJ6RP37QocZ40KAKbfAh5Rp9+ChgkUQzZ3AIBhCMk6CrtJwmgIEPay8/tinpVMqEoRyyrvrM2mZA93g+H9x5OCHX0505tPRQVxKVrnCO/3yhw7BvfT5bVWAmltSwoGbTMG44bjMzZU0Fl7KIEfWR35GyhQD/J0b2wB8xYqbpboXIcbmrR6SYCb+CBeo+u/Mr"
        "解密后33：${processingData.processingAfterResponse(s)}".wqLog()
    }

    @Test
    fun fetchData_Test_sendApi_login陈豪() = runTest {

        launch {
            val paramBean = LoginParamBean(
                "17572696697", "123456",
                imei = "354738106513820",
                devid = "F38C7100486",
                appVersion = "0.0.1",
            )
            val json = Gson().toJson(paramBean)

            val body = RequestBody.create(MediaType.parse("application/json"), json)
            var result = HttpHelper.sendApi(
                domain = domain,
                service = ApiService::class.java,
                serviceFunction = ApiService::login,
                param = body,
                headMap = headMap,
                dataProcessing = if (isEncrypt) processingData else null
            )
//            var result = apiService.login(body)
//            "原始数据：${result}".wqLog()
            "解析完成数据：${result.data}".wqLog()
        }
    }

    @Test
    fun fetchData_Test_sendApi_设备认证() = runTest {

        "fetchData_Test_sendApi_设备认证".wqLog()
        launch {

            val pack =
                "gkDqoB%2BIMsGQO9soNi8sZZO5e2UttYj0xX37LVY%2BBDpXz97QgFQKOO9G8rb3%20IrWS1JQAYubA7zvIcRULuKpPc7VsfNzwThRcQC7PS0fv01Hq6Oipp%2F3o3tF2%20uApxrvyU3OkB5Dj2bulB9afctPwGjXqypC9CS7PHMv%2FCutcLu7lWTX243qWG%20WhAG5Tm8a0Px2uDKJc85dH4tpYSiqBWm18nN8SmcVxmB"

            val apiService =
                HttpHelper.getApiService("http://xfdz-test.tpddns.cn:8082", ApiService::class.java)
            var result = apiService.deviceAuth(pack)
//            var result = apiService.login(body)
//            "原始数据：${result}".wqLog()
            "result：${result}".wqLog()
        }
    }

    @Test
    fun fetchData_Test_sendApi_成测() = runTest {

        "fetchData_Test_sendApi_成测".wqLog()
        launch {

            val pack =
                "gkDqoB%2BIMsGQO9soNi8sZZO5e2UttYj0xX37LVY%2BBDpXz97QgFQKOO9G8rb3%20IrWS1JQAYubA7zvIcRULuKpPc7VsfNzwThRcQC7PS0fv01Hq6Oipp%2F3o3tF2%20uApxrvyU3OkB5Dj2bulB9afctPwGjXqypC9CS7PHMv%2FCutcLu7lWTX243qWG%20WhAG5Tm8a0Px2uDKJc85dH4tpYSiqBWm18nN8SmcVxmB"

            val apiService = HttpHelper.getApiService(
                domain = "http://47.96.65.102:8098",
                service = ApiService::class.java,
                isNeedAllLog = false,
                connectTimeout = 10,
                readTimeout = 10,
                writeTimeout = 10,
            )
//            val apiService = HttpUtil.getApiService("http://47.96.65.102:809", ApiService::class.java)


            val body = RequestBody.create(MediaType.parse("application/json"), pack)
            try {
                var result = apiService.addOne(body)
//            var result = apiService.login(body)
//            "原始数据：${result}".wqLog()
                "result：${result}".wqLog()
            } catch (e: Exception) {
                "e before".wqLog()
                e.printStackTrace()
                "e after".wqLog()
            }
        }
    }

}