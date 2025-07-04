package com.example.autohub.presentation.mapper

import androidx.annotation.StringRes
import com.example.autohub.presentation.model.StringResNamePresentation

interface StringToResourceIdMapper {
    @StringRes
    fun map(resId: StringResNamePresentation): Int
}