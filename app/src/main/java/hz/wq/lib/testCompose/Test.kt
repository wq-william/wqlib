package hz.wq.lib.testCompose

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import hz.wq.common.util.log.LogUtils.wqLog
import hz.wq.composelib.common.bottomTab.BottomTabScreen
import hz.wq.composelib.common.bottomTab.testBottomTab
import hz.wq.composelib.common.topTab.TabPage
import hz.wq.composelib.common.topTab.TopTab
import hz.wq.composelib.common.topTab.testTopTab
import hz.wq.lib.theme.TestGradle85Theme

/**
 * 创建者: W~Q
 */
@Composable
fun TestBottomTab() {
    TestGradle85Theme {
        var selectedTabIndexState = remember { mutableIntStateOf(0) }
        val index = selectedTabIndexState.value
        val topTabs = listOf(
            TopTab(
                name = "testT1",
                contentBgColor = if (index == 0) Color.Blue else Color.Unspecified,
                contentScreen = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Content for Tab testT1")
                    }
                },
                fontColor = Color.DarkGray, fontSelectColor = Color.White,
                fontSize = 12.sp, fontSelectSize = 13.sp,
                tabBgColor = if (index == 0) Color.Blue else Color.Unspecified
            ),
            testTopTab(tabName = "testT2", contentBgColor = if (index == 1) Color.Green else Color.Unspecified, tabBgColor = if (index == 1) Color.Green else Color.Unspecified),
            testTopTab(tabName = "testT3", contentBgColor = if (index == 2) Color.DarkGray else Color.Unspecified, tabBgColor = if (index == 2) Color.DarkGray else Color.Unspecified),
            testTopTab(tabName = "testT4", contentBgColor = if (index == 3) Color.DarkGray else Color.Unspecified, tabBgColor = if (index == 3) Color.DarkGray else Color.Unspecified),
            testTopTab(tabName = "testT5", contentBgColor = if (index == 4) Color.DarkGray else Color.Unspecified, tabBgColor = if (index == 4) Color.DarkGray else Color.Unspecified),
        )

        val bottomTabs = listOf(
            testBottomTab(tabName = "test1", contentScreen = {
                TabPage(
                    topTabs, selectedTabIndexState
                )
            }),
            testBottomTab(tabName = "testB2"),
            testBottomTab(tabName = "testB3"),
            testBottomTab(tabName = "testB4"),
        )
        BottomTabScreen(bottomTabs)
    }
//    MaterialTheme {
//        MainScreen()
//    }
}
