package hz.wq.lib.testCompose

import androidx.compose.runtime.Composable
import hz.wq.common.util.ui.DialogUtil.showDialogMsg
import hz.wq.common.util.ui.StatusBarUtil

/**
 * 创建者: W~Q
 * 描述: 状态栏 功能快速查找
 */
object TestStatusBar {
    val gridButtonItems = listOf(
        GridButtonItem("所有配置(code查看)") { StatusBarUtil.allApi() },
        GridButtonItem("获取状态栏高度") {
            showDialogMsg("获取状态栏高度", "${StatusBarUtil.getHegith()}px")
        },
        GridButtonItem("全屏") { StatusBarUtil.setFullScreen() },
        GridButtonItem("非全屏") { StatusBarUtil.setNonFullScreen() },
        GridButtonItem("切换全屏") { StatusBarUtil.toggleFullScreen() },
        GridButtonItem("当前是否全屏") {
            showDialogMsg("当前是否全屏", "${StatusBarUtil.isFullScreen()}")
        },
        GridButtonItem("隐藏状态栏") { StatusBarUtil.hideBar() },
        GridButtonItem("显示状态栏") { StatusBarUtil.showBar() },
        GridButtonItem("背景橙色") { StatusBarUtil.bgOrange() },
        GridButtonItem("背景黑色") { StatusBarUtil.bgBlack() },
        GridButtonItem("背景白色") { StatusBarUtil.bgWhite() },
        GridButtonItem("黑色字体") { StatusBarUtil.fontBlack() },
        GridButtonItem("白色字体") { StatusBarUtil.fontWhite() },
    )


}


@Composable
fun StatusBarLayout() {
    GridButtonLayout(TestStatusBar.gridButtonItems)
}
