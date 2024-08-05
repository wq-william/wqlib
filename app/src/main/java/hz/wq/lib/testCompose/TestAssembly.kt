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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.material.progressindicator.LinearProgressIndicator
import hz.wq.common.util.log.LogUtils.wqLog
import hz.wq.lib.viewModel.AssemblyViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    val assemblyViewModel = hiltViewModel() as AssemblyViewModel
    var isChecked by remember { mutableStateOf(false) }
    Switch(checked = isChecked, onCheckedChange = { isChecked = it })

    // 使用 LaunchedEffect 来启动一个协程，该协程会更新 progress 值
    LaunchedEffect(key1 = true) {
        launch {
            assemblyViewModel.switchChecked.collect {
                "SwitchButton: $it".wqLog()
                isChecked = it
                isChecked = it
            }
        }
    }

//    Switch(checked = assemblyViewModel.isSwitchChecked(), onCheckedChange = { assemblyViewModel.toggleSwitch(it) })
}


@Composable
fun InfiniteProgress() {
    val assemblyViewModel = hiltViewModel() as AssemblyViewModel
    var progress by remember { mutableIntStateOf(0) }

    LaunchedEffect(progress) {
        assemblyViewModel.updateProgressPercentage(progress.toInt())
    }

    // 使用 LaunchedEffect 来启动一个协程，该协程会更新 progress 值
    LaunchedEffect(key1 = true) {
        while (true) {
            // 更新进度值
            for (i in 0..100) {
                progress = i
                delay(30) // 每次更新之间延迟 100 毫秒
            }
            progress = 0 // 完成后重置进度
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
//        LinearProgressIndicator(progress = progress / 100)
        LinearProgressIndicator(progress = { progress / 100f })
        Text(text = "$progress%")
    }
}

@Composable
fun AssemblyLayout() {

    LazyVerticalGrid(
        columns = GridCells.Fixed(1), // 两列
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(TestAssembly.items) { item ->
            item.invoke()
        }
    }
}
