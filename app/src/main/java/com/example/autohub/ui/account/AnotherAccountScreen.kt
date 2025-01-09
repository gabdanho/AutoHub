package com.example.autohub.ui.account

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.autohub.data.model.ad.CarAd
import com.example.autohub.data.model.user.User
import com.example.autohub.ui.componets.CarAdCard
import com.example.autohub.ui.componets.CustomButton
import com.example.autohub.ui.componets.TopAdAppBar
import com.example.autohub.ui.theme.containerColor

@Composable
fun AnotherAccountScreen(
    modifier: Modifier = Modifier,
    user: User,
    ads: List<CarAd>,
    onCallClick: () -> Unit,
    onBackButtonClick: () -> Unit,
    onWriteClick: () -> Unit,
    onAddClick: (CarAd) -> Unit
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
                        model = user.image,
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
                            text = "${user.firstName} ${user.secondName}",
                            style = MaterialTheme.typography.displaySmall,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = user.city,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                CustomButton(
                    text = "Написать",
                    onClick = { onWriteClick() },
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .weight(1f)
                )
                CustomButton(
                    text = "Позвонить",
                    onClick = { onCallClick() },
                    modifier = Modifier.weight(1f)
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
                items(ads) { ad ->
                    CarAdCard(
                        ad = ad,
                        onAdClick = { onAddClick(ad) }
                    )
                }
            }
        }
    }
}

//@Preview
//@Composable
//private fun AuthUserAccountScreenPreview() {
//    AnotherAccountScreen(
//        user = User(),
//        ads = listOf(),
//        onBackButtonClick = { },
//        onAddClick = { },
//        onWriteClick = { },
//        onCallClick = { }
//    )
//}