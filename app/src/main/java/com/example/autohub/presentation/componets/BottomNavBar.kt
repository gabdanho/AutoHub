package com.example.autohub.presentation.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.autohub.R
import com.example.autohub.presentation.theme.AppTheme

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
            .background(color = AppTheme.colors.barColor)
    ) {
        IconButton(
            onClick = { onAdListClick() }
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = stringResource(id = R.string.content_ads),
                tint = AppTheme.colors.containerColor,
                modifier = Modifier.size(AppTheme.dimens.bottomIconSize)
            )
        }
        IconButton(
            onClick = { onAccountClick() }
        ) {
            Icon(
                imageVector = Icons.Default.AccountBox,
                contentDescription = stringResource(id = R.string.content_account),
                tint = AppTheme.colors.containerColor,
                modifier = Modifier.size(AppTheme.dimens.bottomIconSize)
            )
        }
        IconButton(
            onClick = { onMessageClick() }
        ) {
            Icon(
                imageVector = Icons.Default.MailOutline,
                contentDescription = stringResource(id = R.string.content_messages),
                tint = AppTheme.colors.containerColor,
                modifier = Modifier.size(AppTheme.dimens.bottomIconSize)
            )
        }
    }
}