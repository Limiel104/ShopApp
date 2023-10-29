package com.example.shopapp.presentation.home.composable

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.assertPositionInRootIsEqualTo
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onParent
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.R
import com.example.shopapp.di.AppModule
import com.example.shopapp.domain.model.Banner
import com.example.shopapp.presentation.MainActivity
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.CART_BTN
import com.example.shopapp.util.Constants.HOME_BANNER
import com.example.shopapp.util.Constants.HOME_CONTENT
import com.example.shopapp.util.Constants.HOME_LAZY_COLUMN
import com.example.shopapp.util.Constants.HOME_TOP_BAR
import com.example.shopapp.util.Constants.IMAGE
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
class HomeScreenTest {

    private lateinit var bannerList: List<Banner>

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()

        bannerList = listOf(
            Banner(
                categoryId = "women's clothing",
                resourceId = R.drawable.womans_clothing_banner
            ),
            Banner(
                categoryId = "men's clothing",
                resourceId = R.drawable.mens_clothing_banner
            ),
            Banner(
                categoryId = "jewelery",
                resourceId = R.drawable.jewelery_banner
            ),
            Banner(
                categoryId = "electronics",
                resourceId = R.drawable.electronics_banner
            )
        )
    }

    fun setScreenState() {
        composeRule.activity.setContent {
            val navController = rememberNavController()
            ShopAppTheme() {
                NavHost(
                    navController = navController,
                    startDestination = Screen.HomeScreen.route
                ) {
                    composable(
                        route = Screen.HomeScreen.route
                    ) {


                        HomeContent(
                            bannerList = bannerList,
                            onOfferSelected = {},
                            onGoToCart = {}
                        )
                    }
                }
            }
        }
    }

    @Test
    fun homeScreenTopBar_hasCorrectNumberOfItems() {
        setScreenState()

        composeRule.onNodeWithTag(HOME_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(HOME_TOP_BAR).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(HOME_TOP_BAR).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(2)
    }

    @Test
    fun homeScreenTopBar_topBarIsDisplayedCorrectly() {
        setScreenState()

        composeRule.onNodeWithTag(HOME_TOP_BAR).assertPositionInRootIsEqualTo(0.dp,0.dp)
        composeRule.onNodeWithTag(HOME_TOP_BAR).assertHeightIsEqualTo(64.dp)
        val deviceWidth = composeRule.onNodeWithTag(HOME_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(HOME_TOP_BAR).assertWidthIsEqualTo(deviceWidth)
    }

    @Test
    fun homeScreenTopBar_shopNameIsDisplayedCorrectly() {
        setScreenState()

        composeRule.onNodeWithTag(HOME_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(HOME_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(HOME_TOP_BAR).onChildAt(0).assertTextContains("Shop Name")
        composeRule.onNodeWithTag(HOME_TOP_BAR).onChildAt(0).assertLeftPositionInRootIsEqualTo(16.dp)
    }

    @Test
    fun homeScreenTopBar_cartButtonIsDisplayedCorrectly() {
        setScreenState()

        composeRule.onNodeWithTag(HOME_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(HOME_TOP_BAR).onChildAt(1).assertIsEnabled()
        composeRule.onNodeWithTag(HOME_TOP_BAR).onChildAt(1).assertContentDescriptionContains(CART_BTN)
        composeRule.onNodeWithTag(HOME_TOP_BAR).onChildAt(1).assertHasClickAction()

        val deviceWidth = composeRule.onNodeWithTag(HOME_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(HOME_TOP_BAR).onChildAt(1).assertPositionInRootIsEqualTo(deviceWidth-48.dp,12.dp)
        composeRule.onNodeWithTag(HOME_TOP_BAR).onChildAt(1).assertHeightIsEqualTo(40.dp)
        composeRule.onNodeWithTag(HOME_TOP_BAR).onChildAt(1).assertWidthIsEqualTo(40.dp)
    }

    @Test
    fun homeScreenLazyColumn_isDisplayingCorrectNumberOfItems() {
        setScreenState()

        composeRule.onNodeWithTag(HOME_LAZY_COLUMN).assertExists()
        composeRule.onNodeWithTag(HOME_LAZY_COLUMN).assertIsDisplayed()
        val numberOfChildrenDisplayed = composeRule.onNodeWithTag(HOME_LAZY_COLUMN).fetchSemanticsNode().children.size
        assertThat(numberOfChildrenDisplayed).isEqualTo(2)
    }

    @Test
    fun homeScreenLazyColumn_isDisplayedCorrectly() {
        setScreenState()

        composeRule.onNodeWithTag(HOME_LAZY_COLUMN).assertPositionInRootIsEqualTo(0.dp,64.dp)
        val deviceWidth = composeRule.onNodeWithTag(HOME_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(HOME_LAZY_COLUMN).assertWidthIsEqualTo(deviceWidth)
    }

    @Test
    fun homeScreenBanner_isDisplayedCorrectly() {
        setScreenState()

        composeRule.onNodeWithTag("$HOME_BANNER ${bannerList[0].resourceId}").onChild().assertContentDescriptionContains(IMAGE)
        composeRule.onNodeWithTag("$HOME_BANNER ${bannerList[0].resourceId}").assertPositionInRootIsEqualTo(0.dp,64.dp)
        val deviceWidth = composeRule.onNodeWithTag(HOME_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag("$HOME_BANNER ${bannerList[0].resourceId}").assertWidthIsEqualTo(deviceWidth)
        composeRule.onNodeWithTag("$HOME_BANNER ${bannerList[0].resourceId}").onChild().assertHasClickAction()
    }
}