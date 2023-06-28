package com.example.shopapp.presentation.product_details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_VM
import com.example.shopapp.util.Constants.TAG
import com.example.shopapp.util.Constants.productId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val shopUseCases: ShopUseCases
): ViewModel() {

    private val _productDetailsState = mutableStateOf(ProductDetailsState())
    val productDetailsState: State<ProductDetailsState> = _productDetailsState

    private val _eventFlow = MutableSharedFlow<ProductDetailsUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        Log.i(TAG, PRODUCT_DETAILS_VM)

        savedStateHandle.get<Int>(productId)?.let { productId ->
            _productDetailsState.value = productDetailsState.value.copy(
                productId = productId
            )
        }

        getProduct(_productDetailsState.value.productId)
    }

    fun onEvent(event: ProductDetailsEvent) {
        when(event) {
            ProductDetailsEvent.GoBack -> {
                viewModelScope.launch {
                    _eventFlow.emit(ProductDetailsUiEvent.NavigateBack)
                }
            }
        }
    }

    private fun getProduct(productId: Int) {
        viewModelScope.launch {
            shopUseCases.getProductUseCase(productId).collect { product ->
                _productDetailsState.value = productDetailsState.value.copy(
                    title = product.title,
                    price = product.price,
                    description = product.description,
                    category = product.category,
                    imageUrl = product.imageUrl
                )
            }
        }
    }
}