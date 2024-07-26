package hz.wq.common.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.ToastUtils
import hz.wq.common.util.log.LogUtils.wqLog
import hz.wq.common.viewModel.CommonViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * 创建者: W~Q
 * 描述: 通用的Activity Compose
 */
open abstract class CommonComposeActivity : ComponentActivity() {
//    var This: CommonComposeActivity? = null

    @Composable
    abstract fun ComposeContent()


    abstract fun getViewModel(): CommonViewModel?

    override fun onCreate(savedInstanceState: Bundle?) {
//        This = this
        super.onCreate(savedInstanceState)
        initData()
        initFinish()

        // Configure window appearance (no title bar, etc.)
//        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
//            MaterialTheme(
////                colors = if (isSystemInDarkTheme()) darkColors() else lightColors(),
//                typography = Typography(),
//                shapes = Shapes(),
//            ) {
            ComposeContent()
//            }
        }
    }

    open fun initData() {
    }


    private fun initFinish() {
        launch {
            getViewModel()?.shouldFinishActivity?.collect {
                "shouldFinishActivity collect".wqLog()
                finish()
            }
        }

    }

    fun CommonComposeActivity.launch(block: suspend CoroutineScope.() -> Unit) {
        this.lifecycleScope.launch(block = block)
    }

    fun showToast(msg: String) {
        ToastUtils.showShort(msg)
    }
}