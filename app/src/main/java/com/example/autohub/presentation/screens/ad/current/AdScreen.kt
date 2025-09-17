package com.example.autohub.presentation.screens.ad.current

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.autohub.R
import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.presentation.componets.CustomButton
import com.example.autohub.presentation.componets.ImagePagerString
import com.example.autohub.presentation.componets.InfoPlaceholder
import com.example.autohub.presentation.componets.ListPhotos
import com.example.autohub.presentation.componets.LoadingCircularIndicator
import com.example.autohub.presentation.componets.TopAdAppBar
import com.example.autohub.presentation.componets.UserNamesAndProfileImage
import com.example.autohub.presentation.mapper.resources.StringToResourceIdMapperImpl
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.StringResNamePresentation
import com.example.autohub.presentation.model.options.CarOption
import com.example.autohub.presentation.theme.AppTheme
import com.example.autohub.presentation.utils.launchDialIntent
import com.example.autohub.presentation.utils.showUiMessage

private const val COUNT_COLUMNS = 1

@Composable
fun AdScreen(
    carAd: CarAd,
    modifier: Modifier = Modifier,
    viewModel: AdScreenViewModel = hiltViewModel<AdScreenViewModel>(),
) {
    val scrollState = rememberScrollState()
    val uiState = viewModel.uiState.collectAsState().value
    val callEvent = viewModel.callEvent.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(carAd) {
        viewModel.getUserData(uid = carAd.userId)
    }

    LaunchedEffect(callEvent) {
        callEvent?.let {
            context.launchDialIntent(phoneNumber = it)
        }
        viewModel.clearCallEvent()
    }

    LaunchedEffect(uiState.uiMessage) {
        context.showUiMessage(uiMessage = uiState.uiMessage) {
            viewModel.clearMessage()
        }
    }

    Scaffold(
        topBar = {
            TopAdAppBar(
                titleText = stringResource(
                    id = R.string.text_title_car_description,
                    carAd.brand,
                    carAd.model,
                    carAd.realiseYear
                ),
                onBackButtonClick = { viewModel.onBackButtonClick() },
                modifier = Modifier
                    .padding(bottom = AppTheme.dimens.extraSmall)
                    .fillMaxWidth()
                    .background(color = AppTheme.colors.barColor)
            )
        }
    ) { innerPadding ->
        when (uiState.loadingState) {
            is LoadingState.Success -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(scrollState)
                ) {
                    ListPhotos(
                        imagesUrl = carAd.imagesUrl,
                        onImageClick = { viewModel.changeImageIdToShow(value = it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(AppTheme.colors.cardColor)
                    )
                    Column(modifier = Modifier.padding(AppTheme.dimens.extraSmall)) {
                        Text(
                            text = stringResource(id = R.string.text_car_price, carAd.price),
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold
                        )
                        if (uiState.authUserId != carAd.userId) {
                            UserNamesAndProfileImage(
                                imageUrl = uiState.user.imageUrl,
                                firstName = uiState.user.firstName,
                                lastName = uiState.user.lastName,
                                city = uiState.user.city,
                                onUserClick = { viewModel.onUserClick() },
                                modifier = Modifier.fillMaxWidth(),
                            )
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(AppTheme.dimens.extraSmall)
                                    .fillMaxWidth()
                            ) {
                                CustomButton(
                                    text = stringResource(id = R.string.button_write_message),
                                    onClick = { viewModel.onMessageClick(participantId = carAd.userId) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = AppTheme.dimens.ultraSmall)
                                        .weight(AppTheme.dimens.fullWeight)
                                )
                                CustomButton(
                                    text = stringResource(id = R.string.button_call),
                                    onClick = { viewModel.callToUser() },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(AppTheme.dimens.fullWeight)
                                )
                            }
                        }
                        FlowRow(
                            maxItemsInEachRow = COUNT_COLUMNS,
                            modifier = Modifier
                                .background(AppTheme.colors.cardColor)
                                .shadow(elevation = AppTheme.dimens.gridAdsElevation)
                        ) {
                            GridItem(
                                title = stringResource(id = R.string.text_brand),
                                value = carAd.brand,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(AppTheme.dimens.extraSmall)
                            )
                            GridItem(
                                title = stringResource(id = R.string.text_model),
                                value = carAd.model,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(AppTheme.dimens.extraSmall)
                            )
                            GridItem(
                                title = stringResource(id = R.string.text_year_created),
                                value = carAd.realiseYear,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(AppTheme.dimens.extraSmall)
                            )
                            GridItem(
                                title = stringResource(id = R.string.text_body),
                                option = carAd.body,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(AppTheme.dimens.extraSmall)
                            )
                            GridItem(
                                title = stringResource(id = R.string.text_engine_type),
                                option = carAd.typeEngine,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(AppTheme.dimens.extraSmall)
                            )
                            GridItem(
                                title = stringResource(id = R.string.text_transmission),
                                option = carAd.transmission,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(AppTheme.dimens.extraSmall)
                            )
                            GridItem(
                                title = stringResource(id = R.string.text_drive),
                                option = carAd.drive,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(AppTheme.dimens.extraSmall)
                            )
                            GridItem(
                                title = stringResource(id = R.string.text_condition),
                                option = carAd.condition,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(AppTheme.dimens.extraSmall)
                            )
                            GridItem(
                                title = stringResource(id = R.string.text_engine_capacity),
                                value = carAd.engineCapacity,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(AppTheme.dimens.extraSmall)
                            )
                            GridItem(
                                title = stringResource(id = R.string.text_steering_wheel),
                                option = carAd.steeringWheelSide,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(AppTheme.dimens.extraSmall)
                            )
                            GridItem(
                                title = stringResource(id = R.string.text_mileage),
                                value = carAd.mileage,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(AppTheme.dimens.extraSmall)
                            )
                            GridItem(
                                title = stringResource(id = R.string.text_color),
                                value = carAd.color,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(AppTheme.dimens.extraSmall)
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.text_description),
                            modifier = Modifier.padding(AppTheme.dimens.extraSmall)
                        )
                        Text(
                            text = carAd.description,
                            modifier = Modifier.padding(AppTheme.dimens.extraSmall)
                        )
                    }
                }
            }

            is LoadingState.Error -> {
                InfoPlaceholder(
                    textRes = R.string.error_to_show_ad_data,
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

    if (uiState.isShowImagePager) {
        ImagePagerString(
            images = carAd.imagesUrl,
            currentImageId = uiState.imageIdToShow,
            onClose = { viewModel.changeIsShowImagePager(value = false) }
        )
    }
}

@Composable
private fun GridItem(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold
        )
        Text(text = value)
    }

}

@Composable
private fun GridItem(
    title: String,
    option: CarOption?,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(
                id = StringToResourceIdMapperImpl().map(
                    resId = option?.textRes
                        ?: StringResNamePresentation.NO_DATA
                )
            )
        )
    }
}