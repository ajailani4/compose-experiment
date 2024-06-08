package com.ajailani.experiment.ui.common.component

import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ajailani.experiment.util.CreditCardVisualTransformation

@Composable
fun CreditCardTextField(
    modifier: Modifier = Modifier,
    value: String,
    setValue: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = setValue,
        visualTransformation = CreditCardVisualTransformation()
    )
}