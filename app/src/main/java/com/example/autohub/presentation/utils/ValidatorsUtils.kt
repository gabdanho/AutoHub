package com.example.autohub.presentation.utils

fun String.isOnlyLetters() = all { it.isLetter() }

fun String.isOnlyDigits(): Boolean = this.replace(".", "").all { it.isDigit() }

fun isValidPhoneNumber(value: String): Boolean {
    val phone = if (value.first() == '+') value.drop(1) else value
    return phone.isOnlyDigits()
}

fun isValidEmail(email: String): Boolean {
    val regex = Regex("[ \t!#\$%&~=',]|\\.{2,}")
    return !('@' !in email || email.count { it == '@' } > 1 || '.' !in email || email.contains(regex))
}

fun isValidCity(value: String): Boolean = value.replace("-", "").isOnlyLetters()

fun isPasswordValid(password: String): Boolean {
    val regex = Regex("[!#\$%&~=',.@?+_]")

    return if (password.length < 8) false
    else if (!password.contains(regex)) false
    else if (!(password.any { it.isLowerCase() } && password.any { it.isUpperCase() } && password.any { it.isDigit() })) false
    else true
}