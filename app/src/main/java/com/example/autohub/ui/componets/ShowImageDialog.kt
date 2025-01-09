package com.example.autohub.ui.componets

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage

@Composable
fun ShowImageDialog(
    modifier: Modifier = Modifier,
    uri: Uri,
    closeDialog: () -> Unit
) {
    Dialog(closeDialog) {
        AsyncImage(
            model = uri,
            contentDescription = "Изображение",
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ShowImageDialog(
    modifier: Modifier = Modifier,
    uri: String,
    closeDialog: () -> Unit
) {
    Dialog(closeDialog) {
        AsyncImage(
            model = uri,
            contentDescription = "Изображение",
            modifier = modifier.fillMaxWidth()
        )
    }
}