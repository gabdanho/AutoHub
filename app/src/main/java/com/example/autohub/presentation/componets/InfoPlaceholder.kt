package com.example.autohub.presentation.componets

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.autohub.presentation.theme.AppTheme

/**
 * Плейсхолдер для отображения информационного текста.
 *
 * @param textRes Ресурс текста.
 * @param modifier Модификатор для настройки компонента.
 */
@Composable
fun InfoPlaceholder(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = textRes),
            color = AppTheme.colors.placeholderColor
        )
    }
}