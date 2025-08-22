package com.example.autohub.data.repository.impl.firebase

import com.example.autohub.data.mapper.toCarAdDomain
import com.example.autohub.data.firebase.model.ad.CarAd
import com.example.autohub.data.firebase.model.safeFirebaseCall
import com.example.autohub.data.firebase.utils.FirebaseStorageUtils
import com.example.autohub.domain.interfaces.repository.firebase.AdDataRepository
import com.example.autohub.domain.model.CarAd as CarAdDomain
import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.SearchFilter
import com.example.autohub.domain.model.User as UserDataDomain
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.domain.utils.TimeProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AdDataRepositoryImpl @Inject constructor(
    private val fbFirestore: FirebaseFirestore,
    private val fbStorageUtils: FirebaseStorageUtils,
    private val timeProvider: TimeProvider
) : AdDataRepository {

    override suspend fun getAdsWithFilters(filters: List<SearchFilter>): FirebaseResult<List<CarAdDomain>> {
        return safeFirebaseCall {
            var fbStoreRef: Query = fbFirestore.collection("ads")

            filters.forEach { (filterName, value) ->
                if (value.isNotEmpty()) {
                    fbStoreRef = fbStoreRef.whereEqualTo(filterName, value)
                }
            }

            val snapshot = fbStoreRef.get().await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(CarAd::class.java)?.toCarAdDomain()
            }
        }
    }

    override suspend fun getAdsBySearchTextAndFilters(
        searchText: String,
        filters: List<SearchFilter>
    ): FirebaseResult<List<CarAdDomain>> {
        return safeFirebaseCall {
            val words = searchText.trim().lowercase().split(' ')
            var fbStoreRef: Query = fbFirestore.collection("ads")

            filters.forEach { (filterName, value) ->
                if (value.isNotEmpty()) {
                    fbStoreRef = fbStoreRef.whereEqualTo(filterName, value)
                }
            }

            val snapshot = fbStoreRef.get().await()
            snapshot.documents.mapNotNull { doc ->
                val adDto = doc.toObject(CarAd::class.java) ?: return@mapNotNull null
                val adInfo = "${adDto.brand} ${adDto.model} ${adDto.realiseYear}".lowercase()

                if (words.any { word -> word in adInfo }) {
                    adDto.toCarAdDomain()
                } else null
            }
        }
    }

    override suspend fun getCurrentUserAds(uid: String): FirebaseResult<List<CarAdDomain>> {
        return safeFirebaseCall {
            val fbStoreRef = fbFirestore.collection("ads")

            val snapshot = fbStoreRef.get().await()
            snapshot.documents
                .mapNotNull { it.toObject(CarAd::class.java) }
                .filter { it.userUID == uid }
                .map { it.toCarAdDomain() }
        }
    }

    override suspend fun createAd(
        carAd: CarAdDomain,
        currentDate: String
    ): FirebaseResult<Unit> {
        return safeFirebaseCall {
            val timeStamp = timeProvider.currentTimeMillis()
            val adReference = "${carAd.userUID}_${timeStamp}"
            val docReference = fbFirestore
                .collection("ads")
                .document(adReference)
            val updatedCarAd = carAd.copy(
                adID = adReference,
                dateAdPublished = currentDate
            )

            docReference.set(updatedCarAd).await()

            // FixMe: Пофиксить (Найти способ как преобразовывать стринги URI в UploadData) -> val imagesUploadData = carAd.imagesUrl.map { ImageUploadData(id = 1, bytes = it.to) }

            uploadAdsImagesToFirebase(imagesUploadData, adReference)
        }
    }

    override suspend fun uploadAdsImagesToFirebase(
        images: List<ImageUploadData>,
        reference: String
    ): FirebaseResult<Unit> {
        return safeFirebaseCall {
            val fbStoreRef = fbFirestore.collection("ads").document(reference)

            val imagesLinks = images.map { image ->
                image.bytes?.let {
                    fbStorageUtils.uploadImageToFirebase(
                        bytes = it,
                        path = "adsImages/$reference/${reference + "_" + image.id}.jpg"
                    )
                }
            }

            fbStoreRef.update("imagesUrl", imagesLinks).await()
        }
    }
}