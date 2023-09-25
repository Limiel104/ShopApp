package com.example.shopapp.presentation.orders.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.presentation.common.composable.ImageItem
import com.example.shopapp.presentation.common.format.priceToString
import com.example.shopapp.util.Constants.cartProductItemImageHeight
import com.example.shopapp.util.Constants.cartProductItemImageWidth

@Composable
fun OrderProductItem(
    product: CartProduct,
    orderId: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .height(IntrinsicSize.Max)
            .padding(bottom = 15.dp)
            .testTag(orderId + product.title)
    ) {
        ImageItem(
            imageUrl = product.imageUrl,
            width = cartProductItemImageWidth,
            height = cartProductItemImageHeight,
            onClick = {}
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 10.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = product.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = product.priceToString()
            )

            Text(
                text = "Amount: ${product.amount}"
            )
        }
    }
}

@Preview
@Composable
fun OrderProductItemPreview() {
    OrderProductItem(
        product = CartProduct(
            id = 2,
            title = "title 2",
            price = 53.34,
            imageUrl = "",
            amount = 2
        ),
        orderId = "orderId1"
    )
}