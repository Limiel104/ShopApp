package com.example.shopapp.presentation.product_details.composable

import android.util.Log
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopapp.domain.model.Product
import com.example.shopapp.presentation.product_details.ProductDetailsEvent
import com.example.shopapp.presentation.product_details.ProductDetailsUiEvent
import com.example.shopapp.presentation.product_details.ProductDetailsViewModel
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_SCREEN_LE
import com.example.shopapp.util.Constants.TAG
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductDetailsScreen(
    navController: NavController,
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {
    val bottomSheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed,
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )
    val product = Product(
        id = viewModel.productDetailsState.value.productId,
        title = viewModel.productDetailsState.value.title,
        price = viewModel.productDetailsState.value.price,
        description = viewModel.productDetailsState.value.description,
        category = viewModel.productDetailsState.value.category,
        imageUrl = viewModel.productDetailsState.value.imageUrl
    )

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is ProductDetailsUiEvent.NavigateBack -> {
                    Log.i(TAG, PRODUCT_DETAILS_SCREEN_LE)
                    navController.popBackStack()
                }
            }
        }
    }

    ProductDetailsContent(
        scaffoldState = scaffoldState,
        product = product,
        onNavigateBack = { viewModel.onEvent(ProductDetailsEvent.GoBack) }
    )
}