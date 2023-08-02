package com.example.shopapp.presentation.favourites.composable

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
import com.example.shopapp.domain.model.Product
import com.example.shopapp.presentation.MainActivity
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.CART_BTN
import com.example.shopapp.util.Constants.FAVOURITES_TOP_BAR
import com.example.shopapp.util.Constants.FAVOURITE_LAZY_VERTICAL_GRID
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
                                Product(
                                    id = 1,
                                    title = "title 1",
                                    price = "123,99 PLN",
                                    description = "description of a product 1",
                                    category = "men's clothing",
                                    imageUrl = "url",
                                    isInFavourites = true
                                ),
                                Product(
                                    id = 3,
                                    title = "title 3",
                                    price = "34,99 PLN",
                                    description = "description of a product 3",
                                    category = "men's clothing",
                                    imageUrl = "url",
                                    isInFavourites = true
                                ),
                                Product(
                                    id = 7,
                                    title = "title 7",
                                    price = "41,99 PLN",
                                    description = "description of a product 7",
                                    category = "women's clothing",
                                    imageUrl = "url",
                                    isInFavourites = false
                                ),
                            ),
                            isLoading = false,
                            onProductSelected = {}
                        )
                    }
                }
            }
        }
    }

    @Test
    fun favouriteScreenTopBar_hasCorrectNumberOfItems() {
        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(2)
    }

    @Test
    fun favouriteScreenTopBar_titleIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).onChildAt(0).assertTextContains("Favourite")
        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).onChildAt(0).assertLeftPositionInRootIsEqualTo(10.dp)
    }

    @Test
    fun categoryScreenTopBar_cartButtonIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).onChildAt(1).assertContentDescriptionContains(CART_BTN)
        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).onChildAt(1).assertHasClickAction()
        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).onChildAt(1).assertTopPositionInRootIsEqualTo(15.dp)
    }

    @Test
    fun categoryScreenLazyVerticalGrid_hasCorrectNumberOfItems() {
        composeRule.onNodeWithTag(FAVOURITE_LAZY_VERTICAL_GRID).assertExists()
        composeRule.onNodeWithTag(FAVOURITE_LAZY_VERTICAL_GRID).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(FAVOURITE_LAZY_VERTICAL_GRID).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(3)
    }

    @Test
    fun categoryListScreenLazyColumn_isDisplayingCategoriesCorrectly() {
        composeRule.onNodeWithTag(FAVOURITE_LAZY_VERTICAL_GRID).onChildAt(0).assertExists()
        composeRule.onNodeWithTag(FAVOURITE_LAZY_VERTICAL_GRID).onChildAt(0).assertIsDisplayed()
        composeRule.onNodeWithTag(FAVOURITE_LAZY_VERTICAL_GRID).onChildAt(0).assertHasClickAction()

        composeRule.onNodeWithTag(FAVOURITE_LAZY_VERTICAL_GRID).onChildAt(1).assertExists()
        composeRule.onNodeWithTag(FAVOURITE_LAZY_VERTICAL_GRID).onChildAt(1).assertIsDisplayed()
        composeRule.onNodeWithTag(FAVOURITE_LAZY_VERTICAL_GRID).onChildAt(1).assertHasClickAction()

        composeRule.onNodeWithTag(FAVOURITE_LAZY_VERTICAL_GRID).onChildAt(2).assertExists()
        composeRule.onNodeWithTag(FAVOURITE_LAZY_VERTICAL_GRID).onChildAt(2).assertIsDisplayed()
        composeRule.onNodeWithTag(FAVOURITE_LAZY_VERTICAL_GRID).onChildAt(2).assertHasClickAction()
    }
}