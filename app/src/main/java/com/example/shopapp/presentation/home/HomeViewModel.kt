package com.example.shopapp.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.domain.model.Offer
import com.example.shopapp.util.Constants.HOME_VM
import com.example.shopapp.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
): ViewModel() {

    private val _homeState = mutableStateOf(HomeState())
    val homeState: State<HomeState> = _homeState

    private val _eventFlow = MutableSharedFlow<HomeUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        Log.i(TAG, HOME_VM)

        _homeState.value = homeState.value.copy(
            offerList = listOf(
                Offer(
                    categoryId = "women's clothing",
                    discountPercent = 10,
                    description = "All clothes for women now 10% cheaper"
                ),
                Offer(
                    categoryId = "men's clothing",
                    discountPercent = 15,
                    description = "All clothes for men now 15% cheaper"
                ),
                Offer(
                    categoryId = "women's clothing",
                    discountPercent = 20,
                    description = "All shirts for women 20% cheaper with code SHIRT20"
                ),
                Offer(
                    categoryId = "jewelery",
                    discountPercent = 50,
                    description = "Buy two pieces of jewelery for the price of one"
                ),
                Offer(
                    discountPercent = 13,
                    description = "13% off for purchase above 200\$"
                ),
                Offer(
                    discountPercent = 10,
                    description = "25% off for purchase above 500\$"
                )
            )
        )
    }

    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.OnOfferSelected -> {
                viewModelScope.launch {
                    _eventFlow.emit(HomeUiEvent.NavigateToCategory(event.value))
                }
            }
            is HomeEvent.GoToCart -> {
                viewModelScope.launch {
                    _eventFlow.emit(HomeUiEvent.NavigateToCart)
                }
            }
        }
    }
}