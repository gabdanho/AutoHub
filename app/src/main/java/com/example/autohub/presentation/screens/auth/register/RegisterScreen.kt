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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.autohub.R
import com.example.autohub.presentation.componets.BackButton
import com.example.autohub.presentation.componets.CustomButton
import com.example.autohub.presentation.componets.InputField
import com.example.autohub.presentation.model.LoadingState

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterScreenViewModel = hiltViewModel<RegisterScreenViewModel>()
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(uiState.loadingState) {
        if (uiState.loadingState is LoadingState.Error) {
            Toast.makeText(context, uiState.loadingState.message, Toast.LENGTH_SHORT).show()
            viewModel.clearLoadingState()
        }
    }

    Scaffold(
        floatingActionButton = { BackButton(onBackClick = viewModel::onBackClick) },
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
                value = uiState.firstNameValue,
                placeHolder = stringResource(id = R.string.placeholder_first_name),
                isError = uiState.isFirstNameError,
                onValueChange = { viewModel.updateFirstNameValue(value = it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            InputField(
                text = stringResource(id = R.string.input_last_name),
                value = uiState.lastNameValue,
                placeHolder = stringResource(id = R.string.placeholder_last_name),
                isError = uiState.isLastNameError,
                onValueChange = { viewModel.updateLastNameValue(value = it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            InputField(
                text = stringResource(id = R.string.input_email),
                value = uiState.emailValue,
                placeHolder = stringResource(id = R.string.placeholder_email),
                isError = uiState.isEmailError,
                onValueChange = { viewModel.updateEmailValue(value = it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            InputField(
                text = stringResource(id = R.string.input_phone),
                value = uiState.phoneValue,
                placeHolder = stringResource(id = R.string.placeholder_phone),
                isError = uiState.isPhoneError,
                keyboardType = KeyboardType.Number,
                onValueChange = { viewModel.updatePhoneValue(value = it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            InputField(
                text = stringResource(id = R.string.input_city),
                value = uiState.cityValue,
                placeHolder = stringResource(id = R.string.placeholder_city),
                isError = uiState.isCityError,
                onValueChange = { viewModel.updateCityValue(value = it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            InputField(
                text = stringResource(id = R.string.input_password),
                value = uiState.passwordValue,
                placeHolder = stringResource(id = R.string.placeholder_password),
                isError = uiState.isPasswordError,
                onValueChange = { viewModel.updatePasswordValue(value = it) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            InputField(
                text = stringResource(id = R.string.input_repeat_password),
                value = uiState.repeatPasswordValue,
                placeHolder = stringResource(id = R.string.placeholder_password),
                isError = uiState.isRepeatPasswordError,
                onValueChange = { viewModel.updateRepeatPasswordValue(value = it) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            CustomButton(
                text = stringResource(id = R.string.button_registration),
                onClick = {
                    viewModel.registerAccount()
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(top = 32.dp)
            )
        }
    }
}