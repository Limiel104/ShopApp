package com.example.shopapp.presentation.product_details.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp.R
import com.example.shopapp.domain.model.Product
import com.example.shopapp.presentation.common.composable.IconButtonCard
import com.example.shopapp.presentation.common.composable.ShopButtonItem
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.FAVOURITE_BTN
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_ADD_TO_CART_BTN
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_BOTTOM_SHEET
import com.example.shopapp.util.Constants.productDescription

@Composable
fun ProductDetailsBottomSheet(
    product: Product,
    isProductInFavourites: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(15.dp)
            .testTag(PRODUCT_DETAILS_BOTTOM_SHEET)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                modifier = Modifier
                    .width(50.dp),
                thickness = 4.dp,
                color = Color.Gray
            )
        }

        Text(
            text = product.title,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = product.price,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )

            IconButtonCard(
                icon = if(isProductInFavourites) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                description = FAVOURITE_BTN,
                color = if(isProductInFavourites) Color.Red else Color.Gray,
                onClick = {}
            )
        }

        Text(
            text = stringResource(id = R.string.description),
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 5.dp)
        )

        Text(
            text = product.description,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(top = 5.dp)
                .padding(bottom = 15.dp)
        )

        ShopButtonItem(
            text = stringResource(id = R.string.add_to_cart),
            testTag = PRODUCT_DETAILS_ADD_TO_CART_BTN,
            onClick = {}
        )
    }
}

@Preview
@Composable
fun ProductDetailsBottomSheetPreview() {
    ShopAppTheme {
        val product = Product(
            id = 1,
            title = "Shirt",
            price = "195,59 PLN",
            description = productDescription,
            category = "men's clothing",
            imageUrl = "",
            isInFavourites = true
        )

        ProductDetailsBottomSheet(
            product = product,
            isProductInFavourites = true
        )
    }
}

@Preview
@Composable
fun ProductDetailsBottomSheetFalsePreview() {
    ShopAppTheme {
        val product = Product(
            id = 1,
            title = "Shirt",
            price = "195,59 PLN",
            description = productDescription,
            category = "men's clothing",
            imageUrl = "",
            isInFavourites = false
        )

        ProductDetailsBottomSheet(
            product = product,
            isProductInFavourites = false
        )
    }
}