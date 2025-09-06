package com.example.autohub.presentation.componets

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.autohub.R
import com.example.autohub.presentation.theme.containerColor as themeContainerColor

@Composable
fun BackButton(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = themeContainerColor,
    contentColor: Color = Color.White,
    shape: Shape = RoundedCornerShape(25.dp)
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