package com.example.autohub.ui.account

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.autohub.data.CarAd
import com.example.autohub.data.User
import com.example.autohub.data.mock.CarAdMock
import com.example.autohub.ui.componets.BottomNavBar
import com.example.autohub.ui.componets.CarAdCard
import com.example.autohub.ui.componets.CustomButton
import com.example.autohub.ui.theme.barColor
import com.example.autohub.ui.theme.containerColor
import com.example.autohub.utils.getUserData
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun AuthUserAccountScreen(
    yourAds: List<CarAd>,
    onChangeInfoClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onAdClick: () -> Unit,
    onAccountClick: () -> Unit,
    onMessageClick: () -> Unit,
    onAdListClick: () -> Unit,
    onAdCreateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val userUID = Firebase.auth.currentUser?.uid!!
    val userData = remember { mutableStateOf(User()) }
    getUserData(userUID) { user -> userData.value = user }

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
                    text = "Аккаунт",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        bottomBar = { BottomNavBar(onAdListClick, onAccountClick, onMessageClick) }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model = userData.value.image,
                        contentDescription = "Account image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(2.dp, containerColor, CircleShape)
                    )
                    Column(
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Text(
                            text = "${userData.value.firstName} ${userData.value.secondName}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = userData.value.city,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Почта: ${userData.value.email}",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Номер телефона: ${userData.value.phoneNumber}"
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                CustomButton(
                    text = "Изменить",
                    onClick = { onChangeInfoClick() },
                    colorButton = buttonColors(
                        containerColor = Color.White
                    ),
                    textColor = Color.Black,
                    border = BorderStroke(4.dp, containerColor),
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f)
                )
                CustomButton(
                    text = "Выход",
                    onClick = { onSignOutClick() },
                    modifier = Modifier.weight(1f)
                )
            }
            Text(
                text = "Ваши объявления",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
            HorizontalDivider()
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                CustomButton(
                    text = "Создать",
                    onClick = { onAdCreateClick() }
                )
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(yourAds) { ad ->
                    CarAdCard(
                        ad = ad,
                        onAdClick = onAdClick
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun AuthUserAccountScreenPreview() {
    AuthUserAccountScreen(
        yourAds = CarAdMock.ads,
        { }, { }, { }, { }, { }, { }, { }
    )
}