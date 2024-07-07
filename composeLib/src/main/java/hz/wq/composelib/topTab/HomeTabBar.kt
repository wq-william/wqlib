package hz.wq.composelib.topTab

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import hz.wq.lib.theme.TestTheme

@Composable
fun HomeTabBar(
//    backgroundColor: Color,
//    tabPage:TabPage

) {
//    TestTheme {
//        // A surface container using the 'background' color from the theme
//        Surface(
//            modifier = Modifier.fillMaxSize(),
////            color = MaterialTheme.colorScheme.background
//        ) {
    TabPage()
//        }
//    }
}


@Composable
fun TabPage() {
    val tabTitles = listOf("Tab 1", "Tab 2", "Tab 3")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

//    val gradient = Brush.horizontalGradient(
//        colors = listOf(Color(0xFF0F9D58), Color(0xFF4285F4))
//    )

    val resultColor = if (selectedTabIndex == 0) Color.Blue else if (selectedTabIndex == 1) Color.Yellow else Color.White
    val bgColor by animateColorAsState(
        targetValue = resultColor,
        label = "bgAnimateColor",
//        animationSpec = tween(durationMillis = 300)
    )

//    val bgColor by animateColorAsState(
//        targetValue = resultColor,
//        animationSpec = tween(durationMillis = 300) ,// 设置动画持续时间为1000毫秒
//        label = "bgAnimateColor"
//    )

    Column() {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.background(Color.White),
            indicator = {} ,// 取消指示器
            divider = {} // 取消分割线
//            indicator = { tabPositions ->
//                TabRowDefaults.Indicator(
//                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
//                )
//            }
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    modifier = Modifier.background(if (selectedTabIndex == index) bgColor else Color.White),
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                )
            }
        }
        TabContent("${selectedTabIndex + 1}", bgColor)
//        when (selectedTabIndex) {
//            0 -> TabContent("$this")
//            1 -> TabContent2()
//            2 -> TabContent3()
//        }
    }
}

@Composable
fun TabContent(name: String, bgColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Content for Tab $name")
    }

}

//@Composable
//fun TabContent2() {
//    // Replace with your actual content for Tab 2
//    Text("Content for Tab 2")
//}
//
//@Composable
//fun TabContent3() {
//    // Replace with your actual content for Tab 3
//    Text("Content for Tab 3")
//}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    TestTheme {
//        TabPage()
//    }
//}