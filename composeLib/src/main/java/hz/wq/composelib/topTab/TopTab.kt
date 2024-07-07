package hz.wq.composelib.topTab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import hz.wq.composelib.bottomTab.BottomTab

/**
 * 创建者: W~Q
 */
class TopTab(
    var name: String,

    var icon: ImageVector = Icons.Filled.Star,
    var selectedIcon: ImageVector = Icons.Filled.Star,
    var contentScreen: @Composable () -> Unit = { },

    var iconWidth: Dp? = null,
    var iconHeight: Dp? = null,
    var iconSelectedColor: Color? = null,
    var iconUnSelectedColor: Color? = null,
    var isNeedText: Boolean = true,
    var fontSize: TextUnit = TextUnit.Unspecified,
    var fontSelectSize: TextUnit = TextUnit.Unspecified,
    var fontColor: Color = Color.Unspecified,
    var fontSelectColor: Color = Color.Unspecified,
    var contentBgColor: Color = Color.Unspecified,
    var tabBgColor: Color = Color.Unspecified
)

fun testTopTab(
    tabName: String = "Test",
    contentBgColor: Color = Color.Unspecified,
    tabBgColor: Color = Color.Unspecified,
    contentScreen: @Composable () -> Unit = { TestTopContent(tabName) }
): TopTab {
    return TopTab(
        name = tabName,
        contentScreen = contentScreen,
        icon = Icons.Filled.Star,
        selectedIcon = Icons.Filled.Star,

//        iconWidth = 10.dp,
//        iconHeight = 10.dp,
//        iconSelectedColor = Color.Green,
//        iconUnSelectedColor = Color.Blue,
//        isNeedText = true,
//        fontSize = 14.sp,
//        fontSelectSize = 16.sp,
//        fontColor = Color.DarkGray,
//        fontSelectColor = Color.Red,
        contentBgColor = contentBgColor,
        tabBgColor = tabBgColor,
//
//        navigationBarItemIndicatorColor = Color.Transparent
    )
}

@Composable
private fun TestTopContent(name: String = "Test") {
//    Text(text = "$name Screen")
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "TestTopContent $name")
    }
}