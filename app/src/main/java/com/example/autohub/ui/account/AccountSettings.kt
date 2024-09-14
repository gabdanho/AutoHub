package com.example.autohub.ui.account

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.autohub.data.User
import com.example.autohub.ui.componets.CustomButton
import com.example.autohub.ui.componets.InputField
import com.example.autohub.ui.componets.TopAdAppBar
import com.example.autohub.ui.theme.containerColor
import com.example.autohub.ui.theme.labelColor
import com.example.autohub.utils.getUserData
import com.example.autohub.utils.uploadImageToFirebase
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import okhttp3.internal.notify
import okhttp3.internal.wait

@Composable
fun AccountSettings(
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isShowDialog = remember { mutableStateOf(false) }
    val isButtonEnabled = remember { mutableStateOf(false) }
    val firstNameState = remember { mutableStateOf("") }
    val secondNameState = remember { mutableStateOf("") }
    val userUID = Firebase.auth.currentUser?.uid!!
    val userData = remember { mutableStateOf(User()) }
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            uploadImageToFirebase(context, uri)

        } else {
            Toast.makeText(context, "Изображение не было выбрано", Toast.LENGTH_SHORT).show()
        }
    }

    val listener = Firebase.firestore.collection("users").document(userUID).addSnapshotListener { value, error ->
        userData.value = value?.toObject(User::class.java) ?: User()
    }

    BackHandler {
        listener.remove()
        onBackButtonClick()
    }

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
                onBackButtonClick = {
                    listener.remove()
                    onBackButtonClick()
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(8.dp)
                .fillMaxSize()
        ) {
            // Имя фамилия
            Text(
                text = "Имя и фамилия",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .fillMaxWidth()
            )
            HorizontalDivider()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                InputField(
                    text = "Имя",
                    value = firstNameState.value,
                    onValueChange = { firstNameState.value = it; isButtonEnabled.value = true },
                    placeHolder = userData.value.firstName
                )
                InputField(
                    text = "Фамилия",
                    value = secondNameState.value,
                    onValueChange = { secondNameState.value = it; isButtonEnabled.value = true },
                    placeHolder = userData.value.secondName
                )
                CustomButton(
                    text = "Принять изменения",
                    isEnabled = isButtonEnabled.value,
                    onClick = {
                        // TODO()
                    },
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            // Фото профиля
            Text(
                text = "Фото профиля",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .fillMaxWidth()
            )
            HorizontalDivider()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                AsyncImage(
                    model = userData.value.image,
                    contentDescription = "Фото профиля",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(2.dp, containerColor, CircleShape)
                )
                CustomButton(
                    text = "Сменить фото профиля",
                    onClick = {
                        galleryLauncher.launch("image/*")
                    },
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f)
                )
            }
            // Смена пароля
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