package com.example.shopapp.presentation.category_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.util.Constants.CATEGORY_LIST_VM
import com.example.shopapp.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryListViewModel @Inject constructor(
): ViewModel() {

    private val _categoryListState = mutableStateOf(CategoryListState())
    val categoryListState: State<CategoryListState> = _categoryListState

    private val _categoryListEventChannel = Channel<CategoryListUiEvent>()
    val categoryListEventChannelFlow = _categoryListEventChannel.receiveAsFlow()

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
                    _categoryListEventChannel.send(CategoryListUiEvent.NavigateToCategory(event.value))
                }
            }
            is CategoryListEvent.GoToCart -> {
                viewModelScope.launch {
                    _categoryListEventChannel.send(CategoryListUiEvent.NavigateToCart)
                }
            }
        }
    }

    fun getCategories() {
        _categoryListState.value = categoryListState.value.copy(
            categoryList = listOf(
                "All",
                "Men's clothing",
                "Women's clothing",
                "Jewelery",
                "Electronics"
            )
        )
    }
}