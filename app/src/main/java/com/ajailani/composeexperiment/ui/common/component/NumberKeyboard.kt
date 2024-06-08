package com.ajailani.composeexperiment.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ajailani.composeexperiment.util.CustomButtonAttr.CustomButtonType
import com.ajailani.composeexperiment.util.CustomButtonAttr.CustomButtonType.ContainedIcon
import com.ajailani.composeexperiment.util.CustomButtonAttr.CustomButtonType.Icon
import com.ajailani.composeexperiment.util.CustomButtonAttr.CustomButtonType.Number
import com.ajailani.composeexperiment.util.CustomButtonAttr.CustomButtonIconType.DELETE

@Composable
fun NumberKeyboard(
    buttonList: List<Pair<CustomButtonType, Any>>,
    amount: String,
    onAmountChanged: (String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        LazyVerticalGrid(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceVariant),
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(10.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(buttonList) { buttonType ->
                when (buttonType.first) {
                    Number -> {
                        NumberOrIconButton(
                            value = "${buttonType.second}",
                            onButtonClicked = {
                                onAmountChanged(amount + it)
                            }
                        ) {
                            Text(text = "${buttonType.second}")
                        }
                    }

                    is Icon -> {
                        NumberOrIconButton(
                            buttonType = buttonType.first,
                            onButtonClicked = {
                                when (buttonType.first.iconType) {
                                    DELETE -> {
                                        onAmountChanged(amount.dropLast(1))
                                    }

                                    else -> {}
                                }
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Backspace,
                                contentDescription = "Icon button"
                            )
                        }
                    }

                    is ContainedIcon -> {
                        NumberOrIconButton(
                            buttonType = buttonType.first,
                            onButtonClicked = {},
                        ) {
                            Icon(
                                imageVector = Icons.Default.Backspace,
                                contentDescription = "Contained button"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun NumberOrIconButton(
    value: String? = null,
    buttonType: CustomButtonType = Number,
    onButtonClicked: (String?) -> Unit,
    content: @Composable () -> Unit
) {
    Button(
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonType.containerColor,
            contentColor = buttonType.contentColor
        ),
        onClick = { onButtonClicked(value) }
    ) {
        content()
    }
}