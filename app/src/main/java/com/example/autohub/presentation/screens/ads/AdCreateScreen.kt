package com.example.autohub.presentation.screens.ads

import android.content.ContentResolver
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.autohub.R
import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.presentation.model.OptionsTypes
import com.example.autohub.presentation.componets.CustomButton
import com.example.autohub.presentation.componets.InputField
import com.example.autohub.presentation.componets.PhotosList
import com.example.autohub.presentation.componets.RowRadioButtons
import com.example.autohub.presentation.componets.TopAdAppBar
import com.example.autohub.data.utils.isOnlyDigits
import com.example.autohub.data.utils.isOnlyLetters

@Composable
fun AdCreateScreen(
    modifier: Modifier = Modifier,
    onCreateAdClick: (CarAd, List<Uri>) -> Unit,
    onBackButtonClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val brandState = remember { mutableStateOf("") }
    val modelState = remember { mutableStateOf("") }
    val colorState = remember { mutableStateOf("") }
    val driveState = remember { mutableStateOf("") }
    val realiseYearState = remember { mutableStateOf("") }
    val bodyState = remember { mutableStateOf("") }
    val typeEngineState = remember { mutableStateOf("") }
    val engineCapacityState = remember { mutableStateOf("") }
    val transmissionState = remember { mutableStateOf("") }
    val steeringWheelSideState = remember { mutableStateOf("") }
    val mileageState = remember { mutableStateOf("") }
    val conditionState = remember { mutableStateOf("") }
    val priceState = remember { mutableStateOf("") }
    val descriptionState = remember { mutableStateOf("") }

    val isBrandError = remember { mutableStateOf(false) }
    val isModelError = remember { mutableStateOf(false) }
    val isColorError = remember { mutableStateOf(false) }
    val isDriveError = remember { mutableStateOf(false) }
    val isRealiseYearError = remember { mutableStateOf(false) }
    val isBodyError = remember { mutableStateOf(false) }
    val isTypeEngineError = remember { mutableStateOf(false) }
    val isEngineCapacityError = remember { mutableStateOf(false) }
    val isTransmissionError = remember { mutableStateOf(false) }
    val isSteeringWheelSideError = remember { mutableStateOf(false) }
    val isMileageError = remember { mutableStateOf(false) }
    val isConditionError = remember { mutableStateOf(false) }
    val isPriceError = remember { mutableStateOf(false) }

    val addImageUri = Uri.Builder()
        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(context.packageName)
        // FixMe : Захардить
        .appendPath("drawable")
        .appendPath("add_img")
        .build()
    val images = remember { mutableStateListOf<Uri>(addImageUri) }
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            images.add(0, uri)
        } else {
            Toast.makeText(context, context.getString(R.string.text_image_not_selected), Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAdAppBar(
                titleText = stringResource(id = R.string.text_creating_ad),
                modifier = Modifier.padding(bottom = 8.dp),
                onBackButtonClick = onBackButtonClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(modifier = Modifier.weight(1.4f)) {
                Text(
                    text = stringResource(id = R.string.text_car_images),
                    modifier = Modifier.padding(16.dp)
                )
                PhotosList(
                    images = images.toList(),
                    onAddImageClick = {
                        galleryLauncher.launch("image/*")
                    }
                )
            }
            Column(
                modifier = Modifier
                    .weight(3f)
                    .verticalScroll(scrollState)
            ) {
                InputField(
                    text = stringResource(id = R.string.input_brand),
                    value = brandState.value,
                    isError = isBrandError.value,
                    onValueChange = { brandState.value = it }
                )
                InputField(
                    text = stringResource(id = R.string.input_model),
                    value = modelState.value,
                    isError = isModelError.value,
                    onValueChange = { modelState.value = it }
                )
                InputField(
                    text = stringResource(id = R.string.text_color),
                    value = colorState.value,
                    isError = isColorError.value,
                    onValueChange = { colorState.value = it }
                )
                InputField(
                    text = stringResource(id = R.string.input_year_created),
                    value = realiseYearState.value,
                    isError = isRealiseYearError.value,
                    keyboardType = KeyboardType.Number,
                    onValueChange = { realiseYearState.value = it }
                )
                RowRadioButtons(
                    option = stringResource(id = R.string.radio_bodywork),
                    isError = isBodyError.value,
                    typesName = OptionsTypes.bodyTypes
                ) { bodyState.value = it }
                RowRadioButtons(
                    option = stringResource(id = R.string.radio_engine_type),
                    isError = isTypeEngineError.value,
                    typesName = OptionsTypes.typeEngineTypes
                ) { typeEngineState.value = it }
                InputField(
                    text = stringResource(id = R.string.input_engine_capacity),
                    value = engineCapacityState.value,
                    isError = isEngineCapacityError.value,
                    keyboardType = KeyboardType.Number,
                    onValueChange = { engineCapacityState.value = it }
                )
                RowRadioButtons(
                    option = stringResource(id = R.string.radio_transmission_type),
                    isError = isTransmissionError.value,
                    typesName = OptionsTypes.transmissionsTypes
                ) { transmissionState.value = it }
                RowRadioButtons(
                    option = stringResource(id = R.string.radio_drive_type),
                    isError = isDriveError.value,
                    typesName = OptionsTypes.driveTypes
                ) { driveState.value = it }
                RowRadioButtons(
                    option = stringResource(id = R.string.radio_steering_wheel),
                    isError = isSteeringWheelSideError.value,
                    typesName = OptionsTypes.steeringWheelSideTypes
                ) { steeringWheelSideState.value = it }
                InputField(
                    text = stringResource(id = R.string.input_mileage),
                    value = mileageState.value,
                    isError = isMileageError.value,
                    keyboardType = KeyboardType.Number,
                    onValueChange = { mileageState.value = it }
                )
                RowRadioButtons(
                    option = stringResource(id = R.string.radio_condition),
                    isError = isConditionError.value,
                    typesName = OptionsTypes.conditionTypes
                ) { conditionState.value = it }
                InputField(
                    text = stringResource(id = R.string.input_price),
                    value = priceState.value,
                    isError = isPriceError.value,
                    keyboardType = KeyboardType.Number,
                    onValueChange = { priceState.value = it }
                )
                InputField(
                    text = stringResource(id = R.string.input_description),
                    value = descriptionState.value,
                    isSingleLine = false,
                    onValueChange = { descriptionState.value = it }
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    CustomButton(
                        text = stringResource(id = R.string.button_create),
                        onClick = {
                            isBrandError.value = brandState.value.isEmpty()
                            isModelError.value = modelState.value.isEmpty()
                            isColorError.value = colorState.value.isEmpty()
                            isDriveError.value = driveState.value.isEmpty()
                            isRealiseYearError.value = realiseYearState.value.isEmpty()
                            isBodyError.value = bodyState.value.isEmpty()
                            isTypeEngineError.value = typeEngineState.value.isEmpty()
                            isEngineCapacityError.value = engineCapacityState.value.isEmpty()
                            isTransmissionError.value = transmissionState.value.isEmpty()
                            isSteeringWheelSideError.value = steeringWheelSideState.value.isEmpty()
                            isMileageError.value = mileageState.value.isEmpty()
                            isConditionError.value = conditionState.value.isEmpty()
                            isPriceError.value = priceState.value.isEmpty()

                            if (images.size == 1) {
                                Toast.makeText(context,
                                    context.getString(R.string.text_add_images), Toast.LENGTH_LONG).show()
                            } else if (brandState.value.isBlank() || modelState.value.isBlank() ||
                                colorState.value.isBlank() || realiseYearState.value.isBlank() ||
                                bodyState.value.isBlank() || typeEngineState.value.isBlank() ||
                                engineCapacityState.value.isBlank() || transmissionState.value.isBlank() ||
                                driveState.value.isBlank() || steeringWheelSideState.value.isBlank() ||
                                mileageState.value.isBlank() || conditionState.value.isBlank() ||
                                priceState.value.isBlank()) {
                                Toast.makeText(context, context.getString(R.string.content_necessary_fill_field), Toast.LENGTH_LONG).show()
                            } else if (!colorState.value.isOnlyLetters()) {
                                isColorError.value = true
                                Toast.makeText(context,
                                    context.getString(R.string.text_incorrect_color), Toast.LENGTH_LONG).show()
                            } else if (!realiseYearState.value.isOnlyDigits()) {
                                isRealiseYearError.value = true
                                Toast.makeText(context,
                                    context.getString(R.string.text_incorrect_year_created), Toast.LENGTH_LONG).show()
                            } else if (!bodyState.value.isOnlyLetters()) {
                                isBodyError.value = true
                                Toast.makeText(context,
                                    context.getString(R.string.text_incorrect_body_type), Toast.LENGTH_LONG).show()
                            } else if (!typeEngineState.value.isOnlyLetters()) {
                                isTypeEngineError.value = true
                                Toast.makeText(context,
                                    context.getString(R.string.text_incorrect_engine_type), Toast.LENGTH_LONG).show()
                            } else if (!engineCapacityState.value.isOnlyDigits()) {
                                isEngineCapacityError.value = true
                                Toast.makeText(context,
                                    context.getString(R.string.text_incorrect_engine_capacity), Toast.LENGTH_LONG).show()
                            } else if (!transmissionState.value.isOnlyLetters()) {
                                isTransmissionError.value = true
                                Toast.makeText(context,
                                    context.getString(R.string.text_incorrect_transmission_type), Toast.LENGTH_LONG).show()
                            } else if (!driveState.value.isOnlyLetters()) {
                                isDriveError.value = true
                                Toast.makeText(context,
                                    context.getString(R.string.text_incorrect_drive_type), Toast.LENGTH_LONG).show()
                            } else if (!steeringWheelSideState.value.isOnlyLetters()) {
                                isSteeringWheelSideError.value = true
                                Toast.makeText(context,
                                    context.getString(R.string.text_incorrect_steering_wheel), Toast.LENGTH_LONG).show()
                            } else if (!mileageState.value.isOnlyDigits()) {
                                isMileageError.value = true
                                Toast.makeText(context,
                                    context.getString(R.string.text_incorrect_mileage), Toast.LENGTH_LONG).show()
                            } else if (!conditionState.value.isOnlyLetters()) {
                                isColorError.value = true
                                Toast.makeText(context,
                                    context.getString(R.string.text_incorrect_condition), Toast.LENGTH_LONG).show()
                            } else if (!priceState.value.isOnlyDigits()) {
                                isPriceError.value = true
                                Toast.makeText(context,
                                    context.getString(R.string.text_incorrect_price), Toast.LENGTH_LONG).show()
                            } else {
                                val carAd = CarAd(
                                    brand = brandState.value,
                                    model = modelState.value,
                                    color = colorState.value,
                                    realiseYear = realiseYearState.value,
                                    body = bodyState.value,
                                    typeEngine = typeEngineState.value,
                                    engineCapacity = engineCapacityState.value,
                                    transmission = transmissionState.value,
                                    drive = driveState.value,
                                    steeringWheelSide = steeringWheelSideState.value,
                                    mileage = mileageState.value,
                                    condition = conditionState.value,
                                    price = priceState.value,
                                    description = descriptionState.value
                                )
                                onCreateAdClick(carAd, images.toList().dropLast(1))
                            }
                        }
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun AdCreateScreenPreview() {
//    AdCreateScreen({ _, _ -> }, { })
//}