package com.example.autohub.presentation.componets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.autohub.presentation.theme.containerColor

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    border: BorderStroke = BorderStroke(0.dp, Color.Transparent),
    colorButton: ButtonColors = buttonColors(
        containerColor = containerColor
    ),
    textColor: Color = Color.White,
    shape: Shape = RoundedCornerShape(topStart = 25.dp, bottomEnd = 25.dp)
) {
    Button(
        onClick = { onClick() },
        shape = shape,
        colors = colorButton,
        border = border,
        enabled = isEnabled,
        modifier = modifier.fillMaxWidth(0.5f)
    ) {
        Text(
            text = text,
            color = textColor
        )
    }
}