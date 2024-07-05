package hz.wq.lib.testCompose.bottomTab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    tabs: List<TabScreen>,
    tabHeight: Dp = 80.dp,
    tabBg: Color = NavigationBarDefaults.containerColor,
) {
    NavigationBar(
        containerColor = tabBg, // 背景颜色
        modifier = Modifier.height(tabHeight) // 设置导航栏高度
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        val currentDestination = navBackStackEntry?.destination
        tabs.forEach { tab ->
            val selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true
            NavigationBarItem(
                modifier = Modifier.background(if (selected) tab.bgColor else Color.Transparent),
//                modifier = Modifier.background(
//                    color = if (selected) tab.bgColor else Color.Transparent,
//                    shape = if (selected) RoundedCornerShape(8.dp) else RoundedCornerShape(0.dp)
//                ),
                // 确保背景完全透明
//                modifier = Modifier.background(
//                    color = if (selected) tab.bgColor else Color.Transparent,
//                    shape = RoundedCornerShape(0.dp) // 平直的背景，无圆角
//                ),
                icon = {
                    val modifier = Modifier.apply {
                        tab.iconWidth?.let {
                            width(it)
                        }
                        tab.iconHeight?.let {
                            height(it)
                        }
                    }
                    Icon(
                        modifier = modifier,
                        imageVector = if (selected) tab.selectedIcon else tab.icon,
                        contentDescription = tab.route,
                        tint = if (selected) {
                            tab.iconSelectedColor ?: LocalContentColor.current
                        } else {
                            tab.iconUnSelectedColor ?: LocalContentColor.current
                        } // 选中和未选中图标颜色
                    )
                },
                label = {
                    if (tab.isNeedText) Text(tab.route, fontSize = tab.fontSize, color = tab.fontColor)
                },
                selected = selected,
                onClick = {
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
