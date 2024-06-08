package com.ajailani.experiment.ui.screen.experiment

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.ajailani.experiment.R
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StickySectionScreen() {
    val density = LocalDensity.current
    val lazyListState = rememberLazyListState()
    val stickySectionAlpha by animateFloatAsState(
        targetValue = lazyListState.run {
            when {
                firstVisibleItemIndex == 1 -> {
                    (firstVisibleItemScrollOffset.toFloat() / layoutInfo.visibleItemsInfo[1].offset.toFloat())
                }
                firstVisibleItemIndex >= 2 -> 1f
                else -> 0f
            }
        },
        label = ""
    )

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemScrollOffset }
            .collect {
                Log.d("StickySwapScreen", "lazyList offset: $it")
                Log.d("StickySwapScreen", "lazyList first visible index: ${lazyListState.firstVisibleItemIndex}")
            }
    }

    LaunchedEffect(stickySectionAlpha) {
        Log.d("StickySwapScreen", "stickySectionAlpha= $stickySectionAlpha")

        /*when (stickySectionAlpha) {
            in 0.1f..0.5f -> {
                lazyListState.animateScrollToItem(1)
            }
            in 0.6f..1f -> {
                lazyListState.animateScrollToItem(2)
            }
        }*/
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .aspectRatio(16f / 9f)
                .background(color = Color.Gray)
        )
        LazyColumn(
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item(key = "header") {
                HeaderContent()
            }

            item(key = "green_section") {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Green)
                )
            }

            stickyHeader(key = "sticky_section") {
                AnimatedVisibility(visible = lazyListState.firstVisibleItemIndex >= 1) {
                    Box(
                        modifier = Modifier
                            .graphicsLayer {
                                alpha = stickySectionAlpha
                            }
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Sticky",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            items(20) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth()
                        .height(80.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.DarkGray)
                )
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}

@Composable
fun HeaderContent() {
    Column(modifier = Modifier.padding(10.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In libero diam, bibendum a massa id, scelerisque vehicula diam. Nunc aliquam est et molestie blandit")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Gray)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StickySwapScreenPreview() {
    StickySectionScreen()
}

enum class GreenSectionState {
    START, END
}