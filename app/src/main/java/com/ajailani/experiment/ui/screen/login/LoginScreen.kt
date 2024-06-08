package com.ajailani.experiment.ui.screen.login

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
import com.ajailani.experiment.util.MixpanelUtil
import com.ajailani.experiment.util.PageViewTracker

@Composable
fun LoginScreen(onNavigateToHome: (String?) -> Unit) {
    PageViewTracker("Login")

    var fullName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            label = {
                Text(text = "Full Name")
            },
            value = fullName,
            onValueChange = { fullName = it }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            onNavigateToHome(fullName)

            MixpanelUtil.updateProfile(mapOf("\$name" to fullName))
            MixpanelUtil.track("User Logged In")
        }) {
            Text(text = "Login")
        }
    }
}