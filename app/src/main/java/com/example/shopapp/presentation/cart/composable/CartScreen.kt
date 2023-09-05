package com.example.shopapp.presentation.cart.composable

import android.util.Log
import android.widget.Toast
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.presentation.cart.CartEvent
import com.example.shopapp.presentation.cart.CartUiEvent
import com.example.shopapp.presentation.cart.CartViewModel
import com.example.shopapp.presentation.common.composable.UserNotLoggedInContent
import com.example.shopapp.util.Constants.CART_SCREEN_LE
import com.example.shopapp.util.Constants.TAG
import com.example.shopapp.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CartScreen(
    navController: NavController,
    bottomBarHeight: Dp,
    viewModel: CartViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val isUserLoggedIn = viewModel.cartState.value.isUserLoggedIn
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
            }
        }
    }

    val cartProducts = listOf(
        CartProduct(
            id = 1,
            title = "title 1",
            price = "123,45 PLN",
            imageUrl = "",
            amount = 1
        ),
        CartProduct(
            id = 2,
            title = "title 2",
            price = "53,34 PLN",
            imageUrl = "",
            amount = 2
        ),
        CartProduct(
            id = 3,
            title = "title 3",
            price = "56,00 PLN",
            imageUrl = "",
            amount = 1
        ),
        CartProduct(
            id = 4,
            title = "title 4",
            price = "23,00 PLN",
            imageUrl = "",
            amount = 1
        ),
        CartProduct(
            id = 5,
            title = "title 5",
            price = "6,86 PLN",
            imageUrl = "",
            amount = 2
        ),
        CartProduct(
            id = 6,
            title = "title 6 dsfhdkjhgdfjkg hdfjkghdfkghfjdh fdhkghdkfjghdfkjghdfjghdfkjghdfkjghdfk",
            price = "44,99 PLN",
            imageUrl = "",
            amount = 3
        ),
        CartProduct(
            id = 7,
            title = "title 7",
            price = "203,99 PLN",
            imageUrl = "",
            amount = 3
        )
    )

    if(isUserLoggedIn) {
        CartContent(
            scaffoldState = scaffoldState,
            bottomBarHeight = bottomBarHeight,
            totalAmount = 155.45,
            cartProducts = cartProducts,
            isLoading = false,
            isDialogActivated = false,
            onGoBack = {},
            onGoHome = {}
        )
    }
    else {
        UserNotLoggedInContent(
            scaffoldState = scaffoldState,
            bottomBarHeight = bottomBarHeight,
            onLogin = { viewModel.onEvent(CartEvent.OnLogin) },
            onSignup = { viewModel.onEvent(CartEvent.OnSignup) }
        )
    }
}