package com.example.autohub.presentation.navigation.model.nav_type

import androidx.compose.runtime.Immutable
import com.example.autohub.presentation.model.ad.CarAd
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class ListCarAdNav(
    val ads: List<CarAd> = emptyList()
)

class ListCarAdNavType(serializer: KSerializer<ListCarAdNav> = ListCarAdNav.serializer())
    : NavTypeSerializer<ListCarAdNav>(
        serializer = serializer
    )