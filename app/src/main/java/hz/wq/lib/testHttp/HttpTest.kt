package hz.wq.lib.testHttp

import com.blankj.utilcode.util.EncryptUtils
import com.google.gson.Gson
import hz.wq.common.log.LogUtils.wqLog
import hz.wq.httplib.bean.ApiResponse
import hz.wq.httplib.interfaces.IDataProcessing
import hz.wq.httplib.utils.Base64
import hz.wq.httplib.utils.HttpUtil
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

object HttpTest {

    suspend fun fetchData_Test_sendApi_login陈豪() {


        try {
            val paramBean = LoginParamBean(
                "17572696697", "123456",
                imei = "354738106513820",
                devid = "F38C7100486",
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
                dataProcessing = if (isEncrypt) processingData else null
            )
//            var result = apiService.login(body)
//            "原始数据：${result}".wqLog()
            "解析完成数据：${result.data}".wqLog()
        } catch (e: Exception) {
            e.printStackTrace()
        }
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