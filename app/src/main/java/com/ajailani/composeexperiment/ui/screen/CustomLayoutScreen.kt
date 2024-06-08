package com.ajailani.composeexperiment.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomLayoutScreen() {
    BasicColumn(modifier = Modifier.padding(20.dp)) {
        Text(
            modifier = Modifier.firstBaselineToTop(24.dp),
            text = "TEST"
        )
        Text(text = "Test 2")
        Text(text = "Test 3")
        Text(text = "Test 4")
        Text(text = "Test 5")
        Text(text = "Test 6")
    }
}

private fun Modifier.firstBaselineToTop(topPadding: Dp) =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]

        val placeableY = topPadding.roundToPx() - firstBaseline
        val height = placeable.height + placeableY

        layout(placeable.width, height) {
            placeable.placeRelative(0, placeableY)
        }
    }

@Composable
fun BasicColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }

        layout(constraints.maxWidth, constraints.maxHeight) {
            var yPosition = 0

            placeables.forEach {
                it.placeRelative(x = 0, y = yPosition)

                yPosition += it.height
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomLayoutScreen() {
    CustomLayoutScreen()
}