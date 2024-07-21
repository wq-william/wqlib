package hz.wq.common

import hz.wq.common.util.log.LogUtils
import hz.wq.common.util.log.LogUtils.wqLog
import kotlinx.coroutines.test.runTest
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class LogTest {

    init {
//        LogUtils.isDebug = true
        LogUtils.isAndroidLog = false
    }

    @Test
    fun logTest() = runTest {
        "点击这里跳转".wqLog()
    }
}