package com.example.shopapp.presentation.favourite.composable

import android.util.Log
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopapp.presentation.common.composable.UserNotLoggedInContent
import com.example.shopapp.presentation.favourite.FavouriteEvent
import com.example.shopapp.presentation.favourite.FavouriteUiEvent
import com.example.shopapp.presentation.favourite.FavouriteViewModel
import com.example.shopapp.util.Constants.FAVOURITE_SCREEN_LE
import com.example.shopapp.util.Constants.TAG
import com.example.shopapp.util.Constants.productId
import com.example.shopapp.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FavouriteScreen(
    navController: NavController,
    bottomBarHeight: Dp,
    viewModel: FavouriteViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val productList = viewModel.favouriteState.value.productList
    val isUserLoggedIn = viewModel.favouriteState.value.isUserLoggedIn

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            Log.i(TAG, FAVOURITE_SCREEN_LE)
            when(event) {
                is FavouriteUiEvent.NavigateToProductDetails -> {
                    navController.navigate(Screen.ProductDetailsScreen.route + "$productId="+ event.productId)
                }
                is FavouriteUiEvent.NavigateToLogin -> {
                    navController.navigate(Screen.LoginScreen.route)
                }
                is FavouriteUiEvent.NavigateToSignup -> {
                    navController.navigate(Screen.SignupScreen.route)
                }
            }
        }
    }

    if(isUserLoggedIn) {
        FavouriteContent(
            scaffoldState = scaffoldState,
            bottomBarHeight = bottomBarHeight,
            productList = productList,
            onProductSelected = { productId: String ->
                viewModel.onEvent(FavouriteEvent.OnProductSelected(productId))
            }
        )
    }
    else {
        UserNotLoggedInContent(
            scaffoldState = scaffoldState,
            bottomBarHeight = bottomBarHeight,
            onLogin = { viewModel.onEvent(FavouriteEvent.OnLogin) },
            onSignup = { viewModel.onEvent(FavouriteEvent.OnSignup) }
        )
    }
}