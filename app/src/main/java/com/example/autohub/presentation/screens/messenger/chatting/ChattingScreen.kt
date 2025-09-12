package com.example.autohub.presentation.screens.messenger.chatting

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.autohub.R
import com.example.autohub.presentation.model.messenger.Message
import com.example.autohub.presentation.model.user.User
import com.example.autohub.presentation.model.user.UserStatus
import com.example.autohub.presentation.model.messenger.ChatSideEffect
import com.example.autohub.presentation.theme.cardColor
import com.example.autohub.presentation.theme.containerColor
import kotlinx.coroutines.launch

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
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopListening()
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
        bottomBar = {
            MessageInputField(
                text = uiState.messageTextValue,
                onValueChange = { viewModel.updateMessageTextValue(value = it) },
                onSendMessageClick = {
                    viewModel.sendMessage()
                    coroutineScope.launch {
                        listState.scrollToItem(messages.size - 1)
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        val circleSize = with(LocalDensity.current) { 4.dp.toPx() }

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(cardColor)
                    .clickable { viewModel.onParticipantClick(user = uiState.participantData) }
            ) {

                Box {
                    AsyncImage(
                        model = uiState.participantData.image,
                        contentDescription = stringResource(id = R.string.content_buyer_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(60.dp)
                            .clip(CircleShape)
                            .border(2.dp, containerColor, CircleShape)
                    )
                    Canvas(
                        modifier = Modifier
                            .size(4.dp)
                            .padding(8.dp)
                    ) {
                        drawCircle(
                            radius = circleSize,
                            color = if (participantStatus == UserStatus.Online) Color.Green else Color.Gray
                        )
                    }
                }
                Text(
                    text = stringResource(
                        id = R.string.send_message_buyer_name,
                        uiState.participantData.firstName,
                        uiState.participantData.lastName
                    ),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.W300,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize()
            ) {
                items(messages) { message ->
                    if (message.receiverUID == uiState.authUserData.uid && !message.isRead)
                        viewModel.markMessageAsRead(messageId = message.id)
                    UserMessage(
                        text = message.text,
                        time = message.formattedData,
                        authUserId = uiState.authUserData.uid,
                        senderUid = message.senderUid,
                        isRead = message.isRead
                    )
                }
            }
        }
    }
}

@Composable
private fun UserMessage(
    text: String,
    time: String,
    authUserId: String,
    senderUid: String,
    isRead: Boolean,
    modifier: Modifier = Modifier,
) {
    val windowInfo = LocalWindowInfo.current
    val maxWidth = with(LocalDensity.current) {
        (windowInfo.containerSize.width * 0.7f).toDp()
    }

    Row(
        horizontalArrangement = if (senderUid == authUserId) Arrangement.End else Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            elevation = CardDefaults.cardElevation(2.dp),
            colors = CardDefaults.cardColors(cardColor),
            modifier = modifier
                .widthIn(max = maxWidth)
                .padding(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                if (!isRead && authUserId == senderUid) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(7.dp)
                            .background(color = containerColor, shape = CircleShape)
                    ) { }
                }
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = 15.sp)) {
                            append(text = "$text ")
                        }
                        withStyle(style = SpanStyle(color = Color.LightGray, fontSize = 10.sp)) {
                            append(text = time)
                        }
                    }, modifier = Modifier.padding(8.dp)
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
            .padding(8.dp)
            .border(width = 2.dp, color = containerColor.copy(alpha = 0.5f))
    ) {
        TextField(
            value = text,
            onValueChange = { onValueChange(it) },
            placeholder = { Text(text = stringResource(id = R.string.placeholder_message)) },
            maxLines = 6,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
            ),
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        )
        IconButton(
            onClick = { onSendMessageClick() }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = stringResource(id = R.string.content_send_message),
                tint = containerColor
            )
        }
    }
}

@Composable
private fun HandleSideEffects(
    chatSideEffect: ChatSideEffect,
    listState: LazyListState,
    messages: List<Message>,
    changeSideEffect: (ChatSideEffect) -> Unit,
) {
    LaunchedEffect(messages) {
        when (chatSideEffect) {
            ChatSideEffect.ScrollToLastMessage -> {
                println("ScrollToLastMessage")
                if (messages.isNotEmpty()) {
                    listState.scrollToItem(messages.size - 1)
                    changeSideEffect(ChatSideEffect.StayInLastMessages)
                }
            }

            ChatSideEffect.StayInLastMessages -> {
                println("StayInLastMessages")
                if (messages.isNotEmpty() && listState.isScrolledToTheEnd()) {
                    listState.scrollToItem(messages.size - 1)
                }
            }
        }
    }
}

private fun LazyListState.isScrolledToTheEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 2