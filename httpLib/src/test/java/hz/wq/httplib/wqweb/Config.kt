package hz.wq.httplib.wqweb

import hz.wq.common.log.LogUtils
import hz.wq.httplib.bean.ApiResponse

object Config {
    init {
        LogUtils.isAndroidLog = false
    }

    val wqWebDoMain = "http://127.0.0.1"
    val userName = "wq123456"
    val password = "123456"

    var tokenStr = ""


    fun <T> ApiResponse<T>.isSuccess(): Boolean {
        return code == "1"
    }
}