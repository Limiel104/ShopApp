package com.example.shopapp.presentation.account.composable

import androidx.activity.compose.setContent
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.assertPositionInRootIsEqualTo
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTopPositionInRootIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
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
import com.example.shopapp.util.Constants.ORDERS_AND_RETURNS_BTN
import com.example.shopapp.util.Constants.bottomBarHeight
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
                            scaffoldState = rememberScaffoldState(),
                            bottomBarHeight = bottomBarHeight.dp,
                            userName = "John",
                            userClubPoints = 234,
                            onLogout = {}
                        )
                    }
                }
            }
        }
    }

    @Test
    fun accountScreenTopBar_hasCorrectNumberOfItems() {
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(2)
    }

    @Test
    fun accountScreenTopBar_topBarIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).assertTopPositionInRootIsEqualTo(15.dp)
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).assertHeightIsEqualTo(36.dp)
        val deviceWidth = composeRule.onNodeWithTag(ACCOUNT_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).assertWidthIsEqualTo(deviceWidth-20.dp)
    }

    @Test
    fun accountScreenTopBar_userNameIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).onChildAt(0).assertTextContains("Hi John")
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).onChildAt(0).assertLeftPositionInRootIsEqualTo(10.dp)
    }

    @Test
    fun accountScreenTopBar_cartButtonIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).onChildAt(1).assertContentDescriptionContains(CART_BTN)
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).onChildAt(1).assertHasClickAction()

        val deviceWidth = composeRule.onNodeWithTag(ACCOUNT_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).onChildAt(1).assertPositionInRootIsEqualTo(deviceWidth-46.dp,15.dp,)
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).onChildAt(1).assertHeightIsEqualTo(36.dp)
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).onChildAt(1).assertWidthIsEqualTo(36.dp)
    }

    @Test
    fun accountScreenPointsCard_isDisplayedCorrectly() {
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
        val deviceWidth = composeRule.onNodeWithTag(ACCOUNT_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(ACCOUNT_LAZY_ROW).assertExists()
        composeRule.onNodeWithTag(ACCOUNT_LAZY_ROW).assertIsDisplayed()

        composeRule.onNodeWithTag(ACCOUNT_LAZY_ROW).assertLeftPositionInRootIsEqualTo(10.dp)
        composeRule.onNodeWithTag(ACCOUNT_LAZY_ROW).assertWidthIsEqualTo(deviceWidth-20.dp)
    }

    @Test
    fun accountScreenLazyRow_isDisplayingCouponsCorrectly() {
        val couponList = listOf(COUPON_ITEM_10, COUPON_ITEM_20, COUPON_ITEM_50)
        val amountList  = listOf(10,20,50)

        couponList.zip(amountList) { coupon, amount ->
            composeRule.onNodeWithTag(ACCOUNT_LAZY_ROW).performScrollToNode(hasTestTag(coupon))
            composeRule.onNodeWithTag(coupon).assertExists()
            composeRule.onNodeWithTag(coupon).assertIsDisplayed()

            composeRule.onNodeWithTag(coupon).assertWidthIsEqualTo(300.dp)
            composeRule.onNodeWithTag(coupon).onChildAt(0).assertTextContains("$amount PLN Discount")
            composeRule.onNodeWithTag(coupon).onChildAt(1).assertTextContains("The coupon is valid for 14 days after activation.\nMinimum purchase amount is ${amount+1}")
            composeRule.onNodeWithTag(coupon).onChildAt(2).assertTextContains("Activate for ${amount*10}")
        }
    }

    @Test
    fun accountScreenLazyRow_allCouponsHaveActivateButton() {
        composeRule.onNodeWithTag(ACCOUNT_LAZY_ROW).performScrollToNode(hasTestTag(COUPON_ITEM_50))
        val activateButtonCount = composeRule.onAllNodesWithTag(ACTIVATE_COUPON_BTN).fetchSemanticsNodes().count()
        assertThat(activateButtonCount).isEqualTo(3)
    }

    @Test
    fun accountScreenButtons_areDisplayedCorrectly() {
        val buttonList = listOf(MY_PROFILE_BTN, ORDERS_AND_RETURNS_BTN, LOGOUT_BTN)
        val textList = listOf("My profile", "Orders and Returns", "Logout")
        val deviceWidth = composeRule.onNodeWithTag(ACCOUNT_CONTENT).onParent().getBoundsInRoot().right

        buttonList.zip(textList) { button, text ->
            composeRule.onNodeWithTag(LOGOUT_BTN).assertIsDisplayed()
            composeRule.onNodeWithTag(LOGOUT_BTN).assertHasClickAction()

            composeRule.onNodeWithTag(button).assertLeftPositionInRootIsEqualTo(10.dp)
            composeRule.onNodeWithTag(button).assertWidthIsEqualTo(deviceWidth-20.dp)
            composeRule.onNodeWithTag(button).assertTextContains(text)
        }
    }
}