package com.example.autohub.presentation.navigation.model.nav_type

import androidx.navigation.NavType
import androidx.savedstate.SavedState
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

open class NavTypeSerializer<T>(private val serializer: KSerializer<T>) : NavType<T>(
    isNullableAllowed = true
) {

    override fun put(bundle: SavedState, key: String, value: T) {
        bundle.putString(key, serializeAsValue(value))
    }

    override fun get(bundle: SavedState, key: String): T? {
        val data = bundle.getString(key) ?: return null
        return parseValue(data)
    }

    override fun parseValue(value: String): T {
        return Json.decodeFromString(serializer, value)
    }

    override fun serializeAsValue(value: T): String {
        return Json.encodeToString(serializer, value)
    }
}