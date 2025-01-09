package com.example.autohub.ui.componets

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
import androidx.compose.ui.unit.dp
import com.example.autohub.ui.theme.barColor
import com.example.autohub.ui.theme.containerColor

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    onAdListClick: () -> Unit,
    onAccountClick: () -> Unit,
    onMessageClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(barColor)
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

//@Preview
//@Composable
//private fun BottomNavBarPreview() {
//    BottomNavBar({ }, { }, { })
//}