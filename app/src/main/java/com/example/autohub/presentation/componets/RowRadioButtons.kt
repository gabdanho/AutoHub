package com.example.autohub.presentation.componets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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

/**
 * Ряд радио-кнопок с названием опции.
 *
 * @param option Название группы опций.
 * @param typesName Список опций.
 * @param returnType Лямбда возвращает выбранный тип или null.
 * @param modifier Модификатор для настройки компонента.
 * @param currentType Текущий выбранный тип.
 * @param isError Флаг ошибки заполнения.
 */
@Composable
fun RowRadioButtons(
    option: String,
    typesName: List<CarOption>,
    returnType: (CarOption?) -> Unit,
    modifier: Modifier = Modifier,
    currentType: CarOption? = null,
    isError: Boolean = false,
) {
    val scrollState = rememberScrollState()

    Column {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = AppTheme.dimens.extraSmall)
        ) {
            Text(text = option)
            if (isError) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = stringResource(id = R.string.content_necessary_fill_field),
                    modifier = Modifier.padding(start = AppTheme.dimens.ultraSmall)
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.extraSmall),
            modifier = modifier
                .padding(horizontal = AppTheme.dimens.extraSmall)
                .horizontalScroll(scrollState)
        ) {
            typesName.forEach { type ->
                OptionRadioButton(
                    optionType = type,
                    currentType = currentType,
                    returnType = returnType
                )
            }
        }
    }
}

@Composable
private fun OptionRadioButton(
    optionType: CarOption,
    returnType: (CarOption?) -> Unit,
    modifier: Modifier = Modifier,
    currentType: CarOption? = null,
    radioButtonColors: RadioButtonColors = RadioButtonColors(
        selectedColor = AppTheme.colors.containerColor,
        disabledSelectedColor = AppTheme.colors.unfocusedTextFieldColor,
        unselectedColor = AppTheme.colors.labelColor,
        disabledUnselectedColor = AppTheme.colors.unfocusedTextFieldColor
    ),
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable {
                val newValue = if (currentType != optionType) optionType else null
                returnType(newValue)
            }
    ) {
        Text(
            text = stringResource(
                id = StringToResourceIdMapperImpl().map(resId = optionType.textRes)
            )
        )
        RadioButton(
            selected = currentType == optionType,
            onClick = {
                val newValue = if (currentType != optionType) optionType else null
                returnType(newValue)
            },
            colors = radioButtonColors
        )
    }

}