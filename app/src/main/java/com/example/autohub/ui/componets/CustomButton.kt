package com.example.autohub.ui.componets

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.autohub.ui.theme.containerColor

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
    border: BorderStroke = BorderStroke(0.dp, Color.Transparent),
    colorButton: ButtonColors = buttonColors(
        containerColor = containerColor
    ),
    textColor: Color = Color.White
) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(topStart = 25.dp, bottomEnd = 25.dp),
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

//@Preview(widthDp = 400)
//@Composable
//private fun CustomButtonPreview() {
//    CustomButton(
//        "Вход",
//        { },
//        colorButton = buttonColors(
//        containerColor = containerColor
//    ))
//}