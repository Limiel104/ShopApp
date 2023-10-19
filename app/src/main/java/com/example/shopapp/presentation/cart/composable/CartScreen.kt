package com.example.shopapp.presentation.cart.composable

import android.util.Log
import android.widget.Toast
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopapp.presentation.cart.CartEvent
import com.example.shopapp.presentation.cart.CartUiEvent
import com.example.shopapp.presentation.cart.CartViewModel
import com.example.shopapp.presentation.common.composable.UserNotLoggedInContent
import com.example.shopapp.util.Constants.CART_SCREEN_LE
import com.example.shopapp.util.Constants.TAG
import com.example.shopapp.util.Constants.snackbarActionLabel
import com.example.shopapp.util.Constants.snackbarMessage
import com.example.shopapp.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val isUserLoggedIn = viewModel.cartState.value.isUserLoggedIn
    val cartProducts = viewModel.cartState.value.cartProducts
    val totalAmount = viewModel.cartState.value.totalAmount
    val isLoading = viewModel.cartState.value.isLoading
    val isDialogActivated = viewModel.cartState.value.isDialogActivated
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            Log.i(TAG, CART_SCREEN_LE)
            when(event) {
                is CartUiEvent.NavigateToLogin -> {
                    navController.navigate(Screen.LoginScreen.route)
                }
                is CartUiEvent.NavigateToSignup -> {
                    navController.navigate(Screen.SignupScreen.route)
                }
                is CartUiEvent.ShowErrorMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
                is CartUiEvent.NavigateBack -> {
                    navController.popBackStack()
                }
                is CartUiEvent.ShowSnackbar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = snackbarMessage,
                        actionLabel = snackbarActionLabel
                    )
                    if(result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(CartEvent.OnCartItemRestore)
                    }
                }
                is CartUiEvent.NavigateToHome -> {
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.HomeScreen.route) { inclusive = true }
                    }
                }
            }
        }
    }

    if(isUserLoggedIn) {
        CartContent(
            scaffoldState = scaffoldState,
            totalAmount = totalAmount,
            cartProducts = cartProducts,
            isLoading = isLoading,
            isDialogActivated = isDialogActivated,
            onPlus = { viewModel.onEvent(CartEvent.OnPlus(it)) },
            onMinus = { viewModel.onEvent(CartEvent.OnMinus(it)) },
            onGoBack = { viewModel.onEvent(CartEvent.OnGoBack) },
            onDelete = { viewModel.onEvent(CartEvent.OnDelete(it)) },
            onOrderPlaced = { viewModel.onEvent(CartEvent.OnOrderPlaced) },
            onGoHome = { viewModel.onEvent(CartEvent.OnGoHome) }
        )
    }
    else {
        UserNotLoggedInContent(
            scaffoldState = scaffoldState,
            onLogin = { viewModel.onEvent(CartEvent.OnLogin) },
            onSignup = { viewModel.onEvent(CartEvent.OnSignup) }
        )
    }
}