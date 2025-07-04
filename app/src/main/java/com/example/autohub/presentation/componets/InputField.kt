package com.example.autohub.presentation.componets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.autohub.R
import com.example.autohub.presentation.theme.labelColor

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    text: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: String = "",
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Unspecified,
    isSingleLine: Boolean = true
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.weight(1f)
        )
        TextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            shape = RoundedCornerShape(20.dp),
            placeholder = {
                Text(
                    text = placeHolder,
                    color = Color.LightGray
                )
            },
            singleLine = isSingleLine,
            // FixMe : другой способ (не по проверке текста)
            visualTransformation = if (text == "Пароль" || text == "Повторите пароль") PasswordVisualTransformation() else VisualTransformation.None,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = labelColor,
                focusedIndicatorColor = labelColor
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            trailingIcon = {
                if (isError) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = stringResource(id = R.string.content_necessary_fill_field)
                    )
                }
            },
            modifier = Modifier.weight(3f)
        )
    }
}