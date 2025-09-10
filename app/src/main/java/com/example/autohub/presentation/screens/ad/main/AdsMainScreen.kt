package com.example.autohub.presentation.screens.ad.main

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.autohub.R
import com.example.autohub.presentation.componets.BottomNavBar
import com.example.autohub.presentation.componets.CarAdCard
import com.example.autohub.presentation.model.SearchFilter
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.navigation.model.nav_type.SearchFiltersNav
import com.example.autohub.presentation.theme.containerColor

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

    LaunchedEffect(uiState.loadingState) {
        if (uiState.loadingState is LoadingState.Error) {
            Toast.makeText(context, uiState.loadingState.message, Toast.LENGTH_SHORT).show()
            viewModel.clearLoadingState()
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
                onAdListClick = { /* Nothing */ },
                onAccountClick = { viewModel.onAccountClick() },
                onMessageClick = { viewModel.onMessageClick() }
            )
        }
    ) { innerPadding ->
        if (uiState.adsList.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier
                    .padding(8.dp)
                    .padding(innerPadding)
            ) {
                items(uiState.adsList) { carAd ->
                    CarAdCard(
                        ad = carAd,
                        onAdClick = { viewModel.onAdClick(carAd = carAd) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }
        } else {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.text_ads_not_found),
                    color = Color.LightGray
                )
            }
        }
    }
}

@Composable
private fun SearchAdsBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    getAds: () -> Unit,
    onFiltersClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        TextField(
            value = searchText,
            onValueChange = { onSearchTextChange(it) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    tint = Color.LightGray,
                    contentDescription = stringResource(id = R.string.content_search_field)
                )
            },
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.text_search_ads),
                    color = Color.LightGray
                )
            },
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = containerColor,
                focusedIndicatorColor = containerColor
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    getAds()
                }
            ),
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.List,
            contentDescription = stringResource(id = R.string.content_search_filters),
            modifier = Modifier
                .size(40.dp)
                .padding(start = 8.dp)
                .clickable { onFiltersClick() }
        )
    }
}