package com.example.autohub.domain.interfaces.repository.remote

import com.example.autohub.domain.model.ad.CarAd
import com.example.autohub.domain.model.ad.SearchFilter
import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.result.FirebaseResult

interface AdDataRepository {

    suspend fun getAds(
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