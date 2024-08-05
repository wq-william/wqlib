package hz.wq.lib.activity

import android.os.Bundle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import hz.wq.common.activity.CommonComposeActivity
import hz.wq.common.viewModel.CommonViewModel
import hz.wq.composelib.common.bottomTab.BottomTabScreen
import hz.wq.composelib.common.bottomTab.testBottomTab
import hz.wq.composelib.common.topTab.TabPage
import hz.wq.composelib.common.topTab.TopTab
import hz.wq.lib.testCompose.AssemblyLayout
import hz.wq.lib.testCompose.DialogGridLayout
import hz.wq.lib.testCompose.ModuleLayout
import hz.wq.lib.testCompose.StatusBarLayout
import hz.wq.lib.theme.TestGradle85Theme
@AndroidEntryPoint
class HomeTabActivity : CommonComposeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @Composable
    override fun ComposeContent() {

        var selectedTabIndexState = remember { mutableIntStateOf(0) }
        TestGradle85Theme {
            val bottomTabs = listOf(
                testBottomTab(tabName = "通用", contentScreen = {
                    TabPage(getTopTab(selectedTabIndexState), selectedTabIndexState)
                }),
                testBottomTab(tabName = "testB2"),
                testBottomTab(tabName = "testB3"),
                testBottomTab(tabName = "testB4"),
            )
            BottomTabScreen(bottomTabs)
        }
    }

    @Composable
    private fun getTopTab(indexState: MutableState<Int>): List<TopTab> {
        var selectedTabIndex by indexState
        val topTabs = listOf(
            TopTab(name = "模块") { ModuleLayout() },
            TopTab(name = "弹出框") { DialogGridLayout() },
            TopTab(name = "状态栏") { StatusBarLayout() },
            TopTab(name = "UI组件") { AssemblyLayout() },
//                TopTab(name = "testT3"),
//                TopTab(name = "testT4"),
//                TopTab(name = "testT5"),
        )

        val context = LocalContext.current
        val selectBgColor = Color(ContextCompat.getColor(context, hz.wq.common.R.color.common_line_color))
        val selectFontColor = Color(ContextCompat.getColor(context, hz.wq.common.R.color.common_primary_dark_color))
        topTabs.forEachIndexed { position, topTab ->
            topTab.run {
                contentBgColor = if (selectedTabIndex == position) selectBgColor else Color.Unspecified
                fontColor = Color.DarkGray
                fontSelectColor = selectFontColor
                fontSize = 12.sp
                fontSelectSize = 13.sp
                tabBgColor = if (selectedTabIndex == position) selectBgColor else Color.Unspecified
            }
        }
        return topTabs
    }

    override fun getViewModel(): CommonViewModel? {
        return null
    }

}