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
import com.blankj.utilcode.util.ToastUtils
import hz.wq.common.TestCommon
import hz.wq.common.activity.CommonComposeActivity
import hz.wq.common.util.log.LogUtils.wqLog
import hz.wq.common.viewModel.CommonViewModel
import hz.wq.composelib.common.login.LoginModuleScreen
import hz.wq.composelib.common.login.enums.LoginResult
import hz.wq.lib.testHttp.HttpTest
import hz.wq.lib.viewModel.WqLoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainActivity : CommonComposeActivity() {

    private val viewModel: WqLoginViewModel by viewModels()
    var showDialog = true
    override fun onBackPressed() {
        if (showDialog) {
            showDialog = false
        } else {
            super.onBackPressed()
        }
    }

    @Composable
    override fun ComposeContent() {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        "test log ".wqLog()
        TestCommon.getTestStr().wqLog()

//        CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
//            HttpTest.fetchData_Test_sendApi_login陈豪()
//        }
//        setContent {
//            LoginPage(viewModel)
////            TestBottomTab()
////            TestGradle85Theme {
////                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
////                    Greeting(
////                        name = "Android gradle8.7 app8.5",
////                        modifier = Modifier.padding(innerPadding)
////                    )
////                }
////            }
//        }
//        lifecycleScope.launch {
//            viewModel.shouldFinishActivity.collect {
//                "shouldFinishActivity collect".wqLog()
//                finish()
//            }
//        }
    }
}
