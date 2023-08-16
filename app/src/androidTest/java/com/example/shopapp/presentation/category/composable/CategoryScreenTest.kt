package com.example.shopapp.presentation.category.composable

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTopPositionInRootIsEqualTo
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shopapp.di.AppModule
import com.example.shopapp.presentation.MainActivity
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.CART_BTN
import com.example.shopapp.util.Constants.CATEGORY_CHECKBOXES
import com.example.shopapp.util.Constants.CATEGORY_FILTER_SECTION
import com.example.shopapp.util.Constants.CATEGORY_PRICE_SLIDER
import com.example.shopapp.util.Constants.CATEGORY_PRICE_SLIDER_ITEM
import com.example.shopapp.util.Constants.CATEGORY_SORT_SECTION
import com.example.shopapp.util.Constants.CATEGORY_TOP_BAR
import com.example.shopapp.util.Constants.SORT_AND_FILTER_BTN
import com.example.shopapp.util.Constants.bottomBarHeight
import com.example.shopapp.util.Constants.categoryId
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
class CategoryScreenTest {

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
                    startDestination = Screen.CategoryScreen.route + categoryId + "={$categoryId}"
                ) {
                    composable(
                        route = Screen.CategoryScreen.route + categoryId + "={$categoryId}",
                        arguments = listOf(
                            navArgument(
                                name = categoryId
                            ) {
                                type = NavType.StringType
                                defaultValue = "all"
                            }
                        )
                    ) {
                        CategoryScreen(
                            navController = navController,
                            bottomBarHeight = bottomBarHeight.dp
                        )
                    }
                }
            }
        }
    }

    @Test
    fun categoryScreenTopBar_hasCorrectNumberOfItems() {
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(CATEGORY_TOP_BAR).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(3)
    }

    @Test
    fun categoryScreenTopBar_categoryNameIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(0).assertExists()
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(0).assertTextContains("all")
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(0).assertLeftPositionInRootIsEqualTo(10.dp)
    }

    @Test
    fun categoryScreenTopBar_sortButtonIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(1).assertContentDescriptionContains(SORT_AND_FILTER_BTN)
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(1).assertHasClickAction()
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(1).assertTopPositionInRootIsEqualTo(15.dp)
    }

    @Test
    fun categoryScreenTopBar_cartButtonIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(2).assertContentDescriptionContains(CART_BTN)
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(2).assertHasClickAction()
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(1).assertTopPositionInRootIsEqualTo(15.dp)
    }

    @Test
    fun categoryScreenSortAndFilterSection_clickToggle_isVisible() {
        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).assertDoesNotExist()
        composeRule.onNodeWithTag(CATEGORY_FILTER_SECTION).assertDoesNotExist()

        composeRule.onNodeWithContentDescription(SORT_AND_FILTER_BTN).performClick()

        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).assertExists()
        composeRule.onNodeWithTag(CATEGORY_FILTER_SECTION).assertExists()
    }

    @Test
    fun categoryScreenSortAndFilterSection_clickToggleTwoTimes_isNotVisible() {
        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).assertDoesNotExist()
        composeRule.onNodeWithTag(CATEGORY_FILTER_SECTION).assertDoesNotExist()

        composeRule.onNodeWithContentDescription(SORT_AND_FILTER_BTN).performClick()

        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_FILTER_SECTION).assertIsDisplayed()

        composeRule.onNodeWithContentDescription(SORT_AND_FILTER_BTN).performClick()

        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).assertDoesNotExist()
        composeRule.onNodeWithTag(CATEGORY_FILTER_SECTION).assertDoesNotExist()
    }

    @Test
    fun categoryScreenSortSection_allItemsAreDisplayedCorrectly() {
        composeRule.onNodeWithContentDescription(SORT_AND_FILTER_BTN).performClick()
        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).assertIsDisplayed()

        val sortOptions = composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).fetchSemanticsNode().children.size
        assertThat(sortOptions).isEqualTo(4)

        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(0).assertTextContains("Name A-Z")
        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(0).assertHasClickAction()

        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(1).assertTextContains("Name Z-A")
        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(1).assertHasClickAction()

        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(2).assertTextContains("Price Low to High")
        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(2).assertHasClickAction()

        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(3).assertTextContains("Price High to Low")
        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(3).assertHasClickAction()
    }

    @Test
    fun categoryScreenFilterSection_filterByPrice_allItemsAreDisplayedCorrectly() {
        composeRule.onNodeWithContentDescription(SORT_AND_FILTER_BTN).performClick()
        composeRule.onNodeWithTag(CATEGORY_FILTER_SECTION).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_PRICE_SLIDER).assertIsDisplayed()

        val items = composeRule.onNodeWithTag(CATEGORY_PRICE_SLIDER).fetchSemanticsNode().children.size
        assertThat(items).isEqualTo(2)

        composeRule.onNodeWithTag(CATEGORY_PRICE_SLIDER).onChildAt(0).assertTextContains("0.00 - 0.00")
        composeRule.onNodeWithTag(CATEGORY_PRICE_SLIDER_ITEM).assertIsDisplayed()
    }

    @Test
    fun categoryScreenFilterSection_filterByCategory_allItemsAreDisplayedCorrectly() {
        composeRule.onNodeWithContentDescription(SORT_AND_FILTER_BTN).performClick()
        composeRule.onNodeWithTag(CATEGORY_FILTER_SECTION).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).assertIsDisplayed()

        val categoryFilterOptions = composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).fetchSemanticsNode().children.size
        assertThat(categoryFilterOptions).isEqualTo(4)

        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(0).assertTextContains("Men's clothing")
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(0).assertHasClickAction()

        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(1).assertTextContains("Women's clothing")
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(1).assertHasClickAction()

        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(2).assertTextContains("Jewelery")
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(2).assertHasClickAction()

        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(3).assertTextContains("Electronics")
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(3).assertHasClickAction()
    }
}