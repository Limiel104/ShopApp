package com.example.shopapp.presentation.favourites.composable

import android.util.Log
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopapp.presentation.favourites.FavouriteEvent
import com.example.shopapp.presentation.favourites.FavouriteUiEvent
import com.example.shopapp.presentation.favourites.FavouriteViewModel
import com.example.shopapp.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FavouriteScreen(
    navController: NavController,
    viewModel: FavouriteViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val productList = viewModel.favouriteState.value.productList

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is FavouriteUiEvent.NavigateToProductDetails -> {
                    Log.i("TAG","FavouriteScreen Launched Effect")
                    navController.navigate(Screen.ProductDetailsScreen.route + "productId="+ event.productId)
                }
            }
        }
    }


    FavouriteContent(
        scaffoldState = scaffoldState,
        productList = productList,
        onProductSelected = { productId: String ->
            viewModel.onEvent(FavouriteEvent.OnProductSelected(productId))
        }
    )
}