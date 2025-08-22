package com.example.autohub.presentation.screens.ad.filters

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.autohub.R
import com.example.autohub.presentation.componets.CustomButton
import com.example.autohub.presentation.componets.InputField
import com.example.autohub.presentation.componets.RowRadioButtons
import com.example.autohub.presentation.componets.TopAdAppBar
import com.example.autohub.presentation.theme.barColor

@Composable
fun FiltersScreen(
    // FixMe : фильтры в дата класс
    // filters: Map<String, String>,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    /*
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
     */

    Scaffold(
        topBar = {
            TopAdAppBar(
                titleText = stringResource(id = R.string.text_filters),
                onBackButtonClick = TODO("onBackButtonClick()"),
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = barColor)
                    .padding(bottom = 8.dp)
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
                    text = stringResource(id = R.string.input_brand),
                    value = TODO("brandState.value"),
                    onValueChange = { TODO("brandState.value = it") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                InputField(
                    text = stringResource(id = R.string.input_model),
                    value = TODO("modelState.value"),
                    onValueChange = { TODO("modelState.value = it") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                InputField(
                    text = stringResource(id = R.string.input_color),
                    value = TODO("colorState.value"),
                    onValueChange = { TODO("colorState.value = it") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                RowRadioButtons(
                    option = stringResource(id = R.string.radio_drive_type),
                    currentType = TODO("driveState.value"),
                    typesName = TODO("OptionsTypes.driveTypes"),
                    returnType = { TODO("driveState.value = it") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp)
                )
                InputField(
                    text = stringResource(id = R.string.input_max_year_created),
                    value = TODO("realiseYearState.value"),
                    keyboardType = KeyboardType.Number,
                    onValueChange = { TODO("realiseYearState.value = it") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                RowRadioButtons(
                    option = stringResource(id = R.string.radio_bodywork),
                    currentType = TODO("bodyState.value"),
                    typesName = TODO("OptionsTypes.bodyTypes"),
                    returnType = { TODO("bodyState.value = it") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp)
                )
                RowRadioButtons(
                    option = stringResource(id = R.string.radio_engine_type),
                    currentType = TODO("typeEngineState.value"),
                    typesName = TODO("OptionsTypes.typeEngineTypes"),
                    returnType = { TODO("typeEngineState.value = it") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp)
                )
                InputField(
                    text = stringResource(id = R.string.input_engine_capacity),
                    value = TODO("engineCapacityState.value"),
                    keyboardType = KeyboardType.Number,
                    onValueChange = { TODO("engineCapacityState.value = it") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                RowRadioButtons(
                    option = stringResource(id = R.string.radio_transmission_type),
                    currentType = TODO("transmissionState.value"),
                    typesName = TODO("OptionsTypes.transmissionsTypes"),
                    returnType = { TODO("transmissionState.value = it") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp)
                )
                RowRadioButtons(
                    option = stringResource(id = R.string.radio_steering_wheel),
                    currentType = TODO("steeringWheelSideState.value"),
                    typesName = TODO("OptionsTypes.steeringWheelSideTypes"),
                    returnType = { TODO("steeringWheelSideState.value = it") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp)
                )
                InputField(
                    text = stringResource(id = R.string.input_max_mileage),
                    value = TODO("mileageState.value"),
                    keyboardType = KeyboardType.Number,
                    onValueChange = { TODO("mileageState.value = it") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                RowRadioButtons(
                    option = stringResource(id = R.string.radio_condition),
                    currentType = TODO("conditionState.value"),
                    typesName = TODO("OptionsTypes.conditionTypes"),
                    returnType = { TODO("conditionState.value = it") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp)
                )
                InputField(
                    text = stringResource(id = R.string.input_price),
                    value = TODO("priceState.value"),
                    keyboardType = KeyboardType.Number,
                    onValueChange = { TODO("priceState.value = it") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                InputField(
                    text = stringResource(id = R.string.input_city),
                    value = TODO("cityState.value"),
                    onValueChange = { TODO("cityState.value = it") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                CustomButton(
                    text = stringResource(id = R.string.button_accept_changes),
                    onClick = {
                        /*
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
                        Toast.makeText(context,
                            context.getString(R.string.text_filters_added), Toast.LENGTH_SHORT).show()
                         */
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.content_clear_all_filters),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(30.dp)
                        .clickable {
                            /*
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

                            Toast.makeText(
                                context,
                                context.getString(R.string.text_all_filters_deleted),
                                Toast.LENGTH_SHORT
                            ).show()
                             */
                        }
                )
            }
        }
    }
}

//@Preview
//@Composable
//private fun FiltersScreenPreview() {
//    FiltersScreen(mapOf(), { }, { }, { }, modifier = Modifier)
//}