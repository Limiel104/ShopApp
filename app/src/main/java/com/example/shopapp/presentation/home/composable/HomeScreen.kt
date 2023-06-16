package com.example.shopapp.presentation.home.composable

import android.util.Log
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopapp.presentation.home.HomeEvent
import com.example.shopapp.presentation.home.HomeUiEvent
import com.example.shopapp.presentation.home.HomeViewModel
import com.example.shopapp.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val offerList = viewModel.homeState.value.offerList

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is HomeUiEvent.NavigateToCategory -> {
                    Log.i("TAG","HomeScreen Launched Effect")
                    navController.navigate(Screen.CategoryScreen.route + "categoryId="+ event.categoryId)
                }
            }
        }
    }

    HomeContent(
        scaffoldState = scaffoldState,
        offerList = offerList,
        onOfferSelected = {categoryId: String ->
            viewModel.onEvent(HomeEvent.OnOfferSelected(categoryId))
        }
    )
}
