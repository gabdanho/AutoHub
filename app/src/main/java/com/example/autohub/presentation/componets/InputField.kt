package com.example.autohub.presentation.componets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.example.autohub.R
import com.example.autohub.presentation.theme.AppTheme

/**
 * Компонент поля ввода с названием, текстовым полем и с индикатором ошибки.
 *
 * @param text Текст заголовка поля (например, "Имя" или "Email").
 * @param value Текущее значение текстового поля.
 * @param onValueChange Лямбда, вызываемая при изменении значения текстового поля.
 * @param modifier Модификатор для настройки внешнего вида и поведения Row.
 * @param placeHolder Плейсхолдер текстового поля.
 * @param isSingleLine Флаг, определяющий, является ли поле однострочным.
 * @param isError Флаг отображения ошибки (если true — показывается иконка предупреждения).
 * @param keyboardType Тип клавиатуры для текстового поля (например, Email, Number и т.д.).
 * @param visualTransformation Визуальное преобразование текста (например, PasswordVisualTransformation).
 * @param placeholderColor Цвет текста плейсхолдера.
 */
@Composable
fun InputField(
    text: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeHolder: String = "",
    isSingleLine: Boolean = true,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Unspecified,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    placeholderColor: Color = AppTheme.colors.placeholderColor
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = text,
            modifier = Modifier.weight(AppTheme.dimens.fullWeight)
        )
        TextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            shape = AppTheme.shapes.textFieldShape,
            placeholder = {
                Text(
                    text = placeHolder,
                    color = placeholderColor
                )
            },
            singleLine = isSingleLine,
            visualTransformation = visualTransformation,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = AppTheme.colors.labelColor,
                focusedIndicatorColor = AppTheme.colors.labelColor
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            trailingIcon = {
                if (isError) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = stringResource(id = R.string.content_necessary_fill_field)
                    )
                }
            },
            modifier = Modifier.weight(AppTheme.dimens.inputFieldWeight)
        )
    }
}