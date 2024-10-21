package com.ajailani.composeexperiment.ui.common.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun Showcase(
    modifier: Modifier = Modifier,
    highlightSpec: Pair<Offset, Size>,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .drawHighlight(highlightSpec.orDefault())
    ) {
        content()
    }

    with(density) {
        Tooltip(
            pointerPosition = DpOffset(
                x = (highlightSpec.second.width / 2).toDp(),
                y = 0.dp
            ),
            contentPosition = DpOffset(
                x = highlightSpec.first.x.toDp(),
                y = (highlightSpec.first.y + highlightSpec.second.height + 60f).toDp()
            )
        )
    }
}

@Composable
fun Tooltip(
    pointerPosition: DpOffset,
    contentPosition: DpOffset
) {
    Column(
        modifier = Modifier.offset(contentPosition.x, contentPosition.y)
    ) {
        Pointer(pointerPosition)
        Column(
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                .padding(8.dp)
        ) {
            Text(text = "This is a tooltip")
            Text(text = "I bring you some information")
        }
    }
}

@Composable
fun Pointer(position: DpOffset) {
    Canvas(
        modifier = Modifier
            .offset(position.x, position.y)
            .size(15.dp)
    ) {
        val path = Path().apply {
            moveTo(size.width / 2f, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }

        drawPath(
            path = path,
            color = Color.White
        )
    }
}

fun Modifier.drawHighlight(spec: Pair<Offset, Size>) =
    drawWithContent {
        drawContent()

        with(drawContext.canvas.nativeCanvas) {
            val checkpoint = saveLayer(null, null)
            drawRect(Color.Black.copy(alpha = 0.8f))
            drawRoundRect(
                color = Color.Transparent,
                topLeft = spec.first,
                size = spec.second,
                blendMode = BlendMode.Clear,
                cornerRadius = CornerRadius(20f, 20f)
            )
            restoreToCount(checkpoint)
        }
    }

fun Modifier.getHighlightSpec(spec: (Pair<Offset, Size>) -> Unit) =
    onGloballyPositioned {
        val coordinate = it.positionInRoot()
        val size = Size(it.size.width.toFloat(), it.size.height.toFloat())
        spec(
            Pair(
                Offset(coordinate.x - 10, coordinate.y - 10),
                Size(size.width * 1.2f, size.height * 1.2f)
            )
        )
    }

fun Pair<Offset, Size>?.orDefault() = this ?: Pair(Offset.Zero, Size.Zero)

@Preview(showBackground = true)
@Composable
private fun ShowcasePreview() {
    var highlightSpec by remember { mutableStateOf<Pair<Offset, Size>?>(null) }

    Showcase(highlightSpec = highlightSpec.orDefault()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.getHighlightSpec { highlightSpec = it }
            ) {
                Text(text = "Hello")
                Text(text = "This is a showcase")
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Lorem ipsum dolor sit amet")
        }
    }
}