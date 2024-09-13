package com.example.autohub.ui.account

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.autohub.ui.componets.CustomButton
import com.example.autohub.ui.componets.TopAdAppBar
import com.example.autohub.ui.theme.containerColor
import com.example.autohub.ui.theme.labelColor
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun AccountSettings(
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isShowDialog = remember { mutableStateOf(false) }

    if (isShowDialog.value) {
        ChangePasswordDialog(
            context = context,
            hideDialog = { isShowDialog.value = false }
        )
    }

    Scaffold(
        topBar = {
            TopAdAppBar(
                titleText = "Настройки аккаунта",
                onBackButtonClick = onBackButtonClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Пароль",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .fillMaxWidth()
            )
            HorizontalDivider()
            CustomButton(
                text = "Сменить пароль",
                onClick = {
                    isShowDialog.value = true
                },
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun ChangePasswordDialog(
    context: Context,
    hideDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    val user = Firebase.auth.currentUser!!
    val passwordState = remember { mutableStateOf("") }
    val isInputError = remember { mutableStateOf(false) }

    Dialog(hideDialog) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = modifier
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = "Пароль должен содержать не менее 6 символов!",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = passwordState.value,
                    onValueChange = {
                        passwordState.value = it
                    },
                    shape = RoundedCornerShape(20.dp),
                    placeholder = {
                        Text("Введите новый пароль")
                    },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = labelColor,
                        focusedIndicatorColor = labelColor
                    ),
                    trailingIcon = { if (isInputError.value) Icon(imageVector = Icons.Filled.Warning, contentDescription = "Необходимо заполнить поле") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CustomButton(
                        text = "Отмена",
                        onClick = { hideDialog() },
                        colorButton = buttonColors(
                            containerColor = Color.White
                        ),
                        textColor = Color.Black,
                        border = BorderStroke(4.dp, containerColor),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .weight(1f)
                    )
                    CustomButton(
                        text = "Подтвердить",
                        onClick = {
                            if (passwordState.value.isEmpty()) {
                                isInputError.value = true
                                Toast.makeText(context, "Поле пароля пустое, введите пароль", Toast.LENGTH_SHORT).show()
                            }
                            else if (passwordState.value.length < 6) {
                                isInputError.value = true
                                Toast.makeText(context, "Пароль должен содержать не менее 6 символов", Toast.LENGTH_SHORT).show()
                            }
                            else {
                                isInputError.value = false

                                user.updatePassword(passwordState.value).addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(context, "Пароль успешно изменён", Toast.LENGTH_SHORT).show()
                                    }
                                    else {
                                        Toast.makeText(context, "Ошибка: ${task.exception?.message ?: "unknown"}", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                hideDialog()
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun AccountSettingsPreview() {
    AccountSettings({ })
}