package com.example.autohub.presentation.screens.messenger.main

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.autohub.R
import com.example.autohub.presentation.model.messenger.ChatInfo
import com.example.autohub.presentation.componets.BottomNavBar
import com.example.autohub.presentation.model.messenger.ChatStatus
import com.example.autohub.presentation.model.user.UserStatus
import com.example.autohub.presentation.theme.barColor
import com.example.autohub.presentation.theme.cardColor
import com.example.autohub.presentation.theme.containerColor

@Composable
fun MessengerScreen(
    modifier: Modifier = Modifier,
    viewModel: MessengerScreenViewModel = hiltViewModel<MessengerScreenViewModel>()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val chats = viewModel.chats.collectAsState().value
    val participantsStatus = viewModel.chatsStatus.collectAsState().value

    val context = LocalContext.current

    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopListening()
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
        viewModel.clearErrorMessage()
    }

    Scaffold(
        topBar = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = barColor)
                    .padding(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.text_messenger),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        bottomBar = {
            BottomNavBar(
                onAdListClick = { viewModel.onAdListClick() },
                onAccountClick = { viewModel.onAccountClick() },
                onMessageClick = { /* Nothing */ }
            )
        }
    ) { innerPadding ->
        if (chats.isNotEmpty()) {
            LazyColumn(
                modifier = modifier
                    .padding(innerPadding)
                    .padding(8.dp)
            ) {
                items(chats) { chat ->
                    participantsStatus[chat.uid]?.let {
                        ChatCard(
                            chatInfo = chat,
                            chatStatus = it,
                            onAnswerClick = { viewModel.onAnswerClick(uid = chat.uid) }
                        )
                    }
                }
            }
        } else {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.text_messeger_is_empty),
                    color = Color.LightGray
                )
            }
        }
    }
}

@Composable
private fun ChatCard(
    chatInfo: ChatInfo,
    chatStatus: ChatStatus,
    onAnswerClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val circleSize = with(LocalDensity.current) { 4.dp.toPx() }

    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable {
                    onAnswerClick(chatInfo.uid)
                }
        ) {
            Box {
                AsyncImage(
                    model = chatInfo.image,
                    contentDescription = stringResource(id = R.string.content_user_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .border(2.dp, containerColor, CircleShape)
                )
                Canvas(modifier = Modifier.size(4.dp)) {
                    drawCircle(
                        radius = circleSize,
                        color = if (chatStatus.status == UserStatus.Online) Color.Green else Color.Gray
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(4f)
                    .fillMaxWidth()
            ) {
                Text(
                    text = chatInfo.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = chatInfo.lastMessage,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            if (chatStatus.countUnreadMessages != 0) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(30.dp)
                        .background(color = containerColor, shape = CircleShape)
                ) {
                    Text(
                        text = chatStatus.countUnreadMessages.toString(),
                        color = Color.White,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}