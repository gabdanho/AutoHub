package com.example.autohub.presentation.componets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import com.example.autohub.R
import com.example.autohub.presentation.theme.AppTheme

@Composable
fun BackButton(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = AppTheme.colors.containerColor,
    contentColor: Color = Color.White,
    shape: Shape = AppTheme.shapes.backButtonShape,
) {
    FloatingActionButton(
        onClick = { onBackClick() },
        containerColor = containerColor,
        contentColor = contentColor,
        shape = shape,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.content_back_to_prev_screen)
        )
    }
}