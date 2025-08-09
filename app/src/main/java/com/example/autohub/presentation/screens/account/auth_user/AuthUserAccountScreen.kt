package com.example.autohub.presentation.screens.account.auth_user

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.autohub.R
import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.presentation.model.user.User
import com.example.autohub.presentation.componets.BottomNavBar
import com.example.autohub.presentation.componets.CarAdCard
import com.example.autohub.presentation.componets.CustomButton
import com.example.autohub.presentation.theme.barColor
import com.example.autohub.presentation.theme.containerColor
import com.example.autohub.data.utils.getUserData
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun AuthUserAccountScreen(
    yourAds: List<CarAd>,
    onChangeInfoClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onAdClick: (CarAd) -> Unit,
    onAccountClick: () -> Unit,
    onMessageClick: () -> Unit,
    onAdListClick: () -> Unit,
    onAdCreateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val userData = remember { mutableStateOf(User()) }

    val userUID = Firebase.auth.currentUser?.uid
    if (userUID != null) {
        getUserData(userUID) { user -> userData.value = user }
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
                    text = stringResource(id = R.string.text_account),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        bottomBar = {
            BottomNavBar(
                onAdListClick = onAdListClick,
                onAccountClick = onAccountClick,
                onMessageClick = onMessageClick
            )
        }
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
                        contentDescription = stringResource(id = R.string.content_user_image),
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
                            text = stringResource(
                                id = R.string.text_user_first_last_name,
                                userData.value.firstName,
                                userData.value.secondName
                            ),
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
                    text = stringResource(id = R.string.text_user_email, userData.value.email),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = stringResource(id = R.string.text_user_phone, userData.value.phoneNumber)
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                CustomButton(
                    text = stringResource(id = R.string.button_change),
                    onClick = { onChangeInfoClick() },
                    colorButton = buttonColors(
                        containerColor = Color.White
                    ),
                    textColor = Color.Black,
                    border = BorderStroke(4.dp, containerColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .weight(1f)
                )
                CustomButton(
                    text = stringResource(id = R.string.button_exit),
                    onClick = { onSignOutClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
            Text(
                text = stringResource(id = R.string.text_your_ads),
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
                    text = stringResource(id = R.string.button_create),
                    onClick = { onAdCreateClick() },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(yourAds) { ad ->
                    CarAdCard(
                        ad = ad,
                        onAdClick = { onAdClick(ad) },
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}

//@Preview
//@Composable
//private fun AuthUserAccountScreenPreview() {
//    AuthUserAccountScreen(
//        yourAds = CarAdFake.ads,
//        { }, { }, { }, { }, { }, { }, { }
//    )
//}