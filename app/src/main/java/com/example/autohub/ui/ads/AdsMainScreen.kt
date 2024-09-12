package com.example.autohub.ui.ads

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.autohub.data.CarAd
import com.example.autohub.data.mock.CarAdMock
import com.example.autohub.ui.componets.BottomNavBar
import com.example.autohub.ui.componets.CarAdCard
import com.example.autohub.ui.theme.containerColor

@Composable
fun AdsMainScreen(
    adsList: List<CarAd>,
    onAccountClick: () -> Unit,
    onMessageClick: () -> Unit,
    onAdListClick: () -> Unit,
    modifier: Modifier = Modifier
) {
     Scaffold(
         topBar = { SearchAdsBar() },
         bottomBar = { BottomNavBar(onAdListClick, onAccountClick, onMessageClick) }
     ) { innerPadding ->
         LazyVerticalGrid(
             columns = GridCells.Fixed(2),
             modifier = modifier
                 .padding(8.dp)
                 .padding(innerPadding)
         ) {
             items(adsList) { carAd ->
                 CarAdCard(carAd, { })
             }
         }
     }
}

@Composable
fun SearchAdsBar(modifier: Modifier = Modifier) {
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
                    contentDescription = "Поисковая строка"
                )
            },
            singleLine = true,
            placeholder = {
                Text(
                    text = "Поиск объявления",
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
            modifier = Modifier.fillMaxWidth(0.8f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AdsMainScreenPreview() {
    AdsMainScreen(
        adsList = CarAdMock.ads,
        { }, { }, { }
    )
}