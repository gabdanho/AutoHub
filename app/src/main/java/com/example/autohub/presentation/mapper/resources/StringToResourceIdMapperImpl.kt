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

        StringResNamePresentation.LABEL_MANUAL to R.string.transmission_type_manual,
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

        StringResNamePresentation.NO_DATA to R.string.text_no_data,

        StringResNamePresentation.ERROR_CALL to R.string.error_to_call,
        StringResNamePresentation.ERROR_EMPTY_PASSWORD to R.string.error_empty_password,
        StringResNamePresentation.ERROR_INCORRECT_PASSWORD to R.string.error_incorrect_password,
        StringResNamePresentation.ERROR_FAILED_CHANGE_PASSWORD to R.string.error_failed_change_password,
        StringResNamePresentation.ERROR_NO_IMAGES to R.string.error_no_images,
        StringResNamePresentation.ERROR_FIELD_AND_OPTIONS_NOT_FILLED_IN to R.string.error_fields_and_options_not_filled_in,
        StringResNamePresentation.ERROR_TO_CREATE_AD to R.string.error_to_create_ad,
        StringResNamePresentation.ERROR_TO_SEND_VERIF_CODE to R.string.error_to_send_verif_code,
        StringResNamePresentation.ERROR_TO_SEND_LINK_TO_RESET_PASSWORD to R.string.error_to_send_link_to_reset_password,
        StringResNamePresentation.ERROR_PASSWORDS_DONT_MATCH to R.string.error_passwords_dont_match,
        StringResNamePresentation.ERROR_REGISTRATION_FAILED to R.string.error_registration_failed,
        StringResNamePresentation.ERROR_SEND_MESSAGE to R.string.error_send_message,
        StringResNamePresentation.ERROR_OPEN_CHAT to R.string.error_open_chat,
        StringResNamePresentation.ERROR_SHOW_USER_DATA to R.string.error_to_show_user_data,
        StringResNamePresentation.ERROR_LISTEN_CHATS to R.string.error_listen_chats,
        StringResNamePresentation.ERROR_GET_USER to R.string.error_get_user,
        StringResNamePresentation.ERROR_TIMEOUT_ERROR to R.string.error_timeout_error,
        StringResNamePresentation.ERROR_LOGIN to R.string.error_login,
        StringResNamePresentation.ERROR_UPDATE_USER_FIELDS to R.string.error_update_user_field,
        StringResNamePresentation.ERROR_NAME_INCORRECT to R.string.error_name_incorrect,
        StringResNamePresentation.ERROR_PHONE_INCORRECT to R.string.error_incorrect_phone,
        StringResNamePresentation.ERROR_EMAIL_INCORRECT to R.string.error_incorrect_email,
        StringResNamePresentation.ERROR_CITY_INCORRECT to R.string.error_incorrect_city,
        StringResNamePresentation.ERROR_CANT_SEND_MESSAGE_NO_INTERNET to R.string.error_cant_send_message_no_internet,

        StringResNamePresentation.INFO_PASSWORD_IS_CHANGED to R.string.info_message_changed,
        StringResNamePresentation.INFO_VERIF_CODE_SENT to R.string.info_verif_code_sent,
        StringResNamePresentation.INFO_LINK_TO_RESET_PASSWORD_SENT to R.string.info_link_to_reset_password_sent,
        StringResNamePresentation.INFO_REGISTRATION_SUCCESS to R.string.info_registration_success,
        StringResNamePresentation.INFO_UPDATE_USER_FIELDS_SUCCESS to R.string.info_update_user_fields_success,
        StringResNamePresentation.INFO_AD_CREATED to R.string.info_ad_created,
    )

    override fun map(resId: StringResNamePresentation): Int {
        return resourceMapPresentation[resId]
            ?: throw IllegalArgumentException("CANT_MAP_THIS_ID_$resId")
    }
}