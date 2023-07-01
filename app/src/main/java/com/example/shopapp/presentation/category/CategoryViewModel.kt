package com.example.shopapp.presentation.category

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.Constants.CATEGORY_VM
import com.example.shopapp.util.Constants.TAG
import com.example.shopapp.util.Constants.all
import com.example.shopapp.util.Constants.categoryId
import com.example.shopapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val shopUseCases: ShopUseCases
): ViewModel() {

    private val _categoryState = mutableStateOf(CategoryState())
    val categoryState: State<CategoryState> = _categoryState

    private val _eventFlow = MutableSharedFlow<CategoryUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        Log.i(TAG, CATEGORY_VM)

        savedStateHandle.get<String>(categoryId)?.let { categoryId ->
            _categoryState.value = categoryState.value.copy(
                categoryId = categoryId
            )
        }

        getProducts(_categoryState.value.categoryId)
    }

    fun onEvent(event: CategoryEvent) {
        when(event) {
            is CategoryEvent.OnProductSelected -> {
                viewModelScope.launch {
                    _categoryState.value = categoryState.value.copy(
                        productId = event.value
                    )
                    _eventFlow.emit(CategoryUiEvent.NavigateToProductDetails(event.value))
                }
            }
            is CategoryEvent.ToggleSortSection -> {
                _categoryState.value = categoryState.value.copy(
                    isSortSectionVisible = !_categoryState.value.isSortSectionVisible
                )
            }
        }
    }

    fun getProducts(categoryId: String) {
        if(categoryId == all) getAllProducts()
        else getProductsFromCategory(categoryId)
    }

    fun getAllProducts() {
        viewModelScope.launch {
            shopUseCases.getProductsUseCase().collect { response ->
                when(response) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        response.data?.let { products ->
                            _categoryState.value = categoryState.value.copy(
                                productList = products
                            )
                        }
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                    }
                }
            }
        }
    }

    fun getProductsFromCategory(categoryId: String) {
        viewModelScope.launch {
            shopUseCases.getProductsFromCategory(categoryId).collect { response ->
                when(response) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        response.data?.let { products ->
                            _categoryState.value = categoryState.value.copy(
                                productList = products
                            )
                        }
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                    }
                }
            }
        }
    }
}