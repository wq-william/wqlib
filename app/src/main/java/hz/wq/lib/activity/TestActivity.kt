package hz.wq.lib.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.blankj.utilcode.util.LogUtils
import dagger.hilt.android.AndroidEntryPoint
import hz.wq.common.TestCommon
import hz.wq.common.activity.CommonComposeActivity
import hz.wq.common.util.log.LogUtils.wqLog
import hz.wq.common.viewModel.CommonViewModel
import javax.inject.Inject


class A @Inject constructor() {
    val msg = "112333"
}

@AndroidEntryPoint
class TestActivity : CommonComposeActivity() {
    @Inject
    lateinit var a: A

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        LogUtils.i("wq", "wq test")
        "test log ".wqLog()
        TestCommon.getTestStr().wqLog()
        a.msg.wqLog()
    }

    @Composable
    override fun ComposeContent() {
//        TestBottomTab()
    }

    override fun getViewModel(): CommonViewModel? {
        return null
    }

}
