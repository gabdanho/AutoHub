package com.example.autohub.presentation.model

import android.net.Uri

/**
 * Модель изображения для UI.
 *
 * @property uri URI изображения
 * @property byteArray Необязательный массив байтов изображения
 */
class UiImage(
    val uri: Uri,
    val byteArray: ByteArray? = null
)
