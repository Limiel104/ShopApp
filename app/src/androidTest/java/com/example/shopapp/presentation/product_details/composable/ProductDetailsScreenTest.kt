package com.example.shopapp.presentation.product_details.composable

import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.assertPositionInRootIsEqualTo
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.assertTopPositionInRootIsEqualTo
import androidx.compose.ui.test.assertTouchHeightIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.compose.ui.test.swipeUp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shopapp.di.AppModule
import com.example.shopapp.domain.model.Product
import com.example.shopapp.presentation.MainActivity
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.ADD_TO_CART_BTN
import com.example.shopapp.util.Constants.FAVOURITES_BTN
import com.example.shopapp.util.Constants.GO_BACK_BTN
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_ADD_TO_CART_BTN
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_BOTTOM_SHEET
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_CONTENT
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_CPI
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_IMAGE_ITEM
import com.example.shopapp.util.Constants.bottomSheetPeekHeight
import com.example.shopapp.util.Constants.productDescription
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

    private lateinit var product: Product

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()

        product = Product(
            id = 1,
            title = "Shirt",
            price = "195,59 PLN",
            description = productDescription,
            category = "men's clothing",
            imageUrl = "",
            isInFavourites = true
        )
    }

    @OptIn(ExperimentalMaterialApi::class)
    private fun setScreenState(
        product: Product,
        isLoading: Boolean = false
    ) {
        composeRule.activity.setContent {
            val navController = rememberNavController()
            ShopAppTheme() {
                NavHost(
                    navController = navController,
                    startDestination = Screen.ProductDetailsScreen.route + "productId={productId}"
                ) {
                    composable(
                        route = Screen.ProductDetailsScreen.route + "productId={productId}",
                        arguments = listOf(
                            navArgument(
                                name = "productId"
                            ) {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        ProductDetailsContent(
                            scaffoldState = rememberBottomSheetScaffoldState(),
                            product = product,
                            isLoading = isLoading,
                            onNavigateBack = {},
                            onAddToCart = {}
                        )
                    }
                }
            }
        }
    }

    @Test
    fun productDetailsScreenProductDetailsImageItem_hasCorrectNumberOfItems() {
        setScreenState(
            product = product
        )

        composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_ITEM).assertExists()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_ITEM).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_ITEM).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(3)
    }

    @Test
    fun productDetailsScreenProductDetailsImageItem_imageIsDisplayedCorrectly() {
        setScreenState(
            product = product
        )

        val deviceHeight = composeRule.onNodeWithTag(PRODUCT_DETAILS_CONTENT).onParent().getBoundsInRoot().bottom
        val deviceWidth = composeRule.onNodeWithTag(PRODUCT_DETAILS_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_ITEM).assertExists()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_ITEM).assertIsDisplayed()

        composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_ITEM).assertPositionInRootIsEqualTo(15.dp,15.dp)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_ITEM).assertTouchHeightIsEqualTo(deviceHeight-bottomSheetPeekHeight.dp-15.dp)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_ITEM).assertWidthIsEqualTo(deviceWidth-30.dp)
    }

    @Test
    fun productDetailsScreenProductDetailsImageItem_buttonsAreDisplayedCorrectly() {
        setScreenState(
            product = product
        )

        val buttonList = listOf(GO_BACK_BTN, ADD_TO_CART_BTN)
        val deviceWidth = composeRule.onNodeWithTag(PRODUCT_DETAILS_CONTENT).onParent().getBoundsInRoot().right
        val leftPositionList = listOf(20.dp,deviceWidth-20.dp-36.dp)

        buttonList.zip(leftPositionList) { button, leftPosition ->
            composeRule.onNodeWithContentDescription(button).assertExists()
            composeRule.onNodeWithContentDescription(button).assertIsDisplayed()
            composeRule.onNodeWithContentDescription(button).assertHasClickAction()

            composeRule.onNodeWithContentDescription(button).assertTopPositionInRootIsEqualTo(20.dp)
            composeRule.onNodeWithContentDescription(button).assertLeftPositionInRootIsEqualTo(leftPosition)
            composeRule.onNodeWithContentDescription(button).assertHeightIsEqualTo(36.dp)
            composeRule.onNodeWithContentDescription(button).assertWidthIsEqualTo(36.dp)
        }
    }

    @Test
    fun productDetailsScreenProductDetailsBottomSheet_hasCorrectNumberOfItems() {
        setScreenState(
            product = product
        )

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertExists()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsNotDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).performTouchInput { swipeUp() }
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(6)
    }

    @Test
    fun productDetailsScreenProductDetailsBottomSheet_isDisplayedCorrectly() {
        setScreenState(
            product = product
        )
        val deviceWidth = composeRule.onNodeWithTag(PRODUCT_DETAILS_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertExists()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsNotDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).performTouchInput { swipeUp() }
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsDisplayed()

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertLeftPositionInRootIsEqualTo(15.dp)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertWidthIsEqualTo(deviceWidth-30.dp)
    }

    @Test
    fun productDetailsScreenProductDetailsBottomSheet_swipeUpAndDownWorksCorrectly() {
        setScreenState(
            product = product
        )

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
    fun productDetailsScreenProductDetailsBottomSheet_productTitleIsDisplayedCorrectly() {
        setScreenState(
            product = product
        )

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).performTouchInput { swipeUp() }
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsDisplayed()

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(0).assertIsDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(0).assertTextContains("Shirt")
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(0).assertLeftPositionInRootIsEqualTo(15.dp)
    }

    @Test
    fun productDetailsScreenProductDetailsBottomSheet_productPriceIsDisplayedCorrectly() {
        setScreenState(
            product = product
        )

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).performTouchInput { swipeUp() }
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsDisplayed()

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(1).assertIsDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(1).assertTextContains("195,59 PLN")
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(1).assertLeftPositionInRootIsEqualTo(15.dp)
    }

    @Test
    fun productDetailsScreenProductDetailsBottomSheet_favouriteButtonIsDisplayedCorrectly() {
        setScreenState(
            product = product
        )
        val deviceWidth = composeRule.onNodeWithTag(PRODUCT_DETAILS_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).performTouchInput { swipeUp() }
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsDisplayed()

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(2).assertIsDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(2).assertContentDescriptionContains(FAVOURITES_BTN)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(2).assertHasClickAction()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(2).assertLeftPositionInRootIsEqualTo(deviceWidth-15.dp-36.dp-5.dp)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(2).assertHeightIsEqualTo(36.dp)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(2).assertWidthIsEqualTo(36.dp)
    }

    @Test
    fun productDetailsScreenProductDetailsBottomSheet_productDescriptionTitleIsDisplayedCorrectly() {
        setScreenState(
            product = product
        )

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).performTouchInput { swipeUp() }
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsDisplayed()

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(3).assertIsDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(3).assertTextContains("Description")
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(3).assertLeftPositionInRootIsEqualTo(15.dp)
    }

    @Test
    fun productDetailsScreenProductDetailsBottomSheet_productDescriptionIsDisplayedCorrectly() {
        setScreenState(
            product = product
        )
        val deviceWidth = composeRule.onNodeWithTag(PRODUCT_DETAILS_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).performTouchInput { swipeUp() }
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsDisplayed()

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(4).assertIsDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(4).assertTextContains(productDescription)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(4).assertLeftPositionInRootIsEqualTo(15.dp)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(4).assertWidthIsEqualTo(deviceWidth-30.dp)
    }

    @Test
    fun productDetailsScreenProductDetailsBottomSheet_addToCartButtonIsDisplayedCorrectly() {
        setScreenState(
            product = product
        )
        val deviceWidth = composeRule.onNodeWithTag(PRODUCT_DETAILS_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).performTouchInput { swipeUp() }
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsDisplayed()

        composeRule.onNodeWithTag(PRODUCT_DETAILS_ADD_TO_CART_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_ADD_TO_CART_BTN).assertTextEquals("Add to Cart")
        composeRule.onNodeWithTag(PRODUCT_DETAILS_ADD_TO_CART_BTN).assertHasClickAction()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_ADD_TO_CART_BTN).assertLeftPositionInRootIsEqualTo(15.dp)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_ADD_TO_CART_BTN).assertWidthIsEqualTo(deviceWidth-30.dp)
    }

    @Test
    fun favouritesScreenCircularProgressIndicator_IsDisplayedCorrectly() {
        setScreenState(
            product = product,
            isLoading = true
        )

        val deviceWidth = composeRule.onNodeWithTag(PRODUCT_DETAILS_CONTENT).onParent().getBoundsInRoot().right
        val deviceHeight = composeRule.onNodeWithTag(PRODUCT_DETAILS_CONTENT).onParent().getBoundsInRoot().bottom
        val leftPosition = deviceWidth.value/2
        val topPosition = deviceHeight.value/2

        composeRule.onNodeWithTag(PRODUCT_DETAILS_CPI).assertExists()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_CPI).assertIsDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_CPI).assertPositionInRootIsEqualTo(0.dp,0.dp)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_CPI).assertHeightIsEqualTo(deviceHeight)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_CPI).assertWidthIsEqualTo(deviceWidth)

        composeRule.onNodeWithTag(PRODUCT_DETAILS_CPI).onChild().assertPositionInRootIsEqualTo(leftPosition.dp-20.dp,topPosition.dp-20.dp)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_CPI).onChild().assertWidthIsEqualTo(40.dp)
    }
}