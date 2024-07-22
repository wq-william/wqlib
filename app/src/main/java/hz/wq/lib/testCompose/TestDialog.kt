package hz.wq.lib.testCompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * 创建者: W~Q
 * 创建时间:   2024/7/22 10:03
 * 描述: TODO
 */
class TestDialog {
}


@Composable
fun DialogButtonList() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(onClick = { /* Do something */ }) {
            Text(text = "messageDialog")
        }
        Button(onClick = { /* Do something */ }) {
            Text(text = "Button 2")
        }
        Button(onClick = { /* Do something */ }) {
            Text(text = "Button 3")
        }
    }
}


data class GridItem(val text: String, var onClick: () -> Unit = { })

@Composable
fun DialogGridLayout(items: List<GridItem>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1), // 两列
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) { item ->
            Button(
                onClick = item.onClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = item.text)
            }
        }
    }
}
