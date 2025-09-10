package com.example.autohub.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchFilter(
    val name: String,
    val value: String
)