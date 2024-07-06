package hz.wq.lib.testCompose

import androidx.compose.runtime.Composable
import hz.wq.composelib.bottomTab.BottomTabScreen
import hz.wq.composelib.bottomTab.testBottomTab
import hz.wq.composelib.topTab.HomeTabBar
import hz.wq.lib.theme.TestGradle85Theme

/**
 * 创建者: W~Q
 */
@Composable
fun TestBottomTab() {
    TestGradle85Theme {
        val items = listOf(
            testBottomTab(tabName = "test1",contentScreen = { HomeTabBar() }),
            testBottomTab(tabName = "test2"),
            testBottomTab(tabName = "test3"),
            testBottomTab(tabName = "test4"),
        )
        BottomTabScreen(items)
    }
//    MaterialTheme {
//        MainScreen()
//    }
}
