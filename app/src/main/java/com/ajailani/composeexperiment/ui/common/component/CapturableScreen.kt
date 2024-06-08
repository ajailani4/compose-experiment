package com.ajailani.composeexperiment.ui.common.component

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun CapturableScreen(
    view: MutableState<View?>,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val composeView = remember { ComposeView(context) }
    
    AndroidView(factory = { composeView.apply { setContent(content) } })

    view.value = composeView
}