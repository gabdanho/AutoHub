package com.example.autohub.presentation.componets

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.VisualTransformation
import com.example.autohub.presentation.theme.AppTheme


@Composable
fun RoundedCornerTextField(
    text: String,
    label: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = AppTheme.shapes.textFieldShape,
    colors: TextFieldColors = TextFieldDefaults.colors(
        unfocusedContainerColor = AppTheme.colors.unfocusedTextFieldColor,
        focusedContainerColor = AppTheme.colors.focusedTextFieldColor,
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedLabelColor = AppTheme.colors.labelColor,
        focusedLabelColor = AppTheme.colors.labelColor
    ),
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    TextField(
        value = text,
        onValueChange = { onValueChange(it) },
        shape = shape,
        label = { Text(label) },
        colors = colors,
        singleLine = true,
        visualTransformation = visualTransformation,
        modifier = modifier
    )
}