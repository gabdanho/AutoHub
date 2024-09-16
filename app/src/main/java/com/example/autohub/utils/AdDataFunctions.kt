package com.example.autohub.utils

import android.util.Log
import com.example.autohub.data.CarAd
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

fun getAllAds(callBack: (List<CarAd>) -> Unit) {
    val fbStoreRef = Firebase.firestore.collection("ads")
    val ads = mutableListOf<CarAd>()

    fbStoreRef.get().addOnCompleteListener { snapshotQuery ->
        if (snapshotQuery.isSuccessful) {
            for (ad in snapshotQuery.result) {
                ads.add(ad.toObject(CarAd::class.java))
            }
            callBack(ads.toList())
        } else {
            Log.e("AD_INFO", "Ошибка: ${snapshotQuery.exception?.message ?: "unknown"}")
        }
    }
}