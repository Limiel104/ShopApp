package com.example.shopapp.presentation.favourites

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.Constants.FAVOURITE_VM
import com.example.shopapp.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val shopUseCases: ShopUseCases
): ViewModel() {

    private val _favouritesState = mutableStateOf(FavouritesState())
    val favouritesState: State<FavouritesState> = _favouritesState

    private val _eventFlow = MutableSharedFlow<FavouritesUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        Log.i(TAG, FAVOURITE_VM)

        checkIfUserIsLoggedIn()

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

        _favouritesState.value = favouritesState.value.copy(
            productList = productList
        )
    }

    fun onEvent(event: FavouritesEvent) {
        when(event) {
            is FavouritesEvent.OnProductSelected -> {
                viewModelScope.launch {
                    _favouritesState.value = favouritesState.value.copy(
                        productId = event.value
                    )
                    _eventFlow.emit(FavouritesUiEvent.NavigateToProductDetails(event.value))
                }
            }
            is FavouritesEvent.OnLogin -> {
                viewModelScope.launch {
                    _eventFlow.emit(FavouritesUiEvent.NavigateToLogin)
                }

            }
            is FavouritesEvent.OnSignup -> {
                viewModelScope.launch {
                    _eventFlow.emit(FavouritesUiEvent.NavigateToSignup)
                }
            }
        }
    }

    fun checkIfUserIsLoggedIn() {
        viewModelScope.launch {
            val currentUser = shopUseCases.getCurrentUserUseCase()
            _favouritesState.value = favouritesState.value.copy(
                    isUserLoggedIn = currentUser != null
                )
        }
    }
}