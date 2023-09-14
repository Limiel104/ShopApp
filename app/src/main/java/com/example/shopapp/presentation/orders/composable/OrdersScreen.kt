package com.example.shopapp.presentation.orders.composable

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
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

    OrdersContent(
        scaffoldState = scaffoldState,
        bottomBarHeight = bottomBarHeight,
        orders = orders
    )
}