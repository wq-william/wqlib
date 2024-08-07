package hz.wq.composelib.common.login

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hz.wq.common.util.log.LogUtils.wqLog
import hz.wq.composelib.common.login.enums.LoginPageEnum
import hz.wq.composelib.common.login.screen.LoginScreen
import hz.wq.composelib.common.login.screen.RegisterScreen
import hz.wq.composelib.common.login.screen.ResetPasswordScreen
import hz.wq.composelib.common.login.viewModel.BaseLoginViewModel

/**
 * 创建者: W~Q
 */
@Composable
fun LoginModuleScreen(viewModel: BaseLoginViewModel) {
    "1查看viewModel地址：${viewModel}".wqLog()

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = LoginPageEnum.LOGIN.name) {
        composable(LoginPageEnum.LOGIN.name) {
            LoginScreen(navController, viewModel)
        }
        composable(LoginPageEnum.REGISTER.name) {
            RegisterScreen(navController, viewModel)
        }
        composable(LoginPageEnum.RESET_PASSWORD.name) {
            ResetPasswordScreen(navController, viewModel)
        }
    }
}