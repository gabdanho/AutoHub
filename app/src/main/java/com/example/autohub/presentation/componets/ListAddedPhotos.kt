package com.example.autohub.presentation.componets

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.autohub.R
import com.example.autohub.presentation.theme.AppTheme

private const val IMAGE_DIVIDER = 3

@Composable
fun ListAddedPhotos(
    images: List<Uri>,
    onAddImageClick: () -> Unit,
    onImageClick: (Int) -> Unit,
    onRemoveImageClick: (Uri) -> Unit,
    modifier: Modifier = Modifier,
) {
    val windowInfo = LocalWindowInfo.current
    val width = with(LocalDensity.current) { windowInfo.containerSize.width.dp }
    val imageWidth = (width / IMAGE_DIVIDER).coerceAtMost(AppTheme.dimens.maxAdImageWidth)

    LazyRow(
        modifier = modifier
    ) {
        itemsIndexed(images) { id, imageUri ->
            AdImage(
                imageUri = imageUri,
                onImageClick = { onImageClick(id) },
                onRemoveImageClick = { onRemoveImageClick(imageUri) },
                modifier = Modifier
                    .width(imageWidth)
                    .aspectRatio(AppTheme.dimens.imageAspectRatio)
                    .padding(horizontal = AppTheme.dimens.ultraSmall)
            )
        }
        item {
            Image(
                painter = painterResource(id = R.drawable.add_img),
                contentDescription = stringResource(id = R.string.text_add_image),
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .width(imageWidth)
                    .aspectRatio(AppTheme.dimens.imageAspectRatio)
                    .clickable { onAddImageClick() }
                    .padding(horizontal = AppTheme.dimens.ultraSmall)
            )
        }
    }
}

@Composable
fun AdImage(
    imageUri: Uri,
    onImageClick: () -> Unit,
    onRemoveImageClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        AsyncImage(
            model = imageUri,
            contentDescription = stringResource(id = R.string.content_image),
            contentScale = ContentScale.FillHeight,
            modifier = modifier.clickable { onImageClick() }
        )

        IconButton(
            onClick = { onRemoveImageClick() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(AppTheme.dimens.extraSmall)
                .background(color = AppTheme.colors.containerColor, shape = CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                tint = AppTheme.colors.white,
                contentDescription = stringResource(id = R.string.content_delete_image)
            )
        }
    }
}