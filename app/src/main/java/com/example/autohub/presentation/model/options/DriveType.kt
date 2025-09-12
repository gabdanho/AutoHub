package com.example.autohub.presentation.model.options

import com.example.autohub.presentation.FULL_WD_TAG
import com.example.autohub.presentation.FWD_TAG
import com.example.autohub.presentation.RWD_TAG
import com.example.autohub.presentation.model.StringResNamePresentation
import kotlinx.serialization.Serializable

@Serializable
enum class DriveType(override val textRes: StringResNamePresentation, override val tag: String) : CarOption  {
    FWD(textRes = StringResNamePresentation.LABEL_FWD, tag = FWD_TAG),
    RWD(textRes = StringResNamePresentation.LABEL_RWD, tag = RWD_TAG),
    FULL_WD(textRes = StringResNamePresentation.LABEL_FULL_WD, tag = FULL_WD_TAG);

    companion object {
        fun fromTag(value: String?): DriveType? {
            return when(value) {
                FWD_TAG -> FWD
                RWD_TAG -> RWD
                FULL_WD_TAG -> FULL_WD
                else -> null
            }
        }
    }
}