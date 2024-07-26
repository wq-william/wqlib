package hz.wq.lib

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import hz.wq.common.TestCommon
import hz.wq.common.activity.CommonActivity
import hz.wq.common.activity.CommonComposeActivity
import hz.wq.common.util.log.LogUtils.wqLog
import hz.wq.common.viewModel.CommonViewModel
import hz.wq.composelib.common.login.LoginModuleScreen
import hz.wq.composelib.common.login.enums.LoginResult
import hz.wq.lib.viewModel.WqLoginViewModel
import javax.inject.Inject

//@AndroidEntryPoint
//class TestActivity : ComponentActivity() {
@AndroidEntryPoint
class TestActivity : TestBaseActivity() {
    private val viewModel: WqLoginViewModel by viewModels()
    @Inject lateinit var a: A

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        LogUtils.i("wq","wq test")
        "test log ".wqLog()
        TestCommon.getTestStr().wqLog()
        "test log ${viewModel}".wqLog()
        a.msg.wqLog()
    }

}

class A @Inject constructor(){
    val msg = "112333"
}
//@AndroidEntryPoint
//class TestActivity : CommonComposeActivity() {
//
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        LogUtils.i("wq","wq test")
//        "test log ".wqLog()
//        TestCommon.getTestStr().wqLog()
//    }
//
//    @Composable
//    override fun ComposeContent() {
//
//
////        TestBottomTab()
//    }
//
//    override fun getViewModel(): CommonViewModel? {
//        return null
//    }
//
//}
