package com.example.autohub.ui.messenger

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.autohub.data.Message
import com.example.autohub.data.User
import com.example.autohub.ui.ChatViewModel
import com.example.autohub.ui.theme.cardColor
import com.example.autohub.ui.theme.containerColor
import com.example.autohub.utils.getAuthUserUID
import com.example.autohub.utils.getUserData
import com.example.autohub.utils.sendMessage
import kotlinx.coroutines.launch

@Composable
fun ChattingScreen(
    buyerUID: String,
    onBuyerClick: (String) -> Unit,
    viewModel: ChatViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val text = remember { mutableStateOf("") }
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
                            "${buyerData.value.firstName} ${buyerData.value.secondName}.",
                            buyerData.value.image,
                            text.value
                        )
                        text.value = ""
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
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
                AsyncImage(
                    model = buyerData.value.image,
                    contentDescription = "Фото покупателя",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(2.dp, containerColor, CircleShape)
                )
                Text(
                    text = "${buyerData.value.firstName} ${buyerData.value.secondName}",
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
                    UserMessage(message)
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
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 15.sp)) {
                    append(text = message.text + " ")
                }
                withStyle(style = SpanStyle(color = Color.LightGray, fontSize = 10.sp)) {
                    append(text = message.time)
                }
            }, modifier = Modifier.padding(8.dp))
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
            placeholder = { Text("Сообщение") },
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
                contentDescription = "Отправить сообщение",
                tint = containerColor
            )
        }
    }
}

// проверяем последний проскроллено ли до последнего элемента в lazy list
fun LazyListState.isScrolledToTheEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 2 // с 1 работает не так