package com.example.autohub.ui.ads

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.autohub.R
import com.example.autohub.ui.componets.CustomButton
import com.example.autohub.ui.componets.InputField
import com.example.autohub.ui.componets.PhotosList
import com.example.autohub.ui.componets.TopAdAppBar
import com.example.autohub.ui.theme.cardColor
import com.example.autohub.ui.theme.containerColor

@Composable
fun AdCreateScreen(
    onCreateAdClick: () -> Unit,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    val imagesState = remember { mutableStateOf(listOf(R.drawable.add_img)) }
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
                .verticalScroll(scrollState)
        ) {
            Column(modifier = Modifier.weight(1.4f)) {
                Text(
                    text = "Фотографии автомобиля",
                    modifier = Modifier.padding(16.dp)
                )
                PhotosList(imagesState.value, { })
            }
            Column(modifier = Modifier.weight(3f)) {
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
                CustomButton(
                    text = "Создать",
                    onClick = { onCreateAdClick() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AdCreateScreenPreview() {
    AdCreateScreen({ }, { })
}