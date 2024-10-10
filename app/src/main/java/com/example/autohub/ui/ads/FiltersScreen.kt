package com.example.autohub.ui.ads

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.autohub.ui.componets.CustomButton
import com.example.autohub.ui.componets.InputField
import com.example.autohub.ui.componets.TopAdAppBar

@Composable
fun FiltersScreen(
    filters: Map<String, String>,
    onBackButtonClick: () -> Unit,
    onConfirmClick: (Map<String, String>) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    val brandState = remember { mutableStateOf(filters["brand"] ?: "") }
    val modelState = remember { mutableStateOf(filters["model"] ?: "") }
    val colorState = remember { mutableStateOf(filters["color"] ?: "") }
    val driveState = remember { mutableStateOf(filters["drive"] ?: "") }
    val realiseYearState = remember { mutableStateOf(filters["realiseYear"] ?: "") }
    val bodyState = remember { mutableStateOf(filters["body"] ?: "") }
    val typeEngineState = remember { mutableStateOf(filters["typeEngine"] ?: "") }
    val engineCapacityState = remember { mutableStateOf(filters["engineCapacity"] ?: "") }
    val transmissionState = remember { mutableStateOf(filters["transmission"] ?: "") }
    val steeringWheelSideState = remember { mutableStateOf(filters["steeringWheelSide"] ?: "") }
    val mileageState = remember { mutableStateOf(filters["mileage"] ?: "") }
    val conditionState = remember { mutableStateOf(filters["condition"] ?: "") }
    val priceState = remember { mutableStateOf(filters["price"] ?: "") }
    val cityState = remember { mutableStateOf(filters["city"] ?: "") }

    Scaffold(
        topBar = {
            TopAdAppBar(
                titleText = "Фильтры",
                onBackButtonClick = onBackButtonClick,
                modifier = modifier.padding(bottom = 8.dp)
            )
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            Column {
                InputField(
                    text = "Модель",
                    value = modelState.value,
                    onValueChange = { modelState.value = it }
                )
                InputField(
                    text = "Бренд",
                    value = brandState.value,
                    onValueChange = { brandState.value = it }
                )
                InputField(
                    text = "Цвет",
                    value = colorState.value,
                    onValueChange = { colorState.value = it }
                )
                InputField(
                    text = "Привод",
                    value = driveState.value,
                    onValueChange = { driveState.value = it }
                )
                InputField(
                    text = "Макс. год выпуска",
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
                    text = "Тип трансмиссии",
                    value = transmissionState.value,
                    onValueChange = { transmissionState.value = it }
                )
                InputField(
                    text = "Руль",
                    value = steeringWheelSideState.value,
                    onValueChange = { steeringWheelSideState.value = it }
                )
                InputField(
                    text = "Макс. пробег",
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
                InputField(
                    text = "Город",
                    value = cityState.value,
                    onValueChange = { cityState.value = it }
                )
            }
            CustomButton(
                text = "Принять изменения",
                onClick = {
                    onConfirmClick(
                        mapOf(
                            "city" to cityState.value,
                            "brand" to brandState.value,
                            "model" to modelState.value,
                            "realiseYear" to realiseYearState.value,
                            "price" to priceState.value,
                            "body" to bodyState.value,
                            "typeEngine" to typeEngineState.value,
                            "transmission" to transmissionState.value,
                            "drive" to driveState.value,
                            "condition" to conditionState.value,
                            "engineCapacity" to engineCapacityState.value,
                            "steeringWheelSide" to steeringWheelSideState.value,
                            "mileage" to mileageState.value,
                            "color" to colorState.value
                        )
                    )
                },
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun FiltersScreenPreview() {
    FiltersScreen(mapOf(), { }, { }, modifier = Modifier)
}