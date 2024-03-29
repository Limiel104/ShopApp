package com.example.shopapp.presentation.account.composable

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.example.shopapp.presentation.account.AccountEvent
import com.example.shopapp.presentation.account.AccountUiEvent
import com.example.shopapp.presentation.account.AccountViewModel
import com.example.shopapp.presentation.common.composable.UserNotLoggedInContent
import com.example.shopapp.presentation.common.Constants.ACCOUNT_SCREEN_LE
import com.example.shopapp.presentation.common.Constants.TAG
import com.example.shopapp.presentation.navigation.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AccountScreen(
    navController: NavController,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val firstName = viewModel.accountState.value.user.firstName
    val points = viewModel.accountState.value.user.points
    val isUserLoggedIn = viewModel.accountState.value.isUserLoggedIn
    val isCouponActivated = viewModel.accountState.value.isCouponActivated
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.accountEventChannelFlow.collectLatest { event ->
                Log.i(TAG, ACCOUNT_SCREEN_LE)
                when(event) {
                    is AccountUiEvent.NavigateToLogin -> {
                        navController.navigate(Screen.LoginScreen.route)
                    }
                    is AccountUiEvent.NavigateToSignup -> {
                        navController.navigate(Screen.SignupScreen.route)
                    }
                    is AccountUiEvent.NavigateToCart -> {
                        navController.navigate(Screen.CartScreen.route)
                    }
                    is AccountUiEvent.NavigateToOrders -> {
                        navController.navigate(Screen.OrdersScreen.route)
                    }
                    is AccountUiEvent.NavigateToProfile -> {
                        navController.navigate(Screen.ProfileScreen.route + "userUID=" + event.userUID)
                    }
                    is AccountUiEvent.ShowErrorMessage -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    if(isUserLoggedIn) {
        AccountContent(
            name = firstName,
            userPoints = points,
            isCouponActivated = isCouponActivated,
            onActivateCoupon = {viewModel.onEvent(AccountEvent.OnActivateCoupon(it))},
            onLogout = { viewModel.onEvent(AccountEvent.OnLogout) },
            onGoToCart = { viewModel.onEvent(AccountEvent.GoToCart) },
            onGoToOrders = { viewModel.onEvent(AccountEvent.GoToOrders) },
            onGoToProfile = { viewModel.onEvent(AccountEvent.GoToProfile) }
        )
    }
    else {
        UserNotLoggedInContent(
            onLogin = { viewModel.onEvent(AccountEvent.OnLogin) },
            onSignup = { viewModel.onEvent(AccountEvent.OnSignup) }
        )
    }
}