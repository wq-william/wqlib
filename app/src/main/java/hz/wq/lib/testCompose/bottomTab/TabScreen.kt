package hz.wq.lib.testCompose.bottomTab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 创建者: W~Q
 */
class TabScreen(
    var route: String,
    var icon: ImageVector,
    var selectedIcon: ImageVector,
    var contentScreen: @Composable () -> Unit,

    var iconWidth : Dp? =null,
    var iconHeight : Dp? =null,
    var iconSelectedColor: Color? = null,
    var iconUnSelectedColor: Color? = null,
    var isNeedText: Boolean = true,
    var fontSize: TextUnit = TextUnit.Unspecified,
    var fontColor: Color = Color.Unspecified,
    var bgColor: Color = Color.Unspecified,
) {
//    data object Test : TabScreen("Test", Icons.Filled.Star, Icons.Filled.Star, { TestScreen() })
}

fun testTab(tabName: String = "Test"): TabScreen {
    return TabScreen(
        route = tabName,
        icon = Icons.Filled.Star,
        selectedIcon = Icons.Filled.Star,
        contentScreen = { TestScreen(tabName) },
        iconWidth = 10.dp,
        iconHeight = 10.dp,
        iconSelectedColor = Color.Green,
        iconUnSelectedColor = Color.Blue,
        isNeedText = true,
        fontSize = 8.sp,
        fontColor = Color.Red,
        bgColor = Color.Yellow
    )
}

@Composable
fun TestScreen(name: String = "Test") {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "$name Screen")
    }
}