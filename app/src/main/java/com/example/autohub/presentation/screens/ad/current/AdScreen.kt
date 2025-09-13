package com.example.autohub.presentation.screens.ad.current

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.autohub.R
import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.presentation.componets.CustomButton
import com.example.autohub.presentation.componets.InfoPlaceholder
import com.example.autohub.presentation.componets.ListPhotos
import com.example.autohub.presentation.componets.LoadingCircularIndicator
import com.example.autohub.presentation.componets.TopAdAppBar
import com.example.autohub.presentation.mapper.resources.StringToResourceIdMapperImpl
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.StringResNamePresentation
import com.example.autohub.presentation.theme.barColor
import com.example.autohub.presentation.theme.cardColor
import com.example.autohub.presentation.theme.containerColor
import com.example.autohub.presentation.utils.launchDialIntent

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

    println(carAd.imagesUrl.toString())

    LaunchedEffect(carAd) {
        viewModel.getUserData(uid = carAd.userUID)
    }

    LaunchedEffect(callEvent) {
        callEvent?.let {
            context.launchDialIntent(phoneNumber = it)
        }
        viewModel.clearCallEvent()
    }

    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            val resId = StringToResourceIdMapperImpl().map(uiState.message)
            Toast.makeText(context, context.getString(resId), Toast.LENGTH_LONG).show()
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
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
                    .background(color = barColor)
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
                        imageToShow = uiState.imageToShow,
                        changeImageToShow = { viewModel.changeImageToShow(value = it) },
                        modifier = Modifier
                            .weight(1.4f)
                            .fillMaxWidth()
                            .background(cardColor)
                    )
                    Column(modifier = Modifier.weight(3f)) {
                        Text(
                            text = stringResource(id = R.string.text_car_price, carAd.price),
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp)
                        )

                        if (uiState.authUserId != carAd.userUID) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                            ) {
                                AsyncImage(
                                    model = uiState.user.image,
                                    contentDescription = stringResource(id = R.string.content_user_image),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(70.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, containerColor, CircleShape)
                                        .clickable { viewModel.onUserClick() }
                                )
                                Column(
                                    modifier = Modifier.padding(start = 16.dp)
                                ) {
                                    Text(
                                        text = stringResource(
                                            id = R.string.text_seller_main_info,
                                            uiState.user.firstName,
                                            uiState.user.lastName,
                                            uiState.user.city
                                        ),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.clickable { viewModel.onUserClick() }
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        CustomButton(
                                            text = stringResource(id = R.string.button_write_message),
                                            onClick = { viewModel.onMessageClick(participantId = carAd.userUID) },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(end = 4.dp)
                                                .weight(1f)
                                        )
                                        CustomButton(
                                            text = stringResource(id = R.string.button_call),
                                            onClick = { viewModel.callToUser() },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .weight(1f)
                                        )
                                    }
                                }
                            }
                        }
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .background(cardColor)
                                .shadow(elevation = 0.5.dp)
                        ) {
                            item {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.text_brand),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = carAd.brand
                                    )
                                }
                            }
                            item {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.text_model),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = carAd.model
                                    )
                                }
                            }
                            item {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.text_year_created),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = carAd.realiseYear
                                    )
                                }
                            }
                            item {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.text_body),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = stringResource(
                                            id = StringToResourceIdMapperImpl().map(
                                                resId = carAd.body?.textRes
                                                    ?: StringResNamePresentation.NO_DATA
                                            )
                                        )
                                    )
                                }
                            }
                            item {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.text_engine_type),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = stringResource(
                                            id = StringToResourceIdMapperImpl().map(
                                                resId = carAd.typeEngine?.textRes
                                                    ?: StringResNamePresentation.NO_DATA
                                            )
                                        )
                                    )
                                }
                            }
                            item {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.text_transmission),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = stringResource(
                                            id = StringToResourceIdMapperImpl().map(
                                                resId = carAd.transmission?.textRes
                                                    ?: StringResNamePresentation.NO_DATA
                                            )
                                        )
                                    )
                                }
                            }
                            item {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.text_drive),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = stringResource(
                                            id = StringToResourceIdMapperImpl().map(
                                                resId = carAd.drive?.textRes
                                                    ?: StringResNamePresentation.NO_DATA
                                            )
                                        )
                                    )
                                }
                            }
                            item {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.text_condition),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = stringResource(
                                            id = StringToResourceIdMapperImpl().map(
                                                resId = carAd.condition?.textRes
                                                    ?: StringResNamePresentation.NO_DATA
                                            )
                                        )
                                    )
                                }
                            }
                            item {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.text_engine_capacity),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = carAd.engineCapacity
                                    )
                                }
                            }
                            item {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.text_steering_wheel),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = stringResource(
                                            id = StringToResourceIdMapperImpl().map(
                                                resId = carAd.steeringWheelSide?.textRes
                                                    ?: StringResNamePresentation.NO_DATA
                                            )
                                        )
                                    )
                                }
                            }
                            item {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.text_mileage),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = carAd.mileage
                                    )
                                }
                            }
                            item {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.text_color),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = carAd.color
                                    )
                                }
                            }
                        }
                        Text(
                            text = stringResource(id = R.string.text_description),
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = carAd.description,
                            modifier = Modifier.padding(8.dp)
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
}