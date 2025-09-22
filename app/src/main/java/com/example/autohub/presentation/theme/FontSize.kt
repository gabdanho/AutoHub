package com.example.autohub.presentation.theme

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * Представляет размеры шрифтов
 */
data class FontSizes(
    val message: TextUnit,
    val date: TextUnit,
)

val defaultFontSizes = FontSizes(
    message = 15.sp,
    date = 10.sp,
)