package com.ajailani.composeexperiment.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ajailani.composeexperiment.ui.common.component.NumberKeyboard
import com.ajailani.composeexperiment.util.CustomButtonAttr

@Composable
fun CustomNumberKeyboardScreen() {
    Column {
        var amount by remember { mutableStateOf("") }
        val buttonList = listOf(
            Pair(CustomButtonAttr.CustomButtonType.Number, "1"),
            Pair(CustomButtonAttr.CustomButtonType.Number, "2"),
            Pair(CustomButtonAttr.CustomButtonType.Number, "3"),
            Pair(CustomButtonAttr.CustomButtonType.Number, "4"),
            Pair(CustomButtonAttr.CustomButtonType.Number, "5"),
            Pair(CustomButtonAttr.CustomButtonType.Number, "6"),
            Pair(CustomButtonAttr.CustomButtonType.Number, "7"),
            Pair(CustomButtonAttr.CustomButtonType.Number, "8"),
            Pair(CustomButtonAttr.CustomButtonType.Number, "9"),
            Pair(CustomButtonAttr.CustomButtonType.Number, "000"),
            Pair(CustomButtonAttr.CustomButtonType.Number, "0"),
            Pair(CustomButtonAttr.CustomButtonType.Icon(type = CustomButtonAttr.CustomButtonIconType.DELETE), Icons.Default.Backspace)
        )

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = amount
        )
        NumberKeyboard(
            buttonList = buttonList,
            amount = amount,
            onAmountChanged = {
                amount = it
            }
        )
    }
}