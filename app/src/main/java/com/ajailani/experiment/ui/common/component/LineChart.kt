package com.ajailani.experiment.ui.common.component

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    records: List<Double>,
    lineColor: Color = MaterialTheme.colorScheme.primary
) {
    val density = LocalDensity.current

    Canvas(modifier = modifier) {
        val lineDistance = (size.width) / records.size
        val spacing = lineDistance / 2
        val higherValue = records.max()
        val lowerValue = records.min()
        val height = size.height
        var lastX = 0f

        val strokePath = Path().apply {
            records.forEachIndexed { i, record ->
                val nextRecord = records.getOrNull(i + 1) ?: records.last()
                val leftRatio = (record - lowerValue) / (higherValue - lowerValue)
                val rightRatio = (nextRecord - lowerValue) / (higherValue - lowerValue)

                val x1 = spacing + i * lineDistance
                val y1 = height - (leftRatio * height).toFloat()
                val x2 = spacing + (i + 1) * lineDistance
                val y2 = height - (rightRatio * height).toFloat()

                if (i == 0) moveTo(x1, y1)

                lastX = (x1 + x2) / 2f

                quadraticBezierTo(x1, y1, lastX, (y1 + y2) / 2f)
            }
        }

        val fillPath = android.graphics.Path(strokePath.asAndroidPath())
            .asComposePath()
            .apply {
                lineTo(lastX, size.height - spacing)
                lineTo(spacing, size.height - spacing)
                close()
            }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    lineColor.copy(alpha = 0.5f),
                    Color.Transparent
                ),
                endY = size.height - spacing
            )
        )

        drawPath(
            path = strokePath,
            color = lineColor,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        )

        // X Axis
        for (i in records.indices) {
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "${i + 1}",
                    spacing + i * lineDistance,
                    size.height - 10,
                    Paint().apply {
                        color = android.graphics.Color.BLACK
                        textAlign = Paint.Align.CENTER
                        textSize = density.run { 14.sp.toPx() }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLineChart() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            records = listOf(
                250.0,
                0.0,
                340.0,
                680.0,
                520.0,
                930.0,
                600.0,
                458.0,
                785.0,
                840.0,
                1200.0,
                670.0
            )
        )
    }
}