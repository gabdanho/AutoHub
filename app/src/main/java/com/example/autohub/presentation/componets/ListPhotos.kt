package com.example.autohub.presentation.componets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.autohub.R
import com.example.autohub.presentation.theme.AppTheme
import com.example.autohub.presentation.utils.getImageInPagerWidth

@Composable
fun ListPhotos(
    imagesUrl: List<String>,
    onImageClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val imageWidth = getImageInPagerWidth()

    LazyRow(modifier = modifier) {
        itemsIndexed(imagesUrl) { id, url ->
            AsyncImage(
                model = url,
                contentDescription = stringResource(id = R.string.content_image),
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .width(imageWidth)
                    .aspectRatio(AppTheme.dimens.imageAspectRatio)
                    .clickable { onImageClick(id) }
                    .padding(horizontal = AppTheme.dimens.ultraSmall)
            )
        }
    }
}