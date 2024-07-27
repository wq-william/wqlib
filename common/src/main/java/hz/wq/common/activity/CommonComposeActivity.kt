package hz.wq.common.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.ToastUtils
import hz.wq.common.util.log.LogUtils.wqLog
import hz.wq.common.util.ui.StatusBarUtil
import hz.wq.common.util.ui.StatusBarUtil.immersionBar
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
        StatusBarUtil.bgWhite()
        StatusBarUtil.fontBlack()
        initData()
        initFinish()
        setContent {
            ComposeContent()
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