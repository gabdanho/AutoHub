package com.example.autohub.presentation.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.launchDialIntent(phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }

    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}