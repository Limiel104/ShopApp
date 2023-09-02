package com.example.shopapp.presentation.home.composable

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
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onParent
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.di.AppModule
import com.example.shopapp.domain.model.Offer
import com.example.shopapp.presentation.MainActivity
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.CART_BTN
import com.example.shopapp.util.Constants.HOME_CONTENT
import com.example.shopapp.util.Constants.HOME_LAZY_COLUMN
import com.example.shopapp.util.Constants.HOME_TOP_BAR
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
class HomeScreenTest {

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
                    startDestination = Screen.HomeScreen.route
                ) {
                    composable(
                        route = Screen.HomeScreen.route
                    ) {
                        val offerList = listOf(
                            Offer(
                                categoryId = "women's clothing",
                                discountPercent = 10,
                                description = "All clothes for women now 10% cheaper"
                            ),
                            Offer(
                                categoryId = "men's clothing",
                                discountPercent = 15,
                                description = "All clothes for men now 15% cheaper"
                            ),
                            Offer(
                                categoryId = "jewelery",
                                discountPercent = 50,
                                description = "Buy two pieces of jewelery for the price of one"
                            ),
                            Offer(
                                categoryId = "",
                                discountPercent = 13,
                                description = "13% off for purchase above 200\$"
                            )
                        )

                        HomeContent(
                            scaffoldState = rememberScaffoldState(),
                            bottomBarHeight = bottomBarHeight.dp,
                            offerList = offerList,
                            onOfferSelected = {},
                            onGoToCart = {}
                        )
                    }
                }
            }
        }
    }

    @Test
    fun homeScreenTopBar_hasCorrectNumberOfItems() {
        composeRule.onNodeWithTag(HOME_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(HOME_TOP_BAR).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(HOME_TOP_BAR).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(2)
    }

    @Test
    fun homeScreenTopBar_topBarIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(HOME_TOP_BAR).assertTopPositionInRootIsEqualTo(15.dp)
        composeRule.onNodeWithTag(HOME_TOP_BAR).assertHeightIsEqualTo(36.dp)
        val deviceWidth = composeRule.onNodeWithTag(HOME_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(HOME_TOP_BAR).assertWidthIsEqualTo(deviceWidth-20.dp)
    }

    @Test
    fun homeScreenTopBar_shopNameIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(HOME_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(HOME_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(HOME_TOP_BAR).onChildAt(0).assertTextContains("Shop Name")
        composeRule.onNodeWithTag(HOME_TOP_BAR).onChildAt(0).assertLeftPositionInRootIsEqualTo(10.dp)
    }

    @Test
    fun homeScreenTopBar_cartButtonIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(HOME_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(HOME_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(HOME_TOP_BAR).onChildAt(1).assertContentDescriptionContains(CART_BTN)
        composeRule.onNodeWithTag(HOME_TOP_BAR).onChildAt(1).assertHasClickAction()

        val deviceWidth = composeRule.onNodeWithTag(HOME_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(HOME_TOP_BAR).onChildAt(1).assertPositionInRootIsEqualTo(deviceWidth-46.dp,15.dp)
        composeRule.onNodeWithTag(HOME_TOP_BAR).onChildAt(1).assertHeightIsEqualTo(36.dp)
        composeRule.onNodeWithTag(HOME_TOP_BAR).onChildAt(1).assertWidthIsEqualTo(36.dp)
    }

    @Test
    fun homeScreenLazyColumn_isDisplayingCorrectNumberOfItems() {
        composeRule.onNodeWithTag(HOME_LAZY_COLUMN).assertExists()
        composeRule.onNodeWithTag(HOME_LAZY_COLUMN).assertIsDisplayed()
        val numberOfChildrenDisplayed = composeRule.onNodeWithTag(HOME_LAZY_COLUMN).fetchSemanticsNode().children.size
        assertThat(numberOfChildrenDisplayed).isEqualTo(3)
    }

    @Test
    fun homeScreenLazyColumn_isDisplayedCorrectly() {
        composeRule.onNodeWithTag(HOME_LAZY_COLUMN).assertPositionInRootIsEqualTo(10.dp,66.dp)
        val deviceWidth = composeRule.onNodeWithTag(HOME_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(HOME_LAZY_COLUMN).assertWidthIsEqualTo(deviceWidth-20.dp)
    }
}