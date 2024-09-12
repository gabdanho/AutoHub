package com.example.autohub.ui.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.autohub.R
import com.example.autohub.ui.componets.CustomButton
import com.example.autohub.ui.componets.RoundedCornerTextField
import com.example.autohub.ui.theme.containerColor

@Composable
fun LoginScreen(
    isProgressBarWork: Boolean,
    onLoginClick: (String, String) -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "AutoHub логотип"
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "CarHub",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.W300,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        RoundedCornerTextField(
            text = emailState.value,
            onValueChange = { emailState.value = it },
            label = "Логин"
        )
        Spacer(modifier = Modifier.height(8.dp))
        RoundedCornerTextField(
            text = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = "Пароль"
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomButton(
            text = "Войти",
            onClick = {


                if (emailState.value.isEmpty()) {
                    Toast.makeText(context, "Введите почту", Toast.LENGTH_LONG).show()
                }
                else if (passwordState.value.isEmpty()) {
                    Toast.makeText(context, "Введите пароль", Toast.LENGTH_LONG).show()
                }
                else if (passwordState.value.isEmpty() && emailState.value.isEmpty()) {
                    Toast.makeText(context, "Заполните все поля для входа в аккаунт", Toast.LENGTH_LONG).show()
                }
                else {
                    onLoginClick(emailState.value, passwordState.value)
                }
            },
            modifier = Modifier.padding(top = 32.dp)
        )
        CustomButton(
            text = "Регистрация",
            onClick = { onRegisterClick() },
            modifier = Modifier.padding(bottom = 32.dp)
        )
        if (isProgressBarWork) {
            CircularProgressIndicator(
                color = Color.Black,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(false, { _, _ -> }, { })
}