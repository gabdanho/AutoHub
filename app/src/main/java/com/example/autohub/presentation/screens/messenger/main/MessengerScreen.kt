package com.example.autohub.presentation.screens.messenger.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.autohub.R
import com.example.autohub.presentation.model.messenger.ChatConservation
import com.example.autohub.presentation.componets.BottomNavBar
import com.example.autohub.presentation.componets.InfoPlaceholder
import com.example.autohub.presentation.componets.LoadingCircularIndicator
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.messenger.ChatStatus
import com.example.autohub.presentation.model.user.UserStatus
import com.example.autohub.presentation.theme.AppTheme
import com.example.autohub.presentation.utils.showUiMessage

private const val CHAT_MAX_LINES = 1

@Composable
fun MessengerScreen(
    modifier: Modifier = Modifier,
    viewModel: MessengerScreenViewModel = hiltViewModel<MessengerScreenViewModel>(),
) {
    val uiState = viewModel.uiState.collectAsState().value
    val chats = viewModel.chats.collectAsState().value
    val participantsStatus = viewModel.chatsStatus.collectAsState().value

    val context = LocalContext.current

    BackHandler {
        viewModel.onBackButtonClick()
    }

    LaunchedEffect(uiState.uiMessage) {
        context.showUiMessage(uiMessage = uiState.uiMessage) {
            viewModel.clearMessage()
        }
    }

    Scaffold(
        topBar = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = AppTheme.colors.barColor)
                    .padding(AppTheme.dimens.extraSmall)
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
                onMessageClick = { viewModel.onMessageClick() }
            )
        }
    ) { innerPadding ->
        when (uiState.loadingState) {
            is LoadingState.Success -> {
                if (chats.isNotEmpty()) {
                    LazyColumn(
                        modifier = modifier
                            .padding(innerPadding)
                            .padding(AppTheme.dimens.extraSmall)
                    ) {
                        items(chats) { chat ->
                            participantsStatus[chat.uid]?.let {
                                ChatCard(
                                    chatConservation = chat,
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
                            .padding(AppTheme.dimens.extraSmall)
                    ) {
                        Text(
                            text = stringResource(id = R.string.text_messeger_is_empty),
                            color = AppTheme.colors.placeholderColor
                        )
                    }
                }
            }

            is LoadingState.Error -> {
                InfoPlaceholder(
                    textRes = R.string.error_to_show_chat,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                )
            }

            is LoadingState.Loading -> {
                LoadingCircularIndicator(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                )
            }

            null -> {}
        }
    }
}

@Composable
private fun ChatCard(
    chatConservation: ChatConservation,
    chatStatus: ChatStatus,
    onAnswerClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val circleSize = with(LocalDensity.current) { AppTheme.dimens.circleStatusSize.toPx() }
    val onlineColor = AppTheme.colors.userOnlineColor
    val offlineColor = AppTheme.colors.userOfflineColor

    Card(
        elevation = CardDefaults.cardElevation(AppTheme.dimens.smallBorderSize),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.cardColor),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = AppTheme.dimens.extraSmall)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(AppTheme.dimens.extraSmall)
                .fillMaxWidth()
                .clickable {
                    onAnswerClick(chatConservation.uid)
                }
        ) {
            Box {
                AsyncImage(
                    model = chatConservation.imageUrl,
                    contentDescription = stringResource(id = R.string.content_user_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(AppTheme.dimens.chatUserImage)
                        .clip(CircleShape)
                        .border(AppTheme.dimens.smallBorderSize, AppTheme.colors.containerColor, CircleShape)
                )
                Canvas(modifier = Modifier.size(AppTheme.dimens.circleStatusSize)) {
                    drawCircle(
                        radius = circleSize,
                        color = if (chatStatus.status == UserStatus.Online) onlineColor else offlineColor
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(start = AppTheme.dimens.extraSmall)
                    .weight(AppTheme.dimens.chatInfoWidth)
                    .fillMaxWidth()
            ) {
                Text(
                    text = chatConservation.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = CHAT_MAX_LINES,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = chatConservation.lastMessage,
                    maxLines = CHAT_MAX_LINES,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            if (chatStatus.countUnreadMessages != 0) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(AppTheme.dimens.unreadMessagesCircle)
                        .background(color = AppTheme.colors.containerColor, shape = CircleShape)
                ) {
                    Text(
                        text = chatStatus.countUnreadMessages.toString(),
                        color = AppTheme.colors.white,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}