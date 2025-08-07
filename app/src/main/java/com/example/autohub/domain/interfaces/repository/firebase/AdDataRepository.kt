package com.example.autohub.domain.interfaces.repository.firebase

import com.example.autohub.domain.model.CarAd
import com.example.autohub.domain.model.SearchFilter
import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.domain.model.UserData

interface AdDataRepository {

    suspend fun getAdsWithFilters(filters: List<SearchFilter>): FirebaseResult<List<CarAd>>

    suspend fun getAdsBySearchTextAndFilters(
        searchText: String,
        filters: List<SearchFilter>,
    ): FirebaseResult<List<CarAd>>

    suspend fun getCurrentUserAds(uid: String): FirebaseResult<List<CarAd>>

    suspend fun uploadAdsImagesToFirebase(images: List<ImageUploadData>, reference: String): FirebaseResult<Boolean>

    suspend fun createAd(
        userUID: String,
        carAd: CarAd,
        authUserData: UserData,
        currentDate: String,
        timeStamp: String,
        images: List<ImageUploadData>,
    ): FirebaseResult<Boolean>
}