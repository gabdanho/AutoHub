package com.example.autohub.presentation.model

import android.net.Uri

data class UiImage(
    val uri: Uri,
    val byteArray: ByteArray? = null
)
