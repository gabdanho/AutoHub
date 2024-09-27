package com.example.autohub.ui.messenger

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
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.autohub.data.BuyerChat
import com.example.autohub.ui.ChatViewModel
import com.example.autohub.ui.componets.BottomNavBar
import com.example.autohub.ui.theme.barColor
import com.example.autohub.ui.theme.cardColor
import com.example.autohub.ui.theme.containerColor

@Composable
fun MessengerScreen(
    onAnswerClick: (String) -> Unit,
    onAccountClick: () -> Unit,
    onMessageClick: () -> Unit,
    onAdListClick: () -> Unit,
    viewModel: ChatViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val buyers by viewModel.getBuyers().observeAsState(initial = emptyList())

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
                    text = "Мессенджер",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        bottomBar = { BottomNavBar(onAdListClick, onAccountClick, onMessageClick) }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            items(buyers) { buyer ->
                ChatCardBuyer(buyer, onAnswerClick)
            }
        }
    }
}

@Composable
fun ChatCardBuyer(
    buyer: BuyerChat,
    onAnswerClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
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
                .clickable { onAnswerClick(buyer.uid) }
        ) {
            AsyncImage(
                model = buyer.image,
                contentDescription = "Фото покупателя",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(2.dp, containerColor, CircleShape)
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = buyer.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = buyer.lastMessage,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Preview
@Composable
private fun MessengerScreenPreview() {
    MessengerScreen(
        { }, { }, { }, { }
    )
}