package hz.wq.lib.testCompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.delay

/**
 * 创建者: W~Q
 * 创建时间:   2024/8/5 17:09
 * 描述: TODO
 */
object TestAssembly {
    val items: MutableList<@Composable () -> Unit> = mutableListOf()

    init {

        items.add { SwitchButton() }
        items.add { InfiniteProgress() }
    }
}

@Composable
fun SwitchButton() {
    var isChecked by remember { mutableStateOf(false) }
    Switch(checked = false, onCheckedChange = { isChecked = it })
}


@Composable
fun InfiniteProgress() {
    var progress by remember { mutableStateOf(0f) }

    // 使用 LaunchedEffect 来启动一个协程，该协程会更新 progress 值
    LaunchedEffect(key1 = true) {
        while (true) {
            // 更新进度值
            for (i in 0..100) {
                progress = i.toFloat()
                delay(100) // 每次更新之间延迟 100 毫秒
            }
            progress = 0f // 完成后重置进度
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        LinearProgressIndicator(progress = progress / 100)
        Text(text = "$progress%")
    }
}

@Composable
fun AssemblyLayout() {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 两列
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(TestAssembly.items) { item ->
            item.invoke()
        }
    }
}
