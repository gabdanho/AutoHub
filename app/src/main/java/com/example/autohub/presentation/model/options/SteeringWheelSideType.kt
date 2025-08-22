package com.example.autohub.presentation.model.options

import com.example.autohub.presentation.model.StringResNamePresentation

enum class SteeringWheelSideType(override val textRes: StringResNamePresentation) : CarOption  {
    RIGHT(textRes = StringResNamePresentation.LABEL_RIGHT_WHEEL),
    LEFT(textRes = StringResNamePresentation.LABEL_LEFT_WHEEL)
}