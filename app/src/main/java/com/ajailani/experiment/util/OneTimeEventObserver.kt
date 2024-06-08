package com.ajailani.experiment.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun OneTimeEventObserver(
    event: Flow<OneTimeEvent>,
    onNavigate: ((String) -> Unit)? = null,
    onShowToast: ((String?) -> Unit)? = null
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(event, lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            event.collect {
                when (it) {
                    is OneTimeEvent.NavigateEvent -> {
                        onNavigate?.invoke(it.route)
                    }

                    is OneTimeEvent.ShowToastEvent -> {
                        onShowToast?.invoke(it.message)
                    }
                }
            }
        }
    }
}