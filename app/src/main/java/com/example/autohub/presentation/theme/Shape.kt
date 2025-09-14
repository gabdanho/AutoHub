package com.example.autohub.presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

/**
 * Представляет основные формы элементов.
 */
data class Shapes(
    val textFieldShape: RoundedCornerShape = RoundedCornerShape(20.dp),
    val buttonShape: RoundedCornerShape = RoundedCornerShape(topStart = 25.dp, bottomEnd = 25.dp),
    val carAdCardShape: RoundedCornerShape = RoundedCornerShape(bottomEnd = 15.dp, bottomStart = 15.dp),
    val backButtonShape: RoundedCornerShape = RoundedCornerShape(25.dp),
)

val defaultShapes = Shapes()