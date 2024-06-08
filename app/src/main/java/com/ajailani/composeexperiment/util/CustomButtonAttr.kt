package com.ajailani.composeexperiment.util

import androidx.compose.ui.graphics.Color

object CustomButtonAttr {
    sealed class CustomButtonType(
        val containerColor: Color = Color.White,
        val contentColor: Color = Color.Black,
        val iconType: CustomButtonIconType? = null
    ) {
        data object Number : CustomButtonType()

        data class Icon(
            val type: CustomButtonIconType
        ) : CustomButtonType(
            containerColor = Color.Transparent,
            iconType = type
        )

        data class ContainedIcon(
            val type: CustomButtonIconType
        ) : CustomButtonType(iconType = type)
    }

    enum class CustomButtonIconType {
        DELETE
    }
}