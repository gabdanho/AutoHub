package com.example.autohub.presentation.componets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.autohub.R

// TODO : Заменить в экранах modifier:
/*
modifier
    .fillMaxWidth()
    .background(cardColor)
 */

@Composable
fun ListPhotos(
    imagesUrl: List<String>,
    imageToShow: String?,
    changeImageToShow: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier = modifier) {
        items(imagesUrl) { url ->
            AsyncImage(
                model = url,
                contentDescription = stringResource(id = R.string.content_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(300.dp)
                    .clickable { changeImageToShow(url) }
                    .padding(horizontal = 4.dp)
            )
        }
    }

    if (imageToShow != null) {
        ImageDialog(
            url = imageToShow,
            closeDialog = { changeImageToShow(null) }
        )
    }
}