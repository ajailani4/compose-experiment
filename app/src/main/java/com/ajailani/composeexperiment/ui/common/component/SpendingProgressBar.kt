package com.ajailani.composeexperiment.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import java.math.BigDecimal

@Composable
fun SpendingProgressBar() {
    val density = LocalDensity.current
    val progressBarMaxWidth = LocalConfiguration.current.screenWidthDp.dp - 40.dp

    val spending = remember { BigDecimal(10_000_000) }
    val allocated = remember { BigDecimal(500_000) }
    var halfAllocatedTextWidth by remember { mutableStateOf(0.dp) }
    val allocatedThresholdOffsetX = remember {
        if (spending > allocated) {
            (allocated.toFloat() / spending.toFloat()).coerceIn(0f, 1f) * progressBarMaxWidth
        } else 0.dp
    }

    Column {
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Dining",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 20.sp
                )
            )
            Text(
                text = "Rp$spending",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 20.sp
                )
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(contentAlignment = Alignment.CenterStart) {
            Box(
                modifier = Modifier
                    .background(color = Color(0xFFA0A0A0), shape = CircleShape)
                    .fillMaxWidth()
                    .height(2.dp)
            )
            Box(
                modifier = Modifier
                    .background(color = Color(0xFF71DBD3), shape = RoundedCornerShape(2.dp))
                    .width(progressBarMaxWidth * (spending.toFloat() / allocated.toFloat()).coerceIn(0f, 1f))
                    .height(8.dp)
            )

            if (spending > allocated) {
                Box(
                    modifier = Modifier
                        .offset(x = allocatedThresholdOffsetX)
                        .background(color = Color(0xFF000000))
                        .width(2.dp)
                        .height(16.dp)
                )
            }
        }
        Text(
            modifier = Modifier
                .offset(
                    x = allocatedThresholdOffsetX.let {
                        when (it) {
                            in (halfAllocatedTextWidth..progressBarMaxWidth - halfAllocatedTextWidth) ->
                                it - halfAllocatedTextWidth

                            in (progressBarMaxWidth - halfAllocatedTextWidth..progressBarMaxWidth) ->
                                progressBarMaxWidth - (halfAllocatedTextWidth * 2)

                            else -> 0.dp
                        }
                    }
                )
                .onGloballyPositioned {
                    with(density) {
                        halfAllocatedTextWidth = it.size.width.toDp() / 2
                    }
                },
            text = "Allocated: Rp$allocated"
        )
    }
}