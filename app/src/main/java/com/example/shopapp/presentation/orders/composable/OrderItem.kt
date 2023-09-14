package com.example.shopapp.presentation.orders.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.domain.model.Order
import java.util.Date

@Composable
fun OrderItem(
    order: Order,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(bottom = 15.dp)
            .testTag(order.orderId),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = order.date.toString(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = order.totalAmount.toString()
        )
    }
}

@Preview
@Composable
fun OrderItemPreview() {
    OrderItem(
        order = Order(
            orderId = "orderId1",
            userUID = "userUID",
            date = Date(),
            totalAmount = 123.43,
            products = mapOf(
                Pair("3",5),
                Pair("6",1),
                Pair("7",1),
                Pair("9",1),
                Pair("12",2)
            )
        )
    )
}