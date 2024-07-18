package hz.wq.lib

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import hz.wq.common.TestCommon
import hz.wq.common.activity.CommonComposeActivity
import hz.wq.common.log.LogUtils.wqLog
import hz.wq.common.viewModel.CommonViewModel
import hz.wq.composelib.common.login.LoginPage
import hz.wq.composelib.common.login.viewModel.LoginViewModel

//class MainActivity : CommonActivityCompose() {
//    @Composable
//    override fun ComposeContent() {
//        TODO("Not yet implemented")
//    }
//
//    override fun getViewModel(): CommonViewModel? {
//        TODO("Not yet implemented")
//    }
//}
class MainActivity : CommonComposeActivity() {

    private val viewModel: LoginViewModel by viewModels()

    @Composable
    override fun ComposeContent(){
        LoginPage(viewModel)
    }

    override fun getViewModel(): CommonViewModel?{
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


//@Preview(showBackground = true)
//@Composable
//fun MyComposeAppPreview() {
//    TestBottomTab()
//}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    TestGradle85Theme {
//        Greeting("Android")
//    }
//}