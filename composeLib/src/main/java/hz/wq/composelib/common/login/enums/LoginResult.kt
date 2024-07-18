package hz.wq.composelib.common.login.enums

sealed class LoginResult {
    data object LoginSuccess : LoginResult() //登录成功
    data object LoginError : LoginResult() //登录错误
    data object LoggingIn : LoginResult() //登录中
    data object NoState : LoginResult() //未知状态
}
