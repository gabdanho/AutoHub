package com.example.autohub.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.autohub.data.model.user.User
import com.example.autohub.ui.componets.BackButton
import com.example.autohub.ui.componets.CustomButton
import com.example.autohub.ui.componets.InputField
import com.example.autohub.utils.isOnlyLetters
import com.example.autohub.utils.isPasswordValid
import com.example.autohub.utils.isValidCity
import com.example.autohub.utils.isValidEmail
import com.example.autohub.utils.isValidPhoneNumber

@Composable
fun RegisterScreen(
    onBackClick: () -> Unit,
    onRegisterClick: (String, String, User) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val firstNameState = remember { mutableStateOf("") }
    val secondNameState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val phoneState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val passwordRepeatState = remember { mutableStateOf("") }
    val cityState = remember { mutableStateOf("") }

    val isFirstNameError = remember { mutableStateOf(false) }
    val isSecondNameError = remember { mutableStateOf(false) }
    val isEmailError = remember { mutableStateOf(false) }
    val isPhoneError = remember { mutableStateOf(false) }
    val isPasswordError = remember { mutableStateOf(false) }
    val isCityError = remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = { BackButton(onBackClick) },
        floatingActionButtonPosition = FabPosition.Start
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "Регистрация",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            InputField(
                text = "Имя",
                value = firstNameState.value,
                placeHolder = "Иван",
                isError = isFirstNameError.value,
                onValueChange = { firstNameState.value = it }
            )
            InputField(
                text = "Фамилия",
                value = secondNameState.value,
                placeHolder = "Иванов",
                isError = isSecondNameError.value,
                onValueChange = { secondNameState.value = it }
            )
            InputField(
                text = "Почта",
                value = emailState.value,
                placeHolder = "ivan_ivanov@pochta.dot",
                isError = isEmailError.value,
                onValueChange = { emailState.value = it }
            )
            InputField(
                text = "Номер телефона",
                value = phoneState.value,
                placeHolder = "+79998887766",
                isError = isPhoneError.value,
                keyboardType = KeyboardType.Number,
                onValueChange = { phoneState.value = it }
            )
            InputField(
                text = "Город проживания",
                value = cityState.value,
                placeHolder = "Санкт-Петербург",
                isError = isCityError.value,
                onValueChange = { cityState.value = it }
            )
            InputField(
                text = "Пароль",
                value = passwordState.value,
                placeHolder = "●●●●●●●●●●●",
                isError = isPasswordError.value,
                onValueChange = { passwordState.value = it }
            )
            InputField(
                text = "Повторите пароль",
                value = passwordRepeatState.value,
                placeHolder = "●●●●●●●●●●●",
                isError = isPasswordError.value,
                onValueChange = { passwordRepeatState.value = it }
            )
            CustomButton(
                text = "Зарегистроваться",
                onClick = {
                    isFirstNameError.value = firstNameState.value.isEmpty()
                    isSecondNameError.value = secondNameState.value.isEmpty()
                    isPhoneError.value = phoneState.value.isEmpty()
                    isPasswordError.value = passwordState.value.isEmpty()
                    isCityError.value = cityState.value.isEmpty()
                    isEmailError.value = emailState.value.isEmpty()

                    if (
                        firstNameState.value.isEmpty() || secondNameState.value.isEmpty() || phoneState.value.isEmpty() ||
                        emailState.value.isEmpty() || passwordState.value.isEmpty() || cityState.value.isEmpty()
                    ) {
                        Toast.makeText(context, "Заполните все поля ввода", Toast.LENGTH_LONG).show()
                    }
                    else if (!firstNameState.value.isOnlyLetters() || !secondNameState.value.isOnlyLetters()) {
                        isFirstNameError.value = !firstNameState.value.isOnlyLetters()
                        isSecondNameError.value = !secondNameState.value.isOnlyLetters()
                        Toast.makeText(context, "Имя и фамилия должны содержать только буквы", Toast.LENGTH_LONG).show()
                    }
                    else if (!isValidEmail(emailState.value)) {
                        isEmailError.value = true
                        Toast.makeText(context, "Некорректная почта", Toast.LENGTH_LONG).show()
                    }
                    else if (!isValidPhoneNumber(phoneState.value)) {
                        isPhoneError.value = true
                        Toast.makeText(context, "Некорректный номер телефона", Toast.LENGTH_LONG).show()
                    }
                    else if (!isValidCity(cityState.value)) {
                        isCityError.value = true
                        Toast.makeText(context, "Некорректный город", Toast.LENGTH_LONG).show()
                    }
                    else if (passwordState.value != passwordRepeatState.value) {
                        isPasswordError.value = true
                        Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_LONG).show()
                    }
                    else if (!isPasswordValid(passwordState.value, context)) {
                        isPasswordError.value = true
                    }
                    else {
                        val user = User(
                            firstName = firstNameState.value,
                            secondName = secondNameState.value,
                            email = emailState.value,
                            phoneNumber = phoneState.value,
                            city = cityState.value
                        )

                        onRegisterClick(emailState.value, passwordState.value, user)
                    }
                },
                modifier = Modifier.padding(top = 32.dp)
            )
        }


    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen({ }, { _, _, _ -> })
}