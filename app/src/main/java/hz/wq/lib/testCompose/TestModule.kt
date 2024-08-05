package hz.wq.lib.testCompose

import androidx.compose.runtime.Composable
import com.blankj.utilcode.util.ActivityUtils
import hz.wq.lib.activity.LoginActivity
import hz.wq.lib.activity.TestActivity

/**
 * 创建者: W~Q
 * 描述: 模块
 */
object TestModule {
    val gridButtonItems = listOf(
        GridButtonItem("Test") { ActivityUtils.startActivity(TestActivity::class.java) },
        GridButtonItem("登录") { ActivityUtils.startActivity(LoginActivity::class.java) },
    )
}


@Composable
fun ModuleLayout() {
    GridButtonLayout(TestModule.gridButtonItems)
}
