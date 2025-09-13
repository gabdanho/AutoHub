package com.example.autohub.presentation.mapper

import com.example.autohub.domain.model.HandleErrorTag
import com.example.autohub.presentation.model.StringResNamePresentation

fun HandleErrorTag.toStringResNamePresentation(): StringResNamePresentation {
    return when(this) {
        HandleErrorTag.USER_NULL -> StringResNamePresentation.ERROR_GET_USER
        HandleErrorTag.EMAIL_NOT_VERIFIED -> StringResNamePresentation.ERROR_NECESSARY_VERIF_EMAIL
    }
}