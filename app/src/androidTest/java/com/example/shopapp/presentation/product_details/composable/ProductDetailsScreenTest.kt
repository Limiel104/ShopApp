package com.example.shopapp.presentation.product_details.composable

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.compose.ui.test.swipeUp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shopapp.di.AppModule
import com.example.shopapp.presentation.MainActivity
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.ADD_TO_CART_BTN
import com.example.shopapp.util.Constants.FAVOURITE_BTN
import com.example.shopapp.util.Constants.GO_BACK_BTN
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_ADD_TO_CART_BTN
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_BOTTOM_SHEET
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_IMAGE_ITEM
import com.example.shopapp.util.Constants.addToCart
import com.example.shopapp.util.Constants.productDescription
import com.example.shopapp.util.Constants.productDescriptionTitle
import com.example.shopapp.util.Constants.productId
import com.example.shopapp.util.Constants.productName
import com.example.shopapp.util.Constants.productPrice
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
class ProductDetailsScreenTest {

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
                    startDestination = Screen.ProductDetailsScreen.route + productId + "={${productId}}"
                ) {
                    composable(
                        route = Screen.ProductDetailsScreen.route + productId + "={${productId}}",
                        arguments = listOf(
                            navArgument(
                                name = productId
                            ) {
                                type = NavType.StringType
                                defaultValue = productName
                            }
                        )
                    ) {
                        ProductDetailsScreen(
                            navController = navController
                        )
                    }
                }
            }
        }
    }
    @Test
    fun productDetailsScreenProductDetailsImageItem_hasCorrectNumberOfItems() {
        composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_ITEM).assertExists()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_ITEM).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_ITEM).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(3)
    }

    @Test
    fun productDetailsScreenProductDetailsImageItem_goBackButtonIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_ITEM).assertExists()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_ITEM).assertIsDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_ITEM).onChildAt(1).assertContentDescriptionContains(GO_BACK_BTN)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_ITEM).onChildAt(1).assertHasClickAction()
    }

    @Test
    fun productDetailsScreenProductDetailsImageItem_cartButtonIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_ITEM).assertExists()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_ITEM).assertIsDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_ITEM).onChildAt(2).assertContentDescriptionContains(ADD_TO_CART_BTN)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_ITEM).onChildAt(2).assertHasClickAction()
    }

    @Test
    fun productDetailsScreenProductDetailsBottomSheet_hasCorrectNumberOfItems() {
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertExists()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsNotDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).performTouchInput { swipeUp() }
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(6)
    }

    @Test
    fun productDetailsScreenProductDetailsBottomSheet_swipeUpAndDownWorksCorrectly() {
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertExists()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsNotDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).performTouchInput { swipeUp() }
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).performTouchInput { swipeDown() }
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsNotDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).performTouchInput { swipeUp() }
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsDisplayed()
    }

    @Test
    fun productDetailsScreenProductDetailsBottomSheet_productNameIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).performTouchInput { swipeUp() }
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(0).assertTextEquals(productName)
    }

    @Test
    fun productDetailsScreenProductDetailsBottomSheet_productPriceIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).performTouchInput { swipeUp() }
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(1).assertTextEquals(productPrice)
    }

    @Test
    fun productDetailsScreenProductDetailsBottomSheet_favouriteButtonIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).performTouchInput { swipeUp() }
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(2).assertContentDescriptionContains(FAVOURITE_BTN)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(2).assertHasClickAction()
    }

    @Test
    fun productDetailsScreenProductDetailsBottomSheet_productDescriptionTitleIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).performTouchInput { swipeUp() }
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(3).assertTextEquals(productDescriptionTitle)
    }

    @Test
    fun productDetailsScreenProductDetailsBottomSheet_productDescriptionIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).performTouchInput { swipeUp() }
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(4).assertTextEquals(productDescription)
    }

    @Test
    fun productDetailsScreenProductDetailsBottomSheet_addToCartButtonIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).performTouchInput { swipeUp() }
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_ADD_TO_CART_BTN).assertTextEquals(addToCart)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_ADD_TO_CART_BTN).assertHasClickAction()
    }
}