package hz.wq.lib

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hz.wq.common.TestCommon
import hz.wq.common.log.LogUtils.wqLog
import hz.wq.lib.testCompose.TestBottomTab
import hz.wq.lib.testHttp.HttpTest
import hz.wq.lib.theme.TestGradle85Theme
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
            TestBottomTab()
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


@Preview(showBackground = true)
@Composable
fun MyComposeAppPreview() {
    TestBottomTab()
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