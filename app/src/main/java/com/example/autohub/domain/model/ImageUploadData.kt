package com.example.autohub.domain.model

/**
 * Модель данных для загрузки изображения в хранилище.
 *
 * @property id Уникальный идентификатор изображения.
 * @property bytes Сырые данные изображения в виде массива байтов.
 */
class ImageUploadData(val id: Int? = null, val bytes: ByteArray)