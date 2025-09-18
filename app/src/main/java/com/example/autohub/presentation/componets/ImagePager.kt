package com.example.autohub.presentation.componets

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.autohub.R
import com.example.autohub.presentation.theme.AppTheme

/**
 * Пейджер для отображения изображений по URL.
 *
 * @param images Список URL изображений.
 * @param currentImageId Индекс текущего изображения.
 * @param onClose Лямбда вызывается при закрытии пейджера.
 * @param modifier Модификатор для настройки компонента.
 */
@Composable
fun ImagePagerString(
    images: List<String>,
    currentImageId: Int,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(initialPage = currentImageId) { images.size }

    BackHandler {
        onClose()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = modifier
        ) { page ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .background(color = AppTheme.colors.imagePagerBackground)
                    .fillMaxSize()
                    .clickable { onClose() }
            ) {
                AsyncImage(
                    model = images[page],
                    contentDescription = stringResource(id = R.string.content_image),
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {  }
                )
            }
        }

        IconButton(
            onClick = { onClose() },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                tint = AppTheme.colors.white,
                contentDescription = stringResource(id = R.string.content_delete_image)
            )
        }
    }
}

/**
 * Пейджер для отображения изображений из Uri.
 *
 * @param images Список Uri изображений.
 * @param currentImageId Индекс текущего изображения.
 * @param onClose Лямбда вызывается при закрытии пейджера.
 * @param modifier Модификатор для настройки компонента.
 */
@Composable
fun ImagePagerUri(
    images: List<Uri>,
    currentImageId: Int,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(initialPage = currentImageId) { images.size }

    BackHandler {
        onClose()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = modifier
        ) { page ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .background(color = AppTheme.colors.imagePagerBackground)
                    .fillMaxSize()
                    .clickable { onClose() }
            ) {
                AsyncImage(
                    model = images[page],
                    contentDescription = stringResource(id = R.string.content_image),
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {  }
                )
            }
        }

        IconButton(
            onClick = { onClose() },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                tint = AppTheme.colors.white,
                contentDescription = stringResource(id = R.string.content_delete_image)
            )
        }
    }
}