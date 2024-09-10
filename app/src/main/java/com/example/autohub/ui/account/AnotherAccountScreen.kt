package com.example.autohub.ui.account

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.autohub.data.CarAd
import com.example.autohub.data.mock.CarAdMock
import com.example.autohub.ui.componets.CarAdCard
import com.example.autohub.ui.componets.CustomButton
import com.example.autohub.ui.componets.TopAdAppBar
import com.example.autohub.ui.theme.containerColor

@Composable
fun AnotherAccountScreen(
    yourAds: List<CarAd>,
    onBackButtonClick: () -> Unit,
    onWriteUserClick: () -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAdAppBar(
                titleText = "Продавец",
                onBackButtonClick = onBackButtonClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model = "https://redbrickworks.com/wp-content/uploads/2021/02/business-man.png",
                        contentDescription = "Account image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(2.dp, containerColor, CircleShape)
                    )
                    Column(
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Text(
                            text = "Вася Лютый",
                            style = MaterialTheme.typography.displaySmall,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "г. Санкт-Петербург",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                CustomButton(
                    text = "Написать",
                    onClick = { onWriteUserClick() },
                    colorButton = buttonColors(
                        containerColor = Color.White
                    ),
                    textColor = Color.Black,
                    border = BorderStroke(4.dp, containerColor),
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(0.4f)
                )
            }
            Text(
                text = "Выставленные объявления",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
            HorizontalDivider()
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(yourAds) { ad ->
                    CarAdCard(
                        ad = ad,
                        onAdClick = onAddClick
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun AuthUserAccountScreenPreview() {
    AnotherAccountScreen(
        yourAds = CarAdMock.ads,
        onBackButtonClick = { },
        onAddClick = { },
        onWriteUserClick = { }
    )
}