package com.example.autohub.presentation.screens.login

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.autohub.R
import com.example.autohub.presentation.model.user.User
import com.example.autohub.presentation.componets.BackButton
import com.example.autohub.presentation.componets.CustomButton
import com.example.autohub.presentation.componets.InputField
import com.example.autohub.presentation.utils.isOnlyLetters
import com.example.autohub.presentation.utils.isPasswordValid
import com.example.autohub.presentation.utils.isValidCity
import com.example.autohub.presentation.utils.isValidEmail
import com.example.autohub.presentation.utils.isValidPhoneNumber

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onRegisterClick: (String, String, User) -> Unit
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
        floatingActionButton = { BackButton(onBackClick = onBackClick) },
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
                text = stringResource(id = R.string.text_registration),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            InputField(
                text = stringResource(id = R.string.input_first_name),
                value = firstNameState.value,
                placeHolder = stringResource(id = R.string.placeholder_first_name),
                isError = isFirstNameError.value,
                onValueChange = { firstNameState.value = it }
            )
            InputField(
                text = stringResource(id = R.string.input_last_name),
                value = secondNameState.value,
                placeHolder = stringResource(id = R.string.placeholder_last_name),
                isError = isSecondNameError.value,
                onValueChange = { secondNameState.value = it }
            )
            InputField(
                text = stringResource(id = R.string.input_email),
                value = emailState.value,
                placeHolder = stringResource(id = R.string.placeholder_email),
                isError = isEmailError.value,
                onValueChange = { emailState.value = it }
            )
            InputField(
                text = stringResource(id = R.string.input_phone),
                value = phoneState.value,
                placeHolder = stringResource(id = R.string.placeholder_phone),
                isError = isPhoneError.value,
                keyboardType = KeyboardType.Number,
                onValueChange = { phoneState.value = it }
            )
            InputField(
                text = stringResource(id = R.string.input_city),
                value = cityState.value,
                placeHolder = stringResource(id = R.string.placeholder_city),
                isError = isCityError.value,
                onValueChange = { cityState.value = it }
            )
            InputField(
                text = stringResource(id = R.string.input_password),
                value = passwordState.value,
                placeHolder = stringResource(id = R.string.placeholder_password),
                isError = isPasswordError.value,
                onValueChange = { passwordState.value = it }
            )
            InputField(
                text = stringResource(id = R.string.input_repeat_password),
                value = passwordRepeatState.value,
                placeHolder = stringResource(id = R.string.placeholder_password),
                isError = isPasswordError.value,
                onValueChange = { passwordRepeatState.value = it }
            )
            CustomButton(
                text = stringResource(id = R.string.button_registration),
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
                        Toast.makeText(context, context.getString(R.string.content_necessary_fill_field), Toast.LENGTH_LONG).show()
                    }
                    else if (!firstNameState.value.isOnlyLetters() || !secondNameState.value.isOnlyLetters()) {
                        isFirstNameError.value = !firstNameState.value.isOnlyLetters()
                        isSecondNameError.value = !secondNameState.value.isOnlyLetters()
                        Toast.makeText(context,
                            context.getString(R.string.text_incorrect_first_last_name), Toast.LENGTH_LONG).show()
                    }
                    else if (!isValidEmail(emailState.value)) {
                        isEmailError.value = true
                        Toast.makeText(context,
                            context.getString(R.string.text_incorrect_email), Toast.LENGTH_LONG).show()
                    }
                    else if (!isValidPhoneNumber(phoneState.value)) {
                        isPhoneError.value = true
                        Toast.makeText(context,
                            context.getString(R.string.text_incorrect_phone), Toast.LENGTH_LONG).show()
                    }
                    else if (!isValidCity(cityState.value)) {
                        isCityError.value = true
                        Toast.makeText(context, context.getString(R.string.text_incorrect_city), Toast.LENGTH_LONG).show()
                    }
                    else if (passwordState.value != passwordRepeatState.value) {
                        isPasswordError.value = true
                        Toast.makeText(context,
                            context.getString(R.string.text_passwords_dont_match), Toast.LENGTH_LONG).show()
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

//@Preview(showBackground = true)
//@Composable
//private fun RegisterScreenPreview() {
//    RegisterScreen({ }, { _, _, _ -> })
//}