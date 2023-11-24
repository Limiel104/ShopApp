package com.example.shopapp.presentation.orders

import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.domain.model.FirebaseOrder
import com.example.shopapp.domain.model.Order
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.domain.util.OrderOrder
import com.example.shopapp.presentation.common.Constants
import com.example.shopapp.util.MainDispatcherRule
import com.example.shopapp.domain.util.Resource
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.confirmVerified
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

class OrdersViewModelTest {

    @MockK
    private lateinit var shopUseCases: ShopUseCases
    private lateinit var ordersViewModel: OrdersViewModel
    private lateinit var orders: List<Order>
    @MockK
    private lateinit var user: FirebaseUser
    private lateinit var products: List<Product>
    private lateinit var firebaseOrders: List<FirebaseOrder>
    private lateinit var date1: Date
    private lateinit var date2: Date
    private lateinit var date3: Date
    private lateinit var ordersAfterChnageExpandState: List<Order>
    private lateinit var ordersSortedDesc: List<Order>

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { shopUseCases.getCurrentUserUseCase() } returns user
        every { user.uid } returns "userUID"

        date1 = Date(2023,9,18)
        date2 = Date(2023,9,21)
        date3 = Date(2023,9,27)

        firebaseOrders = listOf(
            FirebaseOrder(
                orderId = "orderId1",
                userUID = "userUID",
                date = date3,
                totalAmount = 290.75,
                products = mapOf(
                    Pair("1",2),
                    Pair("3",1),
                    Pair("6",1)
                )
            ),
            FirebaseOrder(
                orderId = "orderId2",
                userUID = "userUID",
                date = date1,
                totalAmount = 347.97,
                products = mapOf(
                    Pair("2",1),
                    Pair("5",3)
                )
            ),
            FirebaseOrder(
                orderId = "orderId3",
                userUID = "userUID",
                date = date2,
                totalAmount = 471.98,
                products = mapOf(
                    Pair("4",2)
                )
            )
        )

        products = listOf(
            Product(
                id = 1,
                title = "Polo Shirt",
                price = 55.99,
                description = Constants.productDescription,
                category = "women's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 2,
                title = "Cargo Pants",
                price = 90.00,
                description = Constants.productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 3,
                title = "Skirt",
                price = 78.78,
                description = Constants.productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 4,
                title = "Jeans",
                price = 235.99,
                description = Constants.productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 5,
                title = "Shirt",
                price = 85.99,
                description = Constants.productDescription,
                category = "women's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 6,
                title = "Blouse",
                price = 99.99,
                description = Constants.productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            )
        ).shuffled()

        orders = listOf(
            Order(
                orderId = "orderId1",
                date = date3,
                totalAmount = 290.75,
                products = listOf(
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
                        id = 6,
                        title = "Blouse",
                        price = 99.99,
                        imageUrl = "imageUrl",
                        amount = 1
                    )
                ),
                isExpanded = false
            ),
            Order(
                orderId = "orderId2",
                date = date1,
                totalAmount = 347.97,
                products = listOf(
                    CartProduct(
                        id = 2,
                        title = "Cargo Pants",
                        price = 90.00,
                        imageUrl = "imageUrl",
                        amount = 1
                    ),
                    CartProduct(
                        id = 5,
                        title = "Shirt",
                        price = 85.99,
                        imageUrl = "imageUrl",
                        amount = 3
                    )
                ),
                isExpanded = false
            ),
            Order(
                orderId = "orderId3",
                date = date2,
                totalAmount = 471.98,
                products = listOf(
                    CartProduct(
                        id = 4,
                        title = "Jeans",
                        price = 235.99,
                        imageUrl = "imageUrl",
                        amount = 1
                    )
                ),
                isExpanded = false
            )
        )

        ordersAfterChnageExpandState = listOf(
            Order(
                orderId = "orderId1",
                date = date3,
                totalAmount = 290.75,
                products = listOf(
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
                        id = 6,
                        title = "Blouse",
                        price = 99.99,
                        imageUrl = "imageUrl",
                        amount = 1
                    )
                ),
                isExpanded = true
            ),
            Order(
                orderId = "orderId2",
                date = date1,
                totalAmount = 347.97,
                products = listOf(
                    CartProduct(
                        id = 2,
                        title = "Cargo Pants",
                        price = 90.00,
                        imageUrl = "imageUrl",
                        amount = 1
                    ),
                    CartProduct(
                        id = 5,
                        title = "Shirt",
                        price = 85.99,
                        imageUrl = "imageUrl",
                        amount = 3
                    )
                ),
                isExpanded = false
            ),
            Order(
                orderId = "orderId3",
                date = date2,
                totalAmount = 471.98,
                products = listOf(
                    CartProduct(
                        id = 4,
                        title = "Jeans",
                        price = 235.99,
                        imageUrl = "imageUrl",
                        amount = 1
                    )
                ),
                isExpanded = false
            )
        )

        ordersSortedDesc = listOf(
            Order(
                orderId = "orderId1",
                date = date3,
                totalAmount = 290.75,
                products = listOf(
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
                        id = 6,
                        title = "Blouse",
                        price = 99.99,
                        imageUrl = "imageUrl",
                        amount = 1
                    )
                ),
                isExpanded = false
            ),
            Order(
                orderId = "orderId3",
                date = date2,
                totalAmount = 471.98,
                products = listOf(
                    CartProduct(
                        id = 4,
                        title = "Jeans",
                        price = 235.99,
                        imageUrl = "imageUrl",
                        amount = 1
                    )
                ),
                isExpanded = false
            ),
            Order(
                orderId = "orderId2",
                date = date1,
                totalAmount = 347.97,
                products = listOf(
                    CartProduct(
                        id = 2,
                        title = "Cargo Pants",
                        price = 90.00,
                        imageUrl = "imageUrl",
                        amount = 1
                    ),
                    CartProduct(
                        id = 5,
                        title = "Shirt",
                        price = 85.99,
                        imageUrl = "imageUrl",
                        amount = 3
                    )
                ),
                isExpanded = false
            )
        )
    }

    @After
    fun tearDown() {
        confirmVerified(shopUseCases)
        clearAllMocks()
    }

    private fun setViewModel(): OrdersViewModel {
        return OrdersViewModel(shopUseCases)
    }

    private fun getCurrentOrdersState(): OrdersState {
        return ordersViewModel.ordersState.value
    }

    @Test
    fun `get orders result is successful`() {
        coEvery {
            shopUseCases.getUserOrdersUseCase("userUID")
        } returns flowOf(Resource.Success(firebaseOrders))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Error("Error"))

        ordersViewModel = setViewModel()
        val resultFirebaseOrders = getCurrentOrdersState().firebaseOrders

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserOrdersUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
        }
        assertThat(resultFirebaseOrders).isEqualTo(firebaseOrders)
    }

    @Test
    fun `get orders result is error`() {
        coEvery {
            shopUseCases.getUserOrdersUseCase("userUID")
        } returns flowOf(Resource.Error("Error"))

        ordersViewModel = setViewModel()
        val resultFirebaseOrders = getCurrentOrdersState().firebaseOrders

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserOrdersUseCase("userUID")
        }
        assertThat(resultFirebaseOrders).isEmpty()
    }

    @Test
    fun `get orders result is loading`() {
        coEvery {
            shopUseCases.getUserOrdersUseCase("userUID")
        } returns flowOf(Resource.Loading(true))

        ordersViewModel = setViewModel()
        val resultFirebaseOrders = getCurrentOrdersState().firebaseOrders
        val loadingState = getCurrentOrdersState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserOrdersUseCase("userUID")
        }
        assertThat(resultFirebaseOrders).isEmpty()
        assertThat(loadingState).isTrue()
    }

    @Test
    fun `get products result is successful`() {
        coEvery {
            shopUseCases.getUserOrdersUseCase("userUID")
        } returns flowOf(Resource.Success(firebaseOrders))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setOrdersUseCase(firebaseOrders,products)
        } returns orders
        every {
            shopUseCases.sortOrdersUseCase(any(),any())
        } returns orders

        ordersViewModel = setViewModel()
        val resultProducts = getCurrentOrdersState().products

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserOrdersUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setOrdersUseCase(firebaseOrders,products)
            shopUseCases.sortOrdersUseCase(any(),any())
        }
        assertThat(resultProducts).isEqualTo(products)
    }

    @Test
    fun `get products result is error`() {
        coEvery {
            shopUseCases.getUserOrdersUseCase("userUID")
        } returns flowOf(Resource.Success(firebaseOrders))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Error("Error"))

        ordersViewModel = setViewModel()
        val resultProducts = getCurrentOrdersState().products

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserOrdersUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
        }
        assertThat(resultProducts).isEmpty()
    }

    @Test
    fun `get products result is loading`() {
        coEvery {
            shopUseCases.getUserOrdersUseCase("userUID")
        } returns flowOf(Resource.Success(firebaseOrders))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Loading(true))

        ordersViewModel = setViewModel()
        val resultProducts = getCurrentOrdersState().products
        val loadingState = getCurrentOrdersState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserOrdersUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
        }
        assertThat(resultProducts).isEmpty()
        assertThat(loadingState).isTrue()
    }

    @Test
    fun `orders are set correctly`() {
        val firebaseOrdersSlot = slot<MutableList<FirebaseOrder>>()
        val productsSlot = slot<MutableList<Product>>()

        coEvery {
            shopUseCases.getUserOrdersUseCase("userUID")
        } returns flowOf(Resource.Success(firebaseOrders))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setOrdersUseCase(
                capture(firebaseOrdersSlot),
                capture(productsSlot)
            )
        } returns orders
        every {
            shopUseCases.sortOrdersUseCase(any(),any())
        } returns orders

        ordersViewModel = setViewModel()
        val resultOrders = getCurrentOrdersState().orders


        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserOrdersUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setOrdersUseCase(any(),any())
            shopUseCases.sortOrdersUseCase(any(),any())
        }
        assertThat(resultOrders).isEqualTo(orders)
        assertThat(firebaseOrdersSlot.captured).isEqualTo(firebaseOrders)
        assertThat(productsSlot.captured).isEqualTo(products)
    }

    @Test
    fun `order expand state is changed correctly`() {
        coEvery {
            shopUseCases.getUserOrdersUseCase("userUID")
        } returns flowOf(Resource.Success(firebaseOrders))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setOrdersUseCase(firebaseOrders,products)
        } returns orders
        every {
            shopUseCases.sortOrdersUseCase(any(),any())
        } returns orders

        ordersViewModel = setViewModel()
        val initialOrders = getCurrentOrdersState().orders
        val resultOrders = ordersViewModel.changeOrderExpandState("orderId1")

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserOrdersUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setOrdersUseCase(firebaseOrders,products)
            shopUseCases.sortOrdersUseCase(any(),any())
        }
        assertThat(initialOrders).isEqualTo(orders)
        assertThat(resultOrders).isEqualTo(ordersAfterChnageExpandState)
    }

    @Test
    fun `orders are sorted correctly`() {
        val orderOrderSlot = slot<OrderOrder.DateDescending>()
        val ordersSlot = slot<MutableList<Order>>()

        coEvery {
            shopUseCases.getUserOrdersUseCase("userUID")
        } returns flowOf(Resource.Success(firebaseOrders))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setOrdersUseCase(firebaseOrders,products)
        } returns orders
        every {
            shopUseCases.sortOrdersUseCase(
                capture(orderOrderSlot),
                capture(ordersSlot)
            )
        } returns ordersSortedDesc

        ordersViewModel = setViewModel()
        val resultOrders = getCurrentOrdersState().orders
        val resultOrderOrder = getCurrentOrdersState().orderOrder

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserOrdersUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setOrdersUseCase(firebaseOrders,products)
            shopUseCases.sortOrdersUseCase(any(),any())
        }
        assertThat(resultOrders).isEqualTo(ordersSortedDesc)
        assertThat(resultOrderOrder).isInstanceOf(OrderOrder.DateDescending::class.java)
        assertThat(orderOrderSlot.captured).isInstanceOf(OrderOrder.DateDescending::class.java)
        assertThat(ordersSlot.captured).isEqualTo(orders)

    }

    @Test
    fun `event OnOrderSelected - orders expand state is set correctly`() {
        coEvery {
            shopUseCases.getUserOrdersUseCase("userUID")
        } returns flowOf(Resource.Success(firebaseOrders))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setOrdersUseCase(firebaseOrders,products)
        } returns orders
        every {
            shopUseCases.sortOrdersUseCase(any(),orders)
        } returns ordersSortedDesc

        ordersViewModel = setViewModel()
        val initialOrders = getCurrentOrdersState().orders
        ordersViewModel.onEvent(OrdersEvent.OnOrderSelected("orderId1"))
        val resultOrders = getCurrentOrdersState().orders

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserOrdersUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setOrdersUseCase(firebaseOrders,products)
            shopUseCases.sortOrdersUseCase(any(),orders)
        }
        assertThat(initialOrders).isEqualTo(ordersSortedDesc)
        assertThat(resultOrders).isEqualTo(ordersAfterChnageExpandState.sortedByDescending { it.date })
    }

    @Test
    fun `event OnOrderChange - orders order is set correctly after change`() {
        coEvery {
            shopUseCases.getUserOrdersUseCase("userUID")
        } returns flowOf(Resource.Success(firebaseOrders))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setOrdersUseCase(firebaseOrders,products)
        } returns orders
        every {
            shopUseCases.sortOrdersUseCase(any(),orders)
        } returns orders

        ordersViewModel = setViewModel()
        val initialOrderOrder = getCurrentOrdersState().orderOrder
        ordersViewModel.onEvent(OrdersEvent.OnOrderChange(OrderOrder.DateAscending()))
        val resultOrderOrder = getCurrentOrdersState().orderOrder

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserOrdersUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setOrdersUseCase(firebaseOrders,products)
            shopUseCases.sortOrdersUseCase(any(),orders)
            shopUseCases.sortOrdersUseCase(any(),orders)
        }
        assertThat(initialOrderOrder).isInstanceOf(OrderOrder.DateDescending::class.java)
        assertThat(resultOrderOrder).isInstanceOf(OrderOrder.DateAscending::class.java)
    }

    @Test
    fun `event ToggleSortSection - sort section state is set correctly after change`() {
        coEvery {
            shopUseCases.getUserOrdersUseCase("userUID")
        } returns flowOf(Resource.Success(firebaseOrders))
        coEvery {
            shopUseCases.getProductsUseCase("all")
        } returns flowOf(Resource.Success(products))
        every {
            shopUseCases.setOrdersUseCase(firebaseOrders,products)
        } returns orders
        every {
            shopUseCases.sortOrdersUseCase(any(),orders)
        } returns ordersSortedDesc

        ordersViewModel = setViewModel()
        val isSortSectionVisibleInitially = getCurrentOrdersState().isSortSectionVisible
        ordersViewModel.onEvent(OrdersEvent.ToggleSortSection)
        val isSortSectionVisibleFinally = getCurrentOrdersState().isSortSectionVisible

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserOrdersUseCase("userUID")
            shopUseCases.getProductsUseCase("all")
            shopUseCases.setOrdersUseCase(firebaseOrders,products)
            shopUseCases.sortOrdersUseCase(any(),orders)
        }
        assertThat(isSortSectionVisibleInitially).isFalse()
        assertThat(isSortSectionVisibleFinally).isTrue()
    }
}