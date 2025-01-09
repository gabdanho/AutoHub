package com.example.autohub.ui.componets

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.autohub.ui.theme.containerColor
import com.example.autohub.ui.theme.labelColor
import com.example.autohub.ui.theme.unfocusedTextFieldColor

@Composable
fun RowRadioButtons(
    modifier: Modifier = Modifier,
    option: String,
    currentType: String = "",
    typesName: List<String>,
    isError: Boolean = false,
    returnType: (String) -> Unit
) {
    val selectedType = rememberSaveable { mutableStateOf(currentType) }

    Column {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
        ) {
            Text(option)
            if (isError) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Заполните поле",
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(end = 8.dp)
        ) {
            items(typesName) { type ->
                RadioButton(
                    selected = selectedType.value == type,
                    onClick = {
                        if (selectedType.value != type)
                            selectedType.value = type
                        else
                            selectedType.value = ""
                        returnType(selectedType.value)
                    },
                    colors = RadioButtonColors(
                        selectedColor = containerColor,
                        disabledSelectedColor = unfocusedTextFieldColor,
                        unselectedColor = labelColor,
                        disabledUnselectedColor = unfocusedTextFieldColor
                    )
                )
                Text(
                    text = type,
                    modifier = Modifier.clickable {
                        if (selectedType.value != type)
                            selectedType.value = type
                        else
                            selectedType.value = ""
                        returnType(selectedType.value)
                    }
                )
            }
        }
    }
}

//@Preview
//@Composable
//private fun Test() {
//    RowRadioButtons(
//        option = "pokat",
//        currentType = "poka",
//        typesName = listOf("priv", "poka")
//    ) { }
//}