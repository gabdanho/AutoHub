package com.example.autohub.presentation.model.options

import com.example.autohub.presentation.DIESEL_TAG
import com.example.autohub.presentation.HYBRID_TAG
import com.example.autohub.presentation.PETROL_TAG
import com.example.autohub.presentation.model.StringResNamePresentation

enum class EngineType(
    override val textRes: StringResNamePresentation,
    override val tag: String
) : CarOption  {
    PETROL(textRes = StringResNamePresentation.LABEL_PETROL, tag = PETROL_TAG),
    DIESEL(textRes = StringResNamePresentation.LABEL_DIESEL, tag = DIESEL_TAG),
    HYBRID(textRes = StringResNamePresentation.LABEL_HYBRID, tag = HYBRID_TAG);

    companion object {
        fun fromTag(value: String?): EngineType? {
            return when(value) {
                PETROL_TAG -> PETROL
                DIESEL_TAG -> DIESEL
                HYBRID_TAG -> HYBRID
                else -> null
            }
        }
    }
}