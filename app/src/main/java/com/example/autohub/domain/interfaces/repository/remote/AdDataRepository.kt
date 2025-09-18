package com.example.autohub.domain.interfaces.repository.remote

import com.example.autohub.domain.model.ad.CarAd
import com.example.autohub.domain.model.ad.SearchFilter
import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.result.FirebaseResult

/**
 * Репозиторий для работы с объявлениями автомобилей.
 */
interface AdDataRepository {

    /** Получает список объявлений с фильтром и поиском по тексту. */
    suspend fun getAds(
        searchText: String,
        filters: List<SearchFilter>,
    ): FirebaseResult<List<CarAd>>

    /** Получает список объявлений текущего пользователя. */
    suspend fun getCurrentUserAds(uid: String): FirebaseResult<List<CarAd>>

    /** Загружает изображения объявлений в Firebase Storage. */
    suspend fun uploadAdsImagesToFirebase(
        images: List<ImageUploadData>,
        reference: String
    ): FirebaseResult<Unit>

    /** Создаёт новое объявление с возможностью прикрепления изображений. */
    suspend fun createAd(
        carAdInfo: CarAd,
        images: List<ImageUploadData>
    ): FirebaseResult<Unit>
}