package com.example.autohub.presentation.model.options

import com.example.autohub.presentation.BROKEN_TAG
import com.example.autohub.presentation.NOT_BROKEN_TAG
import com.example.autohub.presentation.NOT_WORKING_TAG
import com.example.autohub.presentation.model.StringResNamePresentation

enum class ConditionType(
    override val textRes: StringResNamePresentation,
    override val tag: String,
) : CarOption {
    NOT_BROKEN(textRes = StringResNamePresentation.LABEL_NOT_BROKEN, tag = NOT_BROKEN_TAG),
    BROKEN(textRes = StringResNamePresentation.LABEL_BROKEN, tag = BROKEN_TAG),
    NOT_WORKING(textRes = StringResNamePresentation.LABEL_NOT_WORKING, tag = NOT_WORKING_TAG);

    companion object {
        fun fromTag(value: String?): ConditionType? {
            return when(value) {
                NOT_BROKEN_TAG -> NOT_BROKEN
                BROKEN_TAG -> BROKEN
                NOT_WORKING_TAG -> NOT_WORKING
                else -> null
            }
        }
    }
}