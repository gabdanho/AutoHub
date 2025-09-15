package com.example.autohub.presentation.screens.messenger.chatting

import android.widget.Toast
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.autohub.R
import com.example.autohub.presentation.componets.InfoPlaceholder
import com.example.autohub.presentation.componets.LoadingCircularIndicator
import com.example.autohub.presentation.mapper.resources.StringToResourceIdMapperImpl
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.messenger.Message
import com.example.autohub.presentation.model.user.User
import com.example.autohub.presentation.model.user.UserStatus
import com.example.autohub.presentation.theme.AppTheme

private const val MAX_LINES_MESSAGES = 6

@Composable
fun ChattingScreen(
    participant: User,
    modifier: Modifier = Modifier,
    viewModel: ChattingScreenViewModel = hiltViewModel<ChattingScreenViewModel>(),
) {
    val uiState = viewModel.uiState.collectAsState().value
    val participantStatus = viewModel.participantStatus.collectAsState().value
    val messages = viewModel.messages.collectAsState().value
    val chatSideEffect = viewModel.chatSideEffect.collectAsState().value
    val listState = rememberLazyListState()
    val context = LocalContext.current

    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopListening()
        }
    }

    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            val resId = StringToResourceIdMapperImpl().map(uiState.message)
            Toast.makeText(context, context.getString(resId), Toast.LENGTH_LONG).show()
            viewModel.clearMessage()
        }
    }

    LaunchedEffect(participant) {
        viewModel.initChat(participant = participant)
    }

    HandleSideEffects(
        chatSideEffect = chatSideEffect,
        listState = listState,
        messages = messages,
        changeSideEffect = { viewModel.changeChatSideEffect(it) }
    )

    Scaffold(
        topBar = {
            ParticipantTopBar(
                imageUrl = uiState.participantData.image,
                firstName = uiState.participantData.firstName,
                lastName = uiState.participantData.lastName,
                participantStatus = participantStatus,
                onParticipantClick = { viewModel.onParticipantClick(user = uiState.participantData) }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        when (uiState.loadingState) {
            is LoadingState.Success -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                        .imePadding()
                ) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.weight(AppTheme.dimens.fullWeight)
                    ) {
                        items(messages) { message ->
                            if (message.receiverId == uiState.authUserData.uid && !message.isRead)
                                viewModel.markMessageAsRead(messageId = message.id)
                            UserMessage(
                                text = message.text,
                                time = message.timeFormatted,
                                authUserId = uiState.authUserData.uid,
                                senderId = message.senderId,
                                isRead = message.isRead
                            )
                        }
                    }

                    MessageInputField(
                        text = uiState.messageTextValue,
                        onValueChange = { viewModel.updateMessageTextValue(value = it) },
                        onSendMessageClick = {
                            viewModel.sendMessage()
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
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
private fun ParticipantTopBar(
    imageUrl: String,
    firstName: String,
    lastName: String,
    participantStatus: UserStatus,
    onParticipantClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(AppTheme.colors.cardColor)
            .clickable { onParticipantClick() }
    ) {

        Box {
            val circleSize = with(LocalDensity.current) { AppTheme.dimens.circleStatusSize.toPx() }
            val onlineColor = AppTheme.colors.userOnlineColor
            val offlineColor = AppTheme.colors.userOfflineColor

            AsyncImage(
                model = imageUrl,
                contentDescription = stringResource(id = R.string.content_participant_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(AppTheme.dimens.extraSmall)
                    .size(AppTheme.dimens.chattingProfileImageSize)
                    .clip(CircleShape)
                    .border(AppTheme.dimens.smallBorderSize, AppTheme.colors.containerColor, CircleShape)
            )
            Canvas(
                modifier = Modifier
                    .size(AppTheme.dimens.circleStatusSize)
                    .padding(AppTheme.dimens.extraSmall)
            ) {
                drawCircle(
                    radius = circleSize,
                    color = if (participantStatus == UserStatus.Online) onlineColor else offlineColor
                )
            }
        }
        Text(
            text = stringResource(id = R.string.text_participant_name, firstName, lastName),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.W300,
            modifier = Modifier.padding(start = AppTheme.dimens.extraSmall)
        )
    }
}

@Composable
private fun UserMessage(
    text: String,
    time: String,
    authUserId: String,
    senderId: String,
    isRead: Boolean,
    modifier: Modifier = Modifier,
) {
    val windowInfo = LocalWindowInfo.current
    val maxWidth = with(LocalDensity.current) {
        (windowInfo.containerSize.width * AppTheme.dimens.messageMaxSize).toDp()
    }

    Row(
        horizontalArrangement = if (senderId == authUserId) Arrangement.End else Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            elevation = CardDefaults.cardElevation(AppTheme.dimens.messageElevation),
            colors = CardDefaults.cardColors(AppTheme.colors.cardColor),
            modifier = modifier
                .widthIn(max = maxWidth)
                .padding(AppTheme.dimens.extraSmall)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = AppTheme.dimens.ultraSmall)
            ) {
                if (!isRead && authUserId == senderId) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(AppTheme.dimens.messageReadCircleSize)
                            .background(color = AppTheme.colors.containerColor, shape = CircleShape)
                    ) { }
                }
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = AppTheme.fonts.message)) {
                            append(text = "$text ")
                        }
                        withStyle(style = SpanStyle(color = AppTheme.colors.dateChatColor, fontSize = AppTheme.fonts.date)) {
                            append(text = time)
                        }
                    }, modifier = Modifier.padding(AppTheme.dimens.extraSmall)
                )
            }
        }
    }
}

@Composable
private fun MessageInputField(
    text: String,
    onValueChange: (String) -> Unit,
    onSendMessageClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(AppTheme.dimens.extraSmall)
            .border(width = AppTheme.dimens.smallBorderSize, color = AppTheme.colors.containerColorAlpha50)
    ) {
        TextField(
            value = text,
            onValueChange = { onValueChange(it) },
            placeholder = { Text(text = stringResource(id = R.string.placeholder_message)) },
            maxLines = MAX_LINES_MESSAGES,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = AppTheme.colors.white,
                focusedContainerColor = AppTheme.colors.white,
            ),
            modifier = Modifier
                .weight(AppTheme.dimens.fullWeight)
                .verticalScroll(scrollState)
        )
        IconButton(
            onClick = { onSendMessageClick() }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = stringResource(id = R.string.content_send_message),
                tint = AppTheme.colors.containerColor
            )
        }
    }
}

@Composable
private fun HandleSideEffects(
    chatSideEffect: ChattingSideEffect,
    listState: LazyListState,
    messages: List<Message>,
    changeSideEffect: (ChattingSideEffect) -> Unit,
) {
    LaunchedEffect(messages) {
        when (chatSideEffect) {
            ChattingSideEffect.ScrollToLastMessage -> {
                if (messages.isNotEmpty()) {
                    listState.animateScrollToItem(messages.size - 1)
                    changeSideEffect(ChattingSideEffect.StayInLastMessages)
                }
            }

            ChattingSideEffect.StayInLastMessages -> {
                if (messages.isNotEmpty() && listState.isScrolledToTheEnd()) {
                    listState.animateScrollToItem(messages.size - 1)
                }
            }
        }
    }
}

private fun LazyListState.isScrolledToTheEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 2