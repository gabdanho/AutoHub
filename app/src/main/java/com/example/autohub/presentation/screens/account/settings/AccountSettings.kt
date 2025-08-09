package com.example.autohub.presentation.screens.account.settings

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.autohub.R
import com.example.autohub.presentation.model.user.User
import com.example.autohub.presentation.componets.CustomButton
import com.example.autohub.presentation.componets.InputField
import com.example.autohub.presentation.componets.TopAdAppBar
import com.example.autohub.presentation.theme.containerColor
import com.example.autohub.presentation.theme.labelColor
import com.example.autohub.presentation.utils.isOnlyLetters
import com.example.autohub.presentation.utils.isPasswordValid
import com.example.autohub.presentation.utils.isValidCity
import com.example.autohub.data.utils.updateCity
import com.example.autohub.data.utils.updateFirstAnsSecondName
import com.example.autohub.data.utils.uploadUserProfileImageToFirebase
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

@Composable
fun AccountSettings(
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isFirstNameError = remember { mutableStateOf(false) }
    val isSecondNameError = remember { mutableStateOf(false) }
    val isCityError = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val isShowDialog = remember { mutableStateOf(false) }
    val firstNameState = remember { mutableStateOf("") }
    val secondNameState = remember { mutableStateOf("") }
    val cityState = remember { mutableStateOf("") }
    val isNamesButtonEnabled = remember { mutableStateOf(false) }
    val isCityButtonEnabled = remember { mutableStateOf(false) }
    val userUID = Firebase.auth.currentUser?.uid ?: ""
    val userData = remember { mutableStateOf(User()) }
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                uploadUserProfileImageToFirebase(context, uri)

            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.text_image_not_selected), Toast.LENGTH_SHORT
                ).show()
            }
        }

    // FixMe : все названия коллекций захардить + вынести во вьюмодель
    val listener = Firebase.firestore.collection("users").document(userUID)
        .addSnapshotListener { value, error ->
            userData.value = value?.toObject(User::class.java) ?: User()
        }

    isNamesButtonEnabled.value =
        firstNameState.value.isNotBlank() || secondNameState.value.isNotBlank()
    isCityButtonEnabled.value = cityState.value.isNotBlank()

    BackHandler {
        listener.remove()
        onBackButtonClick()
    }

    if (isShowDialog.value) {
        ChangePasswordDialog(
            hideDialog = { isShowDialog.value = false }
        )
    }

    Scaffold(
        topBar = {
            TopAdAppBar(
                titleText = stringResource(id = R.string.text_account_settings),
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
                text = stringResource(id = R.string.text_first_and_last_name),
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
                    text = stringResource(id = R.string.input_first_name),
                    value = firstNameState.value,
                    onValueChange = { firstNameState.value = it },
                    isError = isFirstNameError.value,
                    placeHolder = userData.value.firstName,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                InputField(
                    text = stringResource(id = R.string.input_last_name),
                    value = secondNameState.value,
                    onValueChange = { secondNameState.value = it },
                    isError = isSecondNameError.value,
                    placeHolder = userData.value.secondName,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                CustomButton(
                    text = stringResource(id = R.string.button_accept_changes),
                    isEnabled = isNamesButtonEnabled.value,
                    onClick = {
                        if (!firstNameState.value.isOnlyLetters() || !secondNameState.value.isOnlyLetters()) {
                            isFirstNameError.value = !firstNameState.value.isOnlyLetters()
                            isSecondNameError.value = !secondNameState.value.isOnlyLetters()
                            Toast.makeText(
                                context,
                                context.getString(R.string.text_incorrect_first_or_and_last_name),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            updateFirstAnsSecondName(
                                context,
                                firstNameState.value,
                                secondNameState.value
                            )
                            firstNameState.value = ""
                            secondNameState.value = ""
                            isFirstNameError.value = false
                            isSecondNameError.value = false
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
            // Город
            Text(
                text = stringResource(id = R.string.text_city),
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
                    text = stringResource(id = R.string.input_city),
                    value = cityState.value,
                    onValueChange = { cityState.value = it },
                    placeHolder = userData.value.city,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                CustomButton(
                    text = stringResource(id = R.string.button_accept_changes),
                    isEnabled = isCityButtonEnabled.value,
                    onClick = {
                        if (!isValidCity(cityState.value)) {
                            isCityError.value = true
                            Toast.makeText(context,
                                context.getString(R.string.text_incorrect_city), Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            updateCity(context, cityState.value)
                            cityState.value = ""
                            isCityError.value = false
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
            // Фото профиля
            Text(
                text = stringResource(id = R.string.content_user_image),
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
                    contentDescription = stringResource(id = R.string.content_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(2.dp, containerColor, CircleShape)
                )
                CustomButton(
                    text = stringResource(id = R.string.button_change_user_image),
                    onClick = {
                        galleryLauncher.launch("image/*")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                        .weight(1f)
                )
            }
            // Смена пароля
            Text(
                text = stringResource(id = R.string.text_password),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .fillMaxWidth()
            )
            HorizontalDivider()
            CustomButton(
                text = stringResource(id = R.string.button_change_password),
                onClick = {
                    isShowDialog.value = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun ChangePasswordDialog(
    hideDialog: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val passwordState = remember { mutableStateOf("") }
    val isInputError = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Dialog(hideDialog) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = modifier
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = stringResource(id = R.string.text_incorrect_password),
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
                        Text(text = stringResource(id = R.string.text_new_password))
                    },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = labelColor,
                        focusedIndicatorColor = labelColor
                    ),
                    trailingIcon = {
                        if (isInputError.value) Icon(
                            imageVector = Icons.Filled.Warning,
                            contentDescription = stringResource(id = R.string.content_necessary_fill_field)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CustomButton(
                        text = stringResource(id = R.string.button_cancel),
                        onClick = { hideDialog() },
                        colorButton = buttonColors(
                            containerColor = Color.White
                        ),
                        textColor = Color.Black,
                        border = BorderStroke(4.dp, containerColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp)
                            .weight(1f)
                    )
                    val user = Firebase.auth.currentUser
                    if (user != null) {
                        CustomButton(
                            text = stringResource(id = R.string.button_confirm),
                            onClick = {
                                if (passwordState.value.isEmpty()) {
                                    isInputError.value = true
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.text_empty_field_write_password),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (!isPasswordValid(passwordState.value, context)) {
                                    isInputError.value = true
                                } else {
                                    isInputError.value = false

                                    user.updatePassword(passwordState.value)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Toast.makeText(
                                                    context,
                                                    context.getString(R.string.text_password_changed),
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    context.getString(
                                                        R.string.text_error,
                                                        task.exception?.message ?: "unknown"
                                                    ),
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }

                                    hideDialog()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                    }
                }
            }
        }
    }
}

//@Preview
//@Composable
//private fun AccountSettingsPreview() {
//    AccountSettings({ })
//}