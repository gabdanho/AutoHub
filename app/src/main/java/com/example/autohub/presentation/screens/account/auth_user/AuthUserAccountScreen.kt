package com.example.autohub.presentation.screens.account.auth_user

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults.buttonColors
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
import com.example.autohub.presentation.componets.BottomNavBar
import com.example.autohub.presentation.componets.CarAdCard
import com.example.autohub.presentation.componets.CustomButton
import com.example.autohub.presentation.componets.InfoPlaceholder
import com.example.autohub.presentation.componets.LoadingCircularIndicator
import com.example.autohub.presentation.componets.PullToRefreshContainer
import com.example.autohub.presentation.componets.TopBarDefault
import com.example.autohub.presentation.componets.UserNamesAndProfileImage
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.theme.AppTheme
import com.example.autohub.presentation.utils.showUiMessage

/**
 * UI Screen для просмотра и управления собственным аккаунтом авторизованного пользователя.
 *
 * @param modifier [Modifier] для кастомизации компоновки
 * @param viewModel [AuthUserAccountScreenViewModel], используемый для бизнес-логики
 */
@Composable
fun AuthUserAccountScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthUserAccountScreenViewModel = hiltViewModel<AuthUserAccountScreenViewModel>(),
) {
    val uiState = viewModel.uiState.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(uiState.uiMessage) {
        context.showUiMessage(uiMessage = uiState.uiMessage) {
            viewModel.clearMessage()
        }
    }

    Scaffold(
        topBar = {
            TopBarDefault(
                title = stringResource(id = R.string.text_account),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = AppTheme.colors.barColor)
                    .padding(AppTheme.dimens.extraSmall)
            )
        },
        bottomBar = {
            BottomNavBar(
                onAdListClick = { viewModel.onAdListClick() },
                onAccountClick = { viewModel.onAccountClick() },
                onMessageClick = { viewModel.onMessengerClick() }
            )
        }
    ) { innerPadding ->
        PullToRefreshContainer(
            isRefreshing = uiState.loadingState is LoadingState.Loading,
            onRefresh = { viewModel.getUserDataAndAds() },
            modifier = Modifier.padding(innerPadding)
        ) {
            when (uiState.loadingState) {
                is LoadingState.Success -> {
                    LazyColumn(
                        modifier = modifier
                            .padding(AppTheme.dimens.extraSmall)
                            .fillMaxSize()
                    ) {
                        item {
                            UserNamesAndProfileImage(
                                imageUrl = uiState.user.imageUrl,
                                firstName = uiState.user.firstName,
                                lastName = uiState.user.lastName,
                                city = uiState.user.city,
                                modifier = Modifier.fillMaxWidth(),
                            )
                            Column(
                                modifier = Modifier
                                    .padding(vertical = AppTheme.dimens.extraSmall)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = stringResource(
                                        id = R.string.text_user_email,
                                        uiState.user.email
                                    ),
                                    modifier = Modifier.padding(bottom = AppTheme.dimens.extraSmall)
                                )
                                Text(
                                    text = stringResource(
                                        id = R.string.text_user_phone,
                                        uiState.user.phoneNumber
                                    )
                                )
                            }
                        }
                        item {
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
                                        containerColor = AppTheme.colors.white
                                    ),
                                    textColor = AppTheme.colors.textColor,
                                    border = BorderStroke(
                                        AppTheme.dimens.mediumBorderSize,
                                        AppTheme.colors.containerColor
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(AppTheme.dimens.ultraSmall)
                                        .weight(AppTheme.dimens.fullWeight)
                                )
                                CustomButton(
                                    text = stringResource(id = R.string.button_exit),
                                    onClick = { viewModel.onSignOutClick() },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(AppTheme.dimens.fullWeight)
                                )
                            }
                            Text(
                                text = stringResource(id = R.string.text_your_ads),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = AppTheme.dimens.extraSmall)
                            )
                            HorizontalDivider()
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = AppTheme.dimens.extraSmall)
                            ) {
                                CustomButton(
                                    text = stringResource(id = R.string.button_create),
                                    onClick = { viewModel.onAdCreateClick() },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                        items(uiState.ads) { ad ->
                            CarAdCard(
                                ad = ad,
                                imageHeight = AppTheme.dimens.carAdCardImageSize,
                                onAdClick = { viewModel.onAdClick(ad) },
                                modifier = Modifier.padding(AppTheme.dimens.extraSmall)
                            )
                        }
                    }
                }

                is LoadingState.Error -> {
                    InfoPlaceholder(
                        textRes = R.string.error_to_show_user_data,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is LoadingState.Loading -> {
                    LoadingCircularIndicator(
                        modifier = Modifier.fillMaxSize()
                    )
                }

                null -> {}
            }
        }
    }
}