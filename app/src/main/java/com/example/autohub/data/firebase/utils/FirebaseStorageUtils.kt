package com.example.autohub.data.firebase.utils

import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Утилиты для работы с Firebase Storage.
 *
 * @param fbStorage Экземпляр FirebaseStorage
 */
class FirebaseStorageUtils @Inject constructor(
    private val fbStorage: FirebaseStorage
) {

    /**
     * Загружает изображение в Firebase Storage.
     *
     * @param bytes Массив байтов изображения
     * @param path Путь для сохранения в Storage
     * @return Ссылка на загруженное изображение
     * @throws IllegalArgumentException если массив bytes пустой
     * @throws Exception если произошла ошибка при загрузке
     */
    suspend fun uploadImageToFirebase(
        bytes: ByteArray,
        path: String,
    ): String {
        require(bytes.isNotEmpty()) { "EMPTY_BYTE_ARRAY" }

        val ref = fbStorage.reference.child(path)
        val uri = ref.putBytes(bytes).continueWithTask { task ->
            if (!task.isSuccessful) throw task.exception ?: RuntimeException(task.exception?.message)
            ref.downloadUrl
        }.await()

        return uri.toString()
    }
}