package com.example.autohub.presentation.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.net.toUri
import com.example.autohub.presentation.constants.TOAST_DURATION
import com.example.autohub.presentation.mapper.resources.StringToResourceIdMapperImpl
import com.example.autohub.presentation.model.UiMessage
import kotlinx.coroutines.delay

fun Context.launchDialIntent(phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = "tel:$phoneNumber".toUri()
    }

    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}

suspend fun Context.showUiMessage(uiMessage: UiMessage, clearMessage: () -> Unit) {
    if (uiMessage.textResName != null) {
        val resId = StringToResourceIdMapperImpl().map(uiMessage.textResName)
        Toast.makeText(this, this.getString(resId), Toast.LENGTH_SHORT).show()
        delay(TOAST_DURATION)
    }
    if (!uiMessage.details.isNullOrBlank()) {
        Toast.makeText(this, uiMessage.details, Toast.LENGTH_SHORT).show()
    }
}