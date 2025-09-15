package com.example.autohub.presentation.screens.ad.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.autohub.R
import com.example.autohub.presentation.componets.BottomNavBar
import com.example.autohub.presentation.componets.CarAdCard
import com.example.autohub.presentation.componets.InfoPlaceholder
import com.example.autohub.presentation.componets.LoadingCircularIndicator
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.navigation.model.nav_type.SearchFiltersNav
import com.example.autohub.presentation.theme.AppTheme
import com.example.autohub.presentation.utils.showUiMessage

@Composable
fun AdsMainScreen(
    modifier: Modifier = Modifier,
    filters: SearchFiltersNav = SearchFiltersNav(),
    viewModel: AdsMainScreenViewModel = hiltViewModel<AdsMainScreenViewModel>(),
) {
    val uiState = viewModel.uiState.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(filters) {
        viewModel.getAds(filters = filters.filters)
    }

    LaunchedEffect(uiState.uiMessage) {
        context.showUiMessage(uiMessage = uiState.uiMessage) {
            viewModel.clearMessage()
        }
    }

    Scaffold(
        topBar = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                SearchAdsBar(
                    searchText = uiState.searchTextValue,
                    onFiltersClick = { viewModel.onFiltersClick(filters = filters) },
                    onSearchTextChange = { viewModel.updateSearchText(value = it) },
                    getAds = { viewModel.getAds(filters = filters.filters) }
                )
            }
        },
        bottomBar = {
            BottomNavBar(
                onAdListClick = { viewModel.onAdListClick(filters = filters) },
                onAccountClick = { viewModel.onAccountClick() },
                onMessageClick = { viewModel.onMessageClick() }
            )
        }
    ) { innerPadding ->
        when (uiState.loadingState) {
            is LoadingState.Success -> {
                if (uiState.adsList.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = modifier
                            .padding(AppTheme.dimens.extraSmall)
                            .padding(innerPadding)
                    ) {
                        items(uiState.adsList) { carAd ->
                            CarAdCard(
                                ad = carAd,
                                onAdClick = { viewModel.onAdClick(carAd = carAd) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(AppTheme.dimens.extraSmall)
                            )
                        }
                    }
                } else {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(innerPadding)
                            .padding(AppTheme.dimens.extraSmall)
                    ) {
                        Text(
                            text = stringResource(id = R.string.text_ads_not_found),
                            color = AppTheme.colors.placeholderColor
                        )
                    }
                }
            }

            is LoadingState.Error -> {
                InfoPlaceholder(
                    textRes = R.string.error_to_show_page,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                )
            }

            is LoadingState.Loading -> {
                LoadingCircularIndicator(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                )
            }

            null -> {}
        }
    }
}

@Composable
private fun SearchAdsBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    getAds: () -> Unit,
    onFiltersClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(AppTheme.dimens.extraSmall)
    ) {
        TextField(
            value = searchText,
            onValueChange = { onSearchTextChange(it) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    tint = AppTheme.colors.placeholderColor,
                    contentDescription = stringResource(id = R.string.content_search_field)
                )
            },
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.text_search_ads),
                    color = AppTheme.colors.placeholderColor
                )
            },
            shape = AppTheme.shapes.textFieldShape,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = AppTheme.colors.transparent,
                focusedContainerColor = AppTheme.colors.transparent,
                unfocusedIndicatorColor = AppTheme.colors.containerColor,
                focusedIndicatorColor = AppTheme.colors.containerColor
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    getAds()
                }
            ),
            modifier = Modifier.fillMaxWidth(AppTheme.dimens.textFieldWidth)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.List,
            contentDescription = stringResource(id = R.string.content_search_filters),
            modifier = Modifier
                .size(AppTheme.dimens.iconImage)
                .padding(start = AppTheme.dimens.extraSmall)
                .clickable { onFiltersClick() }
        )
    }
}