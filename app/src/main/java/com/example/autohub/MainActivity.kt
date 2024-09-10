package com.example.autohub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.autohub.data.mock.CarAdMock
import com.example.autohub.ui.account.AnotherAccountScreen
import com.example.autohub.ui.messenger.MessengerScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessengerScreen(
                buyers = listOf("Вася Жесткий", "Женя Алблак"),
                onBackButtonClick = { },
                onAnswerClick = { }
            )
        }
    }
}