package com.example.autohub.presentation.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.autohub.R
import com.example.autohub.presentation.theme.barColor
import com.example.autohub.presentation.theme.containerColor

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    onAdListClick: () -> Unit = { },
    onAccountClick: () -> Unit = { },
    onMessageClick: () -> Unit = { }
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(color = barColor)
    ) {
        IconButton(
            onClick = { onAdListClick() }
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = stringResource(id = R.string.content_ads),
                tint = containerColor,
                modifier = Modifier.size(30.dp)
            )
        }
        IconButton(
            onClick = { onAccountClick() }
        ) {
            Icon(
                imageVector = Icons.Default.AccountBox,
                contentDescription = stringResource(id = R.string.content_account),
                tint = containerColor,
                modifier = Modifier.size(30.dp)
            )
        }
        IconButton(
            onClick = { onMessageClick() }
        ) {
            Icon(
                imageVector = Icons.Default.MailOutline,
                contentDescription = stringResource(id = R.string.content_messages),
                tint = containerColor,
                modifier = Modifier.size(30.dp)
            )
            // TODO : Проверить как работает прикол с зеленым кругом
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.size(200.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .background(color = Color.Green, shape = CircleShape)
                )
            }
        }
    }
}