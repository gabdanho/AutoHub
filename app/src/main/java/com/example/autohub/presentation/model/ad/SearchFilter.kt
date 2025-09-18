package com.example.autohub.presentation.model.ad

import kotlinx.serialization.Serializable

/**
 * Модель фильтра для поиска объявлений.
 *
 * @property name Название фильтра
 * @property value Значение фильтра
 */
@Serializable
data class SearchFilter(
    val name: String,
    val value: String
)