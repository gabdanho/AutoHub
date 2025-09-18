package com.example.autohub.presentation.utils

/**
 * Проверяет, содержит ли строка только буквы.
 */
fun String.isOnlyLetters() = all { it.isLetter() }

/**
 * Проверяет, содержит ли строка только цифры и точки.
 */
fun String.isOnlyDigits(): Boolean = this.replace(".", "").all { it.isDigit() }

/**
 * Валидирует номер телефона.
 */
fun isValidPhoneNumber(number: String): Boolean {
    if (number.isBlank()) return false

    val formattedNumber = if (number.first() == '+') number.drop(1) else number

    return formattedNumber.length == 11 && formattedNumber.isOnlyDigits()
}

/**
 * Валидирует email.
 */
fun isValidEmail(email: String): Boolean {
    if (email.isBlank()) return false

    val regex = Regex("[ \t!#\$%&~=',]|\\.{2,}")

    return !('@' !in email || email.count { it == '@' } > 1 || '.' !in email || email.contains(regex))
}

/**
 * Валидирует название города.
 */
fun isValidCity(city: String): Boolean {
    if (city.isBlank()) return false
    if (city.count { it == '-' } > 1 || city.last() == '-') return false

    return city.replace("-", "").isOnlyLetters()
}

/**
 * Валидирует пароль.
 */
fun isPasswordValid(password: String): Boolean {
    if (password.isBlank()) return false

    val regex = Regex("[!#\$%&~=',.@?+_]")

    return if (password.length < 8) false
    else if (!password.contains(regex)) false
    else if (!(password.any { it.isLowerCase() } && password.any { it.isUpperCase() } && password.any { it.isDigit() })) false
    else true
}

/**
 * Валидирует имя.
 */
fun isNameValid(name: String): Boolean {
    if (name.isBlank()) return false
    if (name.count { it == '-' } > 1 || name.last() == '-') return false

    val formattedName = name.replace("-", "").trim()

    return formattedName.isNotBlank() && formattedName.length >= 2 && formattedName.isOnlyLetters()
}