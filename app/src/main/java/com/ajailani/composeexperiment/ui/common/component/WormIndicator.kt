package com.ajailani.composeexperiment.ui.common.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun WormIndicator(
    destination: Int,
    startMoving: Boolean,
    onMoved: () -> Unit
) {
    val density = LocalDensity.current
    val initialWidth = 20.dp
    val targetWidth = 60.dp
    val animationDuration = 150
    val animatableOffset = remember { Animatable(0, Int.VectorConverter) }
    val animatableWidth = remember { Animatable(initialWidth, Dp.VectorConverter) }

    LaunchedEffect(startMoving) {
        if (startMoving) {
            animatableWidth.animateTo(
                targetValue = targetWidth,
                animationSpec = tween(durationMillis = animationDuration)
            )

            animatableOffset.animateTo(
                targetValue = destination,
                animationSpec = tween(durationMillis = animationDuration)
            )

            animatableWidth.animateTo(
                targetValue = initialWidth,
                animationSpec = tween(durationMillis = animationDuration)
            )

            onMoved()
        }
    }

    val adjustedOffset = remember {
        derivedStateOf {
            val expandedPx = density.run { animatableWidth.value.toPx() }
            val initialPx = density.run { initialWidth.toPx() }
            animatableOffset.value - (expandedPx - initialPx) / 2
        }
    }

    Box(
        modifier = Modifier
            .offset { IntOffset(x = adjustedOffset.value.toInt(), y = 0) }
            .size(width = animatableWidth.value, height = 20.dp)
            .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
    )
}

@Composable
fun WormIndicatorImplementation() {
    var destination by remember { mutableIntStateOf(0) }
    var startMoving by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row {
            Button(
                onClick = {
                    destination = 300
                    startMoving = true
                }
            ) {
                Text(text = "Move indicator")
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(
                onClick = {
                    destination = 0
                    startMoving = true
                }
            ) {
                Text(text = "Reset position")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        WormIndicator(
            destination = destination,
            startMoving = startMoving,
            onMoved = { startMoving = false }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WormIndicatorPreview() {
    WormIndicatorImplementation()
}