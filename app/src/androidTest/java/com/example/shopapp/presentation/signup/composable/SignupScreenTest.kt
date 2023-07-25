package com.example.shopapp.presentation.signup.composable

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
import com.example.shopapp.util.Constants.SIGNUP_BTN
import com.example.shopapp.util.Constants.SIGNUP_CONFIRM_PASSWORD_ERROR
import com.example.shopapp.util.Constants.SIGNUP_CONFIRM_PASSWORD_TF
import com.example.shopapp.util.Constants.SIGNUP_CPI
import com.example.shopapp.util.Constants.SIGNUP_EMAIL_ERROR
import com.example.shopapp.util.Constants.SIGNUP_EMAIL_TF
import com.example.shopapp.util.Constants.SIGNUP_PASSWORD_ERROR
import com.example.shopapp.util.Constants.SIGNUP_PASSWORD_TF
import com.example.shopapp.util.Constants.confirmPasswordError
import com.example.shopapp.util.Constants.containsAtLeastOneCapitalLetterError
import com.example.shopapp.util.Constants.containsAtLeastOneDigitError
import com.example.shopapp.util.Constants.containsAtLeastOneSpecialCharError
import com.example.shopapp.util.Constants.emailEmptyError
import com.example.shopapp.util.Constants.passwordEmptyError
import com.example.shopapp.util.Constants.shortPasswordError
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
class SignupScreenTest {

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
                    startDestination = Screen.SignupScreen.route
                ) {
                    composable(
                        route = Screen.SignupScreen.route
                    ) {
                        SignupScreen(
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

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)

        val emailNode = composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).fetchSemanticsNode()
        val textInput = emailNode.config.getOrNull(SemanticsProperties.EditableText).toString()

        assertThat(textInput).isEqualTo(email)
    }

    @Test
    fun enteredPassword_textDisplayedCorrectly() {
        val password = "Qwerty1+"

        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)

        val passwordNode = composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()

        assertThat(textInput.length).isEqualTo(password.length)
    }

    @Test
    fun enteredConfirmPassword_textDisplayedCorrectly() {
        val password = "Qwerty1+"

        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)

        val confirmPasswordNode = composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).fetchSemanticsNode()
        val textInput = confirmPasswordNode.config.getOrNull(SemanticsProperties.EditableText).toString()

        assertThat(textInput.length).isEqualTo(password.length)
    }

    @Test
    fun performClickOnButtonWileEmailTextFieldIsEmpty_errorDisplayedCorrectly() {
        val password = "Qwerty1+"

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertTextEquals(emailEmptyError)

        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
    }

    @Test
    fun performClickOnButtonWilePasswordTextFieldIsEmpty_errorDisplayedCorrectly() {
        val email = "email@email.com"

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertTextEquals(passwordEmptyError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
    }

    @Test
    fun performClickOnButtonWileConfirmPasswordTextFieldIsEmpty_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty1+"

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertTextEquals(confirmPasswordError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
    }

    @Test
    fun performClickOnButtonWilePasswordIsTooShort_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwe"

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertTextEquals(shortPasswordError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
    }

    @Test
    fun performClickOnButtonWilePasswordDoesNotHaveOneDigit_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty++"

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertTextEquals(containsAtLeastOneDigitError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
    }

    @Test
    fun performClickOnButtonWilePasswordDoesNotHaveOneCapitalLetter_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "qwerty1+"

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertTextEquals(containsAtLeastOneCapitalLetterError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
    }

    @Test
    fun performClickOnButtonWilePasswordDoesNotHaveOneSpecialChar_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty11"

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertTextEquals(containsAtLeastOneSpecialCharError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
    }

    @Test
    fun isLoading_circularProgressIndicatorIsDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty1+"

        composeRule.onNodeWithTag(SIGNUP_CPI).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_CPI).assertExists()
    }
}