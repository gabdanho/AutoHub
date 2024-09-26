package com.example.autohub.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavHostController
import com.example.autohub.data.CarAd
import com.example.autohub.data.User
import com.example.autohub.ui.navigation.ScreenRoutes
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream

fun getAllAds(callBack: (List<CarAd>) -> Unit) {
    val fbStoreRef = Firebase.firestore.collection("ads")

    fbStoreRef.get().addOnCompleteListener { snapshotQuery ->
        if (snapshotQuery.isSuccessful) {
            val ads = snapshotQuery.result.map { it.toObject(CarAd::class.java) }
            callBack(ads)
        } else {
            Log.e("AD_INFO", "Ошибка: ${snapshotQuery.exception?.message ?: "unknown"}")
        }
    }
}

fun getAdsBySearchText(searchText: String, callBack: (List<CarAd>) -> Unit) {
    val words = searchText.trim().split(' ')

    val fbStoreRef = Firebase.firestore.collection("ads")
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