package hz.wq.lib.testCompose

import androidx.compose.runtime.Composable
import com.blankj.utilcode.util.ActivityUtils
import hz.wq.lib.LoginActivity
import hz.wq.lib.TestActivity

/**
 * 创建者: W~Q
 * 描述: 模块
 */
object TestModule {
    val gridItems = listOf(
        GridItem("Test") { ActivityUtils.startActivity(TestActivity::class.java) },
        GridItem("登录") { ActivityUtils.startActivity(LoginActivity::class.java) },
    )
}


@Composable
fun ModuleLayout() {
    GridLayout(TestModule.gridItems)
}
