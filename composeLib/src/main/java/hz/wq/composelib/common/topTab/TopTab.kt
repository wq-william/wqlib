package hz.wq.composelib.common.topTab

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
import androidx.compose.ui.unit.sp
import hz.wq.composelib.common.bottomTab.BottomTab

/**
 * 创建者: W~Q
 */
class TopTab(
    var name: String,
    var contentScreen: @Composable () -> Unit = { },
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
        isNeedText = true,
        fontSize = 12.sp,
        fontSelectSize = 12.sp,
        fontColor = Color.DarkGray,
        fontSelectColor = Color.White,
        contentBgColor = contentBgColor,
        tabBgColor = tabBgColor,
    )
}

@Composable
private fun TestTopContent(name: String = "Test") {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "TestTopContent $name")
    }
}