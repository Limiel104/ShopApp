package com.example.shopapp.presentation.favourite.composable

import androidx.activity.compose.setContent
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTopPositionInRootIsEqualTo
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
import com.example.shopapp.util.Constants.FAVOURITE_TOP_BAR
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
                        FavouriteContent(
                            scaffoldState = rememberScaffoldState(),
                            bottomBarHeight = bottomBarHeight.dp,
                            productList = listOf(
                                "men's clothing 1",
                                "men's clothing 2",
                                "women's clothing 1",
                                "jewelery 1",
                                "men's clothing 3",
                                "women's clothing 2",
                                "jewelery 2",
                                "women's clothing 3"
                            ),
                            onProductSelected = {}
                        )
                    }
                }
            }
        }
    }

    @Test
    fun favouriteScreenTopBar_hasCorrectNumberOfItems() {
        composeRule.onNodeWithTag(FAVOURITE_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(FAVOURITE_TOP_BAR).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(FAVOURITE_TOP_BAR).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(2)
    }

    @Test
    fun favouriteScreenTopBar_titleIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(FAVOURITE_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(FAVOURITE_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(FAVOURITE_TOP_BAR).onChildAt(0).assertTextContains("Favourite")
        composeRule.onNodeWithTag(FAVOURITE_TOP_BAR).onChildAt(0).assertLeftPositionInRootIsEqualTo(10.dp)
    }

    @Test
    fun categoryScreenTopBar_cartButtonIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(FAVOURITE_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(FAVOURITE_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(FAVOURITE_TOP_BAR).onChildAt(1).assertContentDescriptionContains(CART_BTN)
        composeRule.onNodeWithTag(FAVOURITE_TOP_BAR).onChildAt(1).assertHasClickAction()
        composeRule.onNodeWithTag(FAVOURITE_TOP_BAR).onChildAt(1).assertTopPositionInRootIsEqualTo(15.dp)
    }
}