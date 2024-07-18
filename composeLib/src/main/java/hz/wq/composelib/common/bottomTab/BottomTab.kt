package hz.wq.composelib.common.bottomTab

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

/**
 * 创建者: W~Q
 */
class BottomTab(
    var route: String,

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
    var bgColor: Color = Color.Unspecified,
    var navigationBarItemIndicatorColor: Color? = null //这里是设置选中后的ICON的背景颜色,透明则取消指示器
)

fun testBottomTab(
    tabName: String = "Test",
    contentScreen: @Composable () -> Unit = { TestBottomContent(tabName) }
): BottomTab {
    return BottomTab(
        route = tabName,
        contentScreen = contentScreen,
        icon = Icons.Filled.Star,
        selectedIcon = Icons.Filled.Star,

//
//        iconWidth = 10.dp,
//        iconHeight = 10.dp,
//        iconSelectedColor = Color.Green,
//        iconUnSelectedColor = Color.Blue,
//        isNeedText = true,
//        fontSize = 14.sp,
//        fontSelectSize = 16.sp,
//        fontColor = Color.DarkGray,
//        fontSelectColor = Color.Red,
//        bgColor = Color.Yellow,
//
//        navigationBarItemIndicatorColor = Color.Transparent
    )
}

@Composable
private fun TestBottomContent(name: String = "Test") {
//    Text(text = "$name Screen")
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "TestBottomContent $name")
    }
}