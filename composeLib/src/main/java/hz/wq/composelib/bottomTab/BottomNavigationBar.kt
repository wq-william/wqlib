package hz.wq.composelib.bottomTab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import hz.wq.common.log.LogUtils.wqLog

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    tabs: List<BottomTab>,
    tabHeight: Dp = 80.dp,
    tabBg: Color = NavigationBarDefaults.containerColor,
) {
    NavigationBar(
        containerColor = tabBg, // 背景颜色
        modifier = Modifier.height(tabHeight) // 设置导航栏高度
    ) {

        var selectRoute = remember {
            mutableStateOf(tabs[0].route)
        }
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentDestination = navBackStackEntry?.destination
        tabs.forEach { tab ->
//            val selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true
            val selected: Boolean = selectRoute.value == tab.route

            NavigationBarItem(
                colors = tab.navigationBarItemIndicatorColor?.let { indicatorColor ->
                    NavigationBarItemDefaults.colors(
                        indicatorColor = indicatorColor,
                    )
                } ?: NavigationBarItemDefaults.colors(),
                modifier = Modifier.background(if (selected) tab.bgColor else Color.Transparent),
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

                    if (tab.isNeedText)
                        Text(
                            tab.route,
                            fontSize = if (selected) tab.fontSelectSize else tab.fontSize,
                            color = if (selected) tab.fontSelectColor else tab.fontColor
                        )
                },
                selected = selected,
                onClick = {
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                        selectRoute.value = tab.route
                    }

                }
            )
        }
    }
}
