package com.example.autohub.presentation.componets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.autohub.R
import com.example.autohub.presentation.mapper.StringToResourceIdMapperImpl
import com.example.autohub.presentation.model.options.CarOption
import com.example.autohub.presentation.theme.containerColor
import com.example.autohub.presentation.theme.labelColor
import com.example.autohub.presentation.theme.unfocusedTextFieldColor

@Composable
fun RowRadioButtons(
    option: String,
    typesName: List<CarOption>,
    returnType: (CarOption?) -> Unit,
    modifier: Modifier = Modifier,
    currentType: CarOption? = null,
    isError: Boolean = false,
    radioButtonColors: RadioButtonColors = RadioButtonColors(
        selectedColor = containerColor,
        disabledSelectedColor = unfocusedTextFieldColor,
        unselectedColor = labelColor,
        disabledUnselectedColor = unfocusedTextFieldColor
    )
) {
    val selectedType = rememberSaveable { mutableStateOf(currentType) }

    Column {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        ) {
            Text(option)
            if (isError) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = stringResource(id = R.string.content_necessary_fill_field),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            items(typesName) { type ->
                Box(
                    modifier = Modifier
                        .clickable {
                            if (selectedType.value != type)
                                selectedType.value = type
                            else
                                selectedType.value = null
                            returnType(selectedType.value)
                        }
                ) {
                    RadioButton(
                        selected = selectedType.value == type,
                        onClick = { },
                        colors = radioButtonColors
                    )
                    Text(
                        text = stringResource(id = StringToResourceIdMapperImpl().map(resId = type.textRes))
                    )
                }
            }
        }
    }
}