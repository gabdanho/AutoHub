package com.example.autohub.presentation.componets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.autohub.R
import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.presentation.theme.cardColor

@Composable
fun CarAdCard(
    ad: CarAd,
    onAdClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(bottomEnd = 15.dp, bottomStart = 15.dp),
    elevation: CardElevation = CardDefaults.cardElevation(4.dp),

) {
    Card(
        shape = shape,
        elevation = elevation,
        colors = CardDefaults.cardColors(cardColor),
        onClick = { onAdClick() },
        modifier = modifier
    ) {
        Column {
            AsyncImage(
                model = if (ad.imagesUrl.isNotEmpty()) ad.imagesUrl.first() else "",
                contentDescription = stringResource(id = R.string.content_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Text(
                text = stringResource(id = R.string.text_ad_price, ad.price),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = stringResource(
                    id = R.string.text_auto_description,
                    ad.brand,
                    ad.model,
                    ad.engineCapacity,
                    ad.realiseYear,
                    ad.mileage
                ),
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = stringResource(
                    id = R.string.text_city_and_date_published,
                    ad.city,
                    ad.dateAdPublished
                ),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}