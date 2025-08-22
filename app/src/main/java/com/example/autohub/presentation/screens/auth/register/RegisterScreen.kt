package com.example.autohub.presentation.screens.auth.register

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = { BackButton(onBackClick = TODO("onBackClick()")) },
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
                value = TODO("firstNameState.value"),
                placeHolder = stringResource(id = R.string.placeholder_first_name),
                isError = TODO("isFirstNameError.value"),
                onValueChange = { TODO("firstNameState.value = it") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            InputField(
                text = stringResource(id = R.string.input_last_name),
                value = TODO("secondNameState.value"),
                placeHolder = stringResource(id = R.string.placeholder_last_name),
                isError = TODO("isSecondNameError.value"),
                onValueChange = { TODO("secondNameState.value = it") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            InputField(
                text = stringResource(id = R.string.input_email),
                value = TODO("emailState.value"),
                placeHolder = stringResource(id = R.string.placeholder_email),
                isError = TODO("isEmailError.value"),
                onValueChange = { TODO("emailState.value = it") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            InputField(
                text = stringResource(id = R.string.input_phone),
                value = TODO("phoneState.value"),
                placeHolder = stringResource(id = R.string.placeholder_phone),
                isError = TODO("isPhoneError.value"),
                keyboardType = KeyboardType.Number,
                onValueChange = { TODO("phoneState.value = it") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            InputField(
                text = stringResource(id = R.string.input_city),
                value = TODO("cityState.value"),
                placeHolder = stringResource(id = R.string.placeholder_city),
                isError = TODO("isCityError.value"),
                onValueChange = { TODO("cityState.value = it") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            InputField(
                text = stringResource(id = R.string.input_password),
                value = TODO("passwordState.value"),
                placeHolder = stringResource(id = R.string.placeholder_password),
                isError = TODO("isPasswordError.value"),
                onValueChange = { TODO("passwordState.value = it") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            InputField(
                text = stringResource(id = R.string.input_repeat_password),
                value = TODO("passwordRepeatState.value"),
                placeHolder = stringResource(id = R.string.placeholder_password),
                isError = TODO("isPasswordError.value"),
                onValueChange = { TODO("passwordRepeatState.value = it") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            CustomButton(
                text = stringResource(id = R.string.button_registration),
                onClick = {
                    /*
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
                     */
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(top = 32.dp)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun RegisterScreenPreview() {
//    RegisterScreen({ }, { _, _, _ -> })
//}