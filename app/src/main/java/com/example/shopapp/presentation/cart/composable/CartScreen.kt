package com.example.shopapp.presentation.cart.composable

import android.util.Log
import android.widget.Toast
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.example.shopapp.presentation.cart.CartEvent
import com.example.shopapp.presentation.cart.CartUiEvent
import com.example.shopapp.presentation.cart.CartViewModel
import com.example.shopapp.presentation.common.composable.UserNotLoggedInContent
import com.example.shopapp.presentation.common.Constants.CART_SCREEN_LE
import com.example.shopapp.presentation.common.Constants.TAG
import com.example.shopapp.presentation.common.Constants.snackbarActionLabel
import com.example.shopapp.presentation.common.Constants.snackbarMessage
import com.example.shopapp.presentation.navigation.Screen

@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val isUserLoggedIn = viewModel.cartState.value.isUserLoggedIn
    val cartProducts = viewModel.cartState.value.cartProducts
    val totalAmount = viewModel.cartState.value.totalAmount
    val isLoading = viewModel.cartState.value.isLoading
    val isDialogActivated = viewModel.cartState.value.isDialogActivated
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.cartEventChannelFlow.collect { event ->
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
                        val result = snackbarHostState.showSnackbar(
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
    }

    if(isUserLoggedIn) {
        CartContent(
            snackbarHostState = snackbarHostState,
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
            onLogin = { viewModel.onEvent(CartEvent.OnLogin) },
            onSignup = { viewModel.onEvent(CartEvent.OnSignup) }
        )
    }
}