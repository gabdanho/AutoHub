package com.example.autohub.ui.componets

import android.net.Uri
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.autohub.ui.theme.cardColor

@Composable
fun PhotosUrlList(
    imagesUrl: List<String>,
    modifier: Modifier = Modifier
) {
    val isShowImageDialog = remember { mutableStateOf(false) }
    val imageUriToShowImage = remember { mutableStateOf("") }

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .background(cardColor)
    ) {
        items(imagesUrl) { url ->
            AsyncImage(
                model = url,
                contentDescription = "Изображение автомобиля",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(300.dp)
                    .clickable {
                        isShowImageDialog.value = true
                        imageUriToShowImage.value = url
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