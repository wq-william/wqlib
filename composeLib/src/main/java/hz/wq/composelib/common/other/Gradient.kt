package hz.wq.composelib.common.other

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import hz.wq.common.util.log.LogUtils.wqLog
import hz.wq.composelib.common.other.Gradient.GradientDirectionType

/**
 * 创建者: W~Q
 * 创建时间:   2024/7/22 15:05
 * 描述: 渐变
 */
object Gradient {
    enum class GradientDirectionType {
        LEFT_RIGHT,
        RIGHT_LEFT,
        TOP_BOTTOM,
        BOTTOM_TOP,
        ;
    }

    fun shadow(
        gradientDirectionType: GradientDirectionType = GradientDirectionType.TOP_BOTTOM,
    ): Brush {
        var startOffset = Offset.Zero
        var endOffset = Offset.Zero
        when (gradientDirectionType) {
            GradientDirectionType.LEFT_RIGHT -> {
                startOffset = Offset.Zero
                endOffset = Offset(x = Float.POSITIVE_INFINITY, y = 0f)
            }

            GradientDirectionType.RIGHT_LEFT -> {
                startOffset = Offset(x = Float.POSITIVE_INFINITY, y = 0f)
                endOffset = Offset.Zero
            }

            GradientDirectionType.TOP_BOTTOM -> {
                startOffset = Offset.Zero
                endOffset = Offset(x = 0f, y = Float.POSITIVE_INFINITY)
            }

            GradientDirectionType.BOTTOM_TOP -> {
                startOffset = Offset(0f, Float.POSITIVE_INFINITY)
                endOffset = Offset.Zero
            }
        }
        return Brush.linearGradient(
            colors = listOf(Color.Black.copy(alpha = 0.1f), Color.Transparent),
            start = startOffset,
            end = endOffset,
        )
    }


}


@Composable
fun BoxShadow(
    alignment: Alignment = Alignment.TopStart,
    gradientDirectionType: GradientDirectionType = GradientDirectionType.TOP_BOTTOM,
    width: Dp? = null,
    heightDp: Dp? = null,
) {
    Box(modifier = Modifier.fillMaxSize()) {
//        Box(
//            modifier = Modifier
//                .align(Alignment.BottomStart)
//                .fillMaxWidth()
//                .height(heightDp!!) // 设置阴影的高度
//                .background(Gradient.shadow(GradientDirectionType.BOTTOM_TOP))
//        )
        var modifier = Modifier
            .align(alignment)
            .background(Gradient.shadow(gradientDirectionType))
//            .fillMaxWidth()
//            .height(heightDp!!)
        modifier = width?.let { modifier.width(it) } ?: run { modifier.fillMaxWidth() }
        modifier = heightDp?.let { modifier.height(it) } ?: run { modifier.fillMaxHeight() }
        Box(modifier = modifier)
    }

}














