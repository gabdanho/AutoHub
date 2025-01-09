package com.example.autohub.ui.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.autohub.R
import com.example.autohub.ui.componets.CustomButton
import com.example.autohub.ui.componets.InputField
import com.example.autohub.ui.componets.RoundedCornerTextField
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    isProgressBarWork: Boolean,
    isShowSendEmailText: Boolean,
    onLoginClick: (String, String) -> Unit,
    onRegisterClick: () -> Unit
) {
    val context = LocalContext.current
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val showDialog = remember { mutableStateOf(false) }
    val fsAuth = Firebase.auth

    // Диалог со сменой пароля
    if (showDialog.value) {
        ChangePasswordDialog(
            context = context,
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
            text = "Забыли пароль?",
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
            contentDescription = "AutoHub логотип"
        )
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "AutoHub",
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
        // Повторно отправить письмо
        if (isShowSendEmailText) {
            Text(
                text = "Отправить письмо повторно",
                color = Color.Blue,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        fsAuth.signInWithEmailAndPassword(emailState.value, passwordState.value).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val currentUser = fsAuth.currentUser
                                if (currentUser != null) {
                                    currentUser.sendEmailVerification()
                                    fsAuth.signOut()
                                    Toast.makeText(context, "Письмо отправлено на почту. Если его нет, проверьте папку \"Спам\"", Toast.LENGTH_LONG).show()
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
    modifier: Modifier = Modifier,
    context: Context,
    onHideDialogClick: () -> Unit
) {
    val emailToResetPassword = remember { mutableStateOf("") }
    val fsAuth = Firebase.auth

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
                        text = "Почта",
                        onValueChange = { emailToResetPassword.value = it },
                        value = emailToResetPassword.value
                    )
                }
                CustomButton(
                    text = "Изменить пароль",
                    onClick = {
                        if (emailToResetPassword.value.isNotEmpty()) {
                            fsAuth.sendPasswordResetEmail(emailToResetPassword.value).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    onHideDialogClick()
                                    Toast.makeText(context, "Чтобы сменить пароль, перейдите по ссылке, отправленной на Вашу почту.", Toast.LENGTH_LONG).show()
                                } else {
                                    Toast.makeText(context, "Ошибка: ${task.exception?.message ?: "unknown"}", Toast.LENGTH_LONG).show()
                                }
                            }
                        } else {
                            Toast.makeText(context, "Введите почту аккаунта", Toast.LENGTH_LONG).show()
                        }
                    },
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth(0.7f)
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