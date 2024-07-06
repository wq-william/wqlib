package hz.wq.composelib.bottomTab

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomTabScreen(tabs:List<BottomTab>, startIndex:Int = 0) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController,tabs,60.dp) }
    ) { innerPadding ->
        if(tabs.isEmpty()){
            return@Scaffold
        }
        NavHost(navController, startDestination = tabs[startIndex].route, modifier = Modifier.padding(innerPadding)) {
            tabs.forEach { tab->
                composable(tab.route) { tab.contentScreen() }
            }
        }
    }
}
