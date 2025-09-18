package com.example.autohub.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.autohub.presentation.constants.WIDTH_DIVIDER
import com.example.autohub.presentation.theme.AppTheme
import java.io.ByteArrayOutputStream

/**
 * Конвертирует [Uri] изображения в массив байт.
 *
 * @receiver Контекст для получения контента
 * @param uri Uri изображения
 * @param quality Качество сжатия JPEG (по умолчанию 50)
 * @return Массив байт изображения или null при ошибке
 */
fun Context.convertUriToBytes(uri: Uri, quality: Int = 50): ByteArray? {
    return try {
        contentResolver.openInputStream(uri)?.use { inputStream ->
            val bitmap = BitmapFactory.decodeStream(inputStream)
            if (bitmap != null) {
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos)
                baos.toByteArray()
            } else {
                null
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * Вычисляет оптимальную ширину изображения для отображения в ImagePager.
 *
 * @return Оптимальная ширина изображения в Dp
 */
@Composable
fun getImageInPagerWidth(): Dp {
    val windowInfo = LocalWindowInfo.current
    val width = with(LocalDensity.current) { windowInfo.containerSize.width.dp }
    val imageWidth = (width / WIDTH_DIVIDER).coerceAtMost(AppTheme.dimens.maxAdImageWidth)

    return imageWidth
}