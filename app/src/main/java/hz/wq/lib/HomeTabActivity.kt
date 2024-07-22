package hz.wq.lib

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


    @Composable
    override fun ComposeContent() {
        val items = listOf(
            GridItem("Message Dialog") {

                // 消息对话框
                MessageDialog.Builder(This)
                    // 标题可以不用填写
                    .setTitle("我是标题")
                    // 内容必须要填写
                    .setMessage("我是内容")
                    // 确定按钮文本
                    .setConfirm("确定")
                    // 设置 null 表示不显示取消按钮
                    .setCancel("取消")
                    // 设置点击按钮后不关闭对话框
                    //.setAutoDismiss(false)
                    .setListener(object : MessageDialog.OnListener {

                        override fun onConfirm(dialog: BaseDialog?) {
                            ToastUtils.showShort("确定了")
                        }

                        override fun onCancel(dialog: BaseDialog?) {
                            ToastUtils.showShort("取消了")
                        }
                    })
                    .show()
            },
            GridItem("Button 2"),
            GridItem("Button 3"),
            GridItem("Button 4"),
            GridItem("Button 5"),
            GridItem("Button 6")
        )
        val context = LocalContext.current
        val selectBgColor = Color(ContextCompat.getColor(context, hz.wq.common.R.color.common_line_color))
        val selectFontColor = Color(ContextCompat.getColor(context, hz.wq.common.R.color.common_primary_dark_color))
//
        TestGradle85Theme {
            var selectedTabIndexState = remember { mutableIntStateOf(0) }
            val index = selectedTabIndexState.value
            val topTabs = listOf(
                TopTab(
                    name = "Dialog",
                    contentBgColor = if (index == 0) selectBgColor else Color.Unspecified,
                    contentScreen = {
                        DialogGridLayout(items)
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