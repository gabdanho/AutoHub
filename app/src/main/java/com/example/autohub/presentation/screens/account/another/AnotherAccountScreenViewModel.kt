package com.example.autohub.presentation.screens.account.another

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.presentation.navigation.Navigator
import com.example.autohub.presentation.navigation.model.graphs.destinations.AccountGraph
import com.example.autohub.presentation.navigation.model.graphs.destinations.MessengerGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnotherAccountScreenViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(AnotherAccountScreenUiState())
    val uiState: StateFlow<AnotherAccountScreenUiState> = _uiState.asStateFlow()

    fun writeToUser() {
        viewModelScope.launch {
            navigator.navigate(
                destination = MessengerGraph.ChattingScreen()
            )
        }
    }

    fun callToUser() {

    }

    fun onAdClick(ad: CarAd) {

    }

    fun prevScreen() {

    }

    private fun getBuyerAds() {

    }
}