package com.ajailani.composeexperiment.ui.screen.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.ajailani.composeexperiment.util.MixpanelUtil
import com.ajailani.composeexperiment.util.PageViewTracker

@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit
) {
    PageViewTracker("Register")

    var username by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            label = {
                Text(text = "Username")
            },
            value = username,
            onValueChange = { username = it }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            onNavigateToLogin()

            MixpanelUtil.identify(username)
            MixpanelUtil.track("User Registered")
        }) {
            Text(text = "Register")
        }
    }
}