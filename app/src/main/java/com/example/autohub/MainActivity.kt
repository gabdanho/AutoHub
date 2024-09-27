package com.example.autohub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import com.example.autohub.data.UserStatus
import com.example.autohub.utils.changeUserStatus

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutoHubEntryPoint()

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CALL_PHONE),
                0
            )
        }
    }

    override fun onPause() {
        super.onPause()
        changeUserStatus(UserStatus.OFFLINE)
    }

    override fun onResume() {
        super.onResume()
        changeUserStatus(UserStatus.ONLINE)
    }
}