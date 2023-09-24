package com.example.shopapp.presentation.orders.composable

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopapp.presentation.orders.OrdersEvent
import com.example.shopapp.presentation.orders.OrdersViewModel

@Composable
fun OrdersScreen(
    navController: NavController,
    bottomBarHeight: Dp,
    viewModel: OrdersViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val orders = viewModel.ordersState.value.orders
    val isLoading = viewModel.ordersState.value.isLoading

    OrdersContent(
        scaffoldState = scaffoldState,
        bottomBarHeight = bottomBarHeight,
        orders = orders,
        isLoading = isLoading,
        onOrderSelected = { viewModel.onEvent(OrdersEvent.OnOrderSelected(it)) }
    )
}