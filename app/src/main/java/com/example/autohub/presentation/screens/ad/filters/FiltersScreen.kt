package com.example.autohub.presentation.screens.ad.filters

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.autohub.R
import com.example.autohub.presentation.componets.CustomButton
import com.example.autohub.presentation.componets.InputField
import com.example.autohub.presentation.componets.RowRadioButtons
import com.example.autohub.presentation.componets.TopAdAppBar
import com.example.autohub.presentation.model.options.BodyType
import com.example.autohub.presentation.model.options.ConditionType
import com.example.autohub.presentation.model.options.DriveType
import com.example.autohub.presentation.model.options.EngineType
import com.example.autohub.presentation.model.options.SteeringWheelSideType
import com.example.autohub.presentation.model.options.TransmissionType
import com.example.autohub.presentation.theme.barColor

@Composable
fun FiltersScreen(
    modifier: Modifier = Modifier,
    viewModel: FiltersScreenViewModel = hiltViewModel<FiltersScreenViewModel>(),
) {
    val scrollState = rememberScrollState()
    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {
            TopAdAppBar(
                titleText = stringResource(id = R.string.text_filters),
                onBackButtonClick = { viewModel.onBackButtonClick() },
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
                    value = uiState.brandValue,
                    onValueChange = { viewModel.updateBrandValue(value = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                InputField(
                    text = stringResource(id = R.string.input_model),
                    value = uiState.modelValue,
                    onValueChange = { viewModel.updateModelValue(value = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                InputField(
                    text = stringResource(id = R.string.input_color),
                    value = uiState.colorValue,
                    onValueChange = { viewModel.updateColorValue(value = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                RowRadioButtons(
                    option = stringResource(id = R.string.radio_drive_type),
                    currentType = uiState.driveTypeValue,
                    typesName = DriveType.entries,
                    returnType = { viewModel.updateDriveTypeValue(value = it as DriveType) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp)
                )
                InputField(
                    text = stringResource(id = R.string.input_max_year_created),
                    value = uiState.realiseYearValue,
                    keyboardType = KeyboardType.Number,
                    onValueChange = { viewModel.updateRealiseYearValue(value = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                RowRadioButtons(
                    option = stringResource(id = R.string.radio_bodywork),
                    currentType = uiState.bodyTypeValue,
                    typesName = BodyType.entries,
                    returnType = { viewModel.updateBodyTypeValue(value = it as BodyType) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp)
                )
                RowRadioButtons(
                    option = stringResource(id = R.string.radio_engine_type),
                    currentType = uiState.engineTypeValue,
                    typesName = EngineType.entries,
                    returnType = { viewModel.updateEngineTypeValue(value = it as EngineType) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp)
                )
                InputField(
                    text = stringResource(id = R.string.input_engine_capacity),
                    value = uiState.engineCapacityValue,
                    keyboardType = KeyboardType.Number,
                    onValueChange = { viewModel.updateEngineCapacityValue(value = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                RowRadioButtons(
                    option = stringResource(id = R.string.radio_transmission_type),
                    currentType = uiState.transmissionValue,
                    typesName = TransmissionType.entries,
                    returnType = { viewModel.updateTransmissionValue(value = it as TransmissionType) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp)
                )
                RowRadioButtons(
                    option = stringResource(id = R.string.radio_steering_wheel),
                    currentType = uiState.steeringWheelSideValue,
                    typesName = SteeringWheelSideType.entries,
                    returnType = { viewModel.updateSteeringWheelSideValue(value = it as SteeringWheelSideType) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp)
                )
                InputField(
                    text = stringResource(id = R.string.input_max_mileage),
                    value = uiState.mileageValue,
                    keyboardType = KeyboardType.Number,
                    onValueChange = { viewModel.updateMileageValue(value = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                RowRadioButtons(
                    option = stringResource(id = R.string.radio_condition),
                    currentType = uiState.conditionValue,
                    typesName = ConditionType.entries,
                    returnType = { viewModel.updateConditionValue(value = it as ConditionType) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp)
                )
                InputField(
                    text = stringResource(id = R.string.input_price),
                    value = uiState.priceValue,
                    keyboardType = KeyboardType.Number,
                    onValueChange = { viewModel.updatePriceValue(value = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                InputField(
                    text = stringResource(id = R.string.input_city),
                    value = uiState.cityValue,
                    onValueChange = { viewModel.updateCityValue(value = it) },
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
                        viewModel.onConfirmClick()
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
                            viewModel.onClearFiltersClick()
                        }
                )
            }
        }
    }
}