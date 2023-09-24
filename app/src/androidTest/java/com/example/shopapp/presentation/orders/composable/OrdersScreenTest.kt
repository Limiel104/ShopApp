package com.example.shopapp.presentation.orders.composable

import androidx.activity.compose.setContent
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.assertPositionInRootIsEqualTo
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.assertTopPositionInRootIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onParent
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.di.AppModule
import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.domain.model.Order
import com.example.shopapp.presentation.MainActivity
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.ORDERS_CONTENT
import com.example.shopapp.util.Constants.ORDERS_CPI
import com.example.shopapp.util.Constants.ORDERS_LAZY_COLUMN
import com.example.shopapp.util.Constants.ORDERS_TOP_BAR
import com.example.shopapp.util.Constants.SORT_BTN
import com.example.shopapp.util.Constants.bottomBarHeight
import com.example.shopapp.util.Screen
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date

@HiltAndroidTest
@UninstallModules(AppModule::class)
class OrdersScreenTest {

    private lateinit var orders: List<Order>

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        orders = listOf(
            Order(
                orderId = "orderId1",
                date = Date(),
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
    }

    private fun setScreenState(
        orders: List<Order>,
        isLoading: Boolean = false
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
                            scaffoldState = rememberScaffoldState(),
                            bottomBarHeight = bottomBarHeight.dp,
                            orders = orders,
                            isLoading = isLoading,
                            onOrderSelected = {}
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
                        OrdersScreen(
                            navController = navController,
                            bottomBarHeight = bottomBarHeight.dp
                        )
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
        Truth.assertThat(numberOfChildren).isEqualTo(2)
    }

    @Test
    fun ordersScreenTopBar_topBarIsDisplayedCorrectly() {
        setScreenState(
            orders = orders
        )

        composeRule.onNodeWithTag(ORDERS_TOP_BAR).assertTopPositionInRootIsEqualTo(15.dp)
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).assertHeightIsEqualTo(36.dp)
        val deviceWidth = composeRule.onNodeWithTag(ORDERS_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).assertWidthIsEqualTo(deviceWidth-20.dp)
    }

    @Test
    fun ordersScreenTopBar_titleIsDisplayedCorrectly() {
        setScreenState(
            orders = orders
        )

        composeRule.onNodeWithTag(ORDERS_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).onChildAt(0).assertTextEquals("Your Orders")
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).onChildAt(0).assertLeftPositionInRootIsEqualTo(10.dp,)
    }

    @Test
    fun ordersScreenTopBar_sortButtonIsDisplayedCorrectly() {
        setScreenState(
            orders = orders
        )

        composeRule.onNodeWithTag(ORDERS_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).onChildAt(1).assertContentDescriptionContains(SORT_BTN)
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).onChildAt(1).assertHasClickAction()

        val deviceWidth = composeRule.onNodeWithTag(ORDERS_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).onChildAt(1).assertPositionInRootIsEqualTo(deviceWidth-46.dp,15.dp)
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).onChildAt(1).assertHeightIsEqualTo(36.dp)
        composeRule.onNodeWithTag(ORDERS_TOP_BAR).onChildAt(1).assertWidthIsEqualTo(36.dp)
    }

    @Test
    fun ordersScreenLazyColumn_hasCorrectNumberOfItems() {
        setScreenState(
            orders = orders
        )

        composeRule.onNodeWithTag(ORDERS_LAZY_COLUMN).assertExists()
        composeRule.onNodeWithTag(ORDERS_LAZY_COLUMN).assertIsDisplayed()
        val numberOfChildrenVisible = composeRule.onNodeWithTag(ORDERS_LAZY_COLUMN).fetchSemanticsNode().children.size
        Truth.assertThat(numberOfChildrenVisible).isEqualTo(3)
    }

    @Test
    fun ordersScreenLazyColumn_isDisplayedCorrectly() {
        setScreenState(
            orders = orders
        )
        val deviceWidth = composeRule.onNodeWithTag(ORDERS_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(ORDERS_LAZY_COLUMN).assertExists()
        composeRule.onNodeWithTag(ORDERS_LAZY_COLUMN).assertIsDisplayed()
        composeRule.onNodeWithTag(ORDERS_LAZY_COLUMN).assertPositionInRootIsEqualTo(20.dp,66.dp)
        composeRule.onNodeWithTag(ORDERS_LAZY_COLUMN).assertWidthIsEqualTo(deviceWidth-40.dp)
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
    }
}