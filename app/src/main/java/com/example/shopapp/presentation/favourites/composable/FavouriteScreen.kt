package com.example.shopapp.presentation.favourites.composable

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.example.shopapp.presentation.common.composable.UserNotLoggedInContent
import com.example.shopapp.presentation.favourites.FavouritesEvent
import com.example.shopapp.presentation.favourites.FavouritesUiEvent
import com.example.shopapp.presentation.favourites.FavouritesViewModel
import com.example.shopapp.util.Constants.FAVOURITES_SCREEN_LE
import com.example.shopapp.util.Constants.TAG
import com.example.shopapp.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FavouriteScreen(
    navController: NavController,
    viewModel: FavouritesViewModel = hiltViewModel()
) {
    val productList = viewModel.favouritesState.value.productList
    val isUserLoggedIn = viewModel.favouritesState.value.isUserLoggedIn
    val isLoading = viewModel.favouritesState.value.isLoading
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.favouritesEventChannelFlow.collectLatest { event ->
                Log.i(TAG, FAVOURITES_SCREEN_LE)
                when(event) {
                    is FavouritesUiEvent.NavigateToProductDetails -> {
                        navController.navigate(Screen.ProductDetailsScreen.route + "productId=" + event.productId)
                    }
                    is FavouritesUiEvent.NavigateToLogin -> {
                        navController.navigate(Screen.LoginScreen.route)
                    }
                    is FavouritesUiEvent.NavigateToSignup -> {
                        navController.navigate(Screen.SignupScreen.route)
                    }
                    is FavouritesUiEvent.ShowErrorMessage -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                    }
                    is FavouritesUiEvent.NavigateToCart -> {
                        navController.navigate(Screen.CartScreen.route)
                    }
                    is FavouritesUiEvent.ShowProductAddedToCartMessage -> {
                        Toast.makeText(context, "Added to the cart", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    if(isUserLoggedIn) {
        FavouriteContent(
            productList = productList,
            isLoading = isLoading,
            onProductSelected = { viewModel.onEvent(FavouritesEvent.OnProductSelected(it)) },
            onDelete = { viewModel.onEvent(FavouritesEvent.OnDelete(it)) },
            onAddToCart = { viewModel.onEvent(FavouritesEvent.OnAddToCart(it)) },
            onGoToCart = { viewModel.onEvent(FavouritesEvent.GoToCart) }
        )
    }
    else {
        UserNotLoggedInContent(
            onLogin = { viewModel.onEvent(FavouritesEvent.OnLogin) },
            onSignup = { viewModel.onEvent(FavouritesEvent.OnSignup) }
        )
    }
}