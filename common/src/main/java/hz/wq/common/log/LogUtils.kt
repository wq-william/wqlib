package hz.wq.common.log

import android.util.Log
import com.blankj.utilcode.util.AppUtils


/**
 * 描述: 日志工具类
 */
object LogUtils {

    /**
     * 是否是安卓Log
     */
    var isAndroidLog: Boolean = true

    fun String.wqLog() {
        if (isAndroidLog) {
            if (AppUtils.isAppDebug()) {
                var clickStr = getClickStr(4)
                Log.i("wq", "$clickStr $this")
            }
        } else {
            var clickStr = getClickStr(3)
            println("$clickStr $this ")
        }
    }

    fun getClickStr(stackTraceIndex: Int): String {
        val stackTrace = Thread.currentThread().stackTrace
        val previousMethod = stackTrace[stackTraceIndex]
        var clickStr = "wqLog.(${previousMethod.fileName}:${previousMethod.lineNumber})"
        return clickStr
    }

}
