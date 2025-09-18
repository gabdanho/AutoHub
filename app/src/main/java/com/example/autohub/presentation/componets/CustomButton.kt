package com.example.autohub.presentation.componets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.example.autohub.presentation.theme.AppTheme

/**
 * Кастомная кнопка.
 *
 * @param text Текст на кнопке.
 * @param onClick Лямбда вызывается при нажатии на кнопку.
 * @param modifier Модификатор для настройки компонента.
 * @param isEnabled Флаг активности кнопки.
 * @param border Граница кнопки.
 * @param colorButton Цвет кнопки.
 * @param textColor Цвет текста.
 * @param shape Форма кнопки.
 */
@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    border: BorderStroke = BorderStroke(AppTheme.dimens.emptySize, AppTheme.colors.transparent),
    colorButton: ButtonColors = buttonColors(
        containerColor = AppTheme.colors.containerColor
    ),
    textColor: Color = Color.White,
    shape: Shape = AppTheme.shapes.buttonShape,
) {
    Button(
        onClick = { onClick() },
        shape = shape,
        colors = colorButton,
        border = border,
        enabled = isEnabled,
        modifier = modifier.fillMaxWidth(AppTheme.dimens.buttonFillHalfWidth)
    ) {
        Text(
            text = text,
            color = textColor
        )
    }
}