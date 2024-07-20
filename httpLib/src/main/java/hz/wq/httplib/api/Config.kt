package hz.wq.httplib.api

import hz.wq.common.log.LogUtils
import hz.wq.httplib.bean.ApiResponse

object Config {
    init {
        LogUtils.isAndroidLog = false
    }

//    val wqWebDoMain = "http://127.0.0.1"
//    val wqWebDoMain = "http://localhost"
    val wqWebDoMain = "http://192.168.0.103"
    val userName = "wq123456"
    val password = "123456"

    var tokenStr = ""


    fun <T> ApiResponse<T>.isSuccess(): Boolean {
        return code == "1"
    }
}