package com.ajailani.composeexperiment.ui.screen.timer

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@Composable
fun Timer1Screen(
    navController: NavController,
    viewModel: Timer1ViewModel = koinViewModel()
) {
    Column {
        Button(onClick = { viewModel.saveTimer() }) {
            Text(text = "Start timer")
        }
        Text(text = "Timer ${viewModel.timer}")
        Button(onClick = { navController.navigate("timer2") }) {
            Text(text = "Go to Timer2")
        }
    }
}