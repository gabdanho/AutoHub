package com.example.autohub.presentation.componets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.autohub.R
import com.example.autohub.presentation.mapper.resources.StringToResourceIdMapperImpl
import com.example.autohub.presentation.model.options.CarOption
import com.example.autohub.presentation.theme.AppTheme

@Composable
fun RowRadioButtons(
    option: String,
    typesName: List<CarOption>,
    returnType: (CarOption?) -> Unit,
    modifier: Modifier = Modifier,
    currentType: CarOption? = null,
    isError: Boolean = false,
    radioButtonColors: RadioButtonColors = RadioButtonColors(
        selectedColor = AppTheme.colors.containerColor,
        disabledSelectedColor = AppTheme.colors.unfocusedTextFieldColor,
        unselectedColor = AppTheme.colors.labelColor,
        disabledUnselectedColor = AppTheme.colors.unfocusedTextFieldColor
    ),
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = AppTheme.dimens.extraSmall)
        ) {
            Text(option)
            if (isError) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = stringResource(id = R.string.content_necessary_fill_field),
                    modifier = Modifier.padding(start = AppTheme.dimens.ultraSmall)
                )
            }
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.extraSmall),
            modifier = modifier.padding(horizontal = AppTheme.dimens.extraSmall)
        ) {
            items(typesName) { type ->
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable {
                            val newValue = if (currentType != type) type else null
                            returnType(newValue)
                        }
                ) {
                    Text(
                        text = stringResource(
                            id = StringToResourceIdMapperImpl().map(resId = type.textRes)
                        )
                    )
                    RadioButton(
                        selected = currentType == type,
                        onClick = {
                            val newValue = if (currentType != type) type else null
                            returnType(newValue)
                        },
                        colors = radioButtonColors
                    )
                }
            }
        }
    }
}