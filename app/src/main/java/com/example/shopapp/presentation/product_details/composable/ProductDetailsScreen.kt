package com.example.shopapp.presentation.product_details.composable

import android.util.Log
import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopapp.domain.model.Product
import com.example.shopapp.presentation.product_details.ProductDetailsEvent
import com.example.shopapp.presentation.product_details.ProductDetailsUiEvent
import com.example.shopapp.presentation.product_details.ProductDetailsViewModel
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_SCREEN_LE
import com.example.shopapp.util.Constants.TAG
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    navController: NavController,
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val product = Product(
        id = viewModel.productDetailsState.value.productId,
        title = viewModel.productDetailsState.value.title,
        price = viewModel.productDetailsState.value.price,
        description = viewModel.productDetailsState.value.description,
        category = viewModel.productDetailsState.value.category,
        imageUrl = viewModel.productDetailsState.value.imageUrl,
        isInFavourites = viewModel.productDetailsState.value.isInFavourites
    )
    val isLoading = viewModel.productDetailsState.value.isLoading
    val context = LocalContext.current


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            Log.i(TAG, PRODUCT_DETAILS_SCREEN_LE)
            when(event) {
                is ProductDetailsUiEvent.NavigateBack -> {
                    navController.popBackStack()
                }
                is ProductDetailsUiEvent.ShowErrorMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
                ProductDetailsUiEvent.ShowProductAddedToCartMessage -> {
                    Toast.makeText(context, "Added to the cart", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    ProductDetailsContent(
        scaffoldState = scaffoldState,
        product = product,
        isLoading = isLoading,
        onNavigateBack = { viewModel.onEvent(ProductDetailsEvent.GoBack) },
        onAddToCart = { viewModel.onEvent(ProductDetailsEvent.OnAddToCart) }
    )
}