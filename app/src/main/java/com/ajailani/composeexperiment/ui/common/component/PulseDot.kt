package com.ajailani.composeexperiment.ui.common.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun PulseDot() {
    val initialSize = 40.dp
    val targetSize = 80.dp
    val animatableSize = remember { Animatable(initialSize, Dp.VectorConverter) }

    LaunchedEffect(Unit) {
        while (true) {
            animatableSize.animateTo(
                targetValue = targetSize
            )
            delay(500)
            animatableSize.animateTo(
                targetValue = initialSize
            )
        }
    }

    Box(
        modifier = Modifier
            .size(animatableSize.value)
            .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
    )
}

@Preview(showBackground = true)
@Composable
private fun PulseDotPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PulseDot()
    }
}