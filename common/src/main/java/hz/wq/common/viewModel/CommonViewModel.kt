package hz.wq.common.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import hz.wq.common.activity.CommonComposeActivity
import hz.wq.common.util.log.LogUtils.wqLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

open class CommonViewModel : ViewModel() {
    private val _shouldFinishActivity = MutableSharedFlow<Unit>()
    val shouldFinishActivity: SharedFlow<Unit> = _shouldFinishActivity

    fun finishActivity() {
        "wq finishActivity".wqLog()
        viewModelScope.launch {
            "wq finishActivity emit".wqLog()
            _shouldFinishActivity.emit(Unit)
        }
    }

    fun ViewModel.launch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(block = block)
    }
}