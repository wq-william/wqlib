package hz.wq.lib

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import hz.wq.common.TestCommon
import hz.wq.common.activity.CommonComposeActivity
import hz.wq.common.log.LogUtils.wqLog
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

    @Composable
    override fun ComposeContent() {

        LoginModuleScreen(viewModel)
        launch {
            viewModel.loginResult.collect {
                "MainActivity loginResult collect $it".wqLog()
            }
        }
        launch {
            viewModel.loginResult.collect {
                if (it == LoginResult.LoginSuccess) {
                    Toast.makeText(this@MainActivity, "登录成功完成了哟", Toast.LENGTH_SHORT).show()
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

        CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
            HttpTest.fetchData_Test_sendApi_login陈豪()
        }
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
