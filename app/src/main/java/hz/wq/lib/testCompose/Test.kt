package hz.wq.lib.testCompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class GridButtonItem(val text: String, var onClick: () -> Unit = { })

@Composable
fun GridButtonLayout(items: List<GridButtonItem> = gridButtonItems) {

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