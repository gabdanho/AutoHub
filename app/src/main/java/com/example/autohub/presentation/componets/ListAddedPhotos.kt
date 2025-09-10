package com.example.autohub.presentation.componets

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.autohub.R

@Composable
fun ListAddedPhotos(
    images: List<Uri>,
    imageToShow: Uri?,
    onAddImageClick: () -> Unit,
    changeImageToShow: (Uri?) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier
    ) {
        items(images) { imageUri ->
            AsyncImage(
                model = imageUri,
                contentDescription = stringResource(id = R.string.content_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(300.dp)
                    .clickable { changeImageToShow(imageUri) }
                    .padding(horizontal = 4.dp)
            )
        }
        item {
            Image(
                painter = painterResource(id = R.drawable.add_img),
                contentDescription = stringResource(id = R.string.text_add_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onAddImageClick() }
                    .padding(horizontal = 4.dp)
            )
        }
    }

    if (imageToShow != null) {
        ImageDialog(
            uri = imageToShow,
            closeDialog = { changeImageToShow(null) }
        )
    }
}