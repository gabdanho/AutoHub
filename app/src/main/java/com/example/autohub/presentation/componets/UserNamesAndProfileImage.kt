package com.example.autohub.presentation.componets

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.example.autohub.R
import com.example.autohub.presentation.theme.AppTheme

/**
 * Отображение имени пользователя и аватара с городом.
 *
 * @param imageUrl URL изображения пользователя.
 * @param firstName Имя пользователя.
 * @param lastName Фамилия пользователя.
 * @param city Город пользователя.
 * @param modifier Модификатор для настройки компонента.
 * @param onUserClick Лямбда вызывается при нажатии на компонент.
 */
@Composable
fun UserNamesAndProfileImage(
    imageUrl: String,
    firstName: String,
    lastName: String,
    city: String,
    modifier: Modifier = Modifier,
    onUserClick: () -> Unit = { },
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onUserClick() }
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = stringResource(id = R.string.content_user_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(AppTheme.dimens.profileImageSize)
                .clip(CircleShape)
                .border(
                    AppTheme.dimens.smallBorderSize,
                    AppTheme.colors.containerColor,
                    CircleShape
                )
        )
        Column(
            modifier = Modifier.padding(start = AppTheme.dimens.medium)
        ) {
            Text(
                text = stringResource(
                    id = R.string.text_user_first_last_name,
                    firstName,
                    lastName
                ),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = AppTheme.dimens.extraSmall)
            )
            Text(
                text = city,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

}