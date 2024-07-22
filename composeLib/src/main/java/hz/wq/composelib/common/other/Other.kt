package hz.wq.composelib.common.other

import androidx.compose.material3.AlertDialog
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Constraints.Companion.Infinity

@Composable
fun LoadingScreen() {
    CircularProgressIndicator(
        modifier = Modifier
            .size(56.dp),
//            .align(Alignment.Center)
        strokeWidth = 3.dp,

        )
}


@Composable
fun GradientBackground(direction: Offset, startColor: Color, endColor: Color) {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(startColor, endColor),
        start = Offset(0f, 0f),
        end = direction
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush),
        contentAlignment = Alignment.Center
    ) {
        Text("Hello, Gradient!")
    }
}

@Preview
@Composable
fun PreviewGradientBackground() {
    GradientBackground(
//        Offset(Infinity, 0f) 用于指定渐变的方向是从左到右无限延伸
//        Offset(0f, Infinity) 则表示从上到下无限延伸。
        direction = Offset(Infinity.toFloat(), 0f), // 从左上到右下
        startColor = Color.Red,
        endColor = Color.Green
    )
}