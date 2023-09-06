package com.example.shopapp.presentation.cart

import com.example.shopapp.domain.model.CartItem
import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.Category
import com.example.shopapp.util.Constants.productDescription
import com.example.shopapp.util.MainDispatcherRule
import com.example.shopapp.util.Resource
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CartViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var shopUseCases: ShopUseCases
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartItems: List<CartItem>
    private lateinit var products: List<Product>
    private lateinit var cartProducts: List<CartProduct>
    @MockK
    private lateinit var user: FirebaseUser

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { shopUseCases.getCurrentUserUseCase() } returns user
        every { user.uid } returns "userUID"

        cartItems = listOf(
            CartItem(
                cartItemId = "cartItemId1",
                userUID = "userUID",
                productId = 1,
                amount = 2
            ),
            CartItem(
                cartItemId = "cartItemId2",
                userUID = "userUID",
                productId = 3,
                amount = 1
            ),
            CartItem(
                cartItemId = "cartItemId3",
                userUID = "userUID",
                productId = 4,
                amount = 3
            ),
            CartItem(
                cartItemId = "cartItemId4",
                userUID = "userUID",
                productId = 6,
                amount = 7
            )
        ).shuffled()

        products = listOf(
            Product(
                id = 1,
                title = "Polo Shirt",
                price = "55,99 PLN",
                description = productDescription,
                category = Category.Women.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 2,
                title = "Cargo Pants",
                price = "90,00 PLN",
                description = productDescription,
                category = Category.Men.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 3,
                title = "Skirt",
                price = "78,78 PLN",
                description = productDescription,
                category = Category.Men.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 4,
                title = "Jeans",
                price = "235,99 PLN",
                description = productDescription,
                category = Category.Men.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 5,
                title = "Shirt",
                price = "85,99 PLN",
                description = productDescription,
                category = Category.Women.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 6,
                title = "Blouse",
                price = "99,99 PLN",
                description = productDescription,
                category = Category.Men.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            )
        ).shuffled()

        cartProducts = listOf(
            CartProduct(
                id = 1,
                title = "Polo Shirt",
                price = "55,99 PLN",
                imageUrl = "imageUrl",
                amount = 2
            ),
            CartProduct(
                id = 3,
                title = "Skirt",
                price = "78,78 PLN",
                imageUrl = "imageUrl",
                amount = 1
            ),
            CartProduct(
                id = 4,
                title = "Jeans",
                price = "235,99 PLN",
                imageUrl = "imageUrl",
                amount = 3
            ),
            CartProduct(
                id = 6,
                title = "Blouse",
                price = "99,99 PLN",
                imageUrl = "imageUrl",
                amount = 7
            )
        ).shuffled()
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    private fun setViewModel(): CartViewModel {
        return CartViewModel(shopUseCases)
    }

    private fun getCurrentCartState(): CartState {
        return cartViewModel.cartState.value
    }

    @Test
    fun `checkIfUserIsLoggedIn is successful`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(emptyList()))

        cartViewModel = setViewModel()

        val isUserLoggedIn = getCurrentCartState().isUserLoggedIn

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
        }
        assertThat(isUserLoggedIn).isTrue()
    }

    @Test
    fun `cart products are set correctly on init`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase(Category.All.id)
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts

        cartViewModel = setViewModel()

        val resultCartProducts = getCurrentCartState().cartProducts

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase(Category.All.id)
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        }
        assertThat(resultCartProducts).containsExactlyElementsIn(cartProducts)
    }

    @Test
    fun `get cart user items result is successful`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase(Category.All.id)
        } returns flowOf(Resource.Success(emptyList()))

        cartViewModel = setViewModel()

        val cartItemsState = getCurrentCartState().cartItems
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase(Category.All.id)
        }
        assertThat(cartItemsState).containsExactlyElementsIn(cartItems)
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `get cart user items result is error`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Error("Error"))

        cartViewModel = setViewModel()

        val cartItemsState = getCurrentCartState().cartItems
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
        }
        assertThat(cartItemsState).isEmpty()
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `get cart user items result is loading`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Loading(true))

        cartViewModel = setViewModel()

        val cartItemsState = getCurrentCartState().cartItems
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
        }
        assertThat(cartItemsState).isEmpty()
        assertThat(isLoading).isTrue()
    }

    @Test
    fun `get products result is successful`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase(Category.All.id)
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts

        cartViewModel = setViewModel()

        val cartItemsState = getCurrentCartState().cartItems
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase(Category.All.id)
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        }
        assertThat(cartItemsState).containsExactlyElementsIn(cartItems)
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `get products result is error`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase(Category.All.id)
        } returns flowOf(Resource.Error("Error"))

        cartViewModel = setViewModel()

        val cartItemsState = getCurrentCartState().cartItems
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase(Category.All.id)
        }
        assertThat(cartItemsState).containsExactlyElementsIn(cartItems)
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `get products result is loading`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase(Category.All.id)
        } returns flowOf(Resource.Loading(true))

        cartViewModel = setViewModel()

        val cartItemsState = getCurrentCartState().cartItems
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase(Category.All.id)
        }
        assertThat(cartItemsState).containsExactlyElementsIn(cartItems)
        assertThat(isLoading).isTrue()
    }
}