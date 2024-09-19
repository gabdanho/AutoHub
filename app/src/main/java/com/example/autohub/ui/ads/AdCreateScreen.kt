package com.example.autohub.ui.ads

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.autohub.data.CarAd
import com.example.autohub.ui.componets.CustomButton
import com.example.autohub.ui.componets.InputField
import com.example.autohub.ui.componets.PhotosList
import com.example.autohub.ui.componets.TopAdAppBar

@Composable
fun AdCreateScreen(
    onCreateAdClick: (CarAd, List<Uri>) -> Unit,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier
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

    val addImageUri = Uri.Builder()
        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(context.packageName)
        .appendPath("drawable")
        .appendPath("add_img")
        .build()
    val images = remember { mutableStateListOf<Uri>(addImageUri) }
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            images.add(0, uri)
        } else {
            Toast.makeText(context, "Изображение не было выбрано", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAdAppBar(
                titleText = "Создание объявления",
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
                    text = "Фотографии автомобиля",
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
                    text = "Бренд",
                    value = brandState.value,
                    onValueChange = { brandState.value = it }
                )
                InputField(
                    text = "Модель",
                    value = modelState.value,
                    onValueChange = { modelState.value = it }
                )
                InputField(
                    text = "Цвет",
                    value = colorState.value,
                    onValueChange = { colorState.value = it }
                )
                InputField(
                    text = "Год выпуска",
                    value = realiseYearState.value,
                    onValueChange = { realiseYearState.value = it }
                )
                InputField(
                    text = "Кузов",
                    value = bodyState.value,
                    onValueChange = { bodyState.value = it }
                )
                InputField(
                    text = "Тип двигателя",
                    value = typeEngineState.value,
                    onValueChange = { typeEngineState.value = it }
                )
                InputField(
                    text = "Объем двигателя",
                    value = engineCapacityState.value,
                    onValueChange = { engineCapacityState.value = it }
                )
                InputField(
                    text = "Трансмиссия",
                    value = transmissionState.value,
                    onValueChange = { transmissionState.value = it }
                )
                InputField(
                    text = "Привод",
                    value = driveState.value,
                    onValueChange = { driveState.value = it }
                )
                InputField(
                    text = "Руль",
                    value = steeringWheelSideState.value,
                    onValueChange = { steeringWheelSideState.value = it }
                )
                InputField(
                    text = "Пробег",
                    value = mileageState.value,
                    onValueChange = { mileageState.value = it }
                )
                InputField(
                    text = "Состояние",
                    value = conditionState.value,
                    onValueChange = { conditionState.value = it }
                )
                InputField(
                    text = "Цена",
                    value = priceState.value,
                    onValueChange = { priceState.value = it }
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    CustomButton(
                        text = "Создать",
                        onClick = {
                            if (images.size == 1) {
                                Toast.makeText(context, "Добавьте изображения", Toast.LENGTH_LONG).show()
                            } else if (brandState.value.isBlank() || modelState.value.isBlank() ||
                                colorState.value.isBlank() || realiseYearState.value.isBlank() ||
                                bodyState.value.isBlank() || typeEngineState.value.isBlank() ||
                                engineCapacityState.value.isBlank() || transmissionState.value.isBlank() ||
                                driveState.value.isBlank() || steeringWheelSideState.value.isBlank() ||
                                mileageState.value.isBlank() || conditionState.value.isBlank() ||
                                priceState.value.isBlank()) {
                                Toast.makeText(context, "Заполните все поля", Toast.LENGTH_LONG).show()
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
                                    price = priceState.value
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

@Preview(showBackground = true)
@Composable
private fun AdCreateScreenPreview() {
    AdCreateScreen({ _, _ -> }, { })
}