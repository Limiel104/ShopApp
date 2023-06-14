package com.example.shopapp.presentation.category.composable

import android.util.Log
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopapp.presentation.category.CategoryEvent
import com.example.shopapp.presentation.category.CategoryUiEvent
import com.example.shopapp.presentation.category.CategoryViewModel
import com.example.shopapp.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CategoryScreen(
    navController: NavController,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val categoryId = viewModel.categoryState.value.categoryId
    val productList = viewModel.categoryState.value.productList
    val isSortSectionToggled = viewModel.categoryState.value.isSortSectionToggled

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is CategoryUiEvent.NavigateToProductDetails -> {
                    Log.i("TAG","CategoryScreen Launched Effect")
                    navController.navigate(Screen.ProductDetailsScreen.route + "productId="+ event.productId)
                }
            }
        }
    }

    CategoryContent(
        scaffoldState = scaffoldState,
        categoryName = categoryId,
        productList = productList,
        isSortSectionToggled = isSortSectionToggled,
        onProductSelected = { productId: String ->
            viewModel.onEvent(CategoryEvent.OnProductSelected(productId))
        }
    )
}