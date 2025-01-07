package com.ajailani.composeexperiment.ui.common.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RippleDot() {
    val initialSize = 40.dp
    val targetSize = 80.dp
    val initialAlpha = 1f
    val targetAlpha = 0f
    val animatableSize = remember { Animatable(initialSize, Dp.VectorConverter) }
    val animatableAlpha = remember { Animatable(initialAlpha, Float.VectorConverter) }

    LaunchedEffect(Unit) {
        while (true) {
            launch {
                animatableSize.animateTo(
                    targetValue = targetSize,
                    animationSpec = tween(durationMillis = 800)
                )
            }
            launch {
                animatableAlpha.animateTo(
                    targetValue = targetAlpha,
                    animationSpec = tween(durationMillis = 800)
                )
            }

            delay(1000)
            animatableSize.snapTo(initialSize)
            animatableAlpha.snapTo(initialAlpha)
        }
    }

    Box(
        modifier = Modifier
            .size(animatableSize.value)
            .alpha(animatableAlpha.value)
            .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
    )
}

@Preview(showBackground = true)
@Composable
private fun RippleDotPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RippleDot()
    }
}