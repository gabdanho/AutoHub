package com.example.autohub.ui.componets

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.autohub.R
import com.example.autohub.ui.theme.cardColor

@Composable
fun PhotosList(
    images: List<Uri>,
    onAddImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isShowImageDialog = remember { mutableStateOf(false) }
    val imageUriToShowImage = remember { mutableStateOf(Uri.EMPTY) }

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .background(cardColor)
    ) {
        items(images) { imageUri ->
            AsyncImage(
                model = imageUri,
                contentDescription = "Изображение автомобиля",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(300.dp)
                    .clickable {
                        if (imageUri == images.last()) {
                            onAddImageClick()
                        } else {
                            isShowImageDialog.value = true
                            imageUriToShowImage.value = imageUri
                        }
                    }
                    .padding(horizontal = 4.dp)
            )
        }
    }

    if (isShowImageDialog.value) {
        ShowImageDialog(
            uri = imageUriToShowImage.value,
            closeDialog = { isShowImageDialog.value = false }
        )
    }
}