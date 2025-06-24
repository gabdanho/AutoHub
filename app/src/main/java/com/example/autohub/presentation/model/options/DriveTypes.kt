package com.example.autohub.presentation.model.options

import com.example.autohub.presentation.model.StringResNamePresentation

enum class DriveTypes(val textRes: StringResNamePresentation) {
    FWD(textRes = StringResNamePresentation.LABEL_FWD),
    RWD(textRes = StringResNamePresentation.LABEL_RWD),
    FULL_WD(textRes = StringResNamePresentation.LABEL_FULL_WD)
}