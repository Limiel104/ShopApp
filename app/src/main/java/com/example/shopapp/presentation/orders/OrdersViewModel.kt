package com.example.shopapp.presentation.orders

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.domain.model.FirebaseOrder
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.Category
import com.example.shopapp.util.Constants.ORDERS_VM
import com.example.shopapp.util.Constants.TAG
import com.example.shopapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val shopUseCases: ShopUseCases
): ViewModel() {

    private val _ordersState = mutableStateOf(OrdersState())
    val ordersState: State<OrdersState> = _ordersState

    private val _eventFlow = MutableSharedFlow<OrdersUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        Log.i(TAG, ORDERS_VM)
        getCurrentUser()
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
                        _eventFlow.emit(OrdersUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun getProducts() {
        viewModelScope.launch {
            shopUseCases.getProductsUseCase(Category.All.id).collect { response ->
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
                            }
                        }
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _eventFlow.emit(OrdersUiEvent.ShowErrorMessage(response.message.toString()))
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
}