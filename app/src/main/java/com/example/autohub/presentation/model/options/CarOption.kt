package com.example.autohub.presentation.model.options

import com.example.autohub.presentation.model.StringResNamePresentation

/**
 * Общий интерфейс для всех опций автомобиля.
 *
 * @property textRes Ресурс строки для отображения значения
 * @property tag Тег для хранения или передачи значения
 */
interface CarOption {
    val textRes: StringResNamePresentation
    val tag: String
}