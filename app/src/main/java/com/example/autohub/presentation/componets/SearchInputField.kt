package com.example.autohub.presentation.componets

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import com.example.autohub.R
import com.example.autohub.presentation.theme.AppTheme

/**
 * Поле ввода для поиска с иконкой.
 *
 * @param searchText Текущий текст поиска.
 * @param placeholder Плейсхолдер.
 * @param onSearchTextChange Лямбда вызывается при изменении текста.
 * @param onDoneClick Лямбда вызывается при нажатии "Done" на клавиатуре.
 * @param modifier Модификатор для настройки компонента.
 * @param textColor Цвет текста.
 * @param iconColor Цвет иконки.
 * @param shape Форма поля.
 * @param colors Цвета поля.
 */
@Composable
fun SearchInputField(
    searchText: String,
    placeholder: String,
    onSearchTextChange: (String) -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = AppTheme.colors.placeholderColor,
    iconColor: Color = AppTheme.colors.placeholderColor,
    shape: Shape = AppTheme.shapes.textFieldShape,
    colors: TextFieldColors = TextFieldDefaults.colors(
        unfocusedContainerColor = AppTheme.colors.transparent,
        focusedContainerColor = AppTheme.colors.transparent,
        unfocusedIndicatorColor = AppTheme.colors.containerColor,
        focusedIndicatorColor = AppTheme.colors.containerColor
    ),
) {
    TextField(
        value = searchText,
        onValueChange = { onSearchTextChange(it) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                tint = iconColor,
                contentDescription = stringResource(id = R.string.content_search_field)
            )
        },
        singleLine = true,
        placeholder = {
            Text(
                text = placeholder,
                color = textColor
            )
        },
        shape = shape,
        colors = colors,
        keyboardActions = KeyboardActions(
            onDone = {
                onDoneClick()
            }
        ),
        modifier = modifier
    )

}