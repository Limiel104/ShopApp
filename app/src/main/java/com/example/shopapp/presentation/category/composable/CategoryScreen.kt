package com.example.shopapp.presentation.category.composable

import android.util.Log
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopapp.presentation.category.CategoryEvent
import com.example.shopapp.presentation.category.CategoryUiEvent
import com.example.shopapp.presentation.category.CategoryViewModel
import com.example.shopapp.util.Constants.CATEGORY_SCREEN_LE
import com.example.shopapp.util.Constants.TAG
import com.example.shopapp.util.Constants.productId
import com.example.shopapp.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CategoryScreen(
    navController: NavController,
    bottomBarHeight: Dp,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val categoryId = viewModel.categoryState.value.categoryId
    val productList = viewModel.categoryState.value.productList
    val isSortSectionVisible = viewModel.categoryState.value.isSortSectionVisible

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is CategoryUiEvent.NavigateToProductDetails -> {
                    Log.i(TAG, CATEGORY_SCREEN_LE)
                    navController.navigate(Screen.ProductDetailsScreen.route + "$productId="+ event.productId)
                }
            }
        }
    }

    CategoryContent(
        scaffoldState = scaffoldState,
        bottomBarHeight = bottomBarHeight,
        categoryName = categoryId,
        productList = productList,
        isSortSectionVisible = isSortSectionVisible,
        onProductSelected = { productId: String ->
            viewModel.onEvent(CategoryEvent.OnProductSelected(productId))
        },
        onSortSelected = { viewModel.onEvent(CategoryEvent.ToggleSortSection) }
    )
}