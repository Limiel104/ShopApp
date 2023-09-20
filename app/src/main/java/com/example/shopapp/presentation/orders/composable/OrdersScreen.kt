package com.example.shopapp.presentation.orders.composable

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.domain.model.Order
import java.util.Date

@Composable
fun OrdersScreen(
    navController: NavController,
    bottomBarHeight: Dp,
) {
    val scaffoldState = rememberScaffoldState()
    val orders = listOf(
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
            )
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
            )
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
            )
        )
    )

    OrdersContent(
        scaffoldState = scaffoldState,
        bottomBarHeight = bottomBarHeight,
        orders = orders
    )
}