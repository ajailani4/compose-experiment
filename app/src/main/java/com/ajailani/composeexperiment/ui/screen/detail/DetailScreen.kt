package com.ajailani.composeexperiment.ui.screen.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ajailani.composeexperiment.util.PageViewTracker

@Composable
fun DetailScreen(username: String?) {
    PageViewTracker("Detail")

    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .imePadding()
            .fillMaxSize()
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "This is Detail Screen")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = username ?: "-")
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = text,
                onValueChange = { text = it }
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Okay")
        }
    }
}