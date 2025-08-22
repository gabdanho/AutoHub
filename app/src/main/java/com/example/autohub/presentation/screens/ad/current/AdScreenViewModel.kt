package com.example.autohub.presentation.screens.ad.current

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AdScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AdScreenUiState())
    val uiState: StateFlow<AdScreenUiState> = _uiState.asStateFlow()

    private val _callEvent = MutableStateFlow<String?>(null)
    val callEvent: StateFlow<String?> = _callEvent.asStateFlow()

    fun onUserClick() {

    }

    fun onMessageClick() {

    }

    fun callToUser() {

    }

    fun onBackButtonClick() {

    }
}