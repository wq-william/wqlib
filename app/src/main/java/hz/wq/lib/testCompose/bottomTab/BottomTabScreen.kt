package hz.wq.lib.testCompose.bottomTab

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomTabScreen(tabs:List<TabScreen>,startIndex:Int = 0) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController,tabs) }
    ) { innerPadding ->
        if(tabs.size==0){
            return@Scaffold
        }
        NavHost(navController, startDestination = tabs[startIndex].route, modifier = Modifier.padding(innerPadding)) {
            tabs.forEach { tab->
                composable(tab.route) { tab.contentScreen() }
            }
//            composable(Screen.Home.route) { HomeScreen() }
//            composable(Screen.Profile.route) { ProfileScreen() }
//            composable(Screen.Settings.route) { SettingsScreen() }
//            composable(Screen.Messages.route) { NotificationsScreen() }
//            composable(Screen.More.route) { MoreScreen() }
        }
    }
}
