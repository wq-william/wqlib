package hz.wq.lib.testCompose

import androidx.compose.runtime.Composable
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import hz.wq.common.dialog.BaseDialog
import hz.wq.common.dialog.MessageDialog
import hz.wq.common.util.ui.DialogUtil.showDialogMsg
import hz.wq.common.util.ui.StatusBarUtil

/**
 * 创建者: W~Q
 * 描述: 状态栏 功能快速查找
 */
object TestStatusBar {
    val gridItems = listOf(
        GridItem("所有配置(code查看)") { StatusBarUtil.allApi() },
        GridItem("获取状态栏高度") {
            showDialogMsg("获取状态栏高度", "${StatusBarUtil.getHegith()}px")
        },
        GridItem("全屏") { StatusBarUtil.setFullScreen() },
        GridItem("非全屏") { StatusBarUtil.setNonFullScreen() },
        GridItem("切换全屏") { StatusBarUtil.toggleFullScreen() },
        GridItem("当前是否全屏") {
            showDialogMsg("当前是否全屏", "${StatusBarUtil.isFullScreen()}")
        },
        GridItem("隐藏状态栏") { StatusBarUtil.hideBar() },
        GridItem("显示状态栏") { StatusBarUtil.showBar() },
        GridItem("背景橙色") { StatusBarUtil.bgOrange() },
        GridItem("背景黑色") { StatusBarUtil.bgBlack() },
        GridItem("背景白色") { StatusBarUtil.bgWhite() },
        GridItem("黑色字体") { StatusBarUtil.fontBlack() },
        GridItem("白色字体") { StatusBarUtil.fontWhite() },
    )


}


@Composable
fun StatusBarLayout() {
    GridLayout(TestStatusBar.gridItems)
}
