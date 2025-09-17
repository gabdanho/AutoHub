package com.example.autohub.presentation.screens.ad.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autohub.domain.interfaces.usecase.GetAdsUseCase
import com.example.autohub.domain.interfaces.usecase.MillisToDateUseCase
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.presentation.mapper.mapListCarAdDomainToPresentation
import com.example.autohub.presentation.mapper.toSearchFilterDomain
import com.example.autohub.presentation.mapper.toStringResNamePresentation
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.ad.SearchFilter
import com.example.autohub.presentation.model.StringResNamePresentation
import com.example.autohub.presentation.model.UiMessage
import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.presentation.navigation.Navigator
import com.example.autohub.presentation.navigation.model.graphs.destinations.AccountGraph
import com.example.autohub.presentation.navigation.model.graphs.destinations.AdGraph
import com.example.autohub.presentation.navigation.model.graphs.destinations.MessengerGraph
import com.example.autohub.presentation.navigation.model.nav_type.SearchFiltersNav
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdsMainScreenViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getAdsUseCase: GetAdsUseCase,
    private val millisToDateUseCase: MillisToDateUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdsMainScreenUiState())
    val uiState: StateFlow<AdsMainScreenUiState> = _uiState.asStateFlow()

    fun onFiltersClick(filters: SearchFiltersNav) {
        viewModelScope.launch {
            navigator.navigate(
                destination = AdGraph.FiltersScreen(searchFilters = filters)
            )
        }
    }

    fun onAccountClick() {
        viewModelScope.launch {
            navigator.navigate(
                destination = AccountGraph.AuthUserAccountScreen
            )
        }
    }

    fun onMessageClick() {
        viewModelScope.launch {
            navigator.navigate(
                destination = MessengerGraph.MessengerScreen
            )
        }
    }

    fun onAdListClick(filters: SearchFiltersNav) {
        viewModelScope.launch {
            navigator.navigate(
                destination = AdGraph.AdsMainScreen(searchFilters = filters)
            )
        }
    }

    fun onAdClick(carAd: CarAd) {
        viewModelScope.launch {
            navigator.navigate(
                destination = AdGraph.CurrentAdScreen(
                    carAd = carAd
                )
            )
        }
    }

    fun getAds(filters: List<SearchFilter>, forced: Boolean = false) {
        if (!forced && _uiState.value.adsList.isNotEmpty()) return

        viewModelScope.launch {
            _uiState.update { state -> state.copy(loadingState = LoadingState.Loading) }

            when (
                val result = getAdsUseCase(
                    searchText = _uiState.value.searchTextValue,
                    filters = filters.map { it.toSearchFilterDomain() }
                )
            ) {
                is FirebaseResult.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            loadingState = LoadingState.Success,
                            adsList = mapListCarAdDomainToPresentation(
                                ads = result.data,
                                millisToDate = millisToDateUseCase::invoke
                            )
                        )
                    }
                }

                is FirebaseResult.Error.TimeoutError -> {
                    _uiState.update { state ->
                        state.copy(
                            uiMessage = UiMessage(textResName = StringResNamePresentation.ERROR_TIMEOUT_ERROR),
                            loadingState = LoadingState.Error(message = result.message)
                        )
                    }
                }

                is FirebaseResult.Error.HandledError -> {
                    _uiState.update { state ->
                        state.copy(
                            uiMessage = UiMessage(textResName = result.tag.toStringResNamePresentation()),
                            loadingState = LoadingState.Error(message = result.message)
                        )
                    }
                }

                is FirebaseResult.Error -> {
                    _uiState.update { state ->
                        state.copy(
                            loadingState = LoadingState.Error(message = result.message),
                        )
                    }
                }
            }
        }
    }

    fun updateSearchText(value: String) {
        _uiState.update { state ->
            state.copy(searchTextValue = value)
        }
    }

    fun clearMessage() {
        _uiState.update { state -> state.copy(uiMessage = UiMessage()) }
    }
}