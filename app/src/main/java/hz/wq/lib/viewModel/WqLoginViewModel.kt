package hz.wq.lib.viewModel

import android.content.Context
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import hz.wq.common.activity.CommonComposeActivity
import hz.wq.common.util.log.LogUtils.wqLog
import hz.wq.composelib.common.login.enums.LoginResult
import hz.wq.composelib.common.login.viewModel.BaseLoginViewModel
import hz.wq.httplib.api.Config
import hz.wq.httplib.api.Config.isSuccess
import hz.wq.httplib.api.service.UserInterface
import hz.wq.httplib.helper.HttpHelper
import hz.wq.lib.testHttp.LoginParamBean
import hz.wq.lib.testHttp.domain
import javax.inject.Inject
import javax.inject.Singleton

//@HiltViewModel
//@Singleton
@HiltViewModel
class WqLoginViewModel  @Inject constructor() : BaseLoginViewModel() {

    init {
        onUserNameChanged("wq123456")
        onPasswordChanged("123456")
    }

    override fun login(name: String, password: String) {
        launch {
            try {
                val apiService = HttpHelper.getApiService(Config.wqWebDoMain, UserInterface::class.java)
                var result = apiService.login(name, password)
                if (result.isSuccess()) {
                    "登录成功了哟".wqLog()
                    emitLoginResult(LoginResult.LoginSuccess)
                } else {
                    emitLoginResult(LoginResult.LoginError)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emitLoginResult(LoginResult.LoginError)
            }
        }

        launch {
            //写三个不同的排序算法




        }

//        if (name == "wq" && password == "123") {
//            emitLoginResult(LoginResult.LoginSuccess)
//        } else {
//            emitLoginResult(LoginResult.LoginError)
//        }
    }

}