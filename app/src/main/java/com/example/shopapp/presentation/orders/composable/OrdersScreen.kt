package com.example.shopapp.presentation.orders.composable

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.example.shopapp.presentation.orders.OrdersEvent
import com.example.shopapp.presentation.orders.OrdersUiEvent
import com.example.shopapp.presentation.orders.OrdersViewModel
import com.example.shopapp.presentation.common.Constants.ORDERS_SCREEN_LE
import com.example.shopapp.presentation.common.Constants.TAG
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OrdersScreen(
    navController: NavController,
    viewModel: OrdersViewModel = hiltViewModel()
) {
    val orders = viewModel.ordersState.value.orders
    val isLoading = viewModel.ordersState.value.isLoading
    val orderOrder = viewModel.ordersState.value.orderOrder
    val isSortSectionVisible = viewModel.ordersState.value.isSortSectionVisible
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.ordersEventChannelFlow.collectLatest { event ->
                Log.i(TAG, ORDERS_SCREEN_LE)
                when(event) {
                    is OrdersUiEvent.ShowErrorMessage -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                    }
                    is OrdersUiEvent.NavigateBack -> {
                        navController.popBackStack()
                    }
                }
            }
        }
    }

    OrdersContent(
        orders = orders,
        isLoading = isLoading,
        orderOrder = orderOrder,
        isSortSectionVisible = isSortSectionVisible,
        onOrderSelected = { viewModel.onEvent(OrdersEvent.OnOrderSelected(it)) },
        onOrderChange = { viewModel.onEvent(OrdersEvent.OnOrderChange(it)) },
        onSortSelected = { viewModel.onEvent(OrdersEvent.ToggleSortSection) },
        onGoBack = { viewModel.onEvent(OrdersEvent.OnGoBack) }
    )
}