package com.example.autohub.presentation.model.options

import com.example.autohub.presentation.model.StringResNamePresentation

enum class EngineType(override val textRes: StringResNamePresentation) : CarOption  {
    PETROL(textRes = StringResNamePresentation.LABEL_PETROL),
    DIESEL(textRes = StringResNamePresentation.LABEL_DIESEL),
    HYBRID(textRes = StringResNamePresentation.LABEL_HYBRID)
}