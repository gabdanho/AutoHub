package com.example.autohub.presentation.mapper.resources

import com.example.autohub.R
import com.example.autohub.presentation.model.StringResNamePresentation

class StringToResourceIdMapperImpl : StringToResourceIdMapper {

    private val resourceMapPresentation = mapOf(
        StringResNamePresentation.LABEL_SEDAN to R.string.body_type_sedan,
        StringResNamePresentation.LABEL_COUPE to R.string.body_type_coupe,
        StringResNamePresentation.LABEL_CONVERTIBLE to R.string.body_type_convertible,
        StringResNamePresentation.LABEL_STATION_WAGON to R.string.body_type_station_wagon,
        StringResNamePresentation.LABEL_HATCHBACK to R.string.body_type_hatchback,
        StringResNamePresentation.LABEL_VAN to R.string.body_type_van,
        StringResNamePresentation.LABEL_MINIVAN to R.string.body_type_minivan,
        StringResNamePresentation.LABEL_SUV to R.string.body_type_suv,
        StringResNamePresentation.LABEL_CROSSOVER to R.string.body_type_crossover,
        StringResNamePresentation.LABEL_PICKUP_TRUCK to R.string.body_type_pickup_truck,
        StringResNamePresentation.LABEL_LIMOUSINE to R.string.body_type_limousine,

        StringResNamePresentation.LABEL_NOT_BROKEN to R.string.condition_type_not_broken,
        StringResNamePresentation.LABEL_BROKEN to R.string.condition_type_broken,
        StringResNamePresentation.LABEL_NOT_WORKING to R.string.condition_type_not_working,

        StringResNamePresentation.LABEL_MANUAL to R.string.transmissoin_type_manual,
        StringResNamePresentation.LABEL_AUTOMATIC to R.string.transmission_type_automatic,
        StringResNamePresentation.LABEL_VARIATOR to R.string.transmission_type_variator,
        StringResNamePresentation.LABEL_ROBOT to R.string.transmission_type_robot,

        StringResNamePresentation.LABEL_RIGHT_WHEEL to R.string.steering_wheel_side_type_right,
        StringResNamePresentation.LABEL_LEFT_WHEEL to R.string.steering_wheel_side_type_left,

        StringResNamePresentation.LABEL_PETROL to R.string.engine_type_petrol,
        StringResNamePresentation.LABEL_DIESEL to R.string.engine_type_diesel,
        StringResNamePresentation.LABEL_HYBRID to R.string.engine_type_hybrid,

        StringResNamePresentation.LABEL_FWD to R.string.drive_type_fwd,
        StringResNamePresentation.LABEL_RWD to R.string.drive_type_rwd,
        StringResNamePresentation.LABEL_FULL_WD to R.string.drive_type_4wd,
    )

    override fun map(resId: StringResNamePresentation): Int {
        return resourceMapPresentation[resId]
            ?: throw IllegalArgumentException("No mapping found for $resId")
    }
}