package com.example.shopapp.presentation.favourites.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.productName
import com.example.shopapp.util.Constants.productPrice

@Composable
fun ProductItemTitle(
    name: String,
    price: String
) {
    Column(
        modifier = Modifier
            .width(180.dp)
            .background(MaterialTheme.colors.background)
            .padding(5.dp)
    ) {

        Text(
            text = name,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = price,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview
@Composable
fun ProductItemTitlePreview() {
    ShopAppTheme {
        ProductItemTitle(
            name = productName,
            price = productPrice
        )
    }
}