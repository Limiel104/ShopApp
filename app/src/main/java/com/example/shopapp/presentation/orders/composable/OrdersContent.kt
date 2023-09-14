package com.example.shopapp.presentation.orders.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.shopapp.domain.model.Order
import com.example.shopapp.util.Constants.ORDERS_CONTENT
import com.example.shopapp.util.Constants.ORDERS_LAZY_COLUMN
import java.util.Date

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OrdersContent(
    scaffoldState: ScaffoldState,
    bottomBarHeight: Dp,
    orders: List<Order>
) {
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 10.dp)
            .padding(bottom = bottomBarHeight)
            .testTag(ORDERS_CONTENT)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .testTag(ORDERS_LAZY_COLUMN)
            ) {
                itemsIndexed(orders) { _, order ->
                    OrderItem(
                        order = order
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun OrdersContentPreview() {
    OrdersContent(
        scaffoldState = rememberScaffoldState(),
        bottomBarHeight = 56.dp,
        orders = listOf(
            Order(
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
            ),
            Order(
                orderId = "orderId2",
                userUID = "userUID",
                date = Date(),
                totalAmount = 54.00,
                products = mapOf(
                    Pair("2",1),
                    Pair("8",1),
                    Pair("9",3),
                    Pair("11",1)
                )
            ),
            Order(
                orderId = "orderId3",
                userUID = "userUID",
                date = Date(),
                totalAmount = 73.99,
                products = mapOf(
                    Pair("1",1),
                    Pair("4",1),
                    Pair("10",1)
                )
            )
        )
    )
}