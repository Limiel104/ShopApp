package com.example.shopapp.presentation.favourites.composable

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.assertPositionInRootIsEqualTo
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onParent
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.di.AppModule
import com.example.shopapp.domain.model.Product
import com.example.shopapp.presentation.MainActivity
import com.example.shopapp.presentation.common.format.priceToString
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants
import com.example.shopapp.util.Constants.ADD_TO_CART_BTN
import com.example.shopapp.util.Constants.CART_BTN
import com.example.shopapp.util.Constants.DELETE_BTN
import com.example.shopapp.util.Constants.FAVOURITES_CONTENT
import com.example.shopapp.util.Constants.FAVOURITES_CPI
import com.example.shopapp.util.Constants.FAVOURITES_TOP_BAR
import com.example.shopapp.util.Constants.FAVOURITES_LAZY_VERTICAL_GRID
import com.example.shopapp.util.Constants.PRODUCT_ITEM_TITLE
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

    private lateinit var productList: List<Product>

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()

        productList = listOf(
            Product(
                id = 1,
                title = "Shirt",
                price = 195.59,
                description = Constants.productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = true
            ),
            Product(
                id = 2,
                title = "Trousers",
                price = 195.59,
                description = Constants.productDescription,
                category = "women's clothing",
                imageUrl = "imageUrl",
                isInFavourites = true
            ),
            Product(
                id = 3,
                title = "Hoodie",
                price = 195.59,
                description = Constants.productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = true
            ),
            Product(
                id = 4,
                title = "Blouse",
                price = 195.59,
                description = Constants.productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = true
            ),
            Product(
                id = 4,
                title = "Earrings",
                price = 400.59,
                description = Constants.productDescription,
                category = "jewelery",
                imageUrl = "imageUrl",
                isInFavourites = true
            )
        )
    }

    private fun setScreenState(
        productList: List<Product>,
        isLoading: Boolean = false,
    ) {
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
                            productList = productList,
                            isLoading = isLoading,
                            onProductSelected = {},
                            onDelete = {},
                            onGoToCart = {}
                        )
                    }
                }
            }
        }
    }

    @Test
    fun favouriteScreenTopBar_hasCorrectNumberOfItems() {
        setScreenState(
            productList = productList
        )

        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(2)
    }

    @Test
    fun categoryScreenTopBar_topBarIsDisplayedCorrectly() {
        setScreenState(
            productList = productList
        )

        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).assertPositionInRootIsEqualTo(0.dp,0.dp)
        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).assertHeightIsEqualTo(64.dp)
        val deviceWidth = composeRule.onNodeWithTag(FAVOURITES_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).assertWidthIsEqualTo(deviceWidth)
    }

    @Test
    fun favouriteScreenTopBar_titleIsDisplayedCorrectly() {
        setScreenState(
            productList = productList
        )

        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).onChildAt(0).assertTextContains("Favourites")
        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).onChildAt(0).assertLeftPositionInRootIsEqualTo(16.dp)
    }

    @Test
    fun favouritesScreenTopBar_cartButtonIsDisplayedCorrectly() {
        setScreenState(
            productList = productList
        )

        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).assertIsEnabled()
        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).onChildAt(1).assertContentDescriptionContains(CART_BTN)
        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).onChildAt(1).assertHasClickAction()

        val deviceWidth = composeRule.onNodeWithTag(FAVOURITES_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).onChildAt(1).assertPositionInRootIsEqualTo(deviceWidth-48.dp,12.dp)
        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).onChildAt(1).assertHeightIsEqualTo(40.dp)
        composeRule.onNodeWithTag(FAVOURITES_TOP_BAR).onChildAt(1).assertWidthIsEqualTo(40.dp)
    }

    @Test
    fun favouritesScreenLazyVerticalGrid_hasCorrectNumberOfVisibleItems() {
        setScreenState(
            productList = productList
        )

        composeRule.onNodeWithTag(FAVOURITES_LAZY_VERTICAL_GRID).assertExists()
        composeRule.onNodeWithTag(FAVOURITES_LAZY_VERTICAL_GRID).assertIsDisplayed()
        val numberOfChildrenVisible = composeRule.onNodeWithTag(FAVOURITES_LAZY_VERTICAL_GRID).fetchSemanticsNode().children.size
        assertThat(numberOfChildrenVisible).isEqualTo(4)
    }

    @Test
    fun favouritesScreenLazyGrid_isDisplayedCorrectly() {
        setScreenState(
            productList = productList
        )
        val deviceWidth = composeRule.onNodeWithTag(FAVOURITES_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(FAVOURITES_LAZY_VERTICAL_GRID).assertExists()
        composeRule.onNodeWithTag(FAVOURITES_LAZY_VERTICAL_GRID).assertIsDisplayed()
        composeRule.onNodeWithTag(FAVOURITES_LAZY_VERTICAL_GRID).assertPositionInRootIsEqualTo(10.dp,64.dp)
        composeRule.onNodeWithTag(FAVOURITES_LAZY_VERTICAL_GRID).assertWidthIsEqualTo(deviceWidth-20.dp)
    }

    @Test
    fun favouritesScreenProduct_isDisplayedCorrectly() {
        setScreenState(
            productList = productList
        )

        composeRule.onNodeWithTag(PRODUCT_ITEM_TITLE + " ${productList[0].title}", useUnmergedTree = true).onChildAt(0).assertTextEquals(productList[0].title)
        composeRule.onNodeWithTag(PRODUCT_ITEM_TITLE + " ${productList[0].title}", useUnmergedTree = true).onChildAt(1).assertTextEquals(productList[0].priceToString())
        composeRule.onNodeWithContentDescription(DELETE_BTN+ " ${productList[0].title}").assertIsDisplayed()
        composeRule.onNodeWithContentDescription(DELETE_BTN+ " ${productList[0].title}").assertHasClickAction()
        composeRule.onNodeWithTag(ADD_TO_CART_BTN+ " ${productList[0].title}").assertIsDisplayed()
        composeRule.onNodeWithTag(ADD_TO_CART_BTN+ " ${productList[0].title}").assertIsEnabled()
        composeRule.onNodeWithTag(ADD_TO_CART_BTN+ " ${productList[0].title}").assertHasClickAction()
        composeRule.onNodeWithTag(ADD_TO_CART_BTN+ " ${productList[0].title}").assertTextEquals("Add to Cart")
        composeRule.onNodeWithTag(productList[0].title).assertPositionInRootIsEqualTo(10.dp,64.dp)
    }

    @Test
    fun favouritesScreenCircularProgressIndicator_IsDisplayedCorrectly() {
        setScreenState(
            productList = productList,
            isLoading = true
        )

        val deviceWidth = composeRule.onNodeWithTag(FAVOURITES_CONTENT).onParent().getBoundsInRoot().right
        val deviceHeight = composeRule.onNodeWithTag(FAVOURITES_CONTENT).onParent().getBoundsInRoot().bottom
        val leftPosition = deviceWidth.value/2
        val topPosition = deviceHeight.value/2

        composeRule.onNodeWithTag(FAVOURITES_CPI).assertExists()
        composeRule.onNodeWithTag(FAVOURITES_CPI).assertIsDisplayed()
        composeRule.onNodeWithTag(FAVOURITES_CPI).assertPositionInRootIsEqualTo(0.dp,0.dp)
        composeRule.onNodeWithTag(FAVOURITES_CPI).assertHeightIsEqualTo(deviceHeight)
        composeRule.onNodeWithTag(FAVOURITES_CPI).assertWidthIsEqualTo(deviceWidth)

        composeRule.onNodeWithTag(FAVOURITES_CPI).onChild().assertPositionInRootIsEqualTo(leftPosition.dp-20.dp,topPosition.dp-20.dp)
        composeRule.onNodeWithTag(FAVOURITES_CPI).onChild().assertWidthIsEqualTo(40.dp)
    }
}