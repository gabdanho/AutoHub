package com.example.autohub.data.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.NavHostController
import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.presentation.model.user.User
import com.example.autohub.presentation.navigation.ScreenRoutes
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream

fun getAllAds(filters: Map<String, String> = emptyMap(), callBack: (List<CarAd>) -> Unit) {
    var fbStoreRef: Query = Firebase.firestore.collection("ads")

    if (filters.isNotEmpty()) {
        filters.forEach { (filter, value) ->
            if (value.isNotEmpty()) {
                fbStoreRef = fbStoreRef.whereEqualTo(filter, value)
            }
        }
    }

    fbStoreRef.get().addOnCompleteListener { snapshotQuery ->
        if (snapshotQuery.isSuccessful) {
            val ads = snapshotQuery.result.map { it.toObject(CarAd::class.java) }
            callBack(ads)
        } else {
            Log.e("AD_INFO", "Ошибка: ${snapshotQuery.exception?.message ?: "unknown"}")
        }
    }
}

fun getAdsBySearchText(searchText: String, filters: Map<String, String> = emptyMap(), callBack: (List<CarAd>) -> Unit) {
    val words = searchText.trim().split(' ')

    var fbStoreRef: Query = Firebase.firestore.collection("ads")

    if (filters.isNotEmpty()) {
        filters.forEach { (filter, value) ->
            if (value.isNotEmpty()) {
                fbStoreRef = fbStoreRef.whereEqualTo(filter, value)
            }
        }
    }

    fbStoreRef.get().addOnCompleteListener { snapshotQuery ->
        if (snapshotQuery.isSuccessful) {
            val ads: MutableList<CarAd> = emptyList<CarAd>().toMutableList()
            snapshotQuery.result.forEach { ad ->
                val adObject = ad.toObject(CarAd::class.java)
                val adInfo = "${adObject.brand} ${adObject.model} ${adObject.realiseYear}".lowercase()
                println("$words $adInfo")

                words.forEach { word ->
                    if (word.lowercase() in adInfo) {
                        ads.add(adObject)
                        return@forEach
                    }
                }
            }
            callBack(ads)
        } else {
            Log.e("AD_INFO", "Ошибка: ${snapshotQuery.exception?.message ?: "unknown"}")
        }
    }
}

fun getCurrentUserAds(uid: String, callBack: (List<CarAd>) -> Unit) {
    val fbStoreRef = Firebase.firestore.collection("ads")

    fbStoreRef.get().addOnCompleteListener { snapshotQuery ->
        val ads = mutableListOf<CarAd>()

        if (snapshotQuery.isSuccessful) {
            snapshotQuery.result.forEach { ad ->
                val carAd = ad.toObject(CarAd::class.java)
                if (carAd.userUID == uid) ads.add(carAd)
            }
            callBack(ads)
        } else {
            Log.e("AD_INFO", "Ошибка: ${snapshotQuery.exception?.message ?: "unknown"}")
        }
    }
}

fun uploadAdsImagesToFirebase(context: Context, images: List<Uri>, reference: String) {
    val fbStoreRef = Firebase.firestore.collection("ads").document(reference)

    var counter = 1
    val linksList = mutableListOf<String>()
    val uploadTasks = mutableListOf<Task<Uri>>()

    images.forEach { uri ->
        val fbStorage = Firebase.storage.reference.child("adsImages/$reference/${reference + "_" + counter}.jpg")

        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        val bytes = baos.toByteArray()

        val uploadTask = fbStorage.putBytes(bytes).continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            fbStorage.downloadUrl
        }

        uploadTask.addOnSuccessListener { uri ->
            linksList.add(uri.toString())
        }.addOnFailureListener { failure ->
            Toast.makeText(context, "Ошибка загрузки: ${failure.message ?: "unknown"}", Toast.LENGTH_SHORT).show()
        }

        uploadTasks.add(uploadTask)

        counter++
    }

    Tasks.whenAllComplete(uploadTasks).addOnCompleteListener {
        fbStoreRef.update("imagesUrl", linksList).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("AD_INFO", "Изображения успешны загружены")
            } else {
                Log.d("AD_INFO", "Ошибка: ${task.exception?.message}")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun createAd(
    carAd: CarAd,
    authUserData: User,
    context: Context,
    images: List<Uri>,
    navController: NavHostController
) {
    val fbStore = Firebase.firestore

    val userUID = getAuthUserUID()
    val adReference = "${userUID}_${System.currentTimeMillis()}"
    val docReference = fbStore
        .collection("ads")
        .document(adReference)

    docReference
        .set(
            carAd.copy(
                adId = adReference,
                userUID = userUID,
                city = authUserData.city,
                dateAdPublished = getFullDate()
            )
        )
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("AD_INFO", "Ad is created: ${carAd.brand} ${carAd.model}")
            } else {
                Log.d("AD_INFO", "Error: ${task.exception?.message ?: "unknown"}")
            }
        }

    uploadAdsImagesToFirebase(context, images, adReference)

    navController.navigate(route = ScreenRoutes.AUTH_USER_ACCOUNT.name)
}