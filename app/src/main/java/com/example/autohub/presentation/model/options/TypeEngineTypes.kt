package com.example.autohub.presentation.model.options

import com.example.autohub.presentation.model.StringResNamePresentation

enum class TypeEngineTypes(val textRes: StringResNamePresentation) {
    PETROL(textRes = StringResNamePresentation.LABEL_PETROL),
    DIESEL(textRes = StringResNamePresentation.LABEL_DIESEL),
    HYBRID(textRes = StringResNamePresentation.LABEL_HYBRID)
}