package com.example.shopapp.presentation.login.composable

import androidx.activity.compose.setContent
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.di.AppModule
import com.example.shopapp.presentation.MainActivity
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants
import com.example.shopapp.util.Constants.LOGIN_BTN
import com.example.shopapp.util.Constants.LOGIN_CPI
import com.example.shopapp.util.Constants.LOGIN_EMAIL_ERROR
import com.example.shopapp.util.Constants.LOGIN_EMAIL_TF
import com.example.shopapp.util.Constants.LOGIN_PASSWORD_ERROR
import com.example.shopapp.util.Constants.LOGIN_PASSWORD_TF
import com.example.shopapp.util.Constants.emailEmptyError
import com.example.shopapp.util.Constants.passwordEmptyError
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
class LoginScreenTest {

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
                    startDestination = Screen.LoginScreen.route
                ) {
                    composable(
                        route = Screen.LoginScreen.route
                    ) {
                        LoginScreen(
                            navController = navController,
                            bottomBarHeight = Constants.bottomBarHeight.dp
                        )
                    }
                }
            }
        }
    }

    @Test
    fun enteredEmail_textDisplayedCorrectly() {
        val email = "email@email.com"

        composeRule.onNodeWithTag(LOGIN_EMAIL_TF).performTextInput(email)

        val emailNode = composeRule.onNodeWithTag(LOGIN_EMAIL_TF).fetchSemanticsNode()
        val textInput = emailNode.config.getOrNull(SemanticsProperties.EditableText).toString()

        assertThat(textInput).isEqualTo(email)
    }

    @Test
    fun enteredPassword_textDisplayedCorrectly() {
        val password = "Qwerty1+"

        composeRule.onNodeWithTag(LOGIN_PASSWORD_TF).performTextInput(password)

        val passwordNode = composeRule.onNodeWithTag(LOGIN_PASSWORD_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()

        assertThat(textInput.length).isEqualTo(password.length)
    }

    @Test
    fun performClickOnButtonWileEmailTextFieldIsEmpty_errorDisplayedCorrectly() {
        val password = "Qwerty1+"

        composeRule.onNodeWithTag(LOGIN_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(LOGIN_PASSWORD_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(LOGIN_PASSWORD_TF).performTextInput(password)

        composeRule.onNodeWithTag(LOGIN_BTN).assertExists()
        composeRule.onNodeWithTag(LOGIN_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(LOGIN_BTN).performClick()

        composeRule.onNodeWithTag(LOGIN_EMAIL_ERROR).assertExists()
        composeRule.onNodeWithTag(LOGIN_EMAIL_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(LOGIN_EMAIL_ERROR).assertTextEquals(emailEmptyError)

        composeRule.onNodeWithTag(LOGIN_PASSWORD_ERROR).assertDoesNotExist()
    }

    @Test
    fun performClickOnButtonWilePasswordTextFieldIsEmpty_errorDisplayedCorrectly() {
        val email = "email@email.com"

        composeRule.onNodeWithTag(LOGIN_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(LOGIN_PASSWORD_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(LOGIN_EMAIL_TF).performTextInput(email)

        composeRule.onNodeWithTag(LOGIN_BTN).assertExists()
        composeRule.onNodeWithTag(LOGIN_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(LOGIN_BTN).performClick()

        composeRule.onNodeWithTag(LOGIN_PASSWORD_ERROR).assertExists()
        composeRule.onNodeWithTag(LOGIN_PASSWORD_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(LOGIN_PASSWORD_ERROR).assertTextEquals(passwordEmptyError)

        composeRule.onNodeWithTag(LOGIN_EMAIL_ERROR).assertDoesNotExist()
    }

    @Test
    fun performClickOnButtonWileAllTextFieldsAreEmpty_errorsDisplayedCorrectly() {
        composeRule.onNodeWithTag(LOGIN_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(LOGIN_PASSWORD_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(LOGIN_BTN).assertExists()
        composeRule.onNodeWithTag(LOGIN_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(LOGIN_BTN).performClick()

        composeRule.onNodeWithTag(LOGIN_EMAIL_ERROR).assertExists()
        composeRule.onNodeWithTag(LOGIN_EMAIL_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(LOGIN_EMAIL_ERROR).assertTextEquals(emailEmptyError)

        composeRule.onNodeWithTag(LOGIN_PASSWORD_ERROR).assertExists()
        composeRule.onNodeWithTag(LOGIN_PASSWORD_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(LOGIN_PASSWORD_ERROR).assertTextEquals(passwordEmptyError)
    }

    @Test
    fun performClickOnButton_noErrors() {
        val email = "email@email.com"
        val password = "Qwerty1+"

        composeRule.onNodeWithTag(LOGIN_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(LOGIN_PASSWORD_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(LOGIN_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(LOGIN_PASSWORD_TF).performTextInput(password)

        composeRule.onNodeWithTag(LOGIN_BTN).assertExists()
        composeRule.onNodeWithTag(LOGIN_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(LOGIN_BTN).performClick()

        composeRule.onNodeWithTag(LOGIN_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(LOGIN_PASSWORD_ERROR).assertDoesNotExist()
    }

    @Test
    fun isLoading_circularProgressIndicatorIsDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty1+"

        composeRule.onNodeWithTag(LOGIN_CPI).assertDoesNotExist()

        composeRule.onNodeWithTag(LOGIN_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(LOGIN_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(LOGIN_BTN).performClick()

        composeRule.onNodeWithTag(LOGIN_CPI).assertExists()
    }
}