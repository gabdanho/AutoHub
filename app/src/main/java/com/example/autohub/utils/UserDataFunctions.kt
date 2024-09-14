package com.example.autohub.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.autohub.data.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream
import java.io.InputStream

fun getUserData(uid: String, callBack: (User) -> Unit) {
    Firebase.firestore.collection("users").document(uid).get().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val user = task.result.toObject(User::class.java)
            callBack(user!!)
        } else {
            Log.e("USER_DATA", "Ошибка: ${task.exception?.message}")
        }
    }
}

fun uploadImageToFirebase(context: Context, uri: Uri) {
    val user = Firebase.auth.currentUser!!
    val fbStorage = Firebase.storage.reference.child("users/${user.uid}/profileImage.jpg")
    val fbStoreRef = Firebase.firestore.collection("users").document(user.uid)

    val inputStream = context.contentResolver.openInputStream(uri)
    val bitmap = BitmapFactory.decodeStream(inputStream)
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
    val bytes = baos.toByteArray()

    val task = fbStorage.putBytes(bytes)
    task.addOnSuccessListener { uploadTask ->
        uploadTask.metadata?.reference?.downloadUrl?.addOnCompleteListener { uriTask ->
            fbStoreRef.update("image", uriTask.result.toString()).addOnCompleteListener {
                Toast.makeText(context, "Изображение успешно изменено", Toast.LENGTH_SHORT).show()
            }
        }
    }.addOnFailureListener { failure ->
        Toast.makeText(context, "Ошибка загрузки: ${failure.message ?: "unknown"}", Toast.LENGTH_SHORT).show()
    }
}