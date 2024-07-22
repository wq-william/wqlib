package hz.wq.composelib.common.bottomTab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hz.wq.composelib.common.other.BoxShadow
import hz.wq.composelib.common.other.Gradient

@Composable
fun BottomTabScreen(tabs: List<BottomTab>, startIndex: Int = 0) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, tabs, 60.dp)
        }
    ) { innerPadding ->
        if (tabs.isEmpty()) {
            return@Scaffold
        }
        NavHost(navController, startDestination = tabs[startIndex].route, modifier = Modifier.padding(innerPadding)) {
            tabs.forEach { tab ->
                composable(tab.route) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        tab.contentScreen()
                        BoxShadow(
                            alignment = Alignment.BottomEnd,
                            heightDp = 6.dp,
                            gradientDirectionType = Gradient.GradientDirectionType.BOTTOM_TOP
                        )
                    }

                }
            }
        }
    }
}
