package com.example.shopapp.presentation.account.composable

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTopPositionInRootIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.di.AppModule
import com.example.shopapp.presentation.MainActivity
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.ACCOUNT_CONTENT
import com.example.shopapp.util.Constants.ACCOUNT_LAZY_ROW
import com.example.shopapp.util.Constants.ACCOUNT_POINTS_CARD
import com.example.shopapp.util.Constants.ACCOUNT_TOP_BAR
import com.example.shopapp.util.Constants.ACTIVATE_COUPON_BTN
import com.example.shopapp.util.Constants.CART_BTN
import com.example.shopapp.util.Constants.COUPON_ITEM_10
import com.example.shopapp.util.Constants.COUPON_ITEM_20
import com.example.shopapp.util.Constants.COUPON_ITEM_50
import com.example.shopapp.util.Constants.LOGOUT_BTN
import com.example.shopapp.util.Constants.MY_PROFILE_BTN
import com.example.shopapp.util.Constants.ORDERS_BTN
import com.example.shopapp.util.Screen
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class AccountScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    private fun setScreenState(
        name: String = "John",
        userPoints: Int = 234,
        isCouponActivated: Boolean = false
    ) {
        composeRule.activity.setContent {
            val navController = rememberNavController()
            ShopAppTheme() {
                NavHost(
                    navController = navController,
                    startDestination = Screen.AccountScreen.route
                ) {
                    composable(
                        route = Screen.AccountScreen.route
                    ) {
                        AccountContent(
                            name = name,
                            userPoints = userPoints,
                            isCouponActivated = isCouponActivated,
                            onActivateCoupon = {},
                            onLogout = {},
                            onGoToCart = {},
                            onGoToOrders = {},
                            onGoToProfile = {}
                        )
                    }
                }
            }
        }
    }

    @Test
    fun accountScreenTopBar_hasCorrectNumberOfItems() {
        setScreenState()

        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(2)
    }

    @Test
    fun accountScreenTopBar_topBarIsDisplayedCorrectly() {
        setScreenState()

        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).assertTopPositionInRootIsEqualTo(0.dp)
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).assertHeightIsEqualTo(64.dp)
        val deviceWidth = composeRule.onNodeWithTag(ACCOUNT_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).assertWidthIsEqualTo(deviceWidth)
    }

    @Test
    fun accountScreenTopBar_userNameIsDisplayedCorrectly() {
        setScreenState()

        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).onChildAt(0).assertTextContains("Hi John")
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).onChildAt(0).assertLeftPositionInRootIsEqualTo(16.dp)
    }

    @Test
    fun accountScreenTopBar_cartButtonIsDisplayedCorrectly() {
        setScreenState()

        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).onChildAt(1).assertContentDescriptionContains(CART_BTN)
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).onChildAt(1).assertHasClickAction()

        val deviceWidth = composeRule.onNodeWithTag(ACCOUNT_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).onChildAt(1).assertLeftPositionInRootIsEqualTo(deviceWidth-48.dp)
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).onChildAt(1).assertHeightIsEqualTo(40.dp)
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).onChildAt(1).assertWidthIsEqualTo(40.dp)
    }

    @Test
    fun accountScreenPointsCard_isDisplayedCorrectly() {
        setScreenState()

        composeRule.onNodeWithTag(ACCOUNT_POINTS_CARD).assertExists()
        composeRule.onNodeWithTag(ACCOUNT_POINTS_CARD).assertIsDisplayed()

        composeRule.onNodeWithTag(ACCOUNT_POINTS_CARD).onChildAt(0).assertTextContains("Shop Club")
        composeRule.onNodeWithTag(ACCOUNT_POINTS_CARD).onChildAt(1).assertTextContains("234 points")

        composeRule.onNodeWithTag(ACCOUNT_POINTS_CARD).assertLeftPositionInRootIsEqualTo(10.dp)
        composeRule.onNodeWithTag(ACCOUNT_POINTS_CARD).onChildAt(0).assertLeftPositionInRootIsEqualTo(25.dp)
        composeRule.onNodeWithTag(ACCOUNT_POINTS_CARD).onChildAt(1).assertLeftPositionInRootIsEqualTo(25.dp)
    }

    @Test
    fun accountScreenLazyRow_isDisplayedCorrectly() {
        setScreenState()
        val deviceWidth = composeRule.onNodeWithTag(ACCOUNT_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(ACCOUNT_LAZY_ROW).assertExists()
        composeRule.onNodeWithTag(ACCOUNT_LAZY_ROW).assertIsDisplayed()

        composeRule.onNodeWithTag(ACCOUNT_LAZY_ROW).assertLeftPositionInRootIsEqualTo(10.dp)
        composeRule.onNodeWithTag(ACCOUNT_LAZY_ROW).assertWidthIsEqualTo(deviceWidth-20.dp)
    }

    @Test
    fun accountScreenLazyRow_isDisplayingCouponsCorrectly() {
        setScreenState()
        val couponList = listOf(COUPON_ITEM_10, COUPON_ITEM_20, COUPON_ITEM_50)
        val amountList  = listOf(10,20,50)

        couponList.zip(amountList) { coupon, amount ->
            composeRule.onNodeWithTag(ACCOUNT_LAZY_ROW).performScrollToNode(hasTestTag(coupon))
            composeRule.onNodeWithTag(coupon).assertExists()
            composeRule.onNodeWithTag(coupon).assertIsDisplayed()

            composeRule.onNodeWithTag(coupon).assertWidthIsEqualTo(300.dp)
            composeRule.onNodeWithTag(coupon).onChildAt(0).assertTextContains("$amount PLN Discount")
            composeRule.onNodeWithTag(coupon).onChildAt(1).assertTextContains("The coupon is valid for 14 days after activation.\nMinimum purchase amount is ${amount+1}")
            composeRule.onNodeWithTag(coupon).onChildAt(2).assertTextContains("Activate for ${amount*100}")
        }
    }

    @Test
    fun accountScreenLazyRow_allCouponsHaveActivateButton() {
        setScreenState()

        composeRule.onNodeWithTag(ACCOUNT_LAZY_ROW).performScrollToNode(hasTestTag(COUPON_ITEM_50))
        val activateButtonCount = composeRule.onAllNodesWithContentDescription(ACTIVATE_COUPON_BTN).fetchSemanticsNodes().count()
        assertThat(activateButtonCount).isEqualTo(3)
    }

    @Test
    fun accountScreenLazyRow_couponsAreNotEnabledTooLittlePoints_couponIsNotActivated() {
        setScreenState()
        val couponList = listOf(COUPON_ITEM_10, COUPON_ITEM_20, COUPON_ITEM_50)
        val amountList  = listOf(10,20,50)

        couponList.zip(amountList) { coupon, amount ->
            composeRule.onNodeWithTag(ACCOUNT_LAZY_ROW).performScrollToNode(hasTestTag(coupon))
            composeRule.onNodeWithTag(ACTIVATE_COUPON_BTN + amount).assertIsNotEnabled()
        }
    }

    @Test
    fun accountScreenLazyRow_allCouponsAreEnabledEnoughPoints_couponIsNotActivated() {
        setScreenState(
            userPoints = 6000,
            isCouponActivated = false
        )
        val couponList = listOf(COUPON_ITEM_10, COUPON_ITEM_20, COUPON_ITEM_50)
        val amountList  = listOf(10,20,50)

        couponList.zip(amountList) { coupon, amount ->
            composeRule.onNodeWithTag(ACCOUNT_LAZY_ROW).performScrollToNode(hasTestTag(coupon))
            composeRule.onNodeWithTag(ACTIVATE_COUPON_BTN + amount).assertIsEnabled()
        }
    }

    @Test
    fun accountScreenLazyRow_someCouponsAreEnabledEnoughPoints_couponIsNotActivated() {
        setScreenState(
            userPoints = 2300,
            isCouponActivated = false
        )
        val couponList = listOf(COUPON_ITEM_10, COUPON_ITEM_20)
        val amountList  = listOf(10,20)

        couponList.zip(amountList) { coupon, amount ->
            composeRule.onNodeWithTag(ACCOUNT_LAZY_ROW).performScrollToNode(hasTestTag(coupon))
            composeRule.onNodeWithTag(ACTIVATE_COUPON_BTN + amount).assertIsEnabled()
        }

        composeRule.onNodeWithTag(ACCOUNT_LAZY_ROW).performScrollToNode(hasTestTag(COUPON_ITEM_50))
        composeRule.onNodeWithTag(ACTIVATE_COUPON_BTN + 50).assertIsNotEnabled()
    }

    @Test
    fun accountScreenLazyRow_allCouponsAreNotEnabled_TooLittlePoints_couponIsActivated() {
        setScreenState(
            isCouponActivated = true
        )
        val couponList = listOf(COUPON_ITEM_10, COUPON_ITEM_20, COUPON_ITEM_50)
        val amountList  = listOf(10,20,50)

        couponList.zip(amountList) { coupon, amount ->
            composeRule.onNodeWithTag(ACCOUNT_LAZY_ROW).performScrollToNode(hasTestTag(coupon))
            composeRule.onNodeWithTag(ACTIVATE_COUPON_BTN + amount).assertIsNotEnabled()
        }
    }

    @Test
    fun accountScreenLazyRow_allCouponsAreNotEnabled_EnoughPointsForAll_couponIsActivated() {
        setScreenState(
            userPoints = 6000,
            isCouponActivated = true
        )
        val couponList = listOf(COUPON_ITEM_10, COUPON_ITEM_20, COUPON_ITEM_50)
        val amountList  = listOf(10,20,50)

        couponList.zip(amountList) { coupon, amount ->
            composeRule.onNodeWithTag(ACCOUNT_LAZY_ROW).performScrollToNode(hasTestTag(coupon))
            composeRule.onNodeWithTag(ACTIVATE_COUPON_BTN + amount).assertIsNotEnabled()
        }
    }

    @Test
    fun accountScreenLazyRow_allCouponsAreNotEnabled_EnoughPointsForTwo_couponIsActivated() {
        setScreenState(
            userPoints = 2300,
            isCouponActivated = true
        )
        val couponList = listOf(COUPON_ITEM_10, COUPON_ITEM_20, COUPON_ITEM_50)
        val amountList  = listOf(10,20,50)

        couponList.zip(amountList) { coupon, amount ->
            composeRule.onNodeWithTag(ACCOUNT_LAZY_ROW).performScrollToNode(hasTestTag(coupon))
            composeRule.onNodeWithTag(ACTIVATE_COUPON_BTN + amount).assertIsNotEnabled()
        }
    }

    @Test
    fun accountScreenMyProfileOption_isDisplayedCorrectly() {
        setScreenState()
        val deviceWidth = composeRule.onNodeWithTag(ACCOUNT_CONTENT).onParent().getBoundsInRoot().right
        val optionWidth = (deviceWidth-30.dp)/2

        composeRule.onNodeWithTag(MY_PROFILE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(MY_PROFILE_BTN).assertHasClickAction()

        composeRule.onNodeWithTag(MY_PROFILE_BTN).assertLeftPositionInRootIsEqualTo(10.dp)
        composeRule.onNodeWithTag(MY_PROFILE_BTN).assertWidthIsEqualTo(optionWidth)
        composeRule.onNodeWithTag(MY_PROFILE_BTN).assertTextContains("My profile")
    }

    @Test
    fun accountScreenOrdersOption_isDisplayedCorrectly() {
        setScreenState()
        val deviceWidth = composeRule.onNodeWithTag(ACCOUNT_CONTENT).onParent().getBoundsInRoot().right
        val optionWidth = (deviceWidth-30.dp)/2
        val leftPosition = optionWidth+20.dp

        composeRule.onNodeWithTag(ORDERS_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(ORDERS_BTN).assertHasClickAction()

        composeRule.onNodeWithTag(ORDERS_BTN).assertLeftPositionInRootIsEqualTo(leftPosition)
        composeRule.onNodeWithTag(ORDERS_BTN).assertWidthIsEqualTo(optionWidth)
        composeRule.onNodeWithTag(ORDERS_BTN).assertTextContains("Orders")
    }

    @Test
    fun accountScreenLogoutOption_isDisplayedCorrectly() {
        setScreenState()
        val deviceWidth = composeRule.onNodeWithTag(ACCOUNT_CONTENT).onParent().getBoundsInRoot().right
        val optionWidth = deviceWidth-20.dp

        composeRule.onNodeWithTag(LOGOUT_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(LOGOUT_BTN).assertHasClickAction()

        composeRule.onNodeWithTag(LOGOUT_BTN).assertLeftPositionInRootIsEqualTo(10.dp)
        composeRule.onNodeWithTag(LOGOUT_BTN).assertWidthIsEqualTo(optionWidth)
        composeRule.onNodeWithTag(LOGOUT_BTN).assertTextContains("Logout")
    }
}