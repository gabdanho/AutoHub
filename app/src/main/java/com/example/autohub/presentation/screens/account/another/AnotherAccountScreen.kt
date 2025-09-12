package com.example.autohub.presentation.screens.account.another

import android.widget.Toast
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.autohub.R
import com.example.autohub.presentation.componets.CarAdCard
import com.example.autohub.presentation.componets.CustomButton
import com.example.autohub.presentation.componets.TopAdAppBar
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.user.User
import com.example.autohub.presentation.theme.barColor
import com.example.autohub.presentation.theme.containerColor
import com.example.autohub.presentation.utils.launchDialIntent

@Composable
fun AnotherAccountScreen(
    user: User,
    modifier: Modifier = Modifier,
    viewModel: AnotherAccountScreenViewModel = hiltViewModel<AnotherAccountScreenViewModel>()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val callEvent = viewModel.callEvent.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(user) {
        viewModel.getUserAds(user = user)
    }

    LaunchedEffect(uiState.loadingState) {
        if (uiState.loadingState is LoadingState.Error) {
            Toast.makeText(context, uiState.loadingState.message, Toast.LENGTH_SHORT).show()
            viewModel.clearLoadingState()
        }
    }

    LaunchedEffect(callEvent) {
        callEvent?.let {
            context.launchDialIntent(phoneNumber = it)
        }
        viewModel.clearCallEvent()
    }

    Scaffold(
        topBar = {
            TopAdAppBar(
                titleText = stringResource(id = R.string.text_seller),
                onBackButtonClick = { viewModel.prevScreen() },
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = barColor)
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
                        model = user.image,
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
                                user.firstName,
                                user.lastName
                            ),
                            style = MaterialTheme.typography.displaySmall,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = user.city,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                CustomButton(
                    text = stringResource(id = R.string.button_write_message),
                    onClick = { viewModel.writeToUser(user = user) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp)
                        .weight(1f)
                )
                CustomButton(
                    text = stringResource(id = R.string.button_call),
                    onClick = { viewModel.callToUser(number = user.phoneNumber) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
            Text(
                text = stringResource(id = R.string.text_displayed_ads),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
            HorizontalDivider()
            if (uiState.sellerAds.isNotEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2)
                ) {
                    items(uiState.sellerAds) { ad ->
                        CarAdCard(
                            ad = ad,
                            onAdClick = { viewModel.onAdClick(ad = ad) },
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = stringResource(id = R.string.text_nothing_was_found))
                }
            }
        }
    }
}

//@Preview
//@Composable
//private fun AuthUserAccountScreenPreview() {
//    AnotherAccountScreen()
//}