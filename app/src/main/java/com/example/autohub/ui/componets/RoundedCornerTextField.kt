package com.example.autohub.ui.componets

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.autohub.ui.theme.borderColor
import com.example.autohub.ui.theme.focusedTextFieldColor
import com.example.autohub.ui.theme.labelColor
import com.example.autohub.ui.theme.unfocusedTextFieldColor

@Composable
fun RoundedCornerTextField(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = text,
        onValueChange = { onValueChange(it) },
        shape = RoundedCornerShape(20.dp),
        label = { Text(label) },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = unfocusedTextFieldColor,
            focusedContainerColor = focusedTextFieldColor,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedLabelColor = labelColor,
            focusedLabelColor = labelColor
        ),
        singleLine = true,
        visualTransformation = if (label == "Пароль") PasswordVisualTransformation() else VisualTransformation.None,
        modifier = modifier.border(1.dp, borderColor, RoundedCornerShape(20.dp))
    )
}

//@Preview
//@Composable
//private fun RoundedCornerTextFieldPreview() {
//    RoundedCornerTextField("", "Password", { _ -> })
//}