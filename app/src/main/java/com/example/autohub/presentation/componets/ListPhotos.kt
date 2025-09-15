package com.example.autohub.presentation.componets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.autohub.R
import com.example.autohub.presentation.theme.AppTheme

private const val WIDTH_DIVIDER = 3

@Composable
fun ListPhotos(
    imagesUrl: List<String>,
    imageToShow: String?,
    changeImageToShow: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val windowInfo = LocalWindowInfo.current
    val width = with(LocalDensity.current) { windowInfo.containerSize.width.dp }
    val imageWidth = (width / WIDTH_DIVIDER).coerceAtMost(AppTheme.dimens.maxAdImageWidth)

    LazyRow(modifier = modifier) {
        items(imagesUrl) { url ->
            AsyncImage(
                model = url,
                contentDescription = stringResource(id = R.string.content_image),
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .width(imageWidth)
                    .aspectRatio(AppTheme.dimens.imageAspectRatio)
                    .clickable { changeImageToShow(url) }
                    .padding(horizontal = AppTheme.dimens.ultraSmall)
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