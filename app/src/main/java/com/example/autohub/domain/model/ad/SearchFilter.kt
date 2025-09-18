package com.example.autohub.domain.model.ad

/**
 * Модель фильтра поиска объявлений.
 *
 * @property name Название фильтра (например, "brand", "price").
 * @property value Значение фильтра.
 */
data class SearchFilter(
    val name: String = "",
    val value: String = ""
)
