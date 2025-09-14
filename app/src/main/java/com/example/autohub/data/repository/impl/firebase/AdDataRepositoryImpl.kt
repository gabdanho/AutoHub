package com.example.autohub.data.repository.impl.firebase

import com.example.autohub.data.firebase.constants.FirebasePaths.createAdImagePath
import com.example.autohub.data.firebase.constants.FirebasePaths.createAdReference
import com.example.autohub.data.firebase.constants.FirestoreCollections.ADS
import com.example.autohub.data.firebase.constants.FirestoreFields.IMAGES_URL_FIELD
import com.example.autohub.data.mapper.toCarAdDomain
import com.example.autohub.data.firebase.model.ad.CarAd
import com.example.autohub.data.firebase.model.safeFirebaseCall
import com.example.autohub.data.firebase.utils.FirebaseStorageUtils
import com.example.autohub.domain.interfaces.repository.remote.AdDataRepository
import com.example.autohub.domain.model.ad.CarAd as CarAdDomain
import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.ad.SearchFilter
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

    override suspend fun getAds(
        searchText: String,
        filters: List<SearchFilter>
    ): FirebaseResult<List<CarAdDomain>> {
        return safeFirebaseCall {
            var fbStoreRef: Query = fbFirestore.collection(ADS)

            filters.forEach { (filterName, value) ->
                if (value.isNotEmpty()) {
                    fbStoreRef = fbStoreRef.whereEqualTo(filterName, value)
                }
            }

            val snapshot = fbStoreRef.get().await()

            if (searchText.isNotBlank()) {
                val words = searchText.trim().lowercase().split(' ')

                snapshot.documents.mapNotNull { doc ->
                    val adDto = doc.toObject(CarAd::class.java) ?: return@mapNotNull null
                    val adInfo = adDto.toSearchableString()

                    if (words.any { word -> word in adInfo }) {
                        adDto.toCarAdDomain()
                    } else null
                }
            } else {
                snapshot.documents.mapNotNull { doc ->
                    doc.toObject(CarAd::class.java)?.toCarAdDomain()
                }
            }
        }
    }

    override suspend fun getCurrentUserAds(uid: String): FirebaseResult<List<CarAdDomain>> {
        return safeFirebaseCall {
            val fbStoreRef = fbFirestore.collection(ADS)

            val snapshot = fbStoreRef.get().await()
            snapshot.documents
                .mapNotNull { it.toObject(CarAd::class.java) }
                .filter { it.userId == uid }
                .map { it.toCarAdDomain() }
        }
    }

    override suspend fun createAd(
        carAdInfo: CarAdDomain,
        images: List<ImageUploadData>
    ): FirebaseResult<Unit> {
        return safeFirebaseCall {
            val timeStamp = timeProvider.currentTimeMillis()
            val currentDate = timeProvider.millisToDate(timeStamp)
            val adReference = createAdReference(userId = carAdInfo.userId, timeStamp = timeStamp)
            val docReference = fbFirestore
                .collection(ADS)
                .document(adReference)
            val updatedCarAd = carAdInfo.copy(
                adID = adReference,
                dateAdPublished = currentDate
            )

            docReference.set(updatedCarAd).await()

            uploadAdsImagesToFirebase(images = images, reference = adReference)
        }
    }

    override suspend fun uploadAdsImagesToFirebase(
        images: List<ImageUploadData>,
        reference: String
    ): FirebaseResult<Unit> {
        return safeFirebaseCall {
            val fbStoreRef = fbFirestore.collection(ADS).document(reference)

            val imagesLinks = images.map { image ->
                image.id?.let { id ->
                    fbStorageUtils.uploadImageToFirebase(
                        bytes = image.bytes,
                        path = createAdImagePath(reference = reference, imageId = id)
                    )
                }
            }

            fbStoreRef.update(IMAGES_URL_FIELD, imagesLinks).await()
        }
    }
}

private fun CarAd.toSearchableString() = "$brand $model $realiseYear".lowercase()