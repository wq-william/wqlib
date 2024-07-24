package hz.wq.lib

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ToastUtils
import hz.wq.common.activity.CommonComposeActivity
import hz.wq.common.dialog.BaseDialog
import hz.wq.common.dialog.MessageDialog
import hz.wq.common.viewModel.CommonViewModel
import hz.wq.composelib.common.bottomTab.BottomTabScreen
import hz.wq.composelib.common.bottomTab.testBottomTab
import hz.wq.composelib.common.topTab.TabPage
import hz.wq.composelib.common.topTab.TopTab
import hz.wq.composelib.common.topTab.testTopTab
import hz.wq.lib.testCompose.DialogGridLayout
import hz.wq.lib.testCompose.GridItem
import hz.wq.lib.theme.TestGradle85Theme

class HomeTabActivity : CommonComposeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Composable
    override fun ComposeContent() {

        val context = LocalContext.current
        val selectBgColor = Color(ContextCompat.getColor(context, hz.wq.common.R.color.common_line_color))
        val selectFontColor = Color(ContextCompat.getColor(context, hz.wq.common.R.color.common_primary_dark_color))
//
        TestGradle85Theme {
            var selectedTabIndexState = remember { mutableIntStateOf(0) }
            val index = selectedTabIndexState.value
            val topTabs = listOf(
                TopTab(
                    name = "弹出框",
                    contentBgColor = if (index == 0) selectBgColor else Color.Unspecified,
                    contentScreen = {
                        DialogGridLayout()
                    },
                    fontColor = Color.DarkGray, fontSelectColor = selectFontColor,
                    fontSize = 12.sp, fontSelectSize = 13.sp,
                    tabBgColor = if (index == 0) selectBgColor else Color.Unspecified
                ),
                testTopTab(tabName = "testT2", contentBgColor = if (index == 1) Color.Green else Color.Unspecified, tabBgColor = if (index == 1) Color.Green else Color.Unspecified),
                testTopTab(tabName = "testT3", contentBgColor = if (index == 2) Color.DarkGray else Color.Unspecified, tabBgColor = if (index == 2) Color.DarkGray else Color.Unspecified),
                testTopTab(tabName = "testT4", contentBgColor = if (index == 3) Color.DarkGray else Color.Unspecified, tabBgColor = if (index == 3) Color.DarkGray else Color.Unspecified),
                testTopTab(tabName = "testT5", contentBgColor = if (index == 4) Color.DarkGray else Color.Unspecified, tabBgColor = if (index == 4) Color.DarkGray else Color.Unspecified),
            )

            val bottomTabs = listOf(
                testBottomTab(tabName = "通用", contentScreen = {
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
    }

    override fun getViewModel(): CommonViewModel? {
        return null
    }
}