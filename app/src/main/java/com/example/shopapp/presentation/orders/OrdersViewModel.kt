package com.example.shopapp.presentation.orders

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.domain.model.FirebaseOrder
import com.example.shopapp.domain.model.Order
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.domain.util.OrderOrder
import com.example.shopapp.util.Constants.ORDERS_VM
import com.example.shopapp.util.Constants.TAG
import com.example.shopapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val shopUseCases: ShopUseCases
): ViewModel() {

    private val _ordersState = mutableStateOf(OrdersState())
    val ordersState: State<OrdersState> = _ordersState

    private val _ordersEventChannel = Channel<OrdersUiEvent>()
    val ordersEventChannelFlow = _ordersEventChannel.receiveAsFlow()

    init {
        Log.i(TAG, ORDERS_VM)
        getCurrentUser()
    }

    fun onEvent(event: OrdersEvent) {
        when(event) {
            is OrdersEvent.OnOrderSelected -> {
                _ordersState.value = ordersState.value.copy(
                    orders = changeOrderExpandState(event.value)
                )
            }
            is OrdersEvent.OnOrderChange -> {
                sortOrders(event.value)
            }
            is OrdersEvent.ToggleSortSection -> {
                _ordersState.value = ordersState.value.copy(
                    isSortSectionVisible = !_ordersState.value.isSortSectionVisible
                )
            }
            is OrdersEvent.OnGoBack -> {
                viewModelScope.launch {
                    _ordersEventChannel.send(OrdersUiEvent.NavigateBack)
                }
            }
        }
    }

    fun getCurrentUser() {
        val userUID = shopUseCases.getCurrentUserUseCase()!!.uid
        _ordersState.value = ordersState.value.copy(
            userUID = userUID
        )
        getOrders(userUID)
    }

    fun getOrders(userUID: String) {
        viewModelScope.launch {
            shopUseCases.getUserOrdersUseCase(userUID).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading orders: ${response.isLoading}")
                        _ordersState.value = ordersState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        response.data?.let { firebaseOrders ->
                            Log.i(TAG, "Orders: $firebaseOrders")
                            if(firebaseOrders.isNotEmpty()) {
                                _ordersState.value = _ordersState.value.copy(
                                    firebaseOrders = firebaseOrders
                                )
                                getProducts()
                            }
                        }
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _ordersEventChannel.send(OrdersUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun getProducts() {
        viewModelScope.launch {
            shopUseCases.getProductsUseCase("all").collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading products: ${response.isLoading}")
                        _ordersState.value = ordersState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        response.data?.let { products ->
                            Log.i(TAG, "Products: $products")
                            if(products.isNotEmpty()) {
                                _ordersState.value = ordersState.value.copy(
                                    products = products
                                )
                                setOrdersProducts()
                                sortOrders(_ordersState.value.orderOrder)
                            }
                        }
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _ordersEventChannel.send(OrdersUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun setOrdersProducts(
        firebaseOrders: List<FirebaseOrder> = _ordersState.value.firebaseOrders,
        products: List<Product> = _ordersState.value.products
    ) {
        _ordersState.value = ordersState.value.copy(
            orders = shopUseCases.setOrdersUseCase(firebaseOrders,products)
        )
    }

    fun changeOrderExpandState(orderId: String): List<Order> {
        val newOrders: MutableList<Order> = mutableListOf()
        val orders = _ordersState.value.orders

        for(order in orders) {
            if(order.orderId == orderId) {
                val newOrder = Order(
                    orderId = order.orderId,
                    date = order.date,
                    totalAmount = order.totalAmount,
                    products = order.products,
                    isExpanded = !order.isExpanded
                )
                newOrders.add(newOrder)
            }
            else {
                newOrders.add(order)
            }
        }
        return newOrders
    }

    fun sortOrders(orderOrder: OrderOrder) {
        _ordersState.value = ordersState.value.copy(
            orderOrder = orderOrder
        )

        _ordersState.value = ordersState.value.copy(
            orders = shopUseCases.sortOrdersUseCase(
                orderOrder = orderOrder,
                orders = _ordersState.value.orders
            )
        )
    }
}