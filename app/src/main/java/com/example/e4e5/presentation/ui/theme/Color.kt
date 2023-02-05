package com.example.e4e5.presentation.ui.theme

import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val GrayTextFieldBackground = Color(0xFFf0f0f0)

@Composable
fun outlinedTextFieldColors(): TextFieldColors {
    return TextFieldDefaults.outlinedTextFieldColors(
        backgroundColor = GrayTextFieldBackground,
        unfocusedBorderColor = GrayTextFieldBackground,
        focusedBorderColor = GrayTextFieldBackground,
    )
}
