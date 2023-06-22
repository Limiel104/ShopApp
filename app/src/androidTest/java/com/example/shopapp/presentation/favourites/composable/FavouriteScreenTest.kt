package com.example.shopapp.presentation.favourites.composable

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
import com.example.shopapp.util.Constants
import com.example.shopapp.util.Constants.CART_BTN
import com.example.shopapp.util.Constants.FAVOURITE_TOP_BAR
import com.example.shopapp.util.Constants.favouriteTitle
import com.example.shopapp.util.Screen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class FavouriteScreenTest {

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
                    startDestination = Screen.FavouriteScreen.route
                ) {
                    composable(
                        route = Screen.FavouriteScreen.route
                    ) {
                        FavouriteScreen(
                            navController = navController,
                            bottomBarHeight = Constants.bottomBarHeight.dp
                        )
                    }
                }
            }
        }
    }

    @Test
    fun favouriteScreenTopBar_titleIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(FAVOURITE_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(FAVOURITE_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(FAVOURITE_TOP_BAR).onChildAt(0).assertTextContains(favouriteTitle)
    }

    @Test
    fun categoryScreenTopBar_cartButtonIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(FAVOURITE_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(FAVOURITE_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(FAVOURITE_TOP_BAR).onChildAt(1).assertContentDescriptionContains(CART_BTN)
        composeRule.onNodeWithTag(FAVOURITE_TOP_BAR).onChildAt(1).assertHasClickAction()
    }
}