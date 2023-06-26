package com.example.shopapp.presentation.category

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.domain.repository.ProductRepository
import com.example.shopapp.util.Constants.CATEGORY_VM
import com.example.shopapp.util.Constants.TAG
import com.example.shopapp.util.Constants.categoryId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: ProductRepository
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

        getProducts()
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

    fun getProducts() {
        viewModelScope.launch {
            _categoryState.value = categoryState.value.copy(
                productList = repository.getProducts()
            )
        }
    }
}