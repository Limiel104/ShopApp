package com.example.shopapp.presentation.category.composable

import androidx.activity.compose.setContent
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertIsToggleable
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.assertPositionInRootIsEqualTo
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.assertTopPositionInRootIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shopapp.di.AppModule
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.util.ProductOrder
import com.example.shopapp.presentation.MainActivity
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Category
import com.example.shopapp.util.Constants.CART_BTN
import com.example.shopapp.util.Constants.CATEGORY_CHECKBOXES
import com.example.shopapp.util.Constants.CATEGORY_CONTENT
import com.example.shopapp.util.Constants.CATEGORY_FILTER_SECTION
import com.example.shopapp.util.Constants.CATEGORY_LAZY_VERTICAL_GRID
import com.example.shopapp.util.Constants.CATEGORY_PRICE_SLIDER
import com.example.shopapp.util.Constants.CATEGORY_PRICE_SLIDER_ITEM
import com.example.shopapp.util.Constants.CATEGORY_SORT_SECTION
import com.example.shopapp.util.Constants.CATEGORY_TOP_BAR
import com.example.shopapp.util.Constants.SORT_AND_FILTER_BTN
import com.example.shopapp.util.Constants.bottomBarHeight
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
class CategoryScreenTest {

    private lateinit var productList: List<Product>
    private lateinit var categoryFilterMap: Map<String,Boolean>

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
                price = "195,59 PLN",
                description = productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = true
            ),
            Product(
                id = 2,
                title = "Trousers",
                price = "195,59 PLN",
                description = productDescription,
                category = "women's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 3,
                title = "Hoodie",
                price = "195,59 PLN",
                description = productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 4,
                title = "Blouse",
                price = "195,59 PLN",
                description = productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = true
            ),
            Product(
                id = 4,
                title = "Earrings",
                price = "400,59 PLN",
                description = productDescription,
                category = "jewelery",
                imageUrl = "imageUrl",
                isInFavourites = true
            )
        )

        categoryFilterMap = mapOf(
            Pair(Category.Men.title,true),
            Pair(Category.Women.title,true),
            Pair(Category.Jewelery.title,true),
            Pair(Category.Electronics.title,true)
        )
    }

    private fun setScreenState(
        categoryId: String = "all",
        productList: List<Product>,
        isSortAndFilterSectionVisible: Boolean = false,
        isLoading: Boolean = false,
        isButtonLocked: Boolean = false,
        isDialogActivated: Boolean = false,
        sliderPosition: ClosedFloatingPointRange<Float> = 0F..0F,
        sliderRange: ClosedFloatingPointRange<Float> = 0F..0F,
        productOrder: ProductOrder = ProductOrder.NameAscending(),
        categoryFilterMap: Map<String,Boolean>
    ) {
        composeRule.activity.setContent {
            val navController = rememberNavController()
            ShopAppTheme() {
                NavHost(
                    navController = navController,
                    startDestination = Screen.CategoryScreen.route + "categoryId={categoryId}"
                ) {
                    composable(
                        route = Screen.CategoryScreen.route + "categoryId={categoryId}",
                        arguments = listOf(
                            navArgument(
                                name = "categoryId"
                            ) {
                                type = NavType.StringType
                                defaultValue = categoryId
                            }
                        )
                    ) {
                        CategoryContent(
                            scaffoldState = rememberScaffoldState(),
                            bottomBarHeight = bottomBarHeight.dp,
                            categoryName = categoryId,
                            productList = productList,
                            isSortAndFilterSectionVisible = isSortAndFilterSectionVisible,
                            isLoading = isLoading,
                            isButtonLocked = isButtonLocked,
                            isDialogActivated = isDialogActivated,
                            sliderPosition = sliderPosition,
                            sliderRange = sliderRange,
                            productOrder = productOrder,
                            categoryFilterMap = categoryFilterMap,
                            onProductSelected = {},
                            onSortAndFilterSelected = {},
                            onFavourite = {},
                            onDismiss = {},
                            onValueChange = {},
                            onOrderChange = {},
                            onCheckedChange = {}
                        )
                    }
                }
            }
        }
    }

    private fun setScreen(
        categoryId: String = "all"
    ) {
        composeRule.activity.setContent {
            val navController = rememberNavController()
            ShopAppTheme() {
                NavHost(
                    navController = navController,
                    startDestination = Screen.CategoryScreen.route + "categoryId={categoryId}"
                ) {
                    composable(
                        route = Screen.CategoryScreen.route + "categoryId={categoryId}",
                        arguments = listOf(
                            navArgument(
                                name = "categoryId"
                            ) {
                                type = NavType.StringType
                                defaultValue = categoryId
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
        setScreenState(
            productList = productList,
            categoryFilterMap = categoryFilterMap
        )

        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(CATEGORY_TOP_BAR).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(3)
    }

    @Test
    fun categoryScreenTopBar_topBarIsDisplayedCorrectly() {
        setScreenState(
            productList = productList,
            categoryFilterMap = categoryFilterMap
        )

        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertTopPositionInRootIsEqualTo(15.dp)
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertHeightIsEqualTo(36.dp)
        val deviceWidth = composeRule.onNodeWithTag(CATEGORY_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertWidthIsEqualTo(deviceWidth-20.dp)
    }

    @Test
    fun categoryScreenTopBar_categoryNameIsDisplayedCorrectly() {
        setScreenState(
            productList = productList,
            categoryFilterMap = categoryFilterMap
        )

        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(0).assertTextEquals("all")
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(0).assertLeftPositionInRootIsEqualTo(10.dp,)
    }

    @Test
    fun categoryListScreenTopBar_sortAndFilterButtonIsDisplayedCorrectly() {
        setScreenState(
            productList = productList,
            categoryFilterMap = categoryFilterMap
        )

        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(1).assertContentDescriptionContains(SORT_AND_FILTER_BTN)
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(1).assertHasClickAction()

        val deviceWidth = composeRule.onNodeWithTag(CATEGORY_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(1).assertPositionInRootIsEqualTo(deviceWidth-82.dp,15.dp)
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(1).assertHeightIsEqualTo(36.dp)
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(1).assertWidthIsEqualTo(36.dp)
    }

    @Test
    fun categoryListScreenTopBar_cartButtonIsDisplayedCorrectly() {
        setScreenState(
            productList = productList,
            categoryFilterMap = categoryFilterMap
        )

        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(2).assertContentDescriptionContains(CART_BTN)
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(2).assertHasClickAction()

        val deviceWidth = composeRule.onNodeWithTag(CATEGORY_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(2).assertPositionInRootIsEqualTo(deviceWidth-46.dp,15.dp)
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(2).assertHeightIsEqualTo(36.dp)
        composeRule.onNodeWithTag(CATEGORY_TOP_BAR).onChildAt(2).assertWidthIsEqualTo(36.dp)
    }

    @Test
    fun categoryScreenSortAndFilterSection_clickToggle_isVisible() {
        setScreen()

        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).assertDoesNotExist()
        composeRule.onNodeWithTag(CATEGORY_FILTER_SECTION).assertDoesNotExist()

        composeRule.onNodeWithContentDescription(SORT_AND_FILTER_BTN).performClick()

        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).assertExists()
        composeRule.onNodeWithTag(CATEGORY_FILTER_SECTION).assertExists()
    }

    @Test
    fun categoryScreenSortAndFilterSection_clickToggleTwoTimes_isNotVisible() {
        setScreen()

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
    fun categoryScreenSortAndFilterSection_isDisplayedCorrectlyWhenCategoryIsALL() {
        setScreenState(
            productList = productList,
            isSortAndFilterSectionVisible = true,
            categoryFilterMap = categoryFilterMap
        )

        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_FILTER_SECTION).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_FILTER_SECTION).onChildAt(0).assertTextEquals("Price")
        composeRule.onNodeWithTag(CATEGORY_PRICE_SLIDER).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_FILTER_SECTION).onChildAt(2).assertTextEquals("Category")
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).assertIsDisplayed()
    }

    @Test
    fun categoryScreenSortAndFilterSection_isDisplayedCorrectlyWhenCategoryIsMensClothing() {
        setScreenState(
            categoryId = "men's clothing",
            productList = productList,
            isSortAndFilterSectionVisible = true,
            categoryFilterMap = categoryFilterMap
        )

        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_FILTER_SECTION).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_FILTER_SECTION).onChildAt(0).assertTextEquals("Price")
        composeRule.onNodeWithTag(CATEGORY_PRICE_SLIDER).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).assertDoesNotExist()
    }

    @Test
    fun categoryScreenSortAndFilterSection_isDisplayedCorrectlyWhenCategoryIsWomensClothing() {
        setScreenState(
            categoryId = "women's clothing",
            productList = productList,
            isSortAndFilterSectionVisible = true,
            categoryFilterMap = categoryFilterMap
        )

        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_FILTER_SECTION).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_FILTER_SECTION).onChildAt(0).assertTextEquals("Price")
        composeRule.onNodeWithTag(CATEGORY_PRICE_SLIDER).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).assertDoesNotExist()
    }

    @Test
    fun categoryScreenSortAndFilterSection_isDisplayedCorrectlyWhenCategoryIsElectronics() {
        setScreenState(
            categoryId = "electronics",
            productList = productList,
            isSortAndFilterSectionVisible = true,
            categoryFilterMap = categoryFilterMap
        )

        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_FILTER_SECTION).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_FILTER_SECTION).onChildAt(0).assertTextEquals("Price")
        composeRule.onNodeWithTag(CATEGORY_PRICE_SLIDER).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).assertDoesNotExist()
    }

    @Test
    fun categoryScreenSortAndFilterSection_isDisplayedCorrectlyWhenCategoryIsJewelery() {
        setScreenState(
            categoryId = "jewelery",
            productList = productList,
            isSortAndFilterSectionVisible = true,
            categoryFilterMap = categoryFilterMap
        )

        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_FILTER_SECTION).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_FILTER_SECTION).onChildAt(0).assertTextEquals("Price")
        composeRule.onNodeWithTag(CATEGORY_PRICE_SLIDER).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).assertDoesNotExist()
    }

    @Test
    fun categoryScreenSortSection_allItemsAreDisplayedCorrectly() {
        setScreenState(
            productList = productList,
            isSortAndFilterSectionVisible = true,
            categoryFilterMap = categoryFilterMap
        )

        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).assertIsDisplayed()

        val sortOptions = composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).fetchSemanticsNode().children.size
        assertThat(sortOptions).isEqualTo(4)

        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(0).assertTextEquals("Name A-Z")
        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(0).assertHasClickAction()
        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(0).assertLeftPositionInRootIsEqualTo(20.dp)

        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(1).assertTextEquals("Name Z-A")
        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(1).assertHasClickAction()
        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(1).assertLeftPositionInRootIsEqualTo(20.dp)


        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(2).assertTextEquals("Price Low to High")
        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(2).assertHasClickAction()

        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(3).assertTextEquals("Price High to Low")
        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(3).assertHasClickAction()
    }

    @Test
    fun categoryScreenSortSection_onlyOneItemCanBeSelectedAtTheSameTime() {
        setScreen()

        composeRule.onNodeWithContentDescription(SORT_AND_FILTER_BTN).performClick()

        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(0).assertIsOn()
        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(1).assertIsOff()
        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(2).assertIsOff()
        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(3).assertIsOff()

        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(2).performClick()

        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(0).assertIsOff()
        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(1).assertIsOff()
        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(2).assertIsOn()
        composeRule.onNodeWithTag(CATEGORY_SORT_SECTION).onChildAt(3).assertIsOff()
    }

    @Test
    fun categoryScreenFilterSection_filterByPrice_allItemsAreDisplayedCorrectly() {
        setScreenState(
            productList = productList,
            isSortAndFilterSectionVisible = true,
            sliderPosition = 3F..5F,
            sliderRange = 0F..10F,
            categoryFilterMap = categoryFilterMap
        )

        composeRule.onNodeWithTag(CATEGORY_FILTER_SECTION).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_FILTER_SECTION).onChildAt(0).assertTextEquals("Price")
        composeRule.onNodeWithTag(CATEGORY_FILTER_SECTION).onChildAt(0).assertLeftPositionInRootIsEqualTo(20.dp)

        composeRule.onNodeWithTag(CATEGORY_PRICE_SLIDER).assertIsDisplayed()
        val items = composeRule.onNodeWithTag(CATEGORY_PRICE_SLIDER).fetchSemanticsNode().children.size
        assertThat(items).isEqualTo(2)

        composeRule.onNodeWithTag(CATEGORY_PRICE_SLIDER).onChildAt(0).assertTextEquals("3.00 - 5.00")
        composeRule.onNodeWithTag(CATEGORY_PRICE_SLIDER_ITEM).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_PRICE_SLIDER_ITEM).assertLeftPositionInRootIsEqualTo(20.dp)
    }

    @Test
    fun categoryScreenFilterSection_filterByCategory_allItemsAreDisplayedCorrectly() {
        setScreenState(
            productList = productList,
            isSortAndFilterSectionVisible = true,
            categoryFilterMap = categoryFilterMap
        )

        composeRule.onNodeWithTag(CATEGORY_FILTER_SECTION).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).assertIsDisplayed()

        val categoryFilterOptions = composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).fetchSemanticsNode().children.size
        assertThat(categoryFilterOptions).isEqualTo(4)

        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(0).assertTextEquals("Men's clothing")
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(0).assertHasClickAction()

        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(1).assertTextEquals("Women's clothing")
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(1).assertHasClickAction()

        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(2).assertTextEquals("Jewelery")
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(2).assertHasClickAction()

        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(3).assertTextEquals("Electronics")
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(3).assertHasClickAction()
    }

    @Test
    fun categoryScreenFilterSection_allItemsCanBeToggled() {
        setScreen()
        composeRule.onNodeWithContentDescription(SORT_AND_FILTER_BTN).performClick()

        for(i in 0..3) {
            composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(i).assertIsToggleable()
            composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(i).assertIsOn()
            composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(i).performClick()
            composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(i).assertIsOff()
        }
    }

    @Test
    fun categoryScreenFilterSection_selectingOneCheckboxDoesNotChangeStateOfTheRest() {
        setScreen()
        composeRule.onNodeWithContentDescription(SORT_AND_FILTER_BTN).performClick()

        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(0).assertIsOn()
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(1).assertIsOn()
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(2).assertIsOn()
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(3).assertIsOn()

        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(2).performClick()

        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(0).assertIsOn()
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(1).assertIsOn()
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(2).assertIsOff()
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(3).assertIsOn()

        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(1).performClick()

        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(0).assertIsOn()
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(1).assertIsOff()
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(2).assertIsOff()
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(3).assertIsOn()

        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(2).performClick()

        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(0).assertIsOn()
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(1).assertIsOff()
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(2).assertIsOn()
        composeRule.onNodeWithTag(CATEGORY_CHECKBOXES).onChildAt(3).assertIsOn()
    }

    @Test
    fun categoryScreenLazyGrid_isDisplayedCorrectly() {
        setScreenState(
            productList = productList,
            categoryFilterMap = categoryFilterMap
        )
        val deviceWidth = composeRule.onNodeWithTag(CATEGORY_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(CATEGORY_LAZY_VERTICAL_GRID).assertExists()
        composeRule.onNodeWithTag(CATEGORY_LAZY_VERTICAL_GRID).assertIsDisplayed()
        composeRule.onNodeWithTag(CATEGORY_LAZY_VERTICAL_GRID).assertPositionInRootIsEqualTo(20.dp,66.dp)
        composeRule.onNodeWithTag(CATEGORY_LAZY_VERTICAL_GRID).assertWidthIsEqualTo(deviceWidth-40.dp)
    }

    @Test
    fun categoryScreenProduct_isDisplayedCorrectly() {
        setScreenState(
            productList = productList,
            categoryFilterMap = categoryFilterMap
        )

        composeRule.onNodeWithTag(productList[0].title).onChildAt(1).assertTextEquals(productList[0].title)
        composeRule.onNodeWithTag(productList[0].title).onChildAt(3).assertTextEquals(productList[0].price)
        composeRule.onNodeWithTag(productList[0].title).assertPositionInRootIsEqualTo(20.dp,66.dp)
    }
}