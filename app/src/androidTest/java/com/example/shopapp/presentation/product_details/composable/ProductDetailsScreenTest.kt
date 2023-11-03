package com.example.shopapp.presentation.product_details.composable

import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.assertPositionInRootIsEqualTo
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildAt
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
import com.example.shopapp.util.Constants.CART_BTN
import com.example.shopapp.util.Constants.FAVOURITES_BTN
import com.example.shopapp.util.Constants.GO_BACK_BTN
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_ADD_TO_CART_BTN
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_BOTTOM_SHEET
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_COLUMN
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_CONTENT
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_CPI
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_IMAGE_BOX
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_TOP_BAR
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
            price = 195.59,
            description = productDescription,
            category = "men's clothing",
            imageUrl = "",
            isInFavourites = true
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
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
    fun productDetailsScreenTopBar_hasCorrectNumberOfItems() {
        setScreenState(
            product = product
        )

        composeRule.onNodeWithTag(PRODUCT_DETAILS_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_TOP_BAR).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(PRODUCT_DETAILS_TOP_BAR).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(2)
    }

    @Test
    fun productDetailsScreenTopBar_topBarIsDisplayedCorrectly() {
        setScreenState(
            product = product
        )

        composeRule.onNodeWithTag(PRODUCT_DETAILS_TOP_BAR).assertPositionInRootIsEqualTo(0.dp,0.dp)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_TOP_BAR).assertHeightIsEqualTo(64.dp)
        val deviceWidth = composeRule.onNodeWithTag(PRODUCT_DETAILS_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(PRODUCT_DETAILS_TOP_BAR).assertWidthIsEqualTo(deviceWidth)
    }

    @Test
    fun productDetailsScreenTopBar_goBackButtonIsDisplayedCorrectly() {
        setScreenState(
            product = product
        )

        composeRule.onNodeWithTag(PRODUCT_DETAILS_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_TOP_BAR).onChildAt(0).assertContentDescriptionContains(GO_BACK_BTN)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_TOP_BAR).onChildAt(0).assertHasClickAction()

        composeRule.onNodeWithTag(PRODUCT_DETAILS_TOP_BAR).onChildAt(0).assertPositionInRootIsEqualTo(8.dp,12.dp)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_TOP_BAR).onChildAt(0).assertHeightIsEqualTo(40.dp)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_TOP_BAR).onChildAt(0).assertWidthIsEqualTo(40.dp)
    }

    @Test
    fun productDetailsScreenTopBar_cartButtonIsDisplayedCorrectly() {
        setScreenState(
            product = product
        )

        composeRule.onNodeWithTag(PRODUCT_DETAILS_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_TOP_BAR).onChildAt(1).assertContentDescriptionContains(CART_BTN)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_TOP_BAR).onChildAt(1).assertHasClickAction()

        val deviceWidth = composeRule.onNodeWithTag(PRODUCT_DETAILS_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(PRODUCT_DETAILS_TOP_BAR).onChildAt(1).assertPositionInRootIsEqualTo(deviceWidth-48.dp,12.dp)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_TOP_BAR).onChildAt(1).assertHeightIsEqualTo(40.dp)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_TOP_BAR).onChildAt(1).assertWidthIsEqualTo(40.dp)
    }

    @Test
    fun productDetailsScreenColumn_hasCorrectNumberOfItems() {
        setScreenState(
            product = product
        )

        composeRule.onNodeWithTag(PRODUCT_DETAILS_COLUMN).assertExists()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_COLUMN).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(PRODUCT_DETAILS_COLUMN).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(1)
    }

    @Test
    fun productDetailsScreenImageBox_hasCorrectNumberOfItems() {
        setScreenState(
            product = product
        )

        composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_BOX).assertExists()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_BOX).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_BOX).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(1)
    }

    @Test
    fun productDetailsScreenImageBox_imageIsDisplayedCorrectly() {
        setScreenState(
            product = product
        )

        val deviceWidth = composeRule.onNodeWithTag(PRODUCT_DETAILS_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_BOX).assertExists()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_BOX).assertIsDisplayed()

        composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_BOX).assertPositionInRootIsEqualTo(0.dp,64.dp)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_IMAGE_BOX).assertWidthIsEqualTo(deviceWidth)
    }

    @Test
    fun productDetailsScreenBottomSheet_hasCorrectNumberOfItems() {
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
    fun productDetailsScreenBottomSheet_isDisplayedCorrectly() {
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
    fun productDetailsScreenBottomSheet_swipeUpAndDownWorksCorrectly() {
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
    fun productDetailsScreenBottomSheet_productTitleIsDisplayedCorrectly() {
        setScreenState(
            product = product
        )

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).performTouchInput { swipeUp() }
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsDisplayed()

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(0).assertIsDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(0).assertTextEquals("Shirt")
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(0).assertLeftPositionInRootIsEqualTo(15.dp)
    }

    @Test
    fun productDetailsScreenBottomSheet_productPriceIsDisplayedCorrectly() {
        setScreenState(
            product = product
        )

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).performTouchInput { swipeUp() }
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsDisplayed()

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(1).assertIsDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(1).assertTextEquals("195,59 PLN")
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(1).assertLeftPositionInRootIsEqualTo(15.dp)
    }

    @Test
    fun productDetailsScreenBottomSheet_favouriteButtonIsDisplayedCorrectly() {
        setScreenState(
            product = product
        )
        val deviceWidth = composeRule.onNodeWithTag(PRODUCT_DETAILS_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).performTouchInput { swipeUp() }
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsDisplayed()

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(2).assertIsDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(2).assertContentDescriptionContains(FAVOURITES_BTN)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(2).assertHasClickAction()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(2).assertLeftPositionInRootIsEqualTo(deviceWidth-15.dp-44.dp)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(2).assertHeightIsEqualTo(40.dp)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(2).assertWidthIsEqualTo(40.dp)
    }

    @Test
    fun productDetailsScreenBottomSheet_productDescriptionTitleIsDisplayedCorrectly() {
        setScreenState(
            product = product
        )

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).performTouchInput { swipeUp() }
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsDisplayed()

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(3).assertIsDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(3).assertTextEquals("Description")
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(3).assertLeftPositionInRootIsEqualTo(15.dp)
    }

    @Test
    fun productDetailsScreenBottomSheet_productDescriptionIsDisplayedCorrectly() {
        setScreenState(
            product = product
        )
        val deviceWidth = composeRule.onNodeWithTag(PRODUCT_DETAILS_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).performTouchInput { swipeUp() }
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsDisplayed()

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(4).assertIsDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(4).assertTextEquals(productDescription)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(4).assertLeftPositionInRootIsEqualTo(15.dp)
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).onChildAt(4).assertWidthIsEqualTo(deviceWidth-30.dp)
    }

    @Test
    fun productDetailsScreenBottomSheet_addToCartButtonIsDisplayedCorrectly() {
        setScreenState(
            product = product
        )

        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).performTouchInput { swipeUp() }
        composeRule.onNodeWithTag(PRODUCT_DETAILS_BOTTOM_SHEET).assertIsDisplayed()

        composeRule.onNodeWithTag(PRODUCT_DETAILS_ADD_TO_CART_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_ADD_TO_CART_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(PRODUCT_DETAILS_ADD_TO_CART_BTN).assertTextEquals("Add to Cart")
        composeRule.onNodeWithTag(PRODUCT_DETAILS_ADD_TO_CART_BTN).assertHasClickAction()
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