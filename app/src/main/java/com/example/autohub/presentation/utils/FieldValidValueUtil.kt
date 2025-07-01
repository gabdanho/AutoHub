package com.example.autohub.presentation.utils

import android.content.Context
import android.widget.Toast

fun String.isOnlyLetters() = all { it.isLetter() }

fun String.isOnlyDigits(): Boolean {
    val str = this.replace(".", "")

    return str.all { it.isDigit() }
}

fun isValidPhoneNumber(phone: String): Boolean {
    val _phone = if (phone.first() == '+') phone.drop(1) else phone
    if (!_phone.all { it.isDigit() }) return false

    return true
}

fun isValidEmail(email: String): Boolean {
    val regex = Regex("[ \t!#\$%&~=',]|\\.{2,}")

    if ('@' !in email || email.count { it == '@' } >= 2 || '.' !in email || email.contains(regex)) return false

    return true
}

fun isValidCity(city: String): Boolean {
    val _city = city.replace("-", "")

    if (!_city.all { it.isLetter() }) return false
    else return true
}

fun isPasswordValid(password: String, context: Context): Boolean {
    val regex = Regex("[!#\$%&~=',.@?+_]")

    if (password.length < 8) {
        Toast.makeText(context, "Пароль должен содержать 8 и более и символов", Toast.LENGTH_SHORT).show()
        return false
    } else if (!password.contains(regex)) {
        Toast.makeText(context, "Пароль должен содержать символы: !#\$%&~=',.@?+-_", Toast.LENGTH_SHORT).show()
        return false
    } else if (!(password.any { it.isLowerCase() } && password.any { it.isUpperCase() } && password.any { it.isDigit() })) {
        Toast.makeText(context, "Пароль должен иметь символы в верхнем и нижнем регистре, а также цифры", Toast.LENGTH_SHORT).show()
        return false
    } else return true
}