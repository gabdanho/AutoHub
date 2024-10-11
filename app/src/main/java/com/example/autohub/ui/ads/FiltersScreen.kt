package com.example.autohub.ui.ads

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.autohub.data.model.ad.OptionsTypes
import com.example.autohub.ui.componets.CustomButton
import com.example.autohub.ui.componets.InputField
import com.example.autohub.ui.componets.RowRadioButtons
import com.example.autohub.ui.componets.TopAdAppBar

@Composable
fun FiltersScreen(
    filters: Map<String, String>,
    onBackButtonClick: () -> Unit,
    onConfirmClick: (Map<String, String>) -> Unit,
    onClearFiltersClick: (Map<String, String>) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

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
                RowRadioButtons(
                    option = "Привод",
                    currentType = driveState.value,
                    typesName = OptionsTypes.driveTypes
                ) { driveState.value = it }
                InputField(
                    text = "Макс. год выпуска",
                    value = realiseYearState.value,
                    keyboardType = KeyboardType.Number,
                    onValueChange = { realiseYearState.value = it }
                )
                RowRadioButtons(
                    option = "Кузов",
                    currentType = bodyState.value,
                    typesName = OptionsTypes.bodyTypes
                ) { bodyState.value = it }
                RowRadioButtons(
                    option = "Тип двигателя",
                    currentType = typeEngineState.value,
                    typesName = OptionsTypes.typeEngineTypes
                ) { typeEngineState.value = it }
                InputField(
                    text = "Объем двигателя",
                    value = engineCapacityState.value,
                    keyboardType = KeyboardType.Number,
                    onValueChange = { engineCapacityState.value = it }
                )
                RowRadioButtons(
                    option = "Тип трансмиссии",
                    currentType = transmissionState.value,
                    typesName = OptionsTypes.transmissionsTypes
                ) { transmissionState.value = it }
                RowRadioButtons(
                    option = "Руль",
                    currentType = steeringWheelSideState.value,
                    typesName = OptionsTypes.steeringWheelSideTypes
                ) { steeringWheelSideState.value = it }
                InputField(
                    text = "Макс. пробег",
                    value = mileageState.value,
                    keyboardType = KeyboardType.Number,
                    onValueChange = { mileageState.value = it }
                )
                RowRadioButtons(
                    option = "Состояние",
                    currentType = conditionState.value,
                    typesName = OptionsTypes.conditionTypes
                ) { conditionState.value = it }
                InputField(
                    text = "Цена",
                    value = priceState.value,
                    keyboardType = KeyboardType.Number,
                    onValueChange = { priceState.value = it }
                )
                InputField(
                    text = "Город",
                    value = cityState.value,
                    onValueChange = { cityState.value = it }
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
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
                        Toast.makeText(context, "Фильтры добавлены", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.padding(top = 8.dp)
                )
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Очистить все фильтры",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(30.dp)
                        .clickable {
                            onClearFiltersClick(
                                mapOf(
                                    "city" to "",
                                    "brand" to "",
                                    "model" to "",
                                    "realiseYear" to "",
                                    "price" to "",
                                    "body" to "",
                                    "typeEngine" to "",
                                    "transmission" to "",
                                    "drive" to "",
                                    "condition" to "",
                                    "engineCapacity" to "",
                                    "steeringWheelSide" to "",
                                    "mileage" to "",
                                    "color" to ""
                                )
                            )

                            Toast.makeText(context, "Фильтры удалены", Toast.LENGTH_SHORT).show()
                        }
                )
            }
        }
    }
}

@Preview
@Composable
private fun FiltersScreenPreview() {
    FiltersScreen(mapOf(), { }, { }, { }, modifier = Modifier)
}