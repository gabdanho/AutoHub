package com.example.autohub.ui.componets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.autohub.R
import com.example.autohub.ui.theme.cardColor

@Composable
fun PhotosList(
    @DrawableRes images: List<Int>,
    onAddImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .background(cardColor)
    ) {
        items(images) { imageId ->
            Image(
                painter = painterResource(imageId),
                contentDescription = "Изображение автомобиля",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        if (imageId == R.drawable.add_img) onAddImageClick()
                    }
                    .padding(horizontal = 4.dp)
            )
        }
    }
}