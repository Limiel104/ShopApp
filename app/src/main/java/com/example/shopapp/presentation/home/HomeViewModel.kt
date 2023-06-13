package com.example.shopapp.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
): ViewModel() {

    private val _homeState = mutableStateOf(HomeState())
    val homeState: State<HomeState> = _homeState

    init {
        Log.i("TAG", "HomeViewModel")

        _homeState.value = homeState.value.copy(
            offerList = listOf(
                "All clothes for women now 10% cheaper",
                "All clothes for men now 15% cheaper",
                "All shirts 20% cheaper with code SHIRT20",
                "Buy two pairs of pants for the price of one",
                "13% off for purchase above 200$",
                "25% off for purchase above 500$"
            )
        )
    }
}