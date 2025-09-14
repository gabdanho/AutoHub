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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.autohub.R
import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.presentation.componets.CustomButton
import com.example.autohub.presentation.componets.InputField
import com.example.autohub.presentation.componets.LoadingCircularIndicator
import com.example.autohub.presentation.componets.TopAdAppBar
import com.example.autohub.presentation.mapper.resources.StringToResourceIdMapperImpl
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.theme.AppTheme
import com.example.autohub.presentation.utils.convertUriToBytes

@Composable
fun AccountSettings(
    modifier: Modifier = Modifier,
    viewModel: AccountSettingsViewModel = hiltViewModel<AccountSettingsViewModel>(),
) {
    val uiState = viewModel.uiState.collectAsState().value
    val context = LocalContext.current
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                val imageBytes = context.convertUriToBytes(uri = uri)
                imageBytes?.let {
                    val imageRef = ImageUploadData(bytes = imageBytes)
                    viewModel.uploadUserProfileImageToFirebase(
                        imageRef = imageRef,
                        uriString = uri.toString()
                    )
                }
            } else {
                Toast.makeText(
                    context, context.getString(R.string.text_image_not_selected), Toast.LENGTH_SHORT
                ).show()
            }
        }
    val scrollState = rememberScrollState()

    BackHandler {
        viewModel.onBackButtonClick()
    }

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

    if (uiState.isShowChangePasswordDialog) {
        ChangePasswordDialog(
            passwordValue = uiState.passwordValue,
            isInputError = uiState.isPasswordError,
            hideDialog = { viewModel.updateIsShowChangePasswordDialog(value = false) },
            onPasswordValueChange = { viewModel.updatePasswordValue(value = it) },
            changePassword = { viewModel.setNewPassword() }
        )
    }

    Scaffold(
        topBar = {
            TopAdAppBar(
                titleText = stringResource(id = R.string.text_account_settings),
                onBackButtonClick = {
                    viewModel.onBackButtonClick()
                }
            )
        }
    ) { innerPadding ->
        if (uiState.loadingState == LoadingState.Loading) {
            LoadingCircularIndicator(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )
        } else {
            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .padding(AppTheme.dimens.extraSmall)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                // Имя фамилия
                Text(
                    text = stringResource(id = R.string.text_first_and_last_name),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(end = AppTheme.dimens.extraSmall)
                        .fillMaxWidth()
                )
                HorizontalDivider()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.dimens.extraSmall)
                ) {
                    InputField(
                        text = stringResource(id = R.string.input_first_name),
                        value = uiState.firstNameValue,
                        onValueChange = { viewModel.updateFirstNameValue(value = it) },
                        isError = uiState.isFirstNameValueError,
                        placeHolder = uiState.user.firstName,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppTheme.dimens.medium)
                    )
                    InputField(
                        text = stringResource(id = R.string.input_last_name),
                        value = uiState.lastNameValue,
                        onValueChange = { viewModel.updateLastNameValue(value = it) },
                        isError = uiState.isLastNameValueError,
                        placeHolder = uiState.user.lastName,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppTheme.dimens.medium)
                    )
                    CustomButton(
                        text = stringResource(id = R.string.button_accept_changes),
                        isEnabled = uiState.isNamesButtonEnabled,
                        onClick = {
                            viewModel.acceptNamesChanges()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = AppTheme.dimens.extraSmall)
                    )
                }
                // Город
                Text(
                    text = stringResource(id = R.string.text_city),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(end = AppTheme.dimens.extraSmall)
                        .fillMaxWidth()
                )
                HorizontalDivider()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.dimens.extraSmall)
                ) {
                    InputField(
                        text = stringResource(id = R.string.input_city),
                        value = uiState.cityValue,
                        onValueChange = { viewModel.updateCityValue(value = it) },
                        placeHolder = uiState.user.city,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppTheme.dimens.medium)
                    )
                    CustomButton(
                        text = stringResource(id = R.string.button_accept_changes),
                        isEnabled = !uiState.isCityValueError,
                        onClick = {
                            viewModel.acceptCityChange()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = AppTheme.dimens.extraSmall)
                    )
                }
                // Фото профиля
                Text(
                    text = stringResource(id = R.string.content_user_image),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(end = AppTheme.dimens.extraSmall)
                        .fillMaxWidth()
                )
                HorizontalDivider()
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.dimens.extraSmall)
                ) {
                    AsyncImage(
                        model = uiState.user.image,
                        contentDescription = stringResource(id = R.string.content_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(AppTheme.dimens.profileImageSize)
                            .clip(CircleShape)
                            .border(AppTheme.dimens.smallBorderSize, AppTheme.colors.containerColor, CircleShape)
                    )
                    CustomButton(
                        text = stringResource(id = R.string.button_change_user_image),
                        onClick = {
                            galleryLauncher.launch("image/*")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = AppTheme.dimens.extraSmall)
                            .weight(AppTheme.dimens.fullWeight)
                    )
                }
                // Смена пароля
                Text(
                    text = stringResource(id = R.string.text_password),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(end = AppTheme.dimens.extraSmall)
                        .fillMaxWidth()
                )
                HorizontalDivider()
                CustomButton(
                    text = stringResource(id = R.string.button_change_password),
                    onClick = {
                        viewModel.updateIsShowChangePasswordDialog(value = true)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = AppTheme.dimens.extraSmall)
                )
            }
        }
    }
}

@Composable
fun ChangePasswordDialog(
    passwordValue: String,
    isInputError: Boolean,
    onPasswordValueChange: (String) -> Unit,
    hideDialog: () -> Unit,
    changePassword: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(hideDialog) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = modifier
        ) {
            Column(modifier = Modifier.padding(AppTheme.dimens.extraSmall)) {
                Text(
                    text = stringResource(id = R.string.text_incorrect_password),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = passwordValue,
                    onValueChange = { onPasswordValueChange(it) },
                    shape = AppTheme.shapes.textFieldShape,
                    placeholder = {
                        Text(text = stringResource(id = R.string.text_new_password))
                    },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = AppTheme.colors.labelColor,
                        focusedIndicatorColor = AppTheme.colors.labelColor
                    ),
                    trailingIcon = {
                        if (isInputError) Icon(
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
                            containerColor = AppTheme.colors.white
                        ),
                        textColor = Color.Black,
                        border = BorderStroke(AppTheme.dimens.mediumBorderSize, AppTheme.colors.containerColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = AppTheme.dimens.extraSmall)
                            .weight(AppTheme.dimens.fullWeight)
                    )
                    CustomButton(
                        text = stringResource(id = R.string.button_confirm),
                        onClick = {
                            changePassword()
                            hideDialog()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(AppTheme.dimens.fullWeight)
                    )
                }
            }
        }
    }
}