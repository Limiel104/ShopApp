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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.domain.model.Order
import com.example.shopapp.util.Constants.ORDERS_CONTENT
import com.example.shopapp.util.Constants.ORDERS_LAZY_COLUMN
import java.util.Date

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OrdersContent(
    scaffoldState: ScaffoldState,
    bottomBarHeight: Dp,
    orders: List<Order>,
    onOrderSelected: (String) -> Unit
) {
    Scaffold(
        topBar = { OrdersTopBar() },
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp, bottom = bottomBarHeight)
            .testTag(ORDERS_CONTENT)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .testTag(ORDERS_LAZY_COLUMN)
            ) {
                itemsIndexed(orders) { _, order ->
                    OrderItem(
                        order = order,
                        onOrderSelected = { onOrderSelected(it) }
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
                date = Date(),
                totalAmount = 123.43,
                products = listOf(
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
            Order(
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
            Order(
                orderId = "orderId3",
                date = Date(),
                totalAmount = 73.99,
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
                    )
                ),
                isExpanded = false
            )
        ),
        onOrderSelected = {}
    )
}