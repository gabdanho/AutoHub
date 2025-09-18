package com.example.autohub.presentation.mapper

import com.example.autohub.domain.model.result.HandleErrorTag
import com.example.autohub.presentation.model.StringResNamePresentation

/**
 * Преобразование [HandleErrorTag] в [StringResNamePresentation] для UI.
 *
 * @receiver Domain-код ошибки
 * @return Presentation-константа строки ошибки
 */
fun HandleErrorTag.toStringResNamePresentation(): StringResNamePresentation {
    return when(this) {
        HandleErrorTag.USER_NULL -> StringResNamePresentation.ERROR_GET_USER
        HandleErrorTag.NO_INTERNET -> StringResNamePresentation.ERROR_CANT_SEND_MESSAGE_NO_INTERNET
    }
}