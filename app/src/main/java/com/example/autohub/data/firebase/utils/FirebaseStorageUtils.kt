package com.example.autohub.data.firebase.utils

import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseStorageUtils @Inject constructor(
    private val fbStorage: FirebaseStorage
) {
    suspend fun uploadImageToFirebase(
        bytes: ByteArray,
        path: String,
    ): String {
        require(bytes.isNotEmpty()) { "Cannot upload empty byte array to Firebase Storage" }

        val ref = fbStorage.reference.child(path)
        val uri = ref.putBytes(bytes).continueWithTask { task ->
            if (!task.isSuccessful) throw task.exception ?: RuntimeException("Unknown upload error")
            ref.downloadUrl
        }.await()

        return uri.toString()
    }
}