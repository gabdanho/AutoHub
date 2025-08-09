package com.example.autohub.presentation.screens.messenger

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.autohub.R
import com.example.autohub.presentation.model.messenger.Message
import com.example.autohub.presentation.model.user.User
import com.example.autohub.presentation.model.user.UserStatus
import com.example.autohub.presentation.ChatViewModel
import com.example.autohub.presentation.theme.cardColor
import com.example.autohub.presentation.theme.containerColor
import com.example.autohub.data.remote.firebase.utils.getAuthUserUID
import com.example.autohub.data.utils.getBuyerStatus
import com.example.autohub.data.utils.getUserData
import com.example.autohub.data.utils.markMessagesAsRead
import com.example.autohub.data.utils.sendMessage

@Composable
fun ChattingScreen(
    buyerUID: String,
    onBuyerClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val text = remember { mutableStateOf("") }
    val context = LocalContext.current
    val buyerData = remember { mutableStateOf(User()) }
    getUserData(buyerUID) { buyerData.value = it }
    val messages by viewModel.getChat(buyerUID).observeAsState(initial = emptyList())
    val listState = rememberLazyListState()
    val isFirstRender = remember { mutableStateOf(true) }

    // programming scroll to last item
    LaunchedEffect(messages) {
        if ((messages.isNotEmpty() && listState.isScrolledToTheEnd()) || (messages.isNotEmpty() && isFirstRender.value)) {
            listState.animateScrollToItem(messages.size - 1)
            isFirstRender.value = false
        }
    }

    Scaffold(
        bottomBar = {
            MessageInputField(
                text = text.value,
                onValueChange = { text.value = it },
                onSendMessageClick = {
                    if (text.value.isNotBlank()) {
                        sendMessage(
                            getAuthUserUID(),
                            buyerUID,
                            context.getString(
                                R.string.send_message_buyer_name,
                                buyerData.value.firstName,
                                buyerData.value.secondName
                            ),
                            buyerData.value.image,
                            text.value
                        )
                        viewModel.sendMessage(
                            context.getString(
                                R.string.send_message_buyer_name,
                                buyerData.value.firstName,
                                buyerData.value.secondName
                            ),
                            text.value,
                            buyerData.value.localToken
                        )
                        text.value = ""
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        val circleSize = with(LocalDensity.current) { 4.dp.toPx() }
        val status = getBuyerStatus(buyerUID).observeAsState(initial = UserStatus.OFFLINE)

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
                    .clickable { onBuyerClick(buyerUID) }
            ) {

                Box {
                    AsyncImage(
                        model = buyerData.value.image,
                        contentDescription = stringResource(id = R.string.content_buyer_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(60.dp)
                            .clip(CircleShape)
                            .border(2.dp, containerColor, CircleShape)
                    )
                    Canvas(modifier = Modifier
                        .size(4.dp)
                        .padding(8.dp)) {
                        drawCircle(
                            radius = circleSize,
                            color = if (status.value == UserStatus.ONLINE) Color.Green else Color.Gray
                        )
                    }
                }
                Text(
                    text = stringResource(
                        id = R.string.send_message_buyer_name,
                        buyerData.value.firstName,
                        buyerData.value.secondName
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
                    if (!message.read && message.receiver == getAuthUserUID())
                        markMessagesAsRead(buyerUID, message.id)
                    UserMessage(message = message)
                }
            }
        }
    }
}

@Composable
fun UserMessage(
    message: Message,
    modifier: Modifier = Modifier
) {
    println(message.read)
    val config = LocalConfiguration.current
    val maxWidth = (config.screenWidthDp * 0.9f).dp

    val authUserUID = getAuthUserUID()

    Row(
        horizontalArrangement = if (message.sender == authUserUID) Arrangement.End else Arrangement.Start,
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
                if (message.sender == authUserUID && !message.read) {
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
                            append(text = message.text + " ")
                        }
                        withStyle(style = SpanStyle(color = Color.LightGray, fontSize = 10.sp)) {
                            append(text = message.time)
                        }
                    }, modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun MessageInputField(
    text: String,
    onValueChange: (String) -> Unit,
    onSendMessageClick: () -> Unit,
    modifier: Modifier = Modifier
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

// проверяем проскроллено ли до последнего элемента в lazy list
fun LazyListState.isScrolledToTheEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 2