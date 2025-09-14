package com.example.autohub.presentation.theme

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * Представляет размеры шрифтов
 */
data class FontSizes(
    val message: TextUnit = 15.sp,
    val date: TextUnit = 10.sp
)

val defaultFontSizes = FontSizes()