package com.example.shopapp.presentation.orders.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.domain.model.Order
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
@Composable
fun OrderItem(
    order: Order,
    isExpanded: Boolean
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
                .testTag(order.orderId),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = SimpleDateFormat("dd/MM/yyyy HH:mm").format(order.date),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "${order.totalAmount} PLN".replace(".", ",")
            )

            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "expand"
                )
            }
        }

        if (isExpanded) {
            LazyColumn(
                modifier = Modifier
                    .height(100.dp)
                    .padding(start = 10.dp)
            ) {
                itemsIndexed(order.products) { _, product ->
                  OrderProductItem(
                      product = product
                  )
                }
            }
        }
    }
}

@Preview
@Composable
fun OrderItemPreviewNotExpanded() {
    OrderItem(
        order = Order(
            orderId = "orderId1",
            date = Date(),
            totalAmount = 123.43,
            products = emptyList()
        ),
        isExpanded = false
    )
}

@Preview
@Composable
fun OrderItemPreviewExpanded() {
    OrderItem(
        order = Order(
            orderId = "orderId2",
            date = Date(),
            totalAmount = 54.00,
            products = listOf(
                CartProduct(
                    id = 2,
                    title = "title 2",
                    price = 53.34,
                    imageUrl = "",
                    amount = 2
                ),
                CartProduct(
                    id = 3,
                    title = "title 3",
                    price = 56.00,
                    imageUrl = "",
                    amount = 1
                ),
                CartProduct(
                    id = 4,
                    title = "title 4",
                    price = 23.00,
                    imageUrl = "",
                    amount = 1
                )
            )
        ),
        isExpanded = true
    )
}