package hz.wq.lib.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hz.wq.composelib.common.login.enums.LoginResult
import hz.wq.composelib.common.login.viewModel.BaseLoginViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
class AssemblyViewModel @Inject constructor() : ViewModel() {
    //开关选中状态
    private val _switchChecked = MutableSharedFlow<Boolean>()
    val switchChecked: SharedFlow<Boolean> = _switchChecked

    //进度百分比
    private val _progressPercentage = MutableSharedFlow<Int>()
    val progressPercentage: SharedFlow<Int> = _progressPercentage



}