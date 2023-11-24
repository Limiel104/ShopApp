package com.example.shopapp.presentation.orders.composable

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.domain.model.Order
import com.example.shopapp.domain.util.OrderOrder
import com.example.shopapp.presentation.common.Constants.ORDERS_CONTENT
import com.example.shopapp.presentation.common.Constants.ORDERS_CPI
import com.example.shopapp.presentation.common.Constants.ORDERS_LAZY_COLUMN
import java.util.Date

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OrdersContent(
    orders: List<Order>,
    isLoading: Boolean,
    orderOrder: OrderOrder,
    isSortSectionVisible: Boolean,
    onOrderSelected: (String) -> Unit,
    onOrderChange: (OrderOrder) -> Unit,
    onSortSelected: () -> Unit,
    onGoBack: () -> Unit
) {
    Scaffold(
        topBar = {
            OrdersTopBar(
                onSortSelected = { onSortSelected() },
                onNavigateBack = { onGoBack() }
            ) },
        modifier = Modifier
            .fillMaxSize()
            .testTag(ORDERS_CONTENT)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 10.dp)
        ) {
            AnimatedVisibility(
                visible = isSortSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrdersSortSection(
                    orderOrder = orderOrder,
                    onOrderChange = { onOrderChange(it) }
                )
            }

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

    if(isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .testTag(ORDERS_CPI),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

fun getOrders(): List<Order> {
    return listOf(
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
    )
}

@Preview
@Composable
fun OrdersContentPreview() {
    OrdersContent(
        orders = getOrders(),
        isLoading = false,
        orderOrder = OrderOrder.DateDescending(),
        isSortSectionVisible = false,
        onOrderSelected = {},
        onOrderChange = {},
        onSortSelected = {},
        onGoBack = {}
    )
}

@Preview
@Composable
fun OrdersContentPreviewSortSectionVisible() {
    OrdersContent(
        orders = getOrders(),
        isLoading = false,
        orderOrder = OrderOrder.DateDescending(),
        isSortSectionVisible = true,
        onOrderSelected = {},
        onOrderChange = {},
        onSortSelected = {},
        onGoBack = {}
    )
}

@Preview
@Composable
fun OrdersContentCPIPreview() {
    OrdersContent(
        orders = getOrders(),
        isLoading = true,
        orderOrder = OrderOrder.DateDescending(),
        isSortSectionVisible = false,
        onOrderSelected = {},
        onOrderChange = {},
        onSortSelected = {},
        onGoBack = {}
    )
}