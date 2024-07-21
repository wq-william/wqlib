package hz.wq.composelib.common

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import hz.wq.common.util.log.LogUtils.wqLog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopAppBar(
    leftImgButton: ImgButton? = null,
    title: String = "",
    vararg rightImgs: ImgButton?
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            leftImgButton?.let {
                IconButton(onClick = it.onClick) {
                    Icon(imageVector = it.imgVector, contentDescription = "leftBtn")
                }
            }

        },
        actions = {
            rightImgs?.forEachIndexed { index, imgButton ->
                imgButton?.let {
                    IconButton(onClick = it.onClick) {
                        Icon(imageVector = it.imgVector, contentDescription = "rightBtn$index")
                    }
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(),
        modifier = Modifier.statusBarsPadding()
    )
}

class ImgButton(
    var imgVector: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    var onClick: () -> Unit,
)


@Preview(showBackground = true)
@Composable
private fun PreviewCustomTopAppBar() {
    CommonTopAppBar(
        leftImgButton = ImgButton(Icons.AutoMirrored.Filled.ArrowBack) {
            "leftBtn".wqLog()
        },
        title = "title",
        rightImgs = arrayOf(
            ImgButton(Icons.Filled.Close) {
                "rightBtn".wqLog()
            }
        )
    )
}