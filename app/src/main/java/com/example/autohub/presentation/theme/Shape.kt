package com.example.autohub.presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

/**
 * Представляет основные формы элементов.
 */
data class Shapes(
    val textFieldShape: RoundedCornerShape,
    val buttonShape: RoundedCornerShape,
    val carAdCardShape: RoundedCornerShape,
    val backButtonShape: RoundedCornerShape,
)

val defaultShapes = Shapes(
    textFieldShape = RoundedCornerShape(20.dp),
    buttonShape = RoundedCornerShape(topStart = 25.dp, bottomEnd = 25.dp),
    carAdCardShape = RoundedCornerShape(bottomEnd = 15.dp, bottomStart = 15.dp),
    backButtonShape = RoundedCornerShape(25.dp),
)