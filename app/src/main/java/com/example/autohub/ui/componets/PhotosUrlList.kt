package com.example.autohub.ui.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.autohub.ui.theme.cardColor

@Composable
fun PhotosUrlList(
    imagesUrl: List<String>,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                    .fillMaxSize()
                    .clickable {
                        onImageClick()
                    }
                    .padding(horizontal = 4.dp)
            )
        }
    }
}