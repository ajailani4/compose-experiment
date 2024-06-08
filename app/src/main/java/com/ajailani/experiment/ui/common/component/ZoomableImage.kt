package com.ajailani.experiment.ui.common.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize

@Composable
fun ZoomableImage(
    modifier: Modifier = Modifier,
    image: Painter,
    processGraphicsLayerChange: (Float, Float, Float, Float) -> Unit
) {
    var zoom by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var size by remember { mutableStateOf(IntSize.Zero) }
    val minScale = 1f
    val maxScale = 3f

    Log.d("Old offset", offset.toString())

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = modifier
                .graphicsLayer {
                    scaleX = zoom
                    scaleY = zoom
                    translationX = offset.x
                    translationY = offset.y

                    processGraphicsLayerChange(zoom, zoom, offset.x, offset.y)
                }
                .pointerInput(Unit) {
                    detectTransformGestures(
                        onGesture = { _, gesturePan, gestureZoom, _ ->
                            zoom = (zoom * gestureZoom).coerceIn(minScale, maxScale)

                            val newOffset = offset + gesturePan.times(zoom)
                            val maxX = size.width * (zoom - 1) / 2f
                            val maxY = size.height * (zoom - 1) / 2f

                            offset = Offset(
                                x = newOffset.x.coerceIn(-maxX, maxX),
                                y = newOffset.y.coerceIn(-maxY, maxY)
                            )
                        }
                    )
                }
                .onSizeChanged {
                    size = it
                },
            painter = image,
            contentDescription = "Test image",
            contentScale = ContentScale.FillBounds
        )
    }
}