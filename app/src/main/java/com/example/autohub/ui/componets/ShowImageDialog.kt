package com.example.autohub.ui.componets

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage

@Composable
fun ShowImageDialog(
    uri: Uri,
    closeDialog: () -> Unit,
    modifier: Modifier = Modifier
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
    uri: String,
    closeDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(closeDialog) {
        AsyncImage(
            model = uri,
            contentDescription = "Изображение",
            modifier = modifier.fillMaxWidth()
        )
    }
}