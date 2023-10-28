package com.example.shopapp.presentation.common.composable

import androidx.activity.compose.setContent
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.di.AppModule
import com.example.shopapp.presentation.MainActivity
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.LOGIN_BTN
import com.example.shopapp.util.Constants.SIGNUP_BTN
import com.example.shopapp.util.Constants.USER_NOT_LOGGED_IN_CONTENT
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
class UserNotLoggedInContentTest {

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
                    startDestination = Screen.AccountScreen.route
                ) {
                    composable(
                        route = Screen.AccountScreen.route
                    ) {
                        UserNotLoggedInContent(
                            scaffoldState = rememberScaffoldState(),
                            bottomBarHeight = bottomBarHeight.dp,
                            onLogin = {},
                            onSignup = {}
                        )
                    }
                }
            }
        }
    }

    @Test
    fun userNotLoggedInScreen_hasCorrectNumberOfItems() {
        val numberOfChildren = composeRule.onNodeWithTag(USER_NOT_LOGGED_IN_CONTENT).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(4)
    }

    @Test
    fun userNotLoggedInScreen_textAreDisplayedCorrectly() {
        val indexList = listOf(0, 1)
        val textList = listOf("You are not logged in", "Login or Signup")

        indexList.zip(textList) { index, text ->
            composeRule.onNodeWithTag(USER_NOT_LOGGED_IN_CONTENT).onChildAt(index).assertExists()
            composeRule.onNodeWithTag(USER_NOT_LOGGED_IN_CONTENT).onChildAt(index).assertIsDisplayed()
            composeRule.onNodeWithTag(USER_NOT_LOGGED_IN_CONTENT).onChildAt(index).assertTextEquals(text)
        }
    }

    @Test
    fun userNotLoggedIn_buttonsAreDisplayedCorrectly() {
        val buttonList = listOf(LOGIN_BTN, SIGNUP_BTN)
        val textList = listOf("Login","Sign up")

        buttonList.zip(textList) { button, text ->
            composeRule.onNodeWithTag(button).assertIsDisplayed()
            composeRule.onNodeWithTag(button).assertIsEnabled()
            composeRule.onNodeWithTag(button).assertTextEquals(text)
            composeRule.onNodeWithTag(button).assertHasClickAction()
        }
    }
}