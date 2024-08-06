package hz.wq.lib.viewModel

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hz.wq.common.util.log.LogUtils.wqLog
import hz.wq.common.viewModel.CommonViewModel
import hz.wq.composelib.common.login.enums.LoginResult
import hz.wq.composelib.common.login.viewModel.BaseLoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AssemblyViewModel @Inject constructor() : CommonViewModel() {

    //进度百分比
    private val _progressPercentage = MutableSharedFlow<Int>()
    val progressPercentage: SharedFlow<Int> = _progressPercentage

    //开关选中状态
    private val _switchChecked = MutableSharedFlow<Boolean>() // 设置默认值为 true

    // 创建一个 SharedFlow，它可以持有最新的值
    val switchChecked: StateFlow<Boolean> = _switchChecked
        .stateIn(
            scope = CoroutineScope(Dispatchers.Main),
            started = SharingStarted.WhileSubscribed(),
            initialValue = false // 设置初始值
        )

    init {
        observeProgressPercentageToggleSwitch()
    }


    /**
     * 获取开关选中状态
     */
    fun isSwitchChecked(): Boolean {
        return switchChecked.value
    }


    /**
     * 切换开关
     */
    fun toggleSwitch(checked: Boolean = isSwitchChecked()) {
        launch {
            _switchChecked.emit(!checked)
        }
    }

    /**
     * 更新进度百分比
     */
    fun updateProgressPercentage(progress: Int) {
        launch {
            _progressPercentage.emit(progress)
        }
    }

    /**
     * 观察进度百分比变化 ，每到100%切换开关
     */
    fun observeProgressPercentageToggleSwitch() {
        launch {
            progressPercentage.collect {
                "进度百分比：$it".wqLog()
                if (it == 100) {
                    toggleSwitch()
                }
            }
        }
    }

}