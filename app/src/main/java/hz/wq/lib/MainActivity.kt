package hz.wq.lib

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hz.wq.lib.theme.TestGradle85Theme
import hz.wq.common.TestCommon
import hz.wq.common.log.LogUtils.wqLog
import hz.wq.lib.testCompose.bottomTab.BottomTabScreen
import hz.wq.lib.testCompose.bottomTab.testTab
import hz.wq.lib.testHttp.HttpTest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        "test log ".wqLog()
        TestCommon.getTestStr().wqLog()


        CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
            HttpTest.fetchData_Test_sendApi_login陈豪()
        }
        setContent {
            MyComposeApp()
//            TestGradle85Theme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android gradle8.7 app8.5",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
        }
    }
}

@Composable
fun MyComposeApp() {
    TestGradle85Theme {
        val items = listOf(
            testTab("test1"),
            testTab("test2"),
            testTab("test3"),
            testTab("test4"),
        )
        BottomTabScreen(items)
    }
//    MaterialTheme {
//        MainScreen()
//    }
}

@Preview(showBackground = true)
@Composable
fun MyComposeAppPreview() {
    MyComposeApp()
}

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