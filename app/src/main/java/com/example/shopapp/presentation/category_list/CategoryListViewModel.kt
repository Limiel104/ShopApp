package com.example.shopapp.presentation.category_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryListViewModel @Inject constructor(

): ViewModel() {

    private val _categoryListState = mutableStateOf(CategoryListState())
    val categoryListState: State<CategoryListState> = _categoryListState

    private val _eventFlow = MutableSharedFlow<CategoryListUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        Log.i("TAG","CategoryListViewModel")
    }

    fun onEvent(event: CategoryListEvent) {
        when(event) {
            is CategoryListEvent.OnCategorySelected -> {
                viewModelScope.launch {
                    _categoryListState.value = categoryListState.value.copy(
                        categoryId = event.value
                    )
                    Log.i("TAG",event.value)
                    _eventFlow.emit(CategoryListUiEvent.NavigateToCategory(event.value))
                }
            }
        }
    }
}