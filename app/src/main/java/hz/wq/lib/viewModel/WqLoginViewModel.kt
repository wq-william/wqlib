package hz.wq.lib.viewModel

import hz.wq.composelib.common.login.enums.LoginResult
import hz.wq.composelib.common.login.viewModel.BaseLoginViewModel

class WqLoginViewModel : BaseLoginViewModel() {

    override fun login(email: String, password: String) {
        if (email == "wq" && password == "123") {
            emitLoginResult(LoginResult.LoginSuccess)
        } else {
            emitLoginResult(LoginResult.LoginError)
        }
    }

}