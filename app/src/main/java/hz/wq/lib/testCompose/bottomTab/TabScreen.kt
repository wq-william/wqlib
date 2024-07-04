package hz.wq.lib.testCompose.bottomTab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * 创建者: W~Q
 */
class TabScreen(var route: String, var icon: ImageVector, var selectedIcon: ImageVector, var contentScreen: @Composable () -> Unit) {
//    data object Test : TabScreen("Test", Icons.Filled.Star, Icons.Filled.Star, { TestScreen() })
}

fun testTab(tabName: String = "Test"): TabScreen {
    return TabScreen(tabName, Icons.Filled.Star, Icons.Filled.Star) { TestScreen(tabName) }
}

@Composable
fun TestScreen(name: String = "Test") {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "$name Screen")
    }
}