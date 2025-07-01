package com.example.autohub.data.repository.firebase

import com.example.autohub.data.mapper.toCarAd
import com.example.autohub.data.model.CarAdDto
import com.example.autohub.data.firebase.model.safeFirebaseCall
import com.example.autohub.data.firebase.utils.uploadImageToFirebase
import com.example.autohub.domain.interfaces.repository.firebase.AdDataRepository
import com.example.autohub.domain.model.CarAd
import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.SearchFilter
import com.example.autohub.domain.model.UserData
import com.example.autohub.domain.model.Result
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await

class AdDataRepositoryImpl: AdDataRepository {

    private var fbFirestore = Firebase.firestore
    private val fbStorage = Firebase.storage

    override suspend fun getAdsWithFilters(filters: List<SearchFilter>): Result<List<CarAd>> {
        return safeFirebaseCall {
            var fbStoreRef: Query = fbFirestore.collection("ads")

            filters.forEach { (filterName, value) ->
                if (value.isNotEmpty()) {
                    fbStoreRef = fbStoreRef.whereEqualTo(filterName, value)
                }
            }

            val snapshot = fbStoreRef.get().await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(CarAdDto::class.java)?.toCarAd()
            }
        }
    }

    override suspend fun getAdsBySearchTextAndFilters(searchText: String, filters: List<SearchFilter>): Result<List<CarAd>> {
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
                val adDto = doc.toObject(CarAdDto::class.java) ?: return@mapNotNull null
                val adInfo = "${adDto.brand} ${adDto.model} ${adDto.realiseYear}".lowercase()

                if (words.any { word -> word in adInfo }) {
                    adDto.toCarAd()
                } else null
            }
        }
    }

    override suspend fun getCurrentUserAds(uid: String): Result<List<CarAd>> {
        return safeFirebaseCall {
            val fbStoreRef = fbFirestore.collection("ads")

            val snapshot = fbStoreRef.get().await()
            snapshot.documents
                .mapNotNull { it.toObject(CarAdDto::class.java) }
                .filter { it.userUID == uid }
                .map { it.toCarAd() }
        }
    }

    override suspend fun createAd(
        userUID: String, // ToDo: Передать Firebase.auth.currentUser?.uid
        carAd: CarAd,
        authUserData: UserData,
        currentDate: String,
        timeStamp: String, // ToDo: Это вот System.currentTimeMillis()
        images: List<ImageUploadData>
    ): Result<Boolean> {
        return safeFirebaseCall {
            val adReference = "${userUID}_${timeStamp}"
            val docReference = fbFirestore
                .collection("ads")
                .document(adReference)
            val updatedCarAd = carAd.copy(
                adID = adReference,
                userUID = userUID,
                city = authUserData.city,
                dateAdPublished = currentDate
            )

            docReference.set(updatedCarAd).await()

            uploadAdsImagesToFirebase(images, adReference)

            true
        }
    }

    override suspend fun uploadAdsImagesToFirebase(images: List<ImageUploadData>, reference: String): Result<Boolean> {
        return safeFirebaseCall {
            val fbStoreRef = fbFirestore.collection("ads").document(reference)

            val imagesLinks = images.map { image ->
                uploadImageToFirebase(
                    bytes = image.bytes,
                    path = "adsImages/$reference/${reference + "_" + image.id}.jpg"
                )
            }

            fbStoreRef.update("imagesUrl", imagesLinks).await()

            true
        }
    }
}