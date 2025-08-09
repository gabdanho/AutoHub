package com.example.autohub.presentation.screens.ads

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.autohub.R
import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.presentation.componets.BottomNavBar
import com.example.autohub.presentation.componets.CarAdCard
import com.example.autohub.presentation.theme.containerColor
import com.example.autohub.data.utils.getAdsBySearchText

@Composable
fun AdsMainScreen(
    adsList: List<CarAd>,
    onAccountClick: () -> Unit,
    onMessageClick: () -> Unit,
    onAdListClick: () -> Unit,
    onAdClick: (CarAd) -> Unit,
    onFiltersClick: () -> Unit,
    onDoneClick: (List<CarAd>) -> Unit,
    modifier: Modifier = Modifier,
    filters: Map<String, String> = emptyMap()
) {
     Scaffold(
         topBar = {
             Row(
                 horizontalArrangement = Arrangement.Center,
                 modifier = Modifier.fillMaxWidth()
             ) {
                 SearchAdsBar(filters = filters, onDoneClick = onDoneClick, onFiltersClick = onFiltersClick)
                 Icon(
                     imageVector = Icons.AutoMirrored.Filled.List,
                     contentDescription = stringResource(id = R.string.content_search_filters),
                     modifier = Modifier
                         .size(40.dp)
                         .clickable { onFiltersClick() }
                 )
             }
         },
         bottomBar = {
             BottomNavBar(
                 onAdListClick = onAdListClick,
                 onAccountClick = onAccountClick,
                 onMessageClick = onMessageClick
             )
         }
     ) { innerPadding ->
         if (adsList.isNotEmpty()) {
             LazyVerticalGrid(
                 columns = GridCells.Fixed(2),
                 modifier = modifier
                     .padding(8.dp)
                     .padding(innerPadding)
             ) {
                 items(adsList) { carAd ->
                     CarAdCard(
                         ad = carAd,
                         onAdClick = { onAdClick(carAd) },
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
fun SearchAdsBar(
    filters: Map<String, String>,
    onDoneClick: (List<CarAd>) -> Unit,
    onFiltersClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val searchTextState = remember { mutableStateOf("") }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        TextField(
            value = searchTextState.value,
            onValueChange = { searchTextState.value = it },
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
                    getAdsBySearchText(searchTextState.value, filters, onDoneClick)
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

//@Preview(showBackground = true)
//@Composable
//private fun AdsMainScreenPreview() {
//    AdsMainScreen(
//        listOf(CarAd()), mapOf(), { }, { }, { }, { }, { }, { }
//    )
//}