package hz.wq.lib

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import hz.wq.common.TestCommon
import hz.wq.common.activity.CommonComposeActivity
import hz.wq.common.util.log.LogUtils.wqLog
import hz.wq.common.viewModel.CommonViewModel
import hz.wq.composelib.common.login.LoginModuleScreen
import hz.wq.composelib.common.login.enums.LoginResult
import hz.wq.lib.viewModel.WqLoginViewModel

//@AndroidEntryPoint
class LoginActivity : CommonComposeActivity() {

    private val viewModel: WqLoginViewModel by viewModels()
//    @Inject
//    lateinit var viewModel: WqLoginViewModel

//        @InjectsewModel: WqLoginViewModel
    private var showDialog = true



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        LogUtils.i("wq","wq test")
        "test log ".wqLog()
        TestCommon.getTestStr().wqLog()
    }
    override fun onBackPressed() {
        if (showDialog) {
            showDialog = false
        } else {
            super.onBackPressed()
        }
    }

    @Composable
    override fun ComposeContent() {
        "查看viewModel地址：${viewModel}".wqLog()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 4.dp), // 这里定义了额外的点击区域
            contentAlignment = Alignment.Center
        ) {
            LoginModuleScreen(viewModel)
        }


        launch {
            viewModel.loginResult.collect {
                "MainActivity loginResult collect $it".wqLog()
            }
        }
        launch {
            viewModel.loginResult.collect {
                if (it == LoginResult.LoginSuccess) {
//                    ToastUtils.make().setMode(ToastUtils.MODE.DARK)//.show("登录成功完成了哟1111")

                    ToastUtils.showShort("登录成功完成了哟")
//                    Toast.makeText(this@MainActivity, "登录成功完成了哟", Toast.LENGTH_SHORT).show()
                }
            }
        }


//        TestBottomTab()
    }

    override fun getViewModel(): CommonViewModel? {
        return viewModel
    }

}
