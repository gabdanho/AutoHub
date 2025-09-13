package com.example.autohub.presentation.screens.account.auth_user

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.autohub.R
import com.example.autohub.presentation.componets.BottomNavBar
import com.example.autohub.presentation.componets.CarAdCard
import com.example.autohub.presentation.componets.CustomButton
import com.example.autohub.presentation.componets.InfoPlaceholder
import com.example.autohub.presentation.componets.LoadingCircularIndicator
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.theme.barColor
import com.example.autohub.presentation.theme.containerColor

@Composable
fun AuthUserAccountScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthUserAccountScreenViewModel = hiltViewModel<AuthUserAccountScreenViewModel>(),
) {
    val uiState = viewModel.uiState.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(uiState.loadingState) {
        if (uiState.loadingState is LoadingState.Error) {
            Toast.makeText(context, uiState.loadingState.message, Toast.LENGTH_SHORT).show()
        }
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
                onAdListClick = { viewModel.onAdListClick() },
                onAccountClick = { viewModel.onAccountClick() },
                onMessageClick = { viewModel.onMessengerClick() }
            )
        }
    ) { innerPadding ->
        when (uiState.loadingState) {
            is LoadingState.Success -> {
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
                                model = uiState.user.image,
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
                                        uiState.user.firstName,
                                        uiState.user.lastName
                                    ),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Text(
                                    text = uiState.user.city,
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
                            text = stringResource(
                                id = R.string.text_user_email,
                                uiState.user.email
                            ),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = stringResource(
                                id = R.string.text_user_phone,
                                uiState.user.phoneNumber
                            )
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
                            onClick = { viewModel.onChangeInfoClick() },
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
                            onClick = { viewModel.onSignOutClick() },
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
                            onClick = { viewModel.onAdCreateClick() },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2)
                    ) {
                        items(uiState.ads) { ad ->
                            CarAdCard(
                                ad = ad,
                                onAdClick = { viewModel.onAdClick(ad = ad) },
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                        }
                    }
                }

            }

            is LoadingState.Error -> {
                InfoPlaceholder(
                    textRes = R.string.error_to_show_user_data,
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