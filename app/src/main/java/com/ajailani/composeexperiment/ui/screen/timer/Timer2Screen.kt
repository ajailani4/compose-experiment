package com.ajailani.composeexperiment.ui.screen.timer

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun Timer2Screen(viewModel: Timer2ViewModel = koinViewModel()) {
    Column {
        Text(text = "Timer ${viewModel.timer}")
    }
}