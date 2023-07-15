package com.example.shopapp.presentation.category_list.composable

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTopPositionInRootIsEqualTo
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.di.AppModule
import com.example.shopapp.presentation.MainActivity
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.CART_BTN
import com.example.shopapp.util.Constants.CATEGORY_LIST_LAZY_COLUMN
import com.example.shopapp.util.Constants.CATEGORY_LIST_TOP_BAR
import com.example.shopapp.util.Constants.CATEGORY_NAME_1
import com.example.shopapp.util.Constants.CATEGORY_NAME_2
import com.example.shopapp.util.Constants.CATEGORY_NAME_3
import com.example.shopapp.util.Constants.CATEGORY_NAME_4
import com.example.shopapp.util.Constants.CATEGORY_NAME_5
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
class CategoryListScreenTest {

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
                    startDestination = Screen.CategoryListScreen.route
                ) {
                    composable(
                        route = Screen.CategoryListScreen.route,
                    ) {
                        CategoryListScreen(
                            navController = navController,
                            bottomBarHeight = bottomBarHeight.dp
                        )
                    }
                }
            }
        }
    }

    @Test
    fun categoryListScreenTopBar_hasCorrectNumberOfItems() {
        composeRule.onNodeWithTag(CATEGORY_LIST_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(CATEGORY_LIST_TOP_BAR).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(CATEGORY_LIST_TOP_BAR).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(1)
    }

    @Test
    fun categoryListScreenTopBar_cartButtonIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(CATEGORY_LIST_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(CATEGORY_LIST_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_LIST_TOP_BAR).onChild().assertContentDescriptionContains(CART_BTN)
        composeRule.onNodeWithTag(CATEGORY_LIST_TOP_BAR).onChild().assertHasClickAction()
        composeRule.onNodeWithTag(CATEGORY_LIST_TOP_BAR).onChild().assertTopPositionInRootIsEqualTo(15.dp)
    }

    @Test
    fun categoryListScreenLazyColumn_hasCorrectNumberOfItems() {
        composeRule.onNodeWithTag(CATEGORY_LIST_LAZY_COLUMN).assertExists()
        composeRule.onNodeWithTag(CATEGORY_LIST_LAZY_COLUMN).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(CATEGORY_LIST_LAZY_COLUMN).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(5)
    }

    @Test
    fun categoryListScreenLazyColumn_isDisplayingCategoriesCorrectly() {
        composeRule.onNodeWithTag(CATEGORY_NAME_1).assertExists()
        composeRule.onNodeWithTag(CATEGORY_NAME_1).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_NAME_1).assertHasClickAction()

        composeRule.onNodeWithTag(CATEGORY_NAME_2).assertExists()
        composeRule.onNodeWithTag(CATEGORY_NAME_2).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_NAME_2).assertHasClickAction()

        composeRule.onNodeWithTag(CATEGORY_NAME_3).assertExists()
        composeRule.onNodeWithTag(CATEGORY_NAME_3).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_NAME_3).assertHasClickAction()

        composeRule.onNodeWithTag(CATEGORY_NAME_4).assertExists()
        composeRule.onNodeWithTag(CATEGORY_NAME_4).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_NAME_4).assertHasClickAction()

        composeRule.onNodeWithTag(CATEGORY_NAME_5).assertExists()
        composeRule.onNodeWithTag(CATEGORY_NAME_5).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_NAME_5).assertHasClickAction()
    }
}