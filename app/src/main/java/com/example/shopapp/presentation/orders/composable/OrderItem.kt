package com.example.shopapp.presentation.orders.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.domain.model.Order
import com.example.shopapp.presentation.common.format.totalAmountToString
import com.example.shopapp.util.Constants.EXPAND_OR_COLLAPSE_BTN
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
@Composable
fun OrderItem(
    order: Order,
    onOrderSelected: (String) -> Unit
) {
    Column() {
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
                text = order.totalAmountToString()
            )

            IconButton(
                onClick = { onOrderSelected(order.orderId) }
            ) {
                Icon(
                    imageVector = if(order.isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = EXPAND_OR_COLLAPSE_BTN
                )
            }
        }

        if(order.isExpanded) {
            LazyColumn(
                modifier = Modifier
                    .height((order.products.size * 100).dp)
                    .padding(start = 10.dp)
            ) {
                itemsIndexed(order.products) { _, product ->
                  OrderProductItem(
                      product = product,
                      orderId = order.orderId
                  )
                }
            }
        }
    }
}

@Preview
@Composable
fun OrderItemPreviewNotExpanded() {
    Surface() {
        OrderItem(
            order = Order(
                orderId = "orderId1",
                date = Date(),
                totalAmount = 123.43,
                products = emptyList(),
                isExpanded = false
            ),
            onOrderSelected = {}
        )
    }
}

@Preview
@Composable
fun OrderItemPreviewExpanded() {
    Surface() {
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
                ),
                isExpanded = true
            ),
            onOrderSelected = {}
        )
    }
}