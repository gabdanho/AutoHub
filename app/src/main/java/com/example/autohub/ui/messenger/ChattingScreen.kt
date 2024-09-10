package com.example.autohub.ui.messenger

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.autohub.ui.componets.TopAdAppBar
import com.example.autohub.ui.theme.cardColor
import com.example.autohub.ui.theme.containerColor

// Test Data Class
data class Message(
    val text: String,
    val userId: Int
)

@Composable
fun ChattingScreen(
    messages: List<Message>,
    byuer: String,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val text = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAdAppBar(
                titleText = "",
                onBackButtonClick = onBackButtonClick
            )
        },
        bottomBar = {
            MessageInputField(
                text = text.value,
                onValueChange = { text.value = it },
                onSendMessageClick = { }
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
            ) {
                AsyncImage(
                    model = "https://redbrickworks.com/wp-content/uploads/2021/02/business-man.png",
                    contentDescription = "Фото покупателя",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(2.dp, containerColor, CircleShape)
                )
                Text(
                    text = byuer,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            LazyColumn(
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

    Row(
        horizontalArrangement = if (message.userId == 1) Arrangement.End else Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            elevation = CardDefaults.cardElevation(2.dp),
            colors = CardDefaults.cardColors(cardColor),
            modifier = modifier
                .widthIn(max = maxWidth)
                .padding(8.dp)
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(8.dp)
            )
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

@Preview
@Composable
private fun ChattingScreenPreview() {
    ChattingScreen(
        byuer = "Саня Лютый",
        onBackButtonClick = { },
        messages = listOf(
            Message("Привет", 0),
            Message("Хай", 1),
            Message("Продай", 0),
            Message("Нет пака", 1)
        )
    )
}