package com.example.autohub.presentation.theme

import androidx.compose.ui.graphics.Color

/**
 * Представляет основные цвета приложения (Light Colors).
 */
data class Colors(
    val unfocusedTextFieldColor: Color = Color(0xFFEEEEEE),
    val focusedTextFieldColor: Color = Color(0xFFFFE9E9),
    val labelColor: Color = Color(0xFF606060),
    val borderColor: Color = Color(0xFFFF4B4B),
    val containerColor: Color = Color(0xFFDC2D2D),
    val cardColor: Color = Color(0xFFFFF1F1),
    val barColor: Color = Color(0xFFF3F3F3),
    val white: Color = Color(0xFFFDFDFD),
    val containerColorAlpha50: Color = Color(0xFFDC2D2D).copy(alpha = 0.5f),
    val placeholderColor: Color = Color.LightGray,
    val transparent: Color = Color.Transparent,
    val textColor: Color = Color.Black,
    val linkTextColor: Color = Color.Blue,
    val userOnlineColor: Color = Color.Green,
    val userOfflineColor: Color = Color.Gray,
    val dateChatColor: Color = Color.LightGray,
)

val lightColors = Colors()