package com.example.autohub.ui.componets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.autohub.data.model.ad.CarAd
import com.example.autohub.ui.theme.cardColor

@Composable
fun CarAdCard(
    ad: CarAd,
    onAdClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(bottomEnd = 15.dp, bottomStart = 15.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(cardColor),
        onClick = { onAdClick() },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            AsyncImage(
                model = if (ad.imagesUrl.isNotEmpty()) ad.imagesUrl.first() else "",
                contentDescription = "Фотография",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Text(
                text = "${ad.price} Р.",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "${ad.brand} ${ad.model} ${ad.engineCapacity}, ${ad.realiseYear}, ${ad.mileage} км",
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "${ad.city}\n${ad.dateAdPublished}",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}