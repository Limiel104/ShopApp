package com.example.shopapp.presentation.favourites

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.Category
import com.example.shopapp.util.Constants.FAVOURITES_VM
import com.example.shopapp.util.Constants.TAG
import com.example.shopapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val shopUseCases: ShopUseCases
): ViewModel() {

    private val _favouritesState = mutableStateOf(FavouritesState())
    val favouritesState: State<FavouritesState> = _favouritesState

    private val _eventFlow = MutableSharedFlow<FavouritesUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        Log.i(TAG, FAVOURITES_VM)

        checkIfUserIsLoggedIn()

        if(_favouritesState.value.isUserLoggedIn) {
            getUserFavourites(_favouritesState.value.userUID)
        }
    }

    fun onEvent(event: FavouritesEvent) {
        when(event) {
            is FavouritesEvent.OnProductSelected -> {
                viewModelScope.launch {
                    _favouritesState.value = favouritesState.value.copy(
                        productId = event.value
                    )
                    _eventFlow.emit(FavouritesUiEvent.NavigateToProductDetails(event.value))
                }
            }
            is FavouritesEvent.OnLogin -> {
                viewModelScope.launch {
                    _eventFlow.emit(FavouritesUiEvent.NavigateToLogin)
                }

            }
            is FavouritesEvent.OnSignup -> {
                viewModelScope.launch {
                    _eventFlow.emit(FavouritesUiEvent.NavigateToSignup)
                }
            }
        }
    }

    fun checkIfUserIsLoggedIn() {
        viewModelScope.launch {
            val currentUser = shopUseCases.getCurrentUserUseCase()

            _favouritesState.value = favouritesState.value.copy(
                isUserLoggedIn = currentUser != null
            )

            if(currentUser != null) {
                _favouritesState.value = favouritesState.value.copy(
                    userUID = currentUser.uid
                )
            }
        }
    }

    fun getUserFavourites(userUID: String) {
        viewModelScope.launch {
            shopUseCases.getUserFavouritesUseCase(userUID).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading favourites: ${response.isLoading}")
                        _favouritesState.value = favouritesState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        response.data?.let { favourites ->
                            _favouritesState.value = favouritesState.value.copy(
                                favouriteList = favourites
                            )
                            if(favourites.isNotEmpty()) {
                                getProducts(Category.All.id)
                            }
                        }
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _eventFlow.emit(FavouritesUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun getProducts(categoryId: String) {
        viewModelScope.launch {
            shopUseCases.getProductsUseCase(categoryId).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading products: ${response.isLoading}")
                        _favouritesState.value = favouritesState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        response.data?.let { products ->
                            if(products.isNotEmpty()) {
                                getFavouriteProducts(products)
                            }
                        }
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _eventFlow.emit(FavouritesUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun getFavouriteProducts(products: List<Product>) {
        val favourites = _favouritesState.value.favouriteList

        _favouritesState.value = favouritesState.value.copy(
            productList = shopUseCases.filterProductsByUserFavouritesUseCase(products,favourites)
        )
    }
}