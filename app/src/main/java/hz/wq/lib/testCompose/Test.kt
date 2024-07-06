package hz.wq.lib.testCompose

import androidx.compose.runtime.Composable
import hz.wq.composelib.bottomTab.BottomTabScreen
import hz.wq.composelib.bottomTab.testTab
import hz.wq.lib.theme.TestGradle85Theme

/**
 * 创建者: W~Q
 */
@Composable
fun TestBottomTab() {
    TestGradle85Theme {
        val items = listOf(
            testTab(tabName = "test1"),
            testTab(tabName = "test2"),
            testTab(tabName = "test3"),
            testTab(tabName = "test4"),
        )
        BottomTabScreen(items)
    }
//    MaterialTheme {
//        MainScreen()
//    }
}
