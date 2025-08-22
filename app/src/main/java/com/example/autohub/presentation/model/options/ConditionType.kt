package com.example.autohub.presentation.model.options

import com.example.autohub.presentation.model.StringResNamePresentation

enum class ConditionType(override val textRes: StringResNamePresentation) : CarOption  {
    NOT_BROKEN(textRes = StringResNamePresentation.LABEL_NOT_BROKEN),
    BROKEN(textRes = StringResNamePresentation.LABEL_BROKEN),
    NOT_WORKING(textRes = StringResNamePresentation.LABEL_NOT_WORKING)
}