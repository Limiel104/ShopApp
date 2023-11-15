package com.example.shopapp.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.R
import com.example.shopapp.domain.model.Banner
import com.example.shopapp.util.Constants.HOME_VM
import com.example.shopapp.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
): ViewModel() {

    private val _homeState = mutableStateOf(HomeState())
    val homeState: State<HomeState> = _homeState

    private val _homeEventChannel = Channel<HomeUiEvent>()
    val homeEventChannelFlow = _homeEventChannel.receiveAsFlow()

    init {
        Log.i(TAG, HOME_VM)

        _homeState.value = homeState.value.copy(
            bannerList = listOf(
                Banner(
                    categoryId = "women's clothing",
                    resourceId = R.drawable.womans_clothing_banner
                ),
                Banner(
                    categoryId = "men's clothing",
                    resourceId = R.drawable.mens_clothing_banner
                ),
                Banner(
                    categoryId = "jewelery",
                    resourceId = R.drawable.jewelery_banner
                ),
                Banner(
                    categoryId = "electronics",
                    resourceId = R.drawable.electronics_banner
                )
            )
        )
    }

    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.OnBannerSelected -> {
                viewModelScope.launch {
                    _homeEventChannel.send(HomeUiEvent.NavigateToCategory(event.value))
                }
            }
            is HomeEvent.GoToCart -> {
                viewModelScope.launch {
                    _homeEventChannel.send(HomeUiEvent.NavigateToCart)
                }
            }
        }
    }
}