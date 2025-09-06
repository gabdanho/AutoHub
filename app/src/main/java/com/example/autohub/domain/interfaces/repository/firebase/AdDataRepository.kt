package com.example.autohub.domain.interfaces.repository.firebase

import com.example.autohub.domain.model.CarAd
import com.example.autohub.domain.model.SearchFilter
import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.result.FirebaseResult

interface AdDataRepository {

    suspend fun getAdsWithFilters(filters: List<SearchFilter>): FirebaseResult<List<CarAd>>

    suspend fun getAdsBySearchTextAndFilters(
        searchText: String,
        filters: List<SearchFilter>,
    ): FirebaseResult<List<CarAd>>

    suspend fun getCurrentUserAds(uid: String): FirebaseResult<List<CarAd>>

    suspend fun uploadAdsImagesToFirebase(
        images: List<ImageUploadData>,
        reference: String
    ): FirebaseResult<Unit>

    suspend fun createAd(
        carAdInfo: CarAd,
        images: List<ImageUploadData>
    ): FirebaseResult<Unit>
}