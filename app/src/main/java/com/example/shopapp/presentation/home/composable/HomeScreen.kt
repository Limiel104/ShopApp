package com.example.shopapp.presentation.home.composable

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.example.shopapp.presentation.home.HomeEvent
import com.example.shopapp.presentation.home.HomeUiEvent
import com.example.shopapp.presentation.home.HomeViewModel
import com.example.shopapp.presentation.common.Constants.HOME_SCREEN_LE
import com.example.shopapp.presentation.common.Constants.TAG
import com.example.shopapp.presentation.navigation.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val bannerList = viewModel.homeState.value.bannerList
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.homeEventChannelFlow.collectLatest { event ->
                Log.i(TAG, HOME_SCREEN_LE)
                when(event) {
                    is HomeUiEvent.NavigateToCategory -> {
                        navController.navigate(Screen.CategoryScreen.route + "categoryId=" + event.categoryId)
                    }
                    is HomeUiEvent.NavigateToCart -> {
                        navController.navigate(Screen.CartScreen.route)
                    }
                }
            }
        }
    }

    HomeContent(
        bannerList = bannerList,
        onOfferSelected = { viewModel.onEvent(HomeEvent.OnBannerSelected(it)) },
        onGoToCart = { viewModel.onEvent(HomeEvent.GoToCart) }
    )
}
