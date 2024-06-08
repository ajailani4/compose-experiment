package com.ajailani.experiment.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ajailani.experiment.util.MixpanelUtil
import com.ajailani.experiment.util.PageViewTracker

@Composable
fun HomeScreen(
    fullName: String?,
    onNavigateToMovies: () -> Unit
) {
    PageViewTracker("Home")

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "This is Home Screen")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = fullName ?: "-")
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onNavigateToMovies) {
            Text(text = "Navigate to movie list")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { MixpanelUtil.reset() }) {
            Text(text = "Logout")
        }
    }
}