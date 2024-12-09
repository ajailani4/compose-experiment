package com.ajailani.composeexperiment.util

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class CustomShape(private val croppedSize: Size) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            Log.d("Cropped size", croppedSize.toString())
            val offsetX = croppedSize.width / 2
            val offsetY = croppedSize.height/ 2

            addRect(
                Rect(
                    offset = Offset(offsetX, offsetY),
                    size = croppedSize
                )
            )
        }

        return Outline.Generic(path = path)
    }
}

class QuarterCircleShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            Path().apply {
                val halfWidth = size.width / 2
                val quarterWidth = size.width / 4
                val quarterHeight = size.height / 4

                reset()
                lineTo(halfWidth + quarterWidth, 0f)
                arcTo(
                    rect = Rect(
                        topLeft = Offset(halfWidth + quarterWidth - 10, -quarterHeight),
                        bottomRight = Offset(size.width + quarterWidth + 10, quarterHeight)
                    ),
                    startAngleDegrees = 180f,
                    sweepAngleDegrees = -90f,
                    forceMoveTo = false
                )
                lineTo(size.width, size.height)
                arcTo(
                    rect = Rect(
                        topLeft = Offset(0f, -size.height),
                        bottomRight = Offset(size.width * 2, size.height)
                    ),
                    startAngleDegrees = 180f,
                    sweepAngleDegrees = -90f,
                    forceMoveTo = false
                )
                close()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomShapePreview() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(QuarterCircleShape())
            .background(color = Color.Red)
    )
}