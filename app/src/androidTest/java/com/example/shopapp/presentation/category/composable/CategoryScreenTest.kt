package com.example.shopapp.presentation.category.composable

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
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
import com.example.shopapp.util.Constants.CATEGORY_SORT_SECTION
import com.example.shopapp.util.Constants.CATEGORY_TOP_BAR
import com.example.shopapp.util.Constants.SORT_BTN
import com.example.shopapp.util.Constants.bottomBarHeight
import com.example.shopapp.util.Constants.categoryId
import com.example.shopapp.util.Constants.categoryName
import com.example.shopapp.util.Screen
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
                                defaultValue = categoryName
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
    fun categoryScreenTopBar_categoryNameIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(0).assertExists()
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(0).assertTextContains(categoryName)
    }

    @Test
    fun categoryScreenTopBar_sortButtonIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(1).assertContentDescriptionContains(SORT_BTN)
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(1).assertHasClickAction()
    }

    @Test
    fun categoryScreenTopBar_cartButtonIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(2).assertContentDescriptionContains(CART_BTN)
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(2).assertHasClickAction()
    }

    @Test
    fun categoryScreenSortSection_clickToggle_isVisible() {
        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(SORT_BTN).performClick()
        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).assertExists()
    }

    @Test
    fun categoryScreenSortSection_clickToggleTwoTimes_isNotVisible() {
        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(SORT_BTN).performClick()
        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).assertIsDisplayed()
        composeRule.onNodeWithContentDescription(SORT_BTN).performClick()
        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).assertDoesNotExist()
    }
}