package com.example.shopapp.presentation.cart

import com.example.shopapp.domain.model.CartItem
import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.domain.model.Order
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
    private lateinit var cartItem: CartItem
    private lateinit var cartItemToUpdate: CartItem
    private lateinit var cartItemToUpdate2: CartItem
    private lateinit var products: List<Product>
    private lateinit var cartProducts: List<CartProduct>
    private lateinit var restoredCartItem: CartItem
    private lateinit var order: Order
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

        cartItem = CartItem(
            cartItemId = "cartItemId2",
            userUID = "userUID",
            productId = 3,
            amount = 1
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
                category = Category.Women.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 2,
                title = "Cargo Pants",
                price = 90.00,
                description = productDescription,
                category = Category.Men.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 3,
                title = "Skirt",
                price = 78.78,
                description = productDescription,
                category = Category.Men.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 4,
                title = "Jeans",
                price = 235.99,
                description = productDescription,
                category = Category.Men.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 5,
                title = "Shirt",
                price = 85.99,
                description = productDescription,
                category = Category.Women.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 6,
                title = "Blouse",
                price = 99.99,
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

        restoredCartItem = CartItem(
            cartItemId = "cartItemId22",
            userUID = "userUID",
            productId = 3,
            amount = 1
        )

        order = Order(
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

    @Test
    fun `fun getCartItemFromCartProduct returns correct result`() {
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

        val resultCartItem = cartViewModel.getCartItemFromCartProductId(3)
        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase(Category.All.id)
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        }
        assertThat(resultCartItem).isEqualTo(cartItem)
    }

    @Test
    fun `total amount of products in the cart is correctly calculated`() {
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

        cartViewModel.calculateTotalAmount()
        val totalAmount = String.format("%.2f",getCurrentCartState().totalAmount)

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase(Category.All.id)
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        }
        assertThat(totalAmount).isEqualTo("1598,66")
    }

    @Test
    fun `update product amount in cart result is successful`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase(Category.All.id)
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
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
            shopUseCases.getProductsUseCase(Category.All.id)
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
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
            shopUseCases.getProductsUseCase(Category.All.id)
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
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
            shopUseCases.getProductsUseCase(Category.All.id)
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
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
            shopUseCases.getProductsUseCase(Category.All.id)
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
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
            shopUseCases.getProductsUseCase(Category.All.id)
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
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
            shopUseCases.getProductsUseCase(Category.All.id)
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
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
            shopUseCases.getProductsUseCase(Category.All.id)
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
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
            shopUseCases.getProductsUseCase(Category.All.id)
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.addProductToCartUseCase("userUID",3,1)
        } returns flowOf(Resource.Success(true))

        cartViewModel = setViewModel()

        cartViewModel.restoreProductToCart(restoredCartItem)
        val loadingState = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase(Category.All.id)
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
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
            shopUseCases.getProductsUseCase(Category.All.id)
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.addProductToCartUseCase("userUID",3,1)
        } returns flowOf(Resource.Loading(true))

        cartViewModel = setViewModel()

        cartViewModel.restoreProductToCart(restoredCartItem)
        val loadingState = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase(Category.All.id)
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.addProductToCartUseCase("userUID",3,1)
        }
        assertThat(loadingState).isTrue()
    }

    @Test
    fun `place order was successful`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase(Category.All.id)
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.addOrderUseCase(order)
        } returns flowOf(Resource.Success(true))
        coEvery {
            shopUseCases.deleteProductFromCartUseCase(any())
        } returns flowOf(Resource.Success(true))

        cartViewModel = setViewModel()

        cartViewModel.addOrder(order)
        val loadingState = getCurrentCartState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase(Category.All.id)
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.addOrderUseCase(order)
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.deleteProductFromCartUseCase(any())
        }
        assertThat(loadingState).isFalse()
    }

    @Test
    fun `event onPlus`() {
        coEvery {
            shopUseCases.getUserCartItemsUseCase("userUID")
        } returns flowOf(Resource.Success(cartItems))
        coEvery {
            shopUseCases.getProductsUseCase(Category.All.id)
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
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
            shopUseCases.getProductsUseCase(Category.All.id)
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
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
            shopUseCases.getProductsUseCase(Category.All.id)
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
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
            shopUseCases.getProductsUseCase(Category.All.id)
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
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
            shopUseCases.getProductsUseCase(Category.All.id)
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
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
            shopUseCases.getProductsUseCase(Category.All.id)
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
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
            shopUseCases.getProductsUseCase(Category.All.id)
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
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
            shopUseCases.getProductsUseCase(Category.All.id)
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
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
            shopUseCases.getProductsUseCase(Category.All.id)
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.addOrderUseCase(any())
        } returns flowOf(Resource.Success(true))
        coEvery {
            shopUseCases.deleteProductFromCartUseCase(any())
        } returns flowOf(Resource.Success(true))

        cartViewModel = setViewModel()

        val initialState = getCurrentCartState()
        cartViewModel.onEvent(CartEvent.OnOrderPlaced)
        val resultState = getCurrentCartState()

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase(Category.All.id)
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
            shopUseCases.addOrderUseCase(any())
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.deleteProductFromCartUseCase(any())
            shopUseCases.deleteProductFromCartUseCase(any())
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
            shopUseCases.getProductsUseCase(Category.All.id)
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        } returns cartProducts
        coEvery {
            shopUseCases.deleteProductFromCartUseCase(any())
        } returns flowOf(Resource.Success(true))

        cartViewModel = setViewModel()

        cartViewModel.onEvent(CartEvent.OnGoHome)

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserCartItemsUseCase("userUID")
            shopUseCases.getProductsUseCase(Category.All.id)
            shopUseCases.setUserCartProductsUseCase(cartItems,products)
        }
    }
}