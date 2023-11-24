package com.example.shopapp.presentation.category_list.composable

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.example.shopapp.presentation.category_list.CategoryListEvent
import com.example.shopapp.presentation.category_list.CategoryListUiEvent
import com.example.shopapp.presentation.category_list.CategoryListViewModel
import com.example.shopapp.presentation.common.Constants.CATEGORY_LIST_SCREEN_LE
import com.example.shopapp.presentation.common.Constants.TAG
import com.example.shopapp.presentation.navigation.Screen
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CategoryListScreen(
    navController: NavController,
    viewModel: CategoryListViewModel = hiltViewModel()
) {
    val categoryList = viewModel.categoryListState.value.categoryList
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.categoryListEventChannelFlow.collectLatest { event ->
                when(event) {
                    is CategoryListUiEvent.NavigateToCategory -> {
                        Log.i(TAG, CATEGORY_LIST_SCREEN_LE)
                        navController.navigate(Screen.CategoryScreen.route + "categoryId=" + event.categoryId)
                    }
                    is CategoryListUiEvent.NavigateToCart -> {
                        navController.navigate(Screen.CartScreen.route)
                    }
                }
            }
        }
    }

    CategoryListContent(
        categoryList = categoryList,
        onCategorySelected = { viewModel.onEvent(CategoryListEvent.OnCategorySelected(it)) },
        onGoToCart = { viewModel.onEvent(CategoryListEvent.GoToCart) }
    )
}