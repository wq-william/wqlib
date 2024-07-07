package hz.wq.composelib.topTab

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.composable

//@Composable
//fun HomeTabBar(
//) {
//    "HomeTabBar ".wqLog()
//    TabPage()
//}


@Composable
fun TabPage(tabs: List<TopTab>, selectedTabIndexState: MutableState<Int>) {
    var selectedTabIndex by selectedTabIndexState

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.background(Color.Transparent),
            indicator = {},// 取消指示器
            divider = {} // 取消分割线
        ) {
            tabs.forEachIndexed { index, tab ->
                var selected = index == selectedTabIndex
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { selectedTabIndexState.value = index }
                        .clip(RoundedCornerShape(12.dp, 12.dp, 0.dp, 0.dp)) // 设置圆角
                        .background(tab.tabBgColor)
//                        .background(if (selectedTabIndexState.value == index) tab.bgColor else Color.Unspecified)
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                ) {

                    if (tab.isNeedText)
                        Text(
                            tab.name,
                            fontSize = if (selected) tab.fontSelectSize else tab.fontSize,
                            color = if (selected) tab.fontSelectColor else tab.fontColor
                        )
                }
            }
        }
        Box(modifier = Modifier.background(tabs[selectedTabIndex].contentBgColor)) {
            tabs[selectedTabIndex].contentScreen()
        }

    }
}

//val bgColor1 by animateColorAsState(targetValue = if (index == 0) Color.Gray else Color.Unspecified, animationSpec = tween(durationMillis = 2000), label = "test1")
//val bgColor2 by animateColorAsState(targetValue = if (index == 1) Color.White else Color.Unspecified, animationSpec = tween(durationMillis = 2000), label = "test1")
//val bgColor3 by animateColorAsState(targetValue = if (index == 2) Color.White else Color.Unspecified, animationSpec = tween(durationMillis = 2000), label = "test1")
//val bgColor4 by animateColorAsState(targetValue = if (index == 3) Color.Red else Color.Unspecified, animationSpec = tween(durationMillis = 2000), label = "test1")

