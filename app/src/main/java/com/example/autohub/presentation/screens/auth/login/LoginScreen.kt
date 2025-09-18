package com.example.autohub.presentation.screens.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.autohub.R
import com.example.autohub.presentation.componets.CustomButton
import com.example.autohub.presentation.componets.InputField
import com.example.autohub.presentation.componets.LoadingCircularIndicator
import com.example.autohub.presentation.componets.RoundedCornerTextField
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.theme.AppTheme
import com.example.autohub.presentation.utils.showUiMessage

/**
 * Экран логина [LoginScreen].
 *
 * @param modifier Модификатор для кастомизации внешнего вида Composable.
 * @param isShowSendEmailText Если true, отображает текст для повторной отправки письма подтверждения.
 * @param viewModel ViewModel экрана [LoginScreenViewModel].
 */
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    isShowSendEmailText: Boolean = false,
    viewModel: LoginScreenViewModel = hiltViewModel<LoginScreenViewModel>(),
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState().value
    val scrollState = rememberScrollState()

    viewModel.updateIsShowSendEmailText(value = isShowSendEmailText)

    LaunchedEffect(uiState.uiMessage) {
        context.showUiMessage(uiMessage = uiState.uiMessage) {
            viewModel.clearMessage()
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .padding(AppTheme.dimens.medium)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Логотип
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = stringResource(id = R.string.context_autohub_logo),
                modifier = Modifier.fillMaxWidth()
            )
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(AppTheme.dimens.medium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.text_autohub),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.W300,
                    modifier = Modifier.padding(bottom = AppTheme.dimens.extraSmall)
                )
                RoundedCornerTextField(
                    text = uiState.emailValue,
                    onValueChange = { viewModel.changeEmailValue(value = it) },
                    label = stringResource(id = R.string.input_login),
                    modifier = Modifier
                        .fillMaxWidth(AppTheme.dimens.textFieldWidth)
                        .border(
                            width = AppTheme.dimens.ultraSmallBorderSize,
                            color = AppTheme.colors.borderColor,
                            shape = AppTheme.shapes.textFieldShape
                        )
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.extraSmall))
                RoundedCornerTextField(
                    text = uiState.passwordValue,
                    onValueChange = { viewModel.changePasswordValue(value = it) },
                    label = stringResource(id = R.string.input_password),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth(AppTheme.dimens.textFieldWidth)
                        .border(
                            width = AppTheme.dimens.ultraSmallBorderSize,
                            color = AppTheme.colors.borderColor,
                            shape = AppTheme.shapes.textFieldShape
                        )
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.extraSmall))
                CustomButton(
                    text = stringResource(id = R.string.button_enter),
                    onClick = {
                        viewModel.login()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = AppTheme.dimens.large)
                )
                CustomButton(
                    text = stringResource(id = R.string.button_registration),
                    onClick = { viewModel.onRegisterButtonClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = AppTheme.dimens.large)
                )
            }
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(AppTheme.dimens.medium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Повторно отправить письмо
                if (uiState.isShowSendEmailText) {
                    Text(
                        text = stringResource(id = R.string.text_send_email_message_again),
                        color = AppTheme.colors.linkTextColor,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .padding(AppTheme.dimens.medium)
                            .clickable {
                                viewModel.resendEmailVerification()
                            }
                    )
                }
                // Забыли пароль
                Text(
                    text = stringResource(id = R.string.text_forgot_password),
                    color = AppTheme.colors.linkTextColor,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .padding(AppTheme.dimens.medium)
                        .clickable {
                            viewModel.changeIsShowPasswordDialog(value = true)
                        }
                )
            }
        }
    }
}

/**
 * Диалог смены пароля.
 *
 * @param email Email для восстановления пароля.
 * @param changeEmailToNewPassword Лямбда для обновления email при вводе.
 * @param onHideDialogClick Лямбда для скрытия диалога.
 * @param forgotPassword Лямбда для вызова процесса восстановления пароля.
 * @param modifier Модификатор для кастомизации внешнего вида Composable.
 */
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
            colors = CardDefaults.cardColors(containerColor = AppTheme.colors.white),
            modifier = modifier
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(AppTheme.dimens.extraSmall)
                        .fillMaxWidth()
                ) {
                    InputField(
                        text = stringResource(id = R.string.input_email),
                        onValueChange = { changeEmailToNewPassword(it) },
                        value = email,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppTheme.dimens.medium)
                    )
                }
                CustomButton(
                    text = stringResource(id = R.string.button_change_password),
                    onClick = { forgotPassword() },
                    modifier = Modifier
                        .fillMaxWidth(AppTheme.dimens.buttonWidth)
                        .padding(bottom = AppTheme.dimens.extraSmall)
                )
            }
        }
    }
}