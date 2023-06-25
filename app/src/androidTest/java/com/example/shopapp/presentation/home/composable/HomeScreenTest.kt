package com.example.shopapp.presentation.home.composable

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.di.AppModule
import com.example.shopapp.presentation.MainActivity
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.CART_BTN
import com.example.shopapp.util.Constants.HOME_TOP_BAR
import com.example.shopapp.util.Constants.bottomBarHeight
import com.example.shopapp.util.Constants.shopName
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
                        HomeScreen(
                            navController = navController,
                            bottomBarHeight = bottomBarHeight.dp
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
    fun homeScreenTopBar_shopNameIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(HOME_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(HOME_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(HOME_TOP_BAR).onChildAt(0).assertTextContains(shopName)
    }

    @Test
    fun homeScreenTopBar_cartButtonIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(HOME_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(HOME_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(HOME_TOP_BAR).onChildAt(1).assertContentDescriptionContains(CART_BTN)
        composeRule.onNodeWithTag(HOME_TOP_BAR).onChildAt(1).assertHasClickAction()
    }
}