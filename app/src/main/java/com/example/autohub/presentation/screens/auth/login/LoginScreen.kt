package com.example.autohub.presentation.screens.auth.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.autohub.R
import com.example.autohub.presentation.componets.CustomButton
import com.example.autohub.presentation.componets.InputField
import com.example.autohub.presentation.componets.LoadingCircularIndicator
import com.example.autohub.presentation.componets.RoundedCornerTextField
import com.example.autohub.presentation.mapper.resources.StringToResourceIdMapperImpl
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.theme.borderColor

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    isShowSendEmailText: Boolean = false,
    viewModel: LoginScreenViewModel = hiltViewModel<LoginScreenViewModel>(),
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState().value

    viewModel.updateIsShowSendEmailText(value = isShowSendEmailText)

    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            val resId = StringToResourceIdMapperImpl().map(uiState.message)
            Toast.makeText(context, context.getString(resId), Toast.LENGTH_LONG).show()
            viewModel.clearMessage()
        }
    }

    LaunchedEffect(uiState.messageDetails) {
        if (!uiState.messageDetails.isNullOrBlank()) {
            Toast.makeText(context, uiState.messageDetails, Toast.LENGTH_LONG).show()
            viewModel.clearMessageDetails()
        }
    }

    // Диалог со сменой пароля
    if (uiState.isShowPasswordDialog) {
        ChangePasswordDialog(
            onHideDialogClick = { viewModel.changeIsShowPasswordDialog(value = false) },
            email = uiState.emailForNewPassword,
            changeEmailToNewPassword = { viewModel.updateEmailForNewPasswordValue(value = it) },
            forgotPassword = { viewModel.forgotPassword() }
        )
    }

    if (uiState.loadingState == LoadingState.Loading) {
        LoadingCircularIndicator(
            modifier = Modifier.fillMaxSize()
        )
    } else {
        // Забыли пароль
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.text_forgot_password),
                color = Color.Blue,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        viewModel.changeIsShowPasswordDialog(value = true)
                    }
            )
        }

        // Логотип
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = stringResource(id = R.string.context_autohub_logo)
            )
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.text_autohub),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.W300,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            RoundedCornerTextField(
                text = uiState.emailValue,
                onValueChange = { viewModel.changeEmailValue(value = it) },
                label = stringResource(id = R.string.input_login),
                modifier = Modifier.border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(20.dp)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            RoundedCornerTextField(
                text = uiState.passwordValue,
                onValueChange = { viewModel.changePasswordValue(value = it) },
                label = stringResource(id = R.string.input_password),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(20.dp)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomButton(
                text = stringResource(id = R.string.button_enter),
                onClick = {
                    viewModel.login()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
            )
            CustomButton(
                text = stringResource(id = R.string.button_registration),
                onClick = { viewModel.onRegisterButtonClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )
            // Повторно отправить письмо
            if (uiState.isShowSendEmailText) {
                Text(
                    text = stringResource(id = R.string.text_send_email_message_again),
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            viewModel.resendEmailVerification()
                        }
                )
            }
        }
    }
}

@Composable
private fun ChangePasswordDialog(
    email: String,
    changeEmailToNewPassword: (String) -> Unit,
    onHideDialogClick: () -> Unit,
    forgotPassword: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(onHideDialogClick) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = modifier
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    InputField(
                        text = stringResource(id = R.string.input_email),
                        onValueChange = { changeEmailToNewPassword(it) },
                        value = email,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
                CustomButton(
                    text = stringResource(id = R.string.button_change_password),
                    onClick = { forgotPassword() },
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .padding(bottom = 8.dp)
                )
            }
        }
    }
}