package com.example.shopapp.presentation.category

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
class CategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _categoryState = mutableStateOf(CategoryState())
    val categoryState: State<CategoryState> = _categoryState

    private val _eventFlow = MutableSharedFlow<CategoryUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        Log.i("TAG","CategoryViewModel")
        val productList = listOf(
            "men's clothing 1",
            "men's clothing 2",
            "women's clothing 1",
            "jewelery 1",
            "men's clothing 3",
            "women's clothing 2",
            "jewelery 2",
            "women's clothing 3"
        )

        savedStateHandle.get<String>("categoryId")?.let { categoryId ->
            val productsFromCategoryList = productList.filter { product ->
                product.contains(categoryId)
            }
            _categoryState.value = categoryState.value.copy(
                categoryId = categoryId,
                productList = productsFromCategoryList
            )
        }
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
            CategoryEvent.ToggleSortSection -> {
                _categoryState.value = categoryState.value.copy(
                    isSortSectionVisible = !_categoryState.value.isSortSectionVisible
                )
            }
        }
    }
}