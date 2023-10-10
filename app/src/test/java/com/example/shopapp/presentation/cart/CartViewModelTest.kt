package com.example.shopapp.presentation.cart

import com.example.shopapp.domain.model.CartItem
import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.domain.model.Coupon
import com.example.shopapp.domain.model.FirebaseOrder
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.use_case.ShopUseCases
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
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date

class CartViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var shopUseCases: ShopUseCases
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartItems: List<CartItem>
    private lateinit var lowPriceCartItems: List<CartItem>
    private lateinit var cartItem: CartItem
    private lateinit var cartItemToUpdate: CartItem
    private lateinit var cartItemToUpdate2: CartItem
    private lateinit var products: List<Product>
    private lateinit var cartProducts: List<CartProduct>
    private lateinit var lowPriceCartProducts: List<CartProduct>
    private lateinit var restoredCartItem: CartItem
    private lateinit var firebaseOrder: FirebaseOrder
    @MockK
    private lateinit var user: FirebaseUser
    private lateinit var date: Date
    private lateinit var currentDate: Date
    private lateinit var coupon: Coupon
    private lateinit var expiredCoupon: Coupon

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

        cartItem = CartItem(
            cartItemId = "cartItemId2",
            userUID = "userUID",
            productId = 3,
            amount = 1
        )

        lowPriceCartItems = listOf(
            CartItem(
                cartItemId = "cartItemId5",
                userUID = "userUID",
                productId = 7,
                amount = 1
            )
        )

        cartItemToUpdate = CartItem(
            cartItemId = "cartItemId2",
            userUID = "userUID",
            productId = 3,
            amount = 2
        )

        cartItemToUpdate2 = CartItem(
            cartItemId = "cartItemId1",
            userUID = "userUID",
            productId = 1,
            amount = 1
        )

        products = listOf(
            Product(
                id = 1,
                title = "Polo Shirt",
                price = 55.99,
                description = productDescription,
                category = "women's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 2,
                title = "Cargo Pants",
                price = 90.00,
                description = productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 3,
                title = "Skirt",
                price = 78.78,
                description = productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 4,
                title = "Jeans",
                price = 235.99,
                description = productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 5,
                title = "Shirt",
                price = 85.99,
                description = productDescription,
                category = "women's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 6,
                title = "Blouse",
                price = 99.99,
                description = productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 7,
                title = "Socks",
                price = 10.00,
                description = productDescription,
                category = "women's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            )
        ).shuffled()

        cartProducts = listOf(
            CartProduct(
                id = 1,
                title = "Polo Shirt",
                price = 55.99,
                imageUrl = "imageUrl",
                amount = 2
            ),
            CartProduct(
                id = 3,
                title = "Skirt",
                price = 78.78,
                imageUrl = "imageUrl",
                amount = 1
            ),
            CartProduct(
                id = 4,
                title = "Jeans",
                price = 235.99,
                imageUrl = "imageUrl",
                amount = 3
            ),
            CartProduct(
                id = 6,
                title = "Blouse",
                price = 99.99,
                imageUrl = "imageUrl",
                amount = 7
            )
        ).shuffled()

        lowPriceCartProducts = listOf(
            CartProduct(
                id = 7,
                title = "Socks",
                price = 10.00,
                imageUrl = "imageUrl",
                amount = 1
            )
        )

        restoredCartItem = CartItem(
            cartItemId = "cartItemId22",
            userUID = "userUID",
            productId = 3,
            amount = 1
        )

        firebaseOrder = FirebaseOrder(
            orderId = "",
            userUID = "userUID",
            date = Date(),
            totalAmount = 1598.66,
            products = mapOf(
                Pair("3",1),
                Pair("1",2),
                Pair("4",3),
                Pair("6",7)
            )
        )

        date = Date(2023,7,11)
        currentDate = Date()

        coupon = Coupon(
            userUID = "userUID",
            amount = 20,
            activationDate = currentDate
        )

        expiredCoupon = Coupon(
            userUID = "userUID",
            amount = 20,
            activationDate = date
        )
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
        } returns flowOf(Resource.Error("Error"))

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
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(any(),any())
        } returns false

        cartViewModel = setViewModel()

        val resultCartProducts = getCurrentCartState().cartProducts

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(any(),any())
        }
        assertThat(resultCartProducts).containsExactlyElementsIn(cartProducts)
    }

    @Test
    fun `get cart user items result is successful`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(emptyList()))

        cartViewModel = setViewModel()

        val cartItemsState = getCurrentCartState().cartItems
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
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
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(any(),any())
        } returns false

        cartViewModel = setViewModel()

        val cartItemsState = getCurrentCartState().cartItems
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(any(),any())
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
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Error("Error"))

        cartViewModel = setViewModel()

        val cartItemsState = getCurrentCartState().cartItems
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
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
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Loading(true))

        cartViewModel = setViewModel()

        val cartItemsState = getCurrentCartState().cartItems
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
        }
        assertThat(cartItemsState).containsExactlyElementsIn(cartItems)
        assertThat(isLoading).isTrue()
    }

    @Test
    fun `fun getCartItemFromCartProduct returns correct result`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(any(),any())
        } returns false

        cartViewModel = setViewModel()

        val resultCartItem = cartViewModel.getCartItemFromCartProductId(3)
        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(any(),any())
        }
        assertThat(resultCartItem).isEqualTo(cartItem)
    }

    @Test
    fun `get user coupon is successful - coupon already activated`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(coupon.activationDate,any())
        } returns false

        cartViewModel = setViewModel()
        val isUserLoggedIn = getCurrentCartState().isUserLoggedIn
        val resultCoupon = getCurrentCartState().coupon
        val resultIsCouponActivated = getCurrentCartState().isCouponActivated
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(coupon.activationDate,any())
        }
        assertThat(isUserLoggedIn).isTrue()
        assertThat(resultCoupon).isEqualTo(coupon)
        assertThat(resultIsCouponActivated).isTrue()
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `get user coupon is successful - coupon not yet activated`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(null))

        cartViewModel = setViewModel()
        val isUserLoggedIn = getCurrentCartState().isUserLoggedIn
        val resultIsCouponActivated = getCurrentCartState().isCouponActivated
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
        }
        assertThat(isUserLoggedIn).isTrue()
        assertThat(resultIsCouponActivated).isFalse()
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `get user coupon is not successful and returns error`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Error("Error"))

        cartViewModel = setViewModel()
        val isUserLoggedIn = getCurrentCartState().isUserLoggedIn
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
        }
        assertThat(isUserLoggedIn).isTrue()
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `get user coupon is loading`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Loading(true))

        cartViewModel = setViewModel()
        val isUserLoggedIn = getCurrentCartState().isUserLoggedIn
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
        }
        assertThat(isUserLoggedIn).isTrue()
        assertThat(isLoading).isTrue()
    }

    @Test
    fun `delete coupon is successful - activated coupon is expired`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(expiredCoupon))
        every {
            shopUseCases.isCouponExpiredUseCase(expiredCoupon.activationDate,any())
        } returns true
        coEvery {
            shopUseCases.deleteCouponUseCase("userUID")
        } returns flowOf(Resource.Success(true))

        cartViewModel = setViewModel()
        val isUserLoggedIn = getCurrentCartState().isUserLoggedIn
        val resultIsCouponActivated = getCurrentCartState().isCouponActivated
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(expiredCoupon.activationDate,any())
            shopUseCases.deleteCouponUseCase("userUID")
        }
        assertThat(isUserLoggedIn).isTrue()
        assertThat(resultIsCouponActivated).isFalse()
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `coupon date is passed correctly`() {
        val dateSlot = slot<Date>()

        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(capture(dateSlot),any())
        } returns false

        cartViewModel = setViewModel()
        val isUserLoggedIn = getCurrentCartState().isUserLoggedIn
        val resultIsCouponActivated = getCurrentCartState().isCouponActivated
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(dateSlot.captured,any())
        }
        assertThat(isUserLoggedIn).isTrue()
        assertThat(resultIsCouponActivated).isTrue()
        assertThat(isLoading).isFalse()
        assertThat(dateSlot.captured).isEqualTo(coupon.activationDate)
    }

    @Test
    fun `delete coupon is not successful and returns error`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(expiredCoupon))
        every {
            shopUseCases.isCouponExpiredUseCase(expiredCoupon.activationDate,any())
        } returns true
        coEvery {
            shopUseCases.deleteCouponUseCase("userUID")
        } returns flowOf(Resource.Error("Error"))

        cartViewModel = setViewModel()
        val isUserLoggedIn = getCurrentCartState().isUserLoggedIn
        val resultIsCouponActivated = getCurrentCartState().isCouponActivated
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(expiredCoupon.activationDate,any())
            shopUseCases.deleteCouponUseCase("userUID")
        }
        assertThat(isUserLoggedIn).isTrue()
        assertThat(resultIsCouponActivated).isFalse()
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `delete coupon is loading`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(expiredCoupon))
        every {
            shopUseCases.isCouponExpiredUseCase(expiredCoupon.activationDate,any())
        } returns true
        coEvery {
            shopUseCases.deleteCouponUseCase("userUID")
        } returns flowOf(Resource.Loading(true))

        cartViewModel = setViewModel()
        val isUserLoggedIn = getCurrentCartState().isUserLoggedIn
        val resultIsCouponActivated = getCurrentCartState().isCouponActivated
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(expiredCoupon.activationDate,any())
            shopUseCases.deleteCouponUseCase("userUID")
        }
        assertThat(isUserLoggedIn).isTrue()
        assertThat(resultIsCouponActivated).isFalse()
        assertThat(isLoading).isTrue()
    }

    @Test
    fun `total amount in the cart is correctly calculated`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(Coupon()))
        every {
            shopUseCases.isCouponExpiredUseCase(any(),any())
        } returns true
        coEvery {
            shopUseCases.deleteCouponUseCase("userUID")
        } returns flowOf(Resource.Success(true))

        cartViewModel = setViewModel()

        cartViewModel.calculateTotalAmount()
        val totalAmount = String.format("%.2f",getCurrentCartState().totalAmount)

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(any(),any())
            shopUseCases.deleteCouponUseCase("userUID")
        }
        assertThat(totalAmount).isEqualTo("1598,66")
    }

    @Test
    fun `total amount is correctly calculated when coupon is activated and total amount is above required`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(any(),any())
        } returns false

        cartViewModel = setViewModel()

        cartViewModel.calculateTotalAmount()
        val totalAmount = String.format("%.2f",getCurrentCartState().totalAmount)

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(any(),any())
        }
        assertThat(totalAmount).isEqualTo("1578,66")
    }

    @Test
    fun `total amount is correctly calculated when coupon is activated and total amount is below required`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(lowPriceCartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(lowPriceCartItems,products)
        } returns lowPriceCartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(Coupon()))
        every {
            shopUseCases.isCouponExpiredUseCase(any(),any())
        } returns false

        cartViewModel = setViewModel()

        cartViewModel.calculateTotalAmount()
        val totalAmount = String.format("%.2f",getCurrentCartState().totalAmount)

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(lowPriceCartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(any(),any())
        }
        assertThat(totalAmount).isEqualTo("10,00")
    }

    @Test
    fun `update product amount in cart result is successful`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(any(),any())
        } returns false
        coEvery {
            shopUseCases.updateProductInCartUseCase(cartItemToUpdate)
        } returns flowOf(Resource.Success(true))

        cartViewModel = setViewModel()

        cartViewModel.updateCartItem(cartItem,2)

        val cartItemsState = getCurrentCartState().cartItems
        val cartProductsState = getCurrentCartState().cartProducts
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(any(),any())
            shopUseCases.updateProductInCartUseCase(cartItemToUpdate)
        }
        assertThat(cartItemsState).containsExactlyElementsIn(cartItems)
        assertThat(cartProductsState).containsExactlyElementsIn(cartProducts)
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `update product amount in cart result is loading`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(any(),any())
        } returns false
        coEvery {
            shopUseCases.updateProductInCartUseCase(cartItemToUpdate)
        } returns flowOf(Resource.Loading(true))

        cartViewModel = setViewModel()

        cartViewModel.updateCartItem(cartItem,2)

        val cartItemsState = getCurrentCartState().cartItems
        val cartProductsState = getCurrentCartState().cartProducts
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(any(),any())
            shopUseCases.updateProductInCartUseCase(cartItemToUpdate)
        }
        assertThat(cartItemsState).containsExactlyElementsIn(cartItems)
        assertThat(cartProductsState).containsExactlyElementsIn(cartProducts)
        assertThat(isLoading).isTrue()
    }

    @Test
    fun `delete product from cart result is successful`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(any(),any())
        } returns false
        coEvery {
            shopUseCases.deleteProductFromCartUseCase("cartItemId2")
        } returns flowOf(Resource.Success(true))

        cartViewModel = setViewModel()

        cartViewModel.deleteCartItem("cartItemId2")

        val cartItemsState = getCurrentCartState().cartItems
        val cartProductsState = getCurrentCartState().cartProducts
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(any(),any())
            shopUseCases.deleteProductFromCartUseCase("cartItemId2")
        }
        assertThat(cartItemsState).containsExactlyElementsIn(cartItems)
        assertThat(cartProductsState).containsExactlyElementsIn(cartProducts)
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `delete products from cart result is loading`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(any(),any())
        } returns false
        coEvery {
            shopUseCases.deleteProductFromCartUseCase("cartItemId2")
        } returns flowOf(Resource.Loading(true))

        cartViewModel = setViewModel()

        cartViewModel.deleteCartItem("cartItemId2")

        val cartItemsState = getCurrentCartState().cartItems
        val cartProductsState = getCurrentCartState().cartProducts
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(any(),any())
            shopUseCases.deleteProductFromCartUseCase("cartItemId2")
        }
        assertThat(cartItemsState).containsExactlyElementsIn(cartItems)
        assertThat(cartProductsState).containsExactlyElementsIn(cartProducts)
        assertThat(isLoading).isTrue()
    }

    @Test
    fun `restore product to cart is successful`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(any(),any())
        } returns false
        coEvery {
            shopUseCases.addProductToCartUseCase("userUID",3,1)
        } returns flowOf(Resource.Success(true))

        cartViewModel = setViewModel()

        cartViewModel.restoreProductToCart(restoredCartItem)
        val loadingState = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(any(),any())
            shopUseCases.addProductToCartUseCase("userUID",3,1)
        }
        assertThat(loadingState).isFalse()
    }

    @Test
    fun `add product to cart is loading - product was not in cart yet`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(any(),any())
        } returns false
        coEvery {
            shopUseCases.addProductToCartUseCase("userUID",3,1)
        } returns flowOf(Resource.Loading(true))

        cartViewModel = setViewModel()

        cartViewModel.restoreProductToCart(restoredCartItem)
        val loadingState = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(any(),any())
            shopUseCases.addProductToCartUseCase("userUID",3,1)
        }
        assertThat(loadingState).isTrue()
    }

    @Test
    fun `get user points is loading`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(any(),any())
        } returns false
        coEvery {
            shopUseCases.addOrderUseCase(firebaseOrder)
        } returns flowOf(Resource.Success(true))
        coEvery {
            shopUseCases.deleteProductFromCartUseCase(any())
        } returns flowOf(Resource.Success(true))
        coEvery {
            shopUseCases.getUserPointsUseCase("userUID")
        } returns flowOf(Resource.Loading(true))

        cartViewModel = setViewModel()

        cartViewModel.addOrder(firebaseOrder)
        val loadingState = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(any(),any())
            shopUseCases.addOrderUseCase(firebaseOrder)
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.getUserPointsUseCase("userUID")
        }
        assertThat(loadingState).isTrue()
    }

    @Test
    fun `update user points is loading`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(any(),any())
        } returns false
        coEvery {
            shopUseCases.addOrderUseCase(firebaseOrder)
        } returns flowOf(Resource.Success(true))
        coEvery {
            shopUseCases.deleteProductFromCartUseCase(any())
        } returns flowOf(Resource.Success(true))
        coEvery {
            shopUseCases.getUserPointsUseCase("userUID")
        } returns flowOf(Resource.Success(100))
        coEvery {
            shopUseCases.updateUserPointsUseCase("userUID",1698)
        } returns flowOf(Resource.Loading(true))

        cartViewModel = setViewModel()

        cartViewModel.addOrder(firebaseOrder)
        val loadingState = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(any(),any())
            shopUseCases.addOrderUseCase(firebaseOrder)
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.getUserPointsUseCase("userUID")
            shopUseCases.updateUserPointsUseCase("userUID",1698)
        }
        assertThat(loadingState).isTrue()
    }

    @Test
    fun `amount of user points after placing order is calculated correctly`() {
        val userUIDSlot = slot<String>()
        val userPointsSlot = slot<Int>()

        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(any(),any())
        } returns false
        coEvery {
            shopUseCases.addOrderUseCase(firebaseOrder)
        } returns flowOf(Resource.Success(true))
        coEvery {
            shopUseCases.deleteProductFromCartUseCase(any())
        } returns flowOf(Resource.Success(true))
        coEvery {
            shopUseCases.getUserPointsUseCase("userUID")
        } returns flowOf(Resource.Success(100))
        coEvery {
            shopUseCases.updateUserPointsUseCase(capture(userUIDSlot),capture(userPointsSlot))
        } returns flowOf(Resource.Success(true))

        cartViewModel = setViewModel()

        cartViewModel.addOrder(firebaseOrder)
        val loadingState = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(any(),any())
            shopUseCases.addOrderUseCase(firebaseOrder)
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.getUserPointsUseCase("userUID")
            shopUseCases.updateUserPointsUseCase(userUIDSlot.captured,userPointsSlot.captured)
        }
        assertThat(loadingState).isFalse()
        assertThat(userUIDSlot.captured).isEqualTo("userUID")
        assertThat(userPointsSlot.captured).isEqualTo(1698)
    }

    @Test
    fun `place order was successful`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(any(),any())
        } returns false
        coEvery {
            shopUseCases.addOrderUseCase(firebaseOrder)
        } returns flowOf(Resource.Success(true))
        coEvery {
            shopUseCases.deleteProductFromCartUseCase(any())
        } returns flowOf(Resource.Success(true))
        coEvery {
            shopUseCases.getUserPointsUseCase("userUID")
        } returns flowOf(Resource.Success(100))
        coEvery {
            shopUseCases.updateUserPointsUseCase("userUID",1698)
        } returns flowOf(Resource.Success(true))

        cartViewModel = setViewModel()

        cartViewModel.addOrder(firebaseOrder)
        val loadingState = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(any(),any())
            shopUseCases.addOrderUseCase(firebaseOrder)
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.getUserPointsUseCase("userUID")
            shopUseCases.updateUserPointsUseCase("userUID",1698)
        }
        assertThat(loadingState).isFalse()
    }

    @Test
    fun `event onPlus`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(any(),any())
        } returns false
        coEvery {
            shopUseCases.updateProductInCartUseCase(cartItemToUpdate)
        } returns flowOf(Resource.Success(true))

        cartViewModel = setViewModel()

        cartViewModel.onEvent(CartEvent.OnPlus(3))

        val cartItemsState = getCurrentCartState().cartItems
        val cartProductsState = getCurrentCartState().cartProducts
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(any(),any())
            shopUseCases.updateProductInCartUseCase(cartItemToUpdate)
        }
        assertThat(cartItemsState).containsExactlyElementsIn(cartItems)
        assertThat(cartProductsState).containsExactlyElementsIn(cartProducts)
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `event onMinus - product amount is more than 1`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(any(),any())
        } returns false
        coEvery {
            shopUseCases.updateProductInCartUseCase(cartItemToUpdate2)
        } returns flowOf(Resource.Success(true))

        cartViewModel = setViewModel()

        cartViewModel.onEvent(CartEvent.OnMinus(1))

        val cartItemsState = getCurrentCartState().cartItems
        val cartProductsState = getCurrentCartState().cartProducts
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(any(),any())
            shopUseCases.updateProductInCartUseCase(cartItemToUpdate2)
        }
        assertThat(cartItemsState).containsExactlyElementsIn(cartItems)
        assertThat(cartProductsState).containsExactlyElementsIn(cartProducts)
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `event onMinus - product amount is equal 1`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(any(),any())
        } returns false
        coEvery {
            shopUseCases.deleteProductFromCartUseCase("cartItemId2")
        } returns flowOf(Resource.Success(true))

        cartViewModel = setViewModel()

        cartViewModel.onEvent(CartEvent.OnMinus(3))

        val cartItemsState = getCurrentCartState().cartItems
        val cartProductsState = getCurrentCartState().cartProducts
        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(any(),any())
            shopUseCases.deleteProductFromCartUseCase("cartItemId2")
        }
        assertThat(cartItemsState).containsExactlyElementsIn(cartItems)
        assertThat(cartProductsState).containsExactlyElementsIn(cartProducts)
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `event onDelete and onRestore - restore product to the cart after delete is successful`() {
        val cartItemIdSlot = slot<String>()
        val userSlot = slot<String>()
        val productIdSlot = slot<Int>()
        val amountSlot = slot<Int>()

        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(any(),any())
        } returns false
        coEvery {
            shopUseCases.deleteProductFromCartUseCase(capture(cartItemIdSlot))
        } returns flowOf(Resource.Success(true))
        coEvery {
            shopUseCases.addProductToCartUseCase(
                capture(userSlot),
                capture(productIdSlot),
                capture(amountSlot)
            )
        } returns flowOf(Resource.Success(true))

        cartViewModel = setViewModel()

        cartViewModel.onEvent(CartEvent.OnDelete(3))
        cartViewModel.onEvent(CartEvent.OnCartItemRestore)

        val isLoading = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(any(),any())
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.addProductToCartUseCase(any(),any(),any())
        }
        assertThat(isLoading).isFalse()
        assertThat(cartItemIdSlot.captured).isEqualTo("cartItemId2")
        assertThat(userSlot.captured).isEqualTo("userUID")
        assertThat(productIdSlot.captured).isEqualTo(3)
        assertThat(amountSlot.captured).isEqualTo(1)
    }

    @Test
    fun `event onPlaceOrder - dialog state is set correctly`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(any(),any())
        } returns false
        coEvery {
            shopUseCases.addOrderUseCase(any())
        } returns flowOf(Resource.Success(true))
        coEvery {
            shopUseCases.deleteProductFromCartUseCase(any())
        } returns flowOf(Resource.Success(true))
        coEvery {
            shopUseCases.getUserPointsUseCase("userUID")
        } returns flowOf(Resource.Success(100))
        coEvery {
            shopUseCases.updateUserPointsUseCase("userUID",1698)
        } returns flowOf(Resource.Success(true))

        cartViewModel = setViewModel()

        val initialState = getCurrentCartState()
        cartViewModel.onEvent(CartEvent.OnOrderPlaced)
        val resultState = getCurrentCartState()

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(any(),any())
            shopUseCases.addOrderUseCase(any())
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.getUserPointsUseCase("userUID")
            shopUseCases.updateUserPointsUseCase("userUID",1698)
        }
        assertThat(initialState.isDialogActivated).isFalse()
        assertThat(resultState.isDialogActivated).isTrue()
    }

    @Test
    fun `event onGoHome - cart is empty after placing order`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(any(),any())
        } returns false
        coEvery {
            shopUseCases.deleteProductFromCartUseCase(any())
        } returns flowOf(Resource.Success(true))

        cartViewModel = setViewModel()

        cartViewModel.onEvent(CartEvent.OnGoHome)

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(any(),any())
        }
    }
}