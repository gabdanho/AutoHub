package com.example.autohub.presentation.screens.ad.create

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.autohub.R
import com.example.autohub.presentation.componets.CustomButton
import com.example.autohub.presentation.componets.ImagePagerUri
import com.example.autohub.presentation.componets.InputField
import com.example.autohub.presentation.componets.ListAddedPhotos
import com.example.autohub.presentation.componets.LoadingCircularIndicator
import com.example.autohub.presentation.componets.RowRadioButtons
import com.example.autohub.presentation.componets.TopAdAppBar
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.UiImage
import com.example.autohub.presentation.model.options.BodyType
import com.example.autohub.presentation.model.options.ConditionType
import com.example.autohub.presentation.model.options.DriveType
import com.example.autohub.presentation.model.options.EngineType
import com.example.autohub.presentation.model.options.SteeringWheelSideType
import com.example.autohub.presentation.model.options.TransmissionType
import com.example.autohub.presentation.theme.AppTheme
import com.example.autohub.presentation.utils.convertUriToBytes
import com.example.autohub.presentation.utils.showUiMessage

@Composable
fun AdCreateScreen(
    modifier: Modifier = Modifier,
    viewModel: AdCreateScreenViewModel = hiltViewModel<AdCreateScreenViewModel>(),
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState().value
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                viewModel.addImage(
                    image = UiImage(
                        uri = uri,
                        byteArray = context.convertUriToBytes(uri = uri)
                    )
                )
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.text_image_not_selected),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    LaunchedEffect(uiState.uiMessage) {
        context.showUiMessage(uiMessage = uiState.uiMessage) {
            viewModel.clearMessage()
        }
    }

    Scaffold(
        topBar = {
            TopAdAppBar(
                titleText = stringResource(id = R.string.text_creating_ad),
                onBackButtonClick = { viewModel.onBackButtonClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = AppTheme.colors.barColor)
                    .padding(bottom = AppTheme.dimens.extraSmall)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (uiState.loadingState == LoadingState.Loading) {
                LoadingCircularIndicator(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                )
            } else {
                Column(
                    modifier = Modifier
                        .padding(horizontal = AppTheme.dimens.extraSmall)
                        .verticalScroll(scrollState)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = stringResource(id = R.string.text_car_images),
                            modifier = Modifier.padding(AppTheme.dimens.medium)
                        )
                        ListAddedPhotos(
                            images = uiState.images.map { it.uri },
                            onAddImageClick = {
                                galleryLauncher.launch("image/*")
                            },
                            onImageClick = { viewModel.updateImageIdToShow(it) },
                            onRemoveImageClick = {
                                viewModel.removeImage(imageUri = it)
                            },
                            modifier = modifier
                                .fillMaxWidth()
                                .background(AppTheme.colors.cardColor)
                        )
                    }
                    InputField(
                        text = stringResource(id = R.string.input_brand),
                        value = uiState.brandValue,
                        isError = uiState.isBrandValueError,
                        onValueChange = { viewModel.updateBrandValue(value = it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppTheme.dimens.medium)
                    )
                    InputField(
                        text = stringResource(id = R.string.input_model),
                        value = uiState.modelValue,
                        isError = uiState.isModelValueError,
                        onValueChange = { viewModel.updateModelValue(value = it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppTheme.dimens.medium)
                    )
                    InputField(
                        text = stringResource(id = R.string.text_color),
                        value = uiState.colorValue,
                        isError = uiState.isColorValueError,
                        onValueChange = { viewModel.updateColorValue(value = it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppTheme.dimens.medium)
                    )
                    InputField(
                        text = stringResource(id = R.string.input_year_created),
                        value = uiState.realiseYearValue,
                        isError = uiState.isRealiseYearValueError,
                        keyboardType = KeyboardType.Number,
                        onValueChange = { viewModel.updateRealiseYearValue(value = it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppTheme.dimens.medium)
                    )

                    RowRadioButtons(
                        option = stringResource(id = R.string.radio_bodywork),
                        currentType = uiState.bodyTypeValue,
                        isError = uiState.isBodyTypeValueError,
                        typesName = BodyType.entries,
                        returnType = { viewModel.updateBodyTypeValue(value = it as BodyType?) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = AppTheme.dimens.extraSmall)
                    )
                    RowRadioButtons(
                        option = stringResource(id = R.string.radio_engine_type),
                        currentType = uiState.engineTypeValue,
                        isError = uiState.isEngineTypeValueError,
                        typesName = EngineType.entries,
                        returnType = { viewModel.updateEngineTypeValue(value = it as EngineType?) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = AppTheme.dimens.extraSmall)
                    )
                    InputField(
                        text = stringResource(id = R.string.input_engine_capacity),
                        value = uiState.engineCapacityValue,
                        isError = uiState.isEngineCapacityValue,
                        keyboardType = KeyboardType.Number,
                        onValueChange = { viewModel.updateEngineCapacityValue(value = it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppTheme.dimens.medium)
                    )
                    RowRadioButtons(
                        option = stringResource(id = R.string.radio_transmission_type),
                        currentType = uiState.transmissionValue,
                        isError = uiState.isTransmissionValueError,
                        typesName = TransmissionType.entries,
                        returnType = { viewModel.updateTransmissionValue(value = it as TransmissionType?) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = AppTheme.dimens.extraSmall)
                    )
                    RowRadioButtons(
                        option = stringResource(id = R.string.radio_drive_type),
                        currentType = uiState.driveTypeValue,
                        isError = uiState.isDriveTypeValueError,
                        typesName = DriveType.entries,
                        returnType = { viewModel.updateDriveTypeValue(value = it as DriveType?) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = AppTheme.dimens.extraSmall)
                    )
                    RowRadioButtons(
                        option = stringResource(id = R.string.radio_steering_wheel),
                        currentType = uiState.steeringWheelSideValue,
                        isError = uiState.isSteeringWheelSideValueError,
                        typesName = SteeringWheelSideType.entries,
                        returnType = { viewModel.updateSteeringWheelSideValue(value = it as SteeringWheelSideType?) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = AppTheme.dimens.extraSmall)
                    )
                    InputField(
                        text = stringResource(id = R.string.input_mileage),
                        value = uiState.mileageValue,
                        isError = uiState.isMileageValueError,
                        keyboardType = KeyboardType.Number,
                        onValueChange = { viewModel.updateMileageValue(value = it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppTheme.dimens.medium)
                    )
                    RowRadioButtons(
                        option = stringResource(id = R.string.radio_condition),
                        currentType = uiState.conditionValue,
                        isError = uiState.isConditionValueError,
                        typesName = ConditionType.entries,
                        returnType = { viewModel.updateConditionValue(value = it as ConditionType?) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = AppTheme.dimens.extraSmall)
                    )
                    InputField(
                        text = stringResource(id = R.string.input_price),
                        value = uiState.priceValue,
                        isError = uiState.isPriceValueError,
                        keyboardType = KeyboardType.Number,
                        onValueChange = { viewModel.updatePriceValue(value = it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppTheme.dimens.medium)
                    )
                    InputField(
                        text = stringResource(id = R.string.input_description),
                        value = uiState.descriptionValue,
                        isSingleLine = false,
                        onValueChange = { viewModel.updateDescriptionValue(value = it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppTheme.dimens.medium)
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = AppTheme.dimens.extraSmall)
                    ) {
                        CustomButton(
                            text = stringResource(id = R.string.button_create),
                            onClick = { viewModel.onCreateAdClick() },
                            modifier = Modifier.fillMaxWidth(AppTheme.dimens.buttonFillHalfWidth)
                        )
                    }
                }
            }
        }
    }

    if (uiState.isShowImagePager) {
        ImagePagerUri(
            images = uiState.images.map { it.uri },
            currentImageId = uiState.imageIdToShow,
            onClose = { viewModel.changeIsShowImagePager(value = false) }
        )
    }
}