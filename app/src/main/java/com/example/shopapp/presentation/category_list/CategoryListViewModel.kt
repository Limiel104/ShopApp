package com.example.shopapp.presentation.category_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.Constants.CATEGORY_LIST_VM
import com.example.shopapp.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryListViewModel @Inject constructor(
    private val shopUseCases: ShopUseCases
): ViewModel() {

    private val _categoryListState = mutableStateOf(CategoryListState())
    val categoryListState: State<CategoryListState> = _categoryListState

    private val _eventFlow = MutableSharedFlow<CategoryListUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        Log.i(TAG, CATEGORY_LIST_VM)
        getCategories()
    }

    fun onEvent(event: CategoryListEvent) {
        when(event) {
            is CategoryListEvent.OnCategorySelected -> {
                viewModelScope.launch {
                    _categoryListState.value = categoryListState.value.copy(
                        categoryId = event.value
                    )
                    _eventFlow.emit(CategoryListUiEvent.NavigateToCategory(event.value))
                }
            }
            is CategoryListEvent.GoToCart -> {
                viewModelScope.launch {
                    _eventFlow.emit(CategoryListUiEvent.NavigateToCart)
                }
            }
        }
    }

    fun getCategories() {
        _categoryListState.value = categoryListState.value.copy(
            categoryList = shopUseCases.getCategoriesUseCase()
        )
    }
}