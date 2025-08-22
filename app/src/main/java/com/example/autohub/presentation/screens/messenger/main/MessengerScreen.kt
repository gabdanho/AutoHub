package com.example.autohub.presentation.screens.messenger.main

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.autohub.R
import com.example.autohub.presentation.model.messenger.ChatInfo
import com.example.autohub.presentation.model.user.UserStatus
import com.example.autohub.presentation.componets.BottomNavBar
import com.example.autohub.presentation.theme.barColor
import com.example.autohub.presentation.theme.cardColor
import com.example.autohub.presentation.theme.containerColor

@Composable
fun MessengerScreen(
    modifier: Modifier = Modifier
) {
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
                onAdListClick = TODO("onAdListClick"),
                onAccountClick = TODO("onAccountClick"),
                onMessageClick = TODO("onMessageClick")
            )
        }
    ) { innerPadding ->
        if (TODO("buyers.isNotEmpty()")) {
            LazyColumn(
                modifier = modifier
                    .padding(innerPadding)
                    .padding(8.dp)
            ) {
                items(TODO("buyers")) { buyer ->
                    ChatCardBuyer(buyer = TODO("buyer"), onAnswerClick = TODO("onAnswerClick()"))
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
private fun ChatCardBuyer(
    buyer: ChatInfo,
    onAnswerClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val circleSize = with(LocalDensity.current) { 4.dp.toPx() }
    val status = TODO("getBuyerStatus(buyer.uid).observeAsState(initial = UserStatus.OFFLINE)")
    TODO("val unreadMessages = remember { mutableIntStateOf(0) }")
    TODO("getCountUnreadMessages(buyer.uid) { unreadMessages.intValue = it }")

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
                    onAnswerClick(buyer.uid)
                }
        ) {
            Box {
                AsyncImage(
                    model = buyer.image,
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
                        color = TODO("if (status.value == UserStatus.ONLINE) Color.Green else Color.Gray")
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
            if (TODO("unreadMessages.intValue != 0")) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(30.dp)
                        .background(color = containerColor, shape = CircleShape)
                ) {
                    Text(
                        text = "", // TODO("unreadMessages.intValue.toString()")
                        color = Color.White,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

//@Preview
//@Composable
//private fun MessengerScreenPreview() {
//    MessengerScreen(
//        { }, { }, { }, { }
//    )
//}