package com.example.autohub.ui.componets

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.autohub.ui.theme.containerColor

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    FloatingActionButton(
        onClick = { onBackClick() },
        containerColor = containerColor,
        contentColor = Color.White,
        shape = RoundedCornerShape(25.dp),
        modifier = modifier
            .padding(8.dp)
            .size(40.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Вернуться на прошлый экран"
        )
    }
}