package com.example.autohub.presentation.componets

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.autohub.R

@Composable
fun ImageDialog(
    uri: Uri,
    closeDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(closeDialog) {
        AsyncImage(
            model = uri,
            contentDescription = stringResource(id = R.string.content_image),
            modifier = modifier
        )
    }
}

@Composable
fun ImageDialog(
    url: String,
    closeDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(closeDialog) {
        AsyncImage(
            model = url,
            contentDescription = stringResource(id = R.string.content_image),
            modifier = modifier
        )
    }
}