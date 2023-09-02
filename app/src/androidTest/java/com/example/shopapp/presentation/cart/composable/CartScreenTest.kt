package com.example.shopapp.presentation.cart.composable

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
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onParent
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.di.AppModule
import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.presentation.MainActivity
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.CART_CONTENT
import com.example.shopapp.util.Constants.CART_LAZY_COLUMN
import com.example.shopapp.util.Constants.CART_TOP_BAR
import com.example.shopapp.util.Constants.GO_BACK_BTN
import com.example.shopapp.util.Constants.MINUS_BTN
import com.example.shopapp.util.Constants.PLUS_BTN
import com.example.shopapp.util.Constants.bottomBarHeight
import com.example.shopapp.util.Screen
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class CartScreenTest {

    private lateinit var cartProductList: List<CartProduct>

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()

        cartProductList = listOf(
            CartProduct(
                id = 1,
                title = "title 1",
                price = "123,45 PLN",
                imageUrl = "",
                amount = 1
            ),
            CartProduct(
                id = 2,
                title = "title 2",
                price = "53,34 PLN",
                imageUrl = "",
                amount = 2
            ),
            CartProduct(
                id = 3,
                title = "title 3",
                price = "56,00 PLN",
                imageUrl = "",
                amount = 1
            ),
            CartProduct(
                id = 4,
                title = "title 4",
                price = "23,00 PLN",
                imageUrl = "",
                amount = 1
            ),
            CartProduct(
                id = 5,
                title = "title 5",
                price = "6,86 PLN",
                imageUrl = "",
                amount = 2
            ),
            CartProduct(
                id = 6,
                title = "title 6",
                price = "44,99 PLN",
                imageUrl = "",
                amount = 3
            ),
            CartProduct(
                id = 7,
                title = "title 7",
                price = "203,99 PLN",
                imageUrl = "",
                amount = 3
            )
        )
    }

    private fun setScreenState(
        cartProducts: List<CartProduct>,
        totalAmount: Double = 0.0,
        isDialogActivated: Boolean = false
    ) {
        composeRule.activity.setContent {
            val navController = rememberNavController()
            ShopAppTheme() {
                NavHost(
                    navController = navController,
                    startDestination = Screen.CartScreen.route
                ) {
                    composable(
                        route = Screen.CartScreen.route,
                    ) {
                        CartContent(
                            scaffoldState = rememberScaffoldState(),
                            bottomBarHeight = bottomBarHeight.dp,
                            cartProducts = cartProducts,
                            totalAmount = totalAmount,
                            isDialogActivated = isDialogActivated,
                            onGoBack = {},
                            onGoHome = {}
                        )
                    }
                }
            }
        }
    }

    @Test
    fun cartScreenTopBar_hasCorrectNumberOfItems() {
        setScreenState(
            cartProducts = cartProductList
        )

        composeRule.onNodeWithTag(CART_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(CART_TOP_BAR).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(CART_TOP_BAR).fetchSemanticsNode().children.size
        Truth.assertThat(numberOfChildren).isEqualTo(2)
    }

    @Test
    fun cartScreenTopBar_topBarIsDisplayedCorrectly() {
        setScreenState(
            cartProducts = cartProductList
        )

        composeRule.onNodeWithTag(CART_TOP_BAR).assertTopPositionInRootIsEqualTo(15.dp)
        composeRule.onNodeWithTag(CART_TOP_BAR).assertHeightIsEqualTo(36.dp)
        val deviceWidth = composeRule.onNodeWithTag(CART_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(CART_TOP_BAR).assertWidthIsEqualTo(deviceWidth-20.dp)
    }

    @Test
    fun cartListScreenTopBar_goBackButtonIsDisplayedCorrectly() {
        setScreenState(
            cartProducts = cartProductList
        )

        composeRule.onNodeWithTag(CART_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(CART_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(CART_TOP_BAR).onChildAt(0).assertContentDescriptionContains(GO_BACK_BTN)
        composeRule.onNodeWithTag(CART_TOP_BAR).onChildAt(0).assertHasClickAction()

        composeRule.onNodeWithTag(CART_TOP_BAR).onChildAt(0).assertPositionInRootIsEqualTo(10.dp,15.dp)
        composeRule.onNodeWithTag(CART_TOP_BAR).onChildAt(0).assertHeightIsEqualTo(36.dp)
        composeRule.onNodeWithTag(CART_TOP_BAR).onChildAt(0).assertWidthIsEqualTo(36.dp)
    }

    @Test
    fun cartScreenTopBar_categoryNameIsDisplayedCorrectly() {
        setScreenState(
            cartProducts = cartProductList
        )

        composeRule.onNodeWithTag(CART_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(CART_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(CART_TOP_BAR).onChildAt(1).assertTextEquals("Cart")
        composeRule.onNodeWithTag(CART_TOP_BAR).onChildAt(1).assertLeftPositionInRootIsEqualTo(56.dp,)
    }

    @Test
    fun cartScreenLazyColumn_hasCorrectNumberOfVisibleItems() {
        setScreenState(
            cartProducts = cartProductList
        )

        composeRule.onNodeWithTag(CART_LAZY_COLUMN).assertExists()
        composeRule.onNodeWithTag(CART_LAZY_COLUMN).assertIsDisplayed()
        val numberOfChildrenVisible = composeRule.onNodeWithTag(CART_LAZY_COLUMN).fetchSemanticsNode().children.size
        Truth.assertThat(numberOfChildrenVisible).isEqualTo(5)
    }

    @Test
    fun cartScreenLazyColumn_isDisplayedCorrectly() {
        setScreenState(
            cartProducts = cartProductList
        )
        val deviceWidth = composeRule.onNodeWithTag(CART_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(CART_LAZY_COLUMN).assertExists()
        composeRule.onNodeWithTag(CART_LAZY_COLUMN).assertIsDisplayed()
        composeRule.onNodeWithTag(CART_LAZY_COLUMN).assertPositionInRootIsEqualTo(20.dp,66.dp)
        composeRule.onNodeWithTag(CART_LAZY_COLUMN).assertWidthIsEqualTo(deviceWidth-40.dp)
    }

    @Test
    fun cartScreenCartProduct_isDisplayedCorrectly() {
        setScreenState(
            cartProducts = cartProductList
        )

        composeRule.onNodeWithTag(cartProductList[0].title).onChildAt(1).assertTextEquals(cartProductList[0].title)
        composeRule.onNodeWithTag(cartProductList[0].title).onChildAt(2).assertTextEquals(cartProductList[0].price)
        composeRule.onNodeWithTag(cartProductList[0].title).onChildAt(3).assertContentDescriptionContains(PLUS_BTN)
        composeRule.onNodeWithTag(cartProductList[0].title).onChildAt(4).assertTextEquals(cartProductList[0].amount.toString())
        composeRule.onNodeWithTag(cartProductList[0].title).onChildAt(5).assertContentDescriptionContains(MINUS_BTN)
        composeRule.onNodeWithTag(cartProductList[0].title).assertPositionInRootIsEqualTo(20.dp,66.dp)
    }
}