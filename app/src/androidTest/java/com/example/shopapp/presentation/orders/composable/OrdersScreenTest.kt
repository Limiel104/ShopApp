package com.example.shopapp.presentation.orders.composable

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.assertPositionInRootIsEqualTo
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.di.AppModule
import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.domain.model.Order
import com.example.shopapp.domain.util.OrderOrder
import com.example.shopapp.presentation.MainActivity
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.presentation.common.Constants.EXPAND_OR_COLLAPSE_BTN
import com.example.shopapp.presentation.common.Constants.GO_BACK_BTN
import com.example.shopapp.presentation.common.Constants.IMAGE
import com.example.shopapp.presentation.common.Constants.ORDERS_CONTENT
import com.example.shopapp.presentation.common.Constants.ORDERS_CPI
import com.example.shopapp.presentation.common.Constants.ORDERS_LAZY_COLUMN
import com.example.shopapp.presentation.common.Constants.ORDERS_SORT_SECTION
import com.example.shopapp.presentation.common.Constants.ORDERS_TOP_BAR
import com.example.shopapp.presentation.common.Constants.SORT_BTN
import com.example.shopapp.presentation.navigation.Screen
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date

@HiltAndroidTest
@UninstallModules(AppModule::class)
class OrdersScreenTest {

    private lateinit var orders: List<Order>
    private lateinit var date: Date
    private lateinit var ordersExpanded: List<Order>

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        date = Date()
        orders = listOf(
            Order(
                orderId = "orderId1",
                date = date,
                totalAmount = 123.43,
                products = listOf(
                    CartProduct(
                        id = 4,
                        title = "title 4",
                        price = 23.00,
                        imageUrl = "",
                        amount = 1
                    )
                ),
                isExpanded = false
            ),
            Order(
                orderId = "orderId2",
                date = Date(),
                totalAmount = 54.00,
                products = listOf(
                    CartProduct(
                        id = 2,
                        title = "title 2",
                        price = 53.34,
                        imageUrl = "",
                        amount = 2
                    ),
                    CartProduct(
                        id = 3,
                        title = "title 3",
                        price = 56.00,
                        imageUrl = "",
                        amount = 1
                    ),
                    CartProduct(
                        id = 4,
                        title = "title 4",
                        price = 23.00,
                        imageUrl = "",
                        amount = 1
                    )
                ),
                isExpanded = false
            ),
            Order(
                orderId = "orderId3",
                date = Date(),
                totalAmount = 73.99,
                products = listOf(
                    CartProduct(
                        id = 2,
                        title = "title 2",
                        price = 53.34,
                        imageUrl = "",
                        amount = 2
                    ),
                    CartProduct(
                        id = 3,
                        title = "title 3",
                        price = 56.00,
                        imageUrl = "",
                        amount = 1
                    )
                ),
                isExpanded = false
            )
        )
        ordersExpanded = listOf(
            Order(
                orderId = "orderId1",
                date = date,
                totalAmount = 123.43,
                products = listOf(
                    CartProduct(
                        id = 4,
                        title = "title 4",
                        price = 23.00,
                        imageUrl = "",
                        amount = 1
                    )
                ),
                isExpanded = true
            ),
            Order(
                orderId = "orderId2",
                date = Date(),
                totalAmount = 54.00,
                products = listOf(
                    CartProduct(
                        id = 2,
                        title = "title 2",
                        price = 53.34,
                        imageUrl = "",
                        amount = 2
                    ),
                    CartProduct(
                        id = 3,
                        title = "title 3",
                        price = 56.00,
                        imageUrl = "",
                        amount = 1
                    ),
                    CartProduct(
                        id = 4,
                        title = "title 4",
                        price = 23.00,
                        imageUrl = "",
                        amount = 1
                    )
                ),
                isExpanded = true
            ),
            Order(
                orderId = "orderId3",
                date = Date(),
                totalAmount = 73.99,
                products = listOf(
                    CartProduct(
                        id = 2,
                        title = "title 2",
                        price = 53.34,
                        imageUrl = "",
                        amount = 2
                    ),
                    CartProduct(
                        id = 3,
                        title = "title 3",
                        price = 56.00,
                        imageUrl = "",
                        amount = 1
                    )
                ),
                isExpanded = true
            )
        )
    }

    private fun setScreenState(
        orders: List<Order>,
        isLoading: Boolean = false,
        orderOrder: OrderOrder = OrderOrder.DateDescending(),
        isSortSectionVisible: Boolean = false
    ) {
        composeRule.activity.setContent {
            val navController = rememberNavController()
            ShopAppTheme() {
                NavHost(
                    navController = navController,
                    startDestination = Screen.OrdersScreen.route
                ) {
                    composable(
                        route = Screen.OrdersScreen.route
                    ) {
                        OrdersContent(
                            orders = orders,
                            isLoading = isLoading,
                            orderOrder = orderOrder,
                            isSortSectionVisible = isSortSectionVisible,
                            onOrderSelected = {},
                            onOrderChange = {},
                            onSortSelected = {},
                            onGoBack = {}
                        )
                    }
                }
            }
        }
    }

    private fun setScreen() {
        composeRule.activity.setContent {
            val navController = rememberNavController()
            ShopAppTheme() {
                NavHost(
                    navController = navController,
                    startDestination = Screen.OrdersScreen.route
                ) {
                    composable(
                        route = Screen.OrdersScreen.route
                    ) {
                        OrdersScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun ordersScreenTopBar_hasCorrectNumberOfItems() {
        setScreenState(
            orders = orders
        )

        composeRule.onNodeWithTag(ORDERS_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(ORDERS_TOP_BAR).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(3)
    }

    @Test
    fun ordersScreenTopBar_topBarIsDisplayedCorrectly() {
        setScreenState(
            orders = orders
        )

        composeRule.onNodeWithTag(ORDERS_TOP_BAR).assertPositionInRootIsEqualTo(0.dp,0.dp)
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).assertHeightIsEqualTo(64.dp)
        val deviceWidth = composeRule.onNodeWithTag(ORDERS_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).assertWidthIsEqualTo(deviceWidth)
    }

    @Test
    fun ordersScreenTopBar_goBackButtonIsDisplayedCorrectly() {
        setScreenState(
            orders = orders
        )

        composeRule.onNodeWithTag(ORDERS_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).onChildAt(0).assertContentDescriptionContains(GO_BACK_BTN)
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).onChildAt(0).assertHasClickAction()

        composeRule.onNodeWithTag(ORDERS_TOP_BAR).onChildAt(0).assertPositionInRootIsEqualTo(8.dp,12.dp)
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).onChildAt(0).assertHeightIsEqualTo(40.dp)
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).onChildAt(0).assertWidthIsEqualTo(40.dp)
    }

    @Test
    fun ordersScreenTopBar_titleIsDisplayedCorrectly() {
        setScreenState(
            orders = orders
        )

        composeRule.onNodeWithTag(ORDERS_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).onChildAt(1).assertTextEquals("Your Orders")
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).onChildAt(1).assertLeftPositionInRootIsEqualTo(56.dp,)
    }

    @Test
    fun ordersScreenTopBar_sortButtonIsDisplayedCorrectly() {
        setScreenState(
            orders = orders
        )

        composeRule.onNodeWithTag(ORDERS_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).onChildAt(2).assertContentDescriptionContains(SORT_BTN)
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).onChildAt(2).assertHasClickAction()

        val deviceWidth = composeRule.onNodeWithTag(ORDERS_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).onChildAt(2).assertPositionInRootIsEqualTo(deviceWidth-48.dp,12.dp)
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).onChildAt(2).assertHeightIsEqualTo(40.dp)
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).onChildAt(2).assertWidthIsEqualTo(40.dp)
    }

    @Test
    fun ordersScreenSortSection_clickToggle_isVisible() {
        setScreen()

        composeRule.onNodeWithTag(ORDERS_SORT_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(SORT_BTN).performClick()
        composeRule.onNodeWithTag(ORDERS_SORT_SECTION).assertExists()
    }

    @Test
    fun ordersScreenSortSection_clickToggleTwoTimes_isNotVisible() {
        setScreen()

        composeRule.onNodeWithTag(ORDERS_SORT_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(SORT_BTN).performClick()
        composeRule.onNodeWithTag(ORDERS_SORT_SECTION).assertIsDisplayed()
        composeRule.onNodeWithContentDescription(SORT_BTN).performClick()
        composeRule.onNodeWithTag(ORDERS_SORT_SECTION).assertDoesNotExist()
    }

    @Test
    fun ordersScreenSortSection_isDisplayedCorrectly() {
        setScreenState(
            orders = orders,
            isSortSectionVisible = true
        )

        composeRule.onNodeWithTag(ORDERS_SORT_SECTION).assertIsDisplayed()

        val sortOptions = composeRule.onNodeWithTag(ORDERS_SORT_SECTION).fetchSemanticsNode().children.size
        assertThat(sortOptions).isEqualTo(2)

        composeRule.onNodeWithTag(ORDERS_SORT_SECTION).onChildAt(0).assertTextEquals("Oldest to Newest")
        composeRule.onNodeWithTag(ORDERS_SORT_SECTION).onChildAt(0).assertHasClickAction()
        composeRule.onNodeWithTag(ORDERS_SORT_SECTION).onChildAt(0).assertLeftPositionInRootIsEqualTo(10.dp)

        composeRule.onNodeWithTag(ORDERS_SORT_SECTION).onChildAt(1).assertTextEquals("Newest to Oldest")
        composeRule.onNodeWithTag(ORDERS_SORT_SECTION).onChildAt(0).assertHasClickAction()
    }

    @Test
    fun ordersScreenSortSection_onlyOneItemCanBeSelectedAtTheSameTime() {
        setScreen()

        composeRule.onNodeWithContentDescription(SORT_BTN).performClick()

        composeRule.onNodeWithTag(ORDERS_SORT_SECTION).onChildAt(0).assertIsOff()
        composeRule.onNodeWithTag(ORDERS_SORT_SECTION).onChildAt(1).assertIsOn()

        composeRule.onNodeWithTag(ORDERS_SORT_SECTION).onChildAt(0).performClick()

        composeRule.onNodeWithTag(ORDERS_SORT_SECTION).onChildAt(0).assertIsOn()
        composeRule.onNodeWithTag(ORDERS_SORT_SECTION).onChildAt(1).assertIsOff()
    }

    @Test
    fun ordersScreenLazyColumn_hasCorrectNumberOfItems() {
        setScreenState(
            orders = orders
        )

        composeRule.onNodeWithTag(ORDERS_LAZY_COLUMN).assertExists()
        composeRule.onNodeWithTag(ORDERS_LAZY_COLUMN).assertIsDisplayed()
        val numberOfChildrenVisible = composeRule.onNodeWithTag(ORDERS_LAZY_COLUMN).fetchSemanticsNode().children.size
        assertThat(numberOfChildrenVisible).isEqualTo(3)
    }

    @Test
    fun ordersScreenLazyColumn_isDisplayedCorrectly() {
        setScreenState(
            orders = orders
        )
        val deviceWidth = composeRule.onNodeWithTag(ORDERS_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(ORDERS_LAZY_COLUMN).assertExists()
        composeRule.onNodeWithTag(ORDERS_LAZY_COLUMN).assertIsDisplayed()
        composeRule.onNodeWithTag(ORDERS_LAZY_COLUMN).assertPositionInRootIsEqualTo(10.dp,64.dp)
        composeRule.onNodeWithTag(ORDERS_LAZY_COLUMN).assertWidthIsEqualTo(deviceWidth-20.dp)
    }

    @Test
    fun ordersScreenOrderNotExpanded_hasCorrectNumberOfItems() {
        setScreenState(
            orders = orders
        )

        composeRule.onNodeWithTag(orders[0].orderId).assertExists()
        composeRule.onNodeWithTag(orders[0].orderId).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(orders[0].orderId).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(3)
    }

    @Test
    fun ordersScreenOrderNotExpanded_isDisplayedCorrectly() {
        setScreenState(
            orders = orders
        )
        val formattedDate = SimpleDateFormat("dd/MM/yyyy HH:mm").format(date)

        composeRule.onNodeWithTag(orders[0].orderId).onChildAt(0).assertTextEquals(formattedDate)
        composeRule.onNodeWithTag(orders[0].orderId).onChildAt(1).assertTextEquals("123,43 PLN")
        composeRule.onNodeWithTag(orders[0].orderId).onChildAt(2).assertContentDescriptionContains(EXPAND_OR_COLLAPSE_BTN)
        composeRule.onNodeWithTag(orders[0].orderId).onChildAt(2).assertHasClickAction()

        val deviceWidth = composeRule.onNodeWithTag(ORDERS_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(orders[0].orderId).assertLeftPositionInRootIsEqualTo(10.dp)
        composeRule.onNodeWithTag(orders[0].orderId).assertWidthIsEqualTo(deviceWidth-20.dp)
    }

    @Test
    fun ordersScreenOrderExpanded_hasCorrectNumberOfItems() {
        setScreenState(
            orders = ordersExpanded
        )

        composeRule.onNodeWithTag(orders[0].orderId).assertExists()
        composeRule.onNodeWithTag(orders[0].orderId).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(orders[0].orderId).fetchSemanticsNode().children.size
        val numberOfChildrenExpanded = composeRule.onNodeWithTag(orders[0].orderId + orders[0].products[0].title).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(3)
        assertThat(numberOfChildrenExpanded).isEqualTo(4)
    }

    @Test
    fun ordersScreenOrderExpanded_isDisplayedCorrectly() {
        setScreenState(
            orders = ordersExpanded
        )
        val formattedDate = SimpleDateFormat("dd/MM/yyyy HH:mm").format(date)
        val expandedOrderTag = orders[0].orderId + orders[0].products[0].title

        composeRule.onNodeWithTag(orders[0].orderId).onChildAt(0).assertTextEquals(formattedDate)
        composeRule.onNodeWithTag(orders[0].orderId).onChildAt(1).assertTextEquals("123,43 PLN")
        composeRule.onNodeWithTag(orders[0].orderId).onChildAt(2).assertContentDescriptionContains(EXPAND_OR_COLLAPSE_BTN)
        composeRule.onNodeWithTag(orders[0].orderId).onChildAt(2).assertIsEnabled()
        composeRule.onNodeWithTag(orders[0].orderId).onChildAt(2).assertHasClickAction()
        composeRule.onNodeWithTag(expandedOrderTag).onChildAt(0).assertContentDescriptionContains(IMAGE)
        composeRule.onNodeWithTag(expandedOrderTag).onChildAt(1).assertTextEquals("title 4")
        composeRule.onNodeWithTag(expandedOrderTag).onChildAt(2).assertTextEquals("23,00 PLN")
        composeRule.onNodeWithTag(expandedOrderTag).onChildAt(3).assertTextEquals("Amount: 1")

        val deviceWidth = composeRule.onNodeWithTag(ORDERS_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(orders[0].orderId).assertLeftPositionInRootIsEqualTo(10.dp)
        composeRule.onNodeWithTag(orders[0].orderId).assertWidthIsEqualTo(deviceWidth-20.dp)
    }

    @Test
    fun ordersScreenOrderExpandedImage_isDisplayedCorrectly() {
        setScreenState(
            orders = ordersExpanded
        )
        val expandedOrderTag = orders[0].orderId + orders[0].products[0].title

        composeRule.onNodeWithTag(expandedOrderTag).onChildAt(0).assertWidthIsEqualTo(60.dp)
        composeRule.onNodeWithTag(expandedOrderTag).onChildAt(0).assertHeightIsEqualTo(80.dp)
    }

    @Test
    fun ordersScreenCircularProgressIndicator_IsDisplayedCorrectly() {
        setScreenState(
            orders = orders,
            isLoading = true
        )
        val deviceWidth = composeRule.onNodeWithTag(ORDERS_CONTENT).onParent().getBoundsInRoot().right
        val deviceHeight = composeRule.onNodeWithTag(ORDERS_CONTENT).onParent().getBoundsInRoot().bottom
        val leftPosition = deviceWidth.value/2
        val topPosition = deviceHeight.value/2

        composeRule.onNodeWithTag(ORDERS_CPI).assertExists()
        composeRule.onNodeWithTag(ORDERS_CPI).assertIsDisplayed()
        composeRule.onNodeWithTag(ORDERS_CPI).assertPositionInRootIsEqualTo(0.dp,0.dp)
        composeRule.onNodeWithTag(ORDERS_CPI).assertHeightIsEqualTo(deviceHeight)
        composeRule.onNodeWithTag(ORDERS_CPI).assertWidthIsEqualTo(deviceWidth)

        composeRule.onNodeWithTag(ORDERS_CPI).onChild().assertPositionInRootIsEqualTo(leftPosition.dp-20.dp,topPosition.dp-20.dp)
        composeRule.onNodeWithTag(ORDERS_CPI).onChild().assertWidthIsEqualTo(40.dp)
        composeRule.onNodeWithTag(ORDERS_CPI).onChild().assertHeightIsEqualTo(40.dp)
    }
}