package com.example.autohub.presentation.model.options

import com.example.autohub.presentation.model.StringResNamePresentation

enum class TransmissionTypes(val textRes: StringResNamePresentation) {
    MANUAL(textRes = StringResNamePresentation.LABEL_MANUAL),
    AUTOMATIC(textRes = StringResNamePresentation.LABEL_AUTOMATIC),
    VARIATOR(textRes = StringResNamePresentation.LABEL_VARIATOR),
    ROBOT(textRes = StringResNamePresentation.LABEL_ROBOT)
}