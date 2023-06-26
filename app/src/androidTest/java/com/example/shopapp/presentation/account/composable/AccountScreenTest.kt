package com.example.shopapp.presentation.account.composable

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTopPositionInRootIsEqualTo
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.di.AppModule
import com.example.shopapp.presentation.MainActivity
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.ACCOUNT_LAZY_ROW
import com.example.shopapp.util.Constants.ACCOUNT_POINTS_CARD
import com.example.shopapp.util.Constants.ACCOUNT_TOP_BAR
import com.example.shopapp.util.Constants.CART_BTN
import com.example.shopapp.util.Constants.COUPON_ITEM_10
import com.example.shopapp.util.Constants.COUPON_ITEM_20
import com.example.shopapp.util.Constants.COUPON_ITEM_50
import com.example.shopapp.util.Constants.MY_PROFILE_BTN
import com.example.shopapp.util.Constants.ORDERS_AND_RETURNS_BTN
import com.example.shopapp.util.Constants.bottomBarHeight
import com.example.shopapp.util.Constants.customerClubPoints
import com.example.shopapp.util.Constants.customerName
import com.example.shopapp.util.Constants.hi
import com.example.shopapp.util.Constants.shopClub
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
                        AccountScreen(
                            bottomBarHeight = bottomBarHeight.dp
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
    fun accountScreenTopBar_customerNameIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).onChildAt(0).assertTextContains(hi+ customerName)
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).onChildAt(0).assertLeftPositionInRootIsEqualTo(10.dp)
    }

    @Test
    fun accountScreenTopBar_cartButtonIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).onChildAt(1).assertContentDescriptionContains(CART_BTN)
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).onChildAt(1).assertHasClickAction()
        composeRule.onNodeWithTag(ACCOUNT_TOP_BAR).onChildAt(1).assertTopPositionInRootIsEqualTo(15.dp)
    }

    @Test
    fun accountScreenPointsCard_isDisplayedCorrectly() {
        composeRule.onNodeWithTag(ACCOUNT_POINTS_CARD).assertExists()
        composeRule.onNodeWithTag(ACCOUNT_POINTS_CARD).assertIsDisplayed()

        composeRule.onNodeWithTag(ACCOUNT_POINTS_CARD).onChildAt(0).assertTextContains(shopClub)
        composeRule.onNodeWithTag(ACCOUNT_POINTS_CARD).onChildAt(1).assertTextContains(customerClubPoints)
    }

    @Test
    fun accountScreenLazyRow_isDisplayingCouponsCorrectly() {
        composeRule.onNodeWithTag(ACCOUNT_LAZY_ROW).assertExists()
        composeRule.onNodeWithTag(ACCOUNT_LAZY_ROW).assertIsDisplayed()

        composeRule.onNodeWithTag(COUPON_ITEM_10).assertExists()
        composeRule.onNodeWithTag(COUPON_ITEM_10).assertIsDisplayed()

        composeRule.onNodeWithTag(COUPON_ITEM_20).assertExists()
        composeRule.onNodeWithTag(COUPON_ITEM_20).assertIsDisplayed()

        composeRule.onNodeWithTag(ACCOUNT_LAZY_ROW).performScrollToNode(hasTestTag(COUPON_ITEM_50))
        composeRule.onNodeWithTag(COUPON_ITEM_50).assertIsDisplayed()
    }

    @Test
    fun accountScreenButtons_areDisplayedCorrectly() {
        composeRule.onNodeWithTag(MY_PROFILE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(MY_PROFILE_BTN).assertHasClickAction()

        composeRule.onNodeWithTag(ORDERS_AND_RETURNS_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(ORDERS_AND_RETURNS_BTN).assertHasClickAction()
    }
}