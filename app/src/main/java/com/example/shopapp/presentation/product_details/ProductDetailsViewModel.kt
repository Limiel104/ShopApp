package com.example.shopapp.presentation.product_details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _productDetailsState = mutableStateOf(ProductDetailsState())
    val productDetailsState: State<ProductDetailsState> = _productDetailsState

    private val _eventFlow = MutableSharedFlow<ProductDetailsUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        Log.i("TAG","ProductDetailViewModel")

        savedStateHandle.get<String>("productId")?.let { procustId ->
            _productDetailsState.value = productDetailsState.value.copy(
                name = procustId
            )
        }
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
}