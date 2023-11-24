package com.example.shopapp.presentation.account

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.domain.model.Coupon
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.presentation.common.Constants.ACCOUNT_VM
import com.example.shopapp.presentation.common.Constants.TAG
import com.example.shopapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val shopUseCases: ShopUseCases
): ViewModel() {

    private val _accountState = mutableStateOf(AccountState())
    val accountState: State<AccountState> = _accountState

    private val _accountEventChannel = Channel<AccountUiEvent>()
    val accountEventChannelFlow = _accountEventChannel.receiveAsFlow()

    init {
        Log.i(TAG, ACCOUNT_VM)

        checkIfUserIsLoggedIn()
    }

    fun onEvent(event: AccountEvent) {
        when(event) {
            is AccountEvent.OnLogin -> {
                viewModelScope.launch {
                    _accountEventChannel.send(AccountUiEvent.NavigateToLogin)
                }
            }
            is AccountEvent.OnSignup -> {
                viewModelScope.launch {
                    _accountEventChannel.send(AccountUiEvent.NavigateToSignup)
                }
            }
            is AccountEvent.OnActivateCoupon -> {
                val coupon = Coupon(userUID = _accountState.value.user.userUID,
                    amount = event.value,
                    activationDate = Date()
                )
                activateCoupon(coupon)
            }
            is AccountEvent.OnLogout -> {
                viewModelScope.launch {
                    logout()
                }
            }
            is AccountEvent.GoToCart -> {
                viewModelScope.launch {
                    _accountEventChannel.send(AccountUiEvent.NavigateToCart)
                }
            }
            AccountEvent.GoToOrders -> {
                viewModelScope.launch {
                    _accountEventChannel.send(AccountUiEvent.NavigateToOrders)
                }
            }
            is AccountEvent.GoToProfile -> {
                viewModelScope.launch {
                    val userUID = _accountState.value.user.userUID
                    _accountEventChannel.send(AccountUiEvent.NavigateToProfile(userUID))
                }
            }
        }
    }

    fun checkIfUserIsLoggedIn() {
        viewModelScope.launch {
            val currentUser = shopUseCases.getCurrentUserUseCase()
            _accountState.value = accountState.value.copy(
                isUserLoggedIn = currentUser != null
            )

            if(currentUser != null) {
                getUser(currentUser.uid)
            }
        }
    }

    fun getUser(userUID: String) {
        viewModelScope.launch {
            shopUseCases.getUserUseCase(userUID).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading user: ${response.isLoading}")
                        _accountState.value = accountState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        response.data?.let { user ->
                            Log.i(TAG, "User: $user")
                            _accountState.value = accountState.value.copy(
                                user = user[0]
                            )
                            getUserCoupon(userUID)
                        }
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _accountEventChannel.send(AccountUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun getUserCoupon(userUID: String) {
        viewModelScope.launch {
            shopUseCases.getUserCouponUseCase(userUID).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading coupon: ${response.isLoading}")
                        _accountState.value = accountState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        response.data?.let { coupon ->
                            val isCouponAlreadyExpired = shopUseCases.isCouponExpiredUseCase(
                                coupon.activationDate,
                                Calendar.getInstance().time
                            )
                            if(!isCouponAlreadyExpired) {
                                Log.i(TAG,"Coupon: $coupon")
                                _accountState.value = _accountState.value.copy(
                                    isCouponActivated = true,
                                    coupon = coupon
                                )
                            }
                            else {
                                deleteCoupon(userUID)
                            }
                        }
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _accountEventChannel.send(AccountUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun deleteCoupon(userUID: String) {
        viewModelScope.launch {
            shopUseCases.deleteCouponUseCase(userUID).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading delete coupon: ${response.isLoading}")
                        _accountState.value = accountState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        Log.i(TAG,"Expired coupon was deleted")
                        _accountState.value = _accountState.value.copy(
                            isCouponActivated = false
                        )
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _accountEventChannel.send(AccountUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun activateCoupon(coupon: Coupon) {
        viewModelScope.launch {
            shopUseCases.addCouponUseCase(coupon).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading add coupon: ${response.isLoading}")
                        _accountState.value = accountState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        val pointsAfterCouponActivation = _accountState.value.user.points - (coupon.amount*100)
                        updateUserPoints(
                            _accountState.value.user.userUID,
                            pointsAfterCouponActivation
                        )
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _accountEventChannel.send(AccountUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun updateUserPoints(userUID: String, points: Int) {
        viewModelScope.launch {
            shopUseCases.updateUserPointsUseCase(userUID,points).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading update user points: ${response.isLoading}")
                        _accountState.value = accountState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        Log.i(TAG,"User points were updated after coupon activation")
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _accountEventChannel.send(AccountUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun logout() {
        shopUseCases.logoutUseCase()
        _accountState.value = accountState.value.copy(
            isUserLoggedIn = false
        )
    }
}