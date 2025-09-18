package com.example.autohub.presentation.mapper.resources

import androidx.annotation.StringRes
import com.example.autohub.presentation.model.StringResNamePresentation

/**
 * Интерфейс для преобразования идентификаторов строк (StringResNamePresentation)
 * в реальные ресурсы строк Android (@StringRes).
 */
interface StringToResourceIdMapper {
    @StringRes
    fun map(resId: StringResNamePresentation): Int
}