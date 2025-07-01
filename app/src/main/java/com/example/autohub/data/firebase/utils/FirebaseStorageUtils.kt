package com.example.autohub.data.firebase.utils

import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await

suspend fun uploadImageToFirebase(
    bytes: ByteArray, path: String,
): String {
    require(bytes.isNotEmpty()) { "Cannot upload empty byte array to Firebase Storage" }

    val fbStorage = Firebase.storage.reference.child(path)
    val uri = fbStorage.putBytes(bytes).continueWithTask { task ->
        if (!task.isSuccessful) throw task.exception ?: RuntimeException("Unknown upload error")
        fbStorage.downloadUrl
    }.await()

    return uri.toString()
}