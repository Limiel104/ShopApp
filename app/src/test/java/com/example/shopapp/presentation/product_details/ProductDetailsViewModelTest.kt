package com.example.shopapp.presentation.product_details

import androidx.lifecycle.SavedStateHandle
import com.example.shopapp.domain.model.CartItem
import com.example.shopapp.domain.model.Favourite
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.MainDispatcherRule
import com.example.shopapp.domain.util.Resource
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductDetailsViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var shopUseCases: ShopUseCases
    @MockK
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var productDetailsViewModel: ProductDetailsViewModel
    private lateinit var product: Product
    @MockK
    private lateinit var user: FirebaseUser
    private lateinit var cartItem: CartItem
    private lateinit var updatedCartItem: CartItem
    private lateinit var favourite: Favourite

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { savedStateHandle.get<Int>("productId") } returns 1
        every { shopUseCases.getCurrentUserUseCase() } returns user
        every { user.uid } returns "userUID"

        product = Product(
            id = 1,
            title = "title 1",
            price = 123.99,
            description = "description of a product 1",
            category = "men's clothing",
            imageUrl = "url",
            isInFavourites = false
        )

        cartItem = CartItem(
            cartItemId = "cartItemId",
            userUID = "userUID",
            productId = 1,
            amount = 1
        )

        updatedCartItem = CartItem(
            cartItemId = "cartItemId",
            userUID = "userUID",
            productId = 1,
            amount = 2
        )

        favourite = Favourite(
            favouriteId = "favouriteId",
            userUID = "userUID",
            productId = 1
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    private fun setViewModel(): ProductDetailsViewModel {
        return ProductDetailsViewModel(savedStateHandle, shopUseCases)
    }

    private fun getCurrentProductDetailsState(): ProductDetailsState {
        return productDetailsViewModel.productDetailsState.value
    }

    @Test
    fun `product id is set correctly on init`() {
        coEvery {
            shopUseCases.getProductUseCase(1)
        } returns Resource.Success(product)
        coEvery {
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        } returns flowOf(Resource.Success(listOf( favourite)))

        productDetailsViewModel = setViewModel()
        val productId = getCurrentProductDetailsState().productId

        verify { savedStateHandle.get<String>("productId") }
        assertThat(productId).isEqualTo(1)
    }

    @Test
    fun `get product result is success`() {
        coEvery {
            shopUseCases.getProductUseCase(1)
        } returns Resource.Success(product)
        coEvery {
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        } returns flowOf(Resource.Error("Error"))

        productDetailsViewModel = setViewModel()
        val productToCheck = Product(
            id = getCurrentProductDetailsState().productId,
            title = getCurrentProductDetailsState().title,
            price = getCurrentProductDetailsState().price,
            description = getCurrentProductDetailsState().description,
            category = getCurrentProductDetailsState().category,
            imageUrl = getCurrentProductDetailsState().imageUrl,
            isInFavourites = false
        )
        val loadingState = getCurrentProductDetailsState().isLoading

        coVerifySequence {
            shopUseCases.getProductUseCase(1)
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        }
        assertThat(productToCheck).isEqualTo(product)
        assertThat(loadingState).isFalse()
    }

    @Test
    fun `get product result is error`() {
        coEvery {
            shopUseCases.getProductUseCase(1)
        } returns Resource.Error("Error")
        coEvery {
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        } returns flowOf(Resource.Error("Error"))

        productDetailsViewModel = setViewModel()
        val productId = getCurrentProductDetailsState().productId
        val title = getCurrentProductDetailsState().title
        val price = getCurrentProductDetailsState().price
        val description = getCurrentProductDetailsState().description
        val category = getCurrentProductDetailsState().category
        val imageUrl = getCurrentProductDetailsState().imageUrl
        val loadingState = getCurrentProductDetailsState().isLoading

        coVerifySequence {
            shopUseCases.getProductUseCase(1)
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        }
        assertThat(productId).isEqualTo(product.id)
        assertThat(title).isEqualTo("")
        assertThat(price).isEqualTo(-1)
        assertThat(description).isEqualTo("")
        assertThat(category).isEqualTo("")
        assertThat(imageUrl).isEqualTo("")
        assertThat(loadingState).isFalse()
    }

    @Test
    fun `get product result is loading`() {
        coEvery {
            shopUseCases.getProductUseCase(1)
        } returns Resource.Loading(true)
        coEvery {
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        } returns flowOf(Resource.Error("Error"))

        productDetailsViewModel = setViewModel()
        val productId = getCurrentProductDetailsState().productId
        val title = getCurrentProductDetailsState().title
        val price = getCurrentProductDetailsState().price
        val description = getCurrentProductDetailsState().description
        val category = getCurrentProductDetailsState().category
        val imageUrl = getCurrentProductDetailsState().imageUrl
        val loadingState = getCurrentProductDetailsState().isLoading

        coVerifySequence {
            shopUseCases.getProductUseCase(1)
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        }
        assertThat(productId).isEqualTo(product.id)
        assertThat(title).isEqualTo("")
        assertThat(price).isEqualTo(-1)
        assertThat(description).isEqualTo("")
        assertThat(category).isEqualTo("")
        assertThat(imageUrl).isEqualTo("")
        assertThat(loadingState).isTrue()
    }

    @Test
    fun `userUID of current user is set correctly`() {
        coEvery {
            shopUseCases.getProductUseCase(1)
        } returns Resource.Success(product)
        coEvery {
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        } returns flowOf(Resource.Success(listOf(favourite)))

        productDetailsViewModel = setViewModel()
        val userUID = getCurrentProductDetailsState().userUID

        coVerifySequence {
            shopUseCases.getProductUseCase(1)
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        }
        assertThat(userUID).isEqualTo("userUID")
    }

    @Test
    fun `get user favourite result is success - product is in favourites`() {
        coEvery {
            shopUseCases.getProductUseCase(1)
        } returns Resource.Success(product)
        coEvery {
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        } returns flowOf(Resource.Success(listOf(favourite)))

        productDetailsViewModel = setViewModel()
        val favouriteId = getCurrentProductDetailsState().favouriteId
        val isInFavourites = getCurrentProductDetailsState().isInFavourites
        val loadingState = getCurrentProductDetailsState().isLoading

        coVerifySequence {
            shopUseCases.getProductUseCase(1)
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        }
        assertThat(favouriteId).isEqualTo("favouriteId")
        assertThat(isInFavourites).isTrue()
        assertThat(loadingState).isFalse()
    }

    @Test
    fun `get user favourite result is success - product is not in favourites`() {
        coEvery {
            shopUseCases.getProductUseCase(1)
        } returns Resource.Success(product)
        coEvery {
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        } returns flowOf(Resource.Success(emptyList()))

        productDetailsViewModel = setViewModel()
        val favouriteId = getCurrentProductDetailsState().favouriteId
        val isInFavourites = getCurrentProductDetailsState().isInFavourites
        val loadingState = getCurrentProductDetailsState().isLoading

        coVerifySequence {
            shopUseCases.getProductUseCase(1)
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        }
        assertThat(favouriteId).isEqualTo("")
        assertThat(isInFavourites).isFalse()
        assertThat(loadingState).isFalse()
    }

    @Test
    fun `get user favourite result is error`() {
        coEvery {
            shopUseCases.getProductUseCase(1)
        } returns Resource.Success(product)
        coEvery {
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        } returns flowOf(Resource.Error("Error"))

        productDetailsViewModel = setViewModel()
        val favouriteId = getCurrentProductDetailsState().favouriteId
        val isInFavourites = getCurrentProductDetailsState().isInFavourites
        val loadingState = getCurrentProductDetailsState().isLoading

        coVerifySequence {
            shopUseCases.getProductUseCase(1)
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        }
        assertThat(favouriteId).isEqualTo("")
        assertThat(isInFavourites).isFalse()
        assertThat(loadingState).isFalse()
    }

    @Test
    fun `get user favourite result is loading`() {
        coEvery {
            shopUseCases.getProductUseCase(1)
        } returns Resource.Success(product)
        coEvery {
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        } returns flowOf(Resource.Loading(true))

        productDetailsViewModel = setViewModel()
        val favouriteId = getCurrentProductDetailsState().favouriteId
        val isInFavourites = getCurrentProductDetailsState().isInFavourites
        val loadingState = getCurrentProductDetailsState().isLoading

        coVerifySequence {
            shopUseCases.getProductUseCase(1)
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        }
        assertThat(favouriteId).isEqualTo("")
        assertThat(isInFavourites).isFalse()
        assertThat(loadingState).isTrue()
    }

    @Test
    fun `add product to cart is successful - product was not in cart yet`() {
        coEvery {
            shopUseCases.getProductUseCase(1)
        } returns Resource.Success(product)
        coEvery {
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        } returns flowOf(Resource.Success(listOf(favourite)))
        coEvery {
            shopUseCases.addProductToCartUseCase("userUID",1,1)
        } returns flowOf(Resource.Success(true))

        productDetailsViewModel = setViewModel()
        productDetailsViewModel.addProductToCart("userUID",1)
        val loadingState = getCurrentProductDetailsState().isLoading

        coVerifySequence {
            shopUseCases.getProductUseCase(1)
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouriteUseCase("userUID",1)
            shopUseCases.addProductToCartUseCase("userUID",1,1)
        }
        assertThat(loadingState).isFalse()
    }

    @Test
    fun `add product to cart is loading - product was not in cart yet`() {
        coEvery {
            shopUseCases.getProductUseCase(1)
        } returns Resource.Success(product)
        coEvery {
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        } returns flowOf(Resource.Success(listOf(favourite)))
        coEvery {
            shopUseCases.addProductToCartUseCase("userUID",1,1)
        } returns flowOf(Resource.Loading(true))

        productDetailsViewModel = setViewModel()
        productDetailsViewModel.addProductToCart("userUID",1)
        val loadingState = getCurrentProductDetailsState().isLoading

        coVerifySequence {
            shopUseCases.getProductUseCase(1)
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouriteUseCase("userUID",1)
            shopUseCases.addProductToCartUseCase("userUID",1,1)
        }
        assertThat(loadingState).isTrue()
    }

    @Test
    fun `update product in cart is successful - product was already in the cart`() {
        coEvery {
            shopUseCases.getProductUseCase(1)
        } returns Resource.Success(product)
        coEvery {
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        } returns flowOf(Resource.Success(listOf(favourite)))
        coEvery {
            shopUseCases.updateProductInCartUseCase(cartItem)
        } returns flowOf(Resource.Success(true))

        productDetailsViewModel = setViewModel()
        productDetailsViewModel.updateProductInCart(
            CartItem("cartItemId","userUID",1,1)
        )
        val loadingState = getCurrentProductDetailsState().isLoading

        coVerifySequence {
            shopUseCases.getProductUseCase(1)
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouriteUseCase("userUID",1)
            shopUseCases.updateProductInCartUseCase(cartItem)
        }
        assertThat(loadingState).isFalse()
    }

    @Test
    fun `update product in cart is loading - product was already in the cart`() {
        coEvery {
            shopUseCases.getProductUseCase(1)
        } returns Resource.Success(product)
        coEvery {
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        } returns flowOf(Resource.Success(listOf(favourite)))
        coEvery {
            shopUseCases.updateProductInCartUseCase(cartItem)
        } returns flowOf(Resource.Loading(true))

        productDetailsViewModel = setViewModel()
        productDetailsViewModel.updateProductInCart(
            CartItem("cartItemId","userUID",1,1)
        )
        val loadingState = getCurrentProductDetailsState().isLoading

        coVerifySequence {
            shopUseCases.getProductUseCase(1)
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouriteUseCase("userUID",1)
            shopUseCases.updateProductInCartUseCase(cartItem)
        }
        assertThat(loadingState).isTrue()
    }

    @Test
    fun `if product was not yet in the cart then fun addOrUpdateProductInCart calls addProductToCart`() {
        coEvery {
            shopUseCases.getProductUseCase(1)
        } returns Resource.Success(product)
        coEvery {
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        } returns flowOf(Resource.Success(listOf(favourite)))
        coEvery {
            shopUseCases.getUserCartItemUseCase("userUID",1)
        } returns flowOf(Resource.Success(emptyList()))
        coEvery {
            shopUseCases.addProductToCartUseCase("userUID",1,1)
        } returns flowOf(Resource.Success(true))

        productDetailsViewModel = setViewModel()
        productDetailsViewModel.addOrUpdateProductInCart("userUID",1)
        val loadingState = getCurrentProductDetailsState().isLoading

        coVerifySequence {
            shopUseCases.getProductUseCase(1)
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouriteUseCase("userUID",1)
            shopUseCases.getUserCartItemUseCase("userUID",1)
            shopUseCases.addProductToCartUseCase("userUID",1,1)
        }
        assertThat(loadingState).isFalse()
    }

    @Test
    fun `if product was already in the cart then fun addOrUpdateProductInCart calls updateProductInCart`() {
        coEvery {
            shopUseCases.getProductUseCase(1)
        } returns Resource.Success(product)
        coEvery {
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        } returns flowOf(Resource.Success(listOf(favourite)))
        coEvery {
            shopUseCases.getUserCartItemUseCase("userUID",1)
        } returns flowOf(Resource.Success(listOf(cartItem)))
        coEvery {
            shopUseCases.updateProductInCartUseCase(updatedCartItem)
        } returns flowOf(Resource.Success(true))

        productDetailsViewModel = setViewModel()
        productDetailsViewModel.addOrUpdateProductInCart("userUID",1)
        val loadingState = getCurrentProductDetailsState().isLoading

        coVerifySequence {
            shopUseCases.getProductUseCase(1)
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouriteUseCase("userUID",1)
            shopUseCases.getUserCartItemUseCase("userUID",1)
            shopUseCases.updateProductInCartUseCase(updatedCartItem)
        }
        assertThat(loadingState).isFalse()
    }

    @Test
    fun `get user cart item is loading`() {
        coEvery {
            shopUseCases.getProductUseCase(1)
        } returns Resource.Success(product)
        coEvery {
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        } returns flowOf(Resource.Success(listOf(favourite)))
        coEvery {
            shopUseCases.getUserCartItemUseCase("userUID",1)
        } returns flowOf(Resource.Loading(true))

        productDetailsViewModel = setViewModel()
        productDetailsViewModel.addOrUpdateProductInCart("userUID",1)
        val loadingState = getCurrentProductDetailsState().isLoading
        println(loadingState)

        coVerifySequence {
            shopUseCases.getProductUseCase(1)
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouriteUseCase("userUID",1)
            shopUseCases.getUserCartItemUseCase("userUID",1)
        }
        assertThat(loadingState).isTrue()
    }

    @Test
    fun `event OnAddToCart is adding product to the cart if product was not yet in the cart`() {
        coEvery {
            shopUseCases.getProductUseCase(1)
        } returns Resource.Success(product)
        coEvery {
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        } returns flowOf(Resource.Success(listOf(favourite)))
        coEvery {
            shopUseCases.getUserCartItemUseCase("userUID",1)
        } returns flowOf(Resource.Success(emptyList()))
        coEvery {
            shopUseCases.addProductToCartUseCase("userUID",1,1)
        } returns flowOf(Resource.Success(true))

        productDetailsViewModel = setViewModel()
        productDetailsViewModel.onEvent(ProductDetailsEvent.OnAddToCart)
        val loadingState = getCurrentProductDetailsState().isLoading

        coVerifySequence {
            shopUseCases.getProductUseCase(1)
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouriteUseCase("userUID",1)
            shopUseCases.getUserCartItemUseCase("userUID",1)
            shopUseCases.addProductToCartUseCase("userUID",1,1)
        }
        assertThat(loadingState).isFalse()
    }

    @Test
    fun `event OnAddToCart is updating product in the cart if product was already in the cart`() {
        coEvery {
            shopUseCases.getProductUseCase(1)
        } returns Resource.Success(product)
        coEvery {
            shopUseCases.getUserFavouriteUseCase("userUID",1)
        } returns flowOf(Resource.Success(listOf(favourite)))
        coEvery {
            shopUseCases.getUserCartItemUseCase("userUID",1)
        } returns flowOf(Resource.Success(listOf(cartItem)))
        coEvery {
            shopUseCases.updateProductInCartUseCase(updatedCartItem)
        } returns flowOf(Resource.Success(true))

        productDetailsViewModel = setViewModel()
        productDetailsViewModel.onEvent(ProductDetailsEvent.OnAddToCart)
        val loadingState = getCurrentProductDetailsState().isLoading

        coVerifySequence {
            shopUseCases.getProductUseCase(1)
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouriteUseCase("userUID",1)
            shopUseCases.getUserCartItemUseCase("userUID",1)
            shopUseCases.updateProductInCartUseCase(updatedCartItem)
        }
        assertThat(loadingState).isFalse()
    }
}