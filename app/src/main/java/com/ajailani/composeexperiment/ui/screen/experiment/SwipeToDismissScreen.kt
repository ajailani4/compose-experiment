package com.ajailani.composeexperiment.ui.screen.experiment

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDismissScreen() {
    var visibility1 by remember { mutableStateOf(true) }
    var visibility2 by remember { mutableStateOf(true) }
    /*val dismissState = rememberDismissState(
        confirmValueChange = {
            when (it) {
                DismissValue.DismissedToEnd -> {
                    visibility1 = false
                    true
                }

                DismissValue.DismissedToStart -> {
                    visibility1 = false
                    true
                }

                DismissValue.Default -> false
            }
        }
    )

    AnimatedVisibility(visible = visibility1) {
        SwipeToDismiss(
            state = dismissState,
            background = {},
            dismissContent = {
                Card {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text(text = "Title", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Description")
                    }
                }
            }
        )
    }

    Spacer(modifier = Modifier.height(10.dp))*/

    // With fling behavior
    AnimatedVisibility(visible = visibility2) {
        Card(modifier = Modifier.swipeToDismiss(onDismissed = { visibility2 = false })) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(text = "Title", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Description")
            }
        }
    }
}

@Composable
private fun Modifier.swipeToDismiss(onDismissed: () -> Unit) = composed {
    val offsetX = remember { Animatable(0f) }

    pointerInput(Unit) {
        val decay = splineBasedDecay<Float>(this)

        coroutineScope {
            while (true) {
                val velocityTracker = VelocityTracker()

                offsetX.stop()

                awaitPointerEventScope {
                    val pointerId = awaitFirstDown().id

                    horizontalDrag(pointerId) { change ->
                        launch {
                            offsetX.snapTo(offsetX.value + change.positionChange().x)
                        }

                        velocityTracker.addPosition(
                            timeMillis = change.uptimeMillis,
                            position = change.position
                        )
                    }
                }

                val velocity = velocityTracker.calculateVelocity().x
                val targetOffsetX = decay.calculateTargetValue(
                    initialValue = offsetX.value,
                    initialVelocity = velocity
                )

                offsetX.updateBounds(
                    lowerBound = -size.width.toFloat(),
                    upperBound = size.width.toFloat()
                )

                launch {
                    if (targetOffsetX.absoluteValue <= size.width) {
                        offsetX.animateTo(targetValue = 0f, initialVelocity = velocity)
                    } else {
                        offsetX.animateDecay(initialVelocity = velocity, animationSpec = decay)
                        onDismissed()
                    }
                }
            }
        }
    }.offset {
        IntOffset(x = offsetX.value.roundToInt(), y = 0)
    }
}