package hz.wq.composelib.common.login

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

/**
 * 创建者: W~Q
 */
@Composable
fun LoginModuleScreen (navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = "home") {
        composable("home") {
//            HomeScreen(navController = navController)
        }
        composable("settings") {
//            SettingsScreen(navController = navController)
        }
    }
}