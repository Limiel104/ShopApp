package com.example.shopapp.presentation.favourites.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.PRODUCT_ITEM_TITLE

@Composable
fun ProductItemTitle(
    name: String,
    price: String
) {
    Column(
        modifier = Modifier
            .width(180.dp)
            .padding(5.dp)
            .testTag("$PRODUCT_ITEM_TITLE $name")
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
    Surface() {
        ShopAppTheme {
            ProductItemTitle(
                name = "Shirt",
                price = "195,59 PLN"
            )
        }
    }
}