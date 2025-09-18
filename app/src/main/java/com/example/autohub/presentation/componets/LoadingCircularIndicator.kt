package com.example.autohub.presentation.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.autohub.presentation.theme.AppTheme

/**
 * Круговой индикатор загрузки.
 *
 * @param modifier Модификатор для настройки компонента.
 */
@Composable
fun LoadingCircularIndicator(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            color = AppTheme.colors.containerColor
        )
    }
}