package com.example.shopapp.presentation.favourites

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.util.Constants.FAVOURITE_VM
import com.example.shopapp.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
): ViewModel() {

    private val _favouriteState = mutableStateOf(FavouriteState())
    val favouriteState: State<FavouriteState> = _favouriteState

    private val _eventFlow = MutableSharedFlow<FavouriteUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        Log.i(TAG, FAVOURITE_VM)

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

        _favouriteState.value = favouriteState.value.copy(
            productList = productList
        )
    }

    fun onEvent(event: FavouriteEvent) {
        when(event) {
            is FavouriteEvent.OnProductSelected -> {
                viewModelScope.launch {
                    _favouriteState.value = favouriteState.value.copy(
                        productId = event.value
                    )
                    _eventFlow.emit(FavouriteUiEvent.NavigateToProductDetails(event.value))
                }
            }
            is FavouriteEvent.OnLogin -> {
                viewModelScope.launch {
                    _eventFlow.emit(FavouriteUiEvent.NavigateToLogin)
                }

            }
            is FavouriteEvent.OnSignup -> {
                viewModelScope.launch {
                    _eventFlow.emit(FavouriteUiEvent.NavigateToSignup)
                }
            }
        }
    }
}