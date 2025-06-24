package com.example.autohub.presentation.model.options

import com.example.autohub.presentation.model.StringResNamePresentation

enum class SteeringWheelSideTypes(val textRes: StringResNamePresentation) {
    RIGHT(textRes = StringResNamePresentation.LABEL_RIGHT_WHEEL),
    LEFT(textRes = StringResNamePresentation.LABEL_LEFT_WHEEL)
}