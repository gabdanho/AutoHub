package com.example.autohub.presentation.model.options

import com.example.autohub.presentation.AUTOMATIC_TAG
import com.example.autohub.presentation.MANUAL_TAG
import com.example.autohub.presentation.ROBOT_TAG
import com.example.autohub.presentation.VARIATOR_TAG
import com.example.autohub.presentation.model.StringResNamePresentation

enum class TransmissionType(
    override val textRes: StringResNamePresentation,
    override val tag: String
) : CarOption  {
    MANUAL(textRes = StringResNamePresentation.LABEL_MANUAL, tag = MANUAL_TAG),
    AUTOMATIC(textRes = StringResNamePresentation.LABEL_AUTOMATIC, tag = AUTOMATIC_TAG),
    VARIATOR(textRes = StringResNamePresentation.LABEL_VARIATOR, tag = VARIATOR_TAG),
    ROBOT(textRes = StringResNamePresentation.LABEL_ROBOT, tag = ROBOT_TAG);

    companion object {
        fun fromTag(value: String?): TransmissionType? {
            return when(value) {
                MANUAL_TAG -> MANUAL
                AUTOMATIC_TAG -> AUTOMATIC
                VARIATOR_TAG -> VARIATOR
                ROBOT_TAG -> ROBOT
                else -> null
            }
        }
    }
}