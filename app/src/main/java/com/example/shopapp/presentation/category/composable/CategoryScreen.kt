package com.example.shopapp.presentation.category.composable

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopapp.presentation.category.CategoryEvent
import com.example.shopapp.presentation.category.CategoryUiEvent
import com.example.shopapp.presentation.category.CategoryViewModel
import com.example.shopapp.util.Constants.CATEGORY_SCREEN_LE
import com.example.shopapp.util.Constants.TAG
import com.example.shopapp.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CategoryScreen(
    navController: NavController,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val categoryId = viewModel.categoryState.value.categoryId
    val productList = viewModel.categoryState.value.productList
    val isSortSectionVisible = viewModel.categoryState.value.isSortAndFilterSectionVisible
    val isLoading = viewModel.categoryState.value.isLoading
    val isButtonEnabled = viewModel.categoryState.value.isButtonEnabled
    val isDialogActivated = viewModel.categoryState.value.isDialogActivated
    val sliderPosition = viewModel.categoryState.value.priceSliderPosition
    val sliderRange = viewModel.categoryState.value.priceSliderRange
    val productOrder = viewModel.categoryState.value.productOrder
    val categoryFilterMap = viewModel.categoryState.value.categoryFilterMap
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            Log.i(TAG, CATEGORY_SCREEN_LE)
            when(event) {
                is CategoryUiEvent.NavigateToProductDetails -> {
                    navController.navigate(Screen.ProductDetailsScreen.route + "productId="+ event.productId)
                }
                is CategoryUiEvent.ShowErrorMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
                is CategoryUiEvent.NavigateToCart -> {
                    navController.navigate(Screen.CartScreen.route)
                }
            }
        }
    }

    CategoryContent(
        categoryName = categoryId,
        productList = productList,
        isSortAndFilterSectionVisible = isSortSectionVisible,
        isLoading = isLoading,
        isButtonEnabled = isButtonEnabled,
        isDialogActivated = isDialogActivated,
        sliderPosition = sliderPosition,
        sliderRange = sliderRange,
        productOrder = productOrder,
        categoryFilterMap = categoryFilterMap,
        onProductSelected = { viewModel.onEvent(CategoryEvent.OnProductSelected(it)) },
        onSortAndFilterSelected = { viewModel.onEvent(CategoryEvent.ToggleSortAndFilterSection) },
        onFavourite = { viewModel.onEvent(CategoryEvent.OnFavouriteButtonSelected(it)) },
        onDismiss = { viewModel.onEvent(CategoryEvent.OnDialogDismissed) },
        onValueChange = { viewModel.onEvent(CategoryEvent.OnPriceSliderPositionChange(it)) },
        onOrderChange = { viewModel.onEvent(CategoryEvent.OnOrderChange(it)) },
        onCheckedChange = { viewModel.onEvent(CategoryEvent.OnCheckBoxToggled(it)) },
        onGoToCart = { viewModel.onEvent(CategoryEvent.GoToCart) }
    )
}