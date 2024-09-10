package com.example.autohub.ui.componets

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
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.autohub.ui.theme.containerColor

@Composable
fun BottomNavBar(
    onAdListClick: () -> Unit,
    onAccountClick: () -> Unit,
    onMessageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { onAdListClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Объявления",
                    tint = containerColor,
                    modifier = Modifier.size(30.dp)
                )
            }
            IconButton(
                onClick = { onAccountClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = "Аккаунт",
                    tint = containerColor,
                    modifier = Modifier.size(30.dp)
                )
            }
            IconButton(
                onClick = { onMessageClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.MailOutline,
                    contentDescription = "Сообщения",
                    tint = containerColor,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun BottomNavBarPreview() {
    BottomNavBar({ }, { }, { })
}