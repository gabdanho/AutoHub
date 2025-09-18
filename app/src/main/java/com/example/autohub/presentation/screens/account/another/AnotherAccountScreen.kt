package com.example.autohub.presentation.screens.account.another

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.autohub.R
import com.example.autohub.presentation.componets.CarAdCard
import com.example.autohub.presentation.componets.CustomButton
import com.example.autohub.presentation.componets.InfoPlaceholder
import com.example.autohub.presentation.componets.LoadingCircularIndicator
import com.example.autohub.presentation.componets.PullToRefreshContainer
import com.example.autohub.presentation.componets.TopAdAppBar
import com.example.autohub.presentation.componets.UserNamesAndProfileImage
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.user.User
import com.example.autohub.presentation.theme.AppTheme
import com.example.autohub.presentation.utils.launchDialIntent
import com.example.autohub.presentation.utils.showUiMessage

/**
 * UI Screen для просмотра профиля другого пользователя и его объявлений.
 *
 * @param user Пользователь, профиль которого отображается
 * @param modifier [Modifier] для кастомизации компоновки
 * @param viewModel [AnotherAccountScreenViewModel], используемый для бизнес-логики
 */
@Composable
fun AnotherAccountScreen(
    user: User,
    modifier: Modifier = Modifier,
    viewModel: AnotherAccountScreenViewModel = hiltViewModel<AnotherAccountScreenViewModel>(),
) {
    val uiState = viewModel.uiState.collectAsState().value
    val callEvent = viewModel.callEvent.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(user) {
        viewModel.getUserAds(user = user)
    }

    LaunchedEffect(uiState.uiMessage) {
        context.showUiMessage(uiMessage = uiState.uiMessage) {
            viewModel.clearMessage()
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
                    .background(color = AppTheme.colors.barColor)
            )
        }
    ) { innerPadding ->
        PullToRefreshContainer(
            isRefreshing = uiState.loadingState is LoadingState.Loading,
            onRefresh = { viewModel.getUserAds(user = user, forced = true) },
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn(
                modifier = modifier
                    .padding(AppTheme.dimens.extraSmall)
                    .fillMaxSize()
            ) {
                item {
                    UserNamesAndProfileImage(
                        imageUrl = user.imageUrl,
                        firstName = user.firstName,
                        lastName = user.lastName,
                        city = user.city,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                item {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(AppTheme.dimens.extraSmall)
                            .fillMaxWidth()
                    ) {
                        CustomButton(
                            text = stringResource(id = R.string.button_write_message),
                            onClick = { viewModel.writeToUser(user = user) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = AppTheme.dimens.extraSmall)
                                .weight(AppTheme.dimens.fullWeight)
                        )
                        CustomButton(
                            text = stringResource(id = R.string.button_call),
                            onClick = { viewModel.callToUser(number = user.phoneNumber) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(AppTheme.dimens.fullWeight)
                        )
                    }
                }
                item {
                    Text(
                        text = stringResource(id = R.string.text_displayed_ads),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppTheme.dimens.extraSmall)
                    )
                    HorizontalDivider()
                }
                when (uiState.loadingState) {
                    is LoadingState.Success -> {
                        if (uiState.sellerAds.isNotEmpty()) {
                            items(uiState.sellerAds) { ad ->
                                CarAdCard(
                                    ad = ad,
                                    onAdClick = { viewModel.onAdClick(ad = ad) },
                                    imageHeight = AppTheme.dimens.carAdCardImageSize,
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .padding(AppTheme.dimens.extraSmall)
                                )
                            }
                        } else {
                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(AppTheme.dimens.extraSmall),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(text = stringResource(id = R.string.text_nothing_was_found))
                                }
                            }
                        }
                    }

                    is LoadingState.Error -> {
                        item {
                            InfoPlaceholder(
                                textRes = R.string.error_to_show_user_data,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    is LoadingState.Loading -> {
                        item {
                            LoadingCircularIndicator(
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    null -> {}
                }
            }
        }
    }
}