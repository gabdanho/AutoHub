package com.example.autohub.presentation.screens.login

import android.content.Context
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.autohub.R
import com.example.autohub.presentation.componets.CustomButton
import com.example.autohub.presentation.componets.InputField
import com.example.autohub.presentation.componets.RoundedCornerTextField
import com.example.autohub.presentation.theme.borderColor
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun LoginScreen(
    isProgressBarWork: Boolean,
    isShowSendEmailText: Boolean,
    onLoginClick: (String, String) -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val showDialog = remember { mutableStateOf(false) }
    val fsAuth = Firebase.auth

    // Диалог со сменой пароля
    if (showDialog.value) {
        ChangePasswordDialog(
            onHideDialogClick = { showDialog.value = false }
        )
    }

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
                    showDialog.value = true
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
        modifier = modifier.fillMaxSize(),
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
            text = emailState.value,
            onValueChange = { emailState.value = it },
            label = stringResource(id = R.string.input_login),
            modifier = Modifier.border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(20.dp)
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        RoundedCornerTextField(
            text = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = stringResource(id = R.string.input_password),
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
                if (emailState.value.isEmpty()) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.text_input_email), Toast.LENGTH_LONG
                    ).show()
                } else if (passwordState.value.isEmpty()) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.text_input_password), Toast.LENGTH_LONG
                    ).show()
                } else if (passwordState.value.isEmpty() && emailState.value.isEmpty()) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.text_fill_all_field_for_enter), Toast.LENGTH_LONG
                    ).show()
                } else {
                    onLoginClick(emailState.value, passwordState.value)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
        )
        CustomButton(
            text = stringResource(id = R.string.button_registration),
            onClick = { onRegisterClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )
        // Повторно отправить письмо
        if (isShowSendEmailText) {
            Text(
                text = stringResource(id = R.string.text_send_email_message_again),
                color = Color.Blue,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        fsAuth.signInWithEmailAndPassword(emailState.value, passwordState.value)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val currentUser = fsAuth.currentUser
                                    if (currentUser != null) {
                                        currentUser.sendEmailVerification()
                                        fsAuth.signOut()
                                        Toast.makeText(
                                            context,
                                            context.getString(R.string.text_message_is_sended),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }
                    }
            )
        }
        // Прогресс бар
        if (isProgressBarWork) {
            CircularProgressIndicator(
                color = Color.Black,
            )
        }
    }
}

@Composable
fun ChangePasswordDialog(
    onHideDialogClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val emailToResetPassword = remember { mutableStateOf("") }
    val fsAuth = Firebase.auth
    val context = LocalContext.current

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
                        onValueChange = { emailToResetPassword.value = it },
                        value = emailToResetPassword.value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
                CustomButton(
                    text = stringResource(id = R.string.button_change_password),
                    onClick = {
                        if (emailToResetPassword.value.isNotEmpty()) {
                            fsAuth.sendPasswordResetEmail(emailToResetPassword.value)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        onHideDialogClick()
                                        Toast.makeText(
                                            context,
                                            context.getString(R.string.text_help_message_to_change_password),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            context.getString(
                                                R.string.text_error,
                                                task.exception?.message ?: "unknown"
                                            ),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                        } else {
                            Toast.makeText(
                                context,
                                context.getString(R.string.text_input_email),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .padding(bottom = 8.dp)
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun LoginScreenPreview() {
//    LoginScreen(false, true, { _, _ -> }, { })
//}