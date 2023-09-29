package com.example.shopapp.presentation.orders.composable

import android.util.Log
import android.widget.Toast
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shopapp.presentation.orders.OrdersEvent
import com.example.shopapp.presentation.orders.OrdersUiEvent
import com.example.shopapp.presentation.orders.OrdersViewModel
import com.example.shopapp.util.Constants.ORDERS_SCREEN_LE
import com.example.shopapp.util.Constants.TAG
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OrdersScreen(
    bottomBarHeight: Dp,
    viewModel: OrdersViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val orders = viewModel.ordersState.value.orders
    val isLoading = viewModel.ordersState.value.isLoading
    val orderOrder = viewModel.ordersState.value.orderOrder
    val isSortSectionVisible = viewModel.ordersState.value.isSortSectionVisible
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            Log.i(TAG, ORDERS_SCREEN_LE)
            when(event) {
                is OrdersUiEvent.ShowErrorMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    OrdersContent(
        scaffoldState = scaffoldState,
        bottomBarHeight = bottomBarHeight,
        orders = orders,
        isLoading = isLoading,
        orderOrder = orderOrder,
        isSortSectionVisible = isSortSectionVisible,
        onOrderSelected = { viewModel.onEvent(OrdersEvent.OnOrderSelected(it)) },
        onOrderChange = { viewModel.onEvent(OrdersEvent.OnOrderChange(it)) },
        onSortSelected = { viewModel.onEvent(OrdersEvent.ToggleSortSection) }
    )
}