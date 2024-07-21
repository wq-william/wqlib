package hz.wq.composelib.common.login.viewModel

import androidx.lifecycle.viewModelScope
import hz.wq.common.util.log.LogUtils.wqLog
import hz.wq.composelib.common.login.enums.LoginResult
import hz.wq.common.viewModel.CommonViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseLoginViewModel : CommonViewModel() {
    //登录状态 驱动场景
    private val _loginResult = MutableSharedFlow<LoginResult>()
    val loginResult: SharedFlow<LoginResult> = _loginResult

    //数据状态管理
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()
    private val _password2 = MutableStateFlow("")
    val password2: StateFlow<String> = _password2.asStateFlow()
    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()
    private val _verificationCode = MutableStateFlow("")
    val verificationCode: StateFlow<String> = _verificationCode.asStateFlow()


    /**
     * 用户名变化
     */
    fun onUserNameChanged(userName: String): Unit {
        _userName.value = userName
        "userName:$userName".wqLog()
    }

    /**
     * 密码变化
     */
    fun onPasswordChanged(password: String) {
        _password.value = password
        "password:$password".wqLog()
    }

    fun onPassword2Changed(password: String) {
        _password2.value = password
        "password:$password".wqLog()
    }
    /**
     * 验证码变化
     */
    fun onVerificationCodeChanged(code: String) {
        _verificationCode.value = code
        "_verificationCode:$_verificationCode".wqLog()
    }

    fun login() {
        login(userName.value, password.value)
    }

    abstract fun login(email: String, password: String)
//    abstract fun lre(email: String, password: String)
//    {
////        emitLoginResult(if (email == "123" && password == "456") LoginResult.LoginSuccess else LoginResult.LoginError)
//    }

    fun emitLoginResult(loginResult: LoginResult) {
        viewModelScope.launch {
//            delay(1000L)
            _loginResult.emit(loginResult)
        }
    }

    fun launch(){
        viewModelScope.launch {

        }
    }

    fun resetState() {
        viewModelScope.launch {
            _loginResult.emit(LoginResult.NoState)
        }
    }
}
