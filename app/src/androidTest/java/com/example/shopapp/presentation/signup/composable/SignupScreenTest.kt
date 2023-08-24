package com.example.shopapp.presentation.signup.composable

import androidx.activity.compose.setContent
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.assertPositionInRootIsEqualTo
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.di.AppModule
import com.example.shopapp.presentation.MainActivity
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.SIGNUP_BTN
import com.example.shopapp.util.Constants.SIGNUP_CONFIRM_PASSWORD_ERROR
import com.example.shopapp.util.Constants.SIGNUP_CONFIRM_PASSWORD_TF
import com.example.shopapp.util.Constants.SIGNUP_CONTENT
import com.example.shopapp.util.Constants.SIGNUP_CPI
import com.example.shopapp.util.Constants.SIGNUP_EMAIL_ERROR
import com.example.shopapp.util.Constants.SIGNUP_EMAIL_TF
import com.example.shopapp.util.Constants.SIGNUP_PASSWORD_ERROR
import com.example.shopapp.util.Constants.SIGNUP_PASSWORD_TF
import com.example.shopapp.util.Constants.bottomBarHeight
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
    }

    private fun setScreenState(
        email: String = "",
        emailError: String? = null,
        password: String = "",
        passwordError: String? = null,
        confirmPassword: String = "",
        confirmPasswordError: String? = null,
        isLoading: Boolean = false
    ) {
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
                        SignupContent(
                            bottomBarHeight = bottomBarHeight.dp,
                            email = email,
                            emailError = emailError,
                            password = password,
                            passwordError = passwordError,
                            confirmPassword = confirmPassword,
                            confirmPasswordError = confirmPasswordError,
                            isLoading = isLoading,
                            onEmailChange = {},
                            onPasswordChange = {},
                            onConfirmPasswordChange = {},
                            onSignup = {}
                        )
                    }
                }
            }
        }
    }

    private fun setScreen() {
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
                            bottomBarHeight = bottomBarHeight.dp
                        )
                    }
                }
            }
        }
    }

    @Test
    fun signupScreen_hasCorrectNumberOfItemsWhenErrorsAreNotDisplayed() {
        setScreenState()

        composeRule.onNodeWithTag(SIGNUP_CONTENT).assertExists()
        composeRule.onNodeWithTag(SIGNUP_CONTENT).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(SIGNUP_CONTENT).fetchSemanticsNode().children.size

        assertThat(numberOfChildren).isEqualTo(5)
    }

    @Test
    fun signupScreen_hasCorrectNumberOfItemsWhenErrorsAreDisplayed() {
        setScreenState(
            emailError = emailEmptyError,
            passwordError = passwordEmptyError,
            confirmPasswordError = confirmPasswordError
        )

        composeRule.onNodeWithTag(SIGNUP_CONTENT).assertExists()
        composeRule.onNodeWithTag(SIGNUP_CONTENT).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(SIGNUP_CONTENT).fetchSemanticsNode().children.size

        assertThat(numberOfChildren).isEqualTo(8)
    }

    @Test
    fun signupScreen_isDisplayedCorrectly() {
        setScreenState()
        val deviceWidth = composeRule.onNodeWithTag(SIGNUP_CONTENT).onParent().getBoundsInRoot().right
        val deviceHeight = composeRule.onNodeWithTag(SIGNUP_CONTENT).onParent().getBoundsInRoot().bottom

        composeRule.onNodeWithTag(SIGNUP_CONTENT).assertPositionInRootIsEqualTo(20.dp,0.dp)
        composeRule.onNodeWithTag(SIGNUP_CONTENT).assertHeightIsEqualTo(deviceHeight-bottomBarHeight.dp)
        composeRule.onNodeWithTag(SIGNUP_CONTENT).assertWidthIsEqualTo(deviceWidth-40.dp)
    }

    @Test
    fun signupScreenEmailTextField_textIsDisplayedCorrectly() {
        val email = "email@email.com"
        setScreenState(
            email = email
        )
        val deviceWidth = composeRule.onNodeWithTag(SIGNUP_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        val emailNode = composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).fetchSemanticsNode()
        val textInput = emailNode.config.getOrNull(SemanticsProperties.EditableText).toString()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).assertLeftPositionInRootIsEqualTo(20.dp)
        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).assertWidthIsEqualTo(deviceWidth-40.dp)
        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        assertThat(textInput).isEqualTo(email)
    }

    @Test
    fun signupScreenPasswordTextField_textIsDisplayedCorrectly() {
        val password = "Qwerty1+"
        setScreenState(
            password = password
        )
        val deviceWidth = composeRule.onNodeWithTag(SIGNUP_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        val passwordNode = composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()

        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).assertLeftPositionInRootIsEqualTo(20.dp)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).assertWidthIsEqualTo(deviceWidth-40.dp)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        assertThat(textInput).isEqualTo(password)
    }

    @Test
    fun signupScreenConfirmPasswordTextField_textIsDisplayedCorrectly() {
        val confirmPassword = "Qwerty1+"
        setScreenState(
            confirmPassword = confirmPassword
        )
        val deviceWidth = composeRule.onNodeWithTag(SIGNUP_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(confirmPassword)
        val passwordNode = composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()

        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).assertLeftPositionInRootIsEqualTo(20.dp)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).assertWidthIsEqualTo(deviceWidth-40.dp)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        assertThat(textInput).isEqualTo(confirmPassword)
    }

    @Test
    fun signupScreenEmailErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            emailError = emailEmptyError
        )

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertTextEquals(emailEmptyError)
        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertLeftPositionInRootIsEqualTo(20.dp)
    }

    @Test
    fun signupScreenPasswordErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            passwordError = passwordEmptyError
        )

        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertTextEquals(passwordEmptyError)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertLeftPositionInRootIsEqualTo(20.dp)
    }

    @Test
    fun signupScreenConfirmPasswordErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            confirmPasswordError = confirmPasswordError
        )

        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertTextEquals(confirmPasswordError)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertLeftPositionInRootIsEqualTo(20.dp)
    }

    @Test
    fun signupScreenEmailErrorTextField_performClickOnButtonWileEmailTextFieldIsEmpty_errorDisplayedCorrectly() {
        val password = "Qwerty1+"
        setScreen()

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
    fun signupScreenPasswordErrorTextField_performClickOnButtonWilePasswordTextFieldIsEmpty_errorDisplayedCorrectly() {
        val email = "email@email.com"
        setScreen()

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
    fun signupScreenConfirmPasswordErrorTextField_performClickOnButtonWileConfirmPasswordTextFieldIsEmpty_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        setScreen()

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
    fun signupScreenPasswordTextField_performClickOnButtonWilePasswordIsTooShort_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwe"
        setScreen()

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
    fun signupScreenPasswordTextField_performClickOnButtonWilePasswordDoesNotHaveOneDigit_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty++"
        setScreen()

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
    fun signupScreenPasswordTextField_performClickOnButtonWilePasswordDoesNotHaveOneCapitalLetter_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "qwerty1+"
        setScreen()

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
    fun signupScreenPasswordTextField_performClickOnButtonWilePasswordDoesNotHaveOneSpecialChar_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty11"
        setScreen()

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
    fun signupScreenErrorTextFields_performClickOnButtonWileAllTextFieldsAreEmpty_errorsDisplayedCorrectly() {
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertTextEquals(emailEmptyError)

        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertTextEquals(passwordEmptyError)

        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenErrorTextFields_noErrorsAfterClickingOnTheButton() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()


        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(confirmPassword)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenCircularProgressIndicator_IsDisplayedCorrectly() {
        setScreenState(
            isLoading = true
        )
        val deviceWidth = composeRule.onNodeWithTag(SIGNUP_CONTENT).onParent().getBoundsInRoot().right
        val deviceHeight = composeRule.onNodeWithTag(SIGNUP_CONTENT).onParent().getBoundsInRoot().bottom
        val leftPosition = deviceWidth.value/2
        val topPosition = deviceHeight.value/2

        composeRule.onNodeWithTag(SIGNUP_CPI).assertExists()
        composeRule.onNodeWithTag(SIGNUP_CPI).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_CPI).assertPositionInRootIsEqualTo(0.dp,0.dp)
        composeRule.onNodeWithTag(SIGNUP_CPI).assertHeightIsEqualTo(deviceHeight)
        composeRule.onNodeWithTag(SIGNUP_CPI).assertWidthIsEqualTo(deviceWidth)

        composeRule.onNodeWithTag(SIGNUP_CPI).onChild().assertPositionInRootIsEqualTo(leftPosition.dp-20.dp,topPosition.dp-20.dp)
        composeRule.onNodeWithTag(SIGNUP_CPI).onChild().assertWidthIsEqualTo(40.dp)
    }

    @Test
    fun isLoading_circularProgressIndicatorIsDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_CPI).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(confirmPassword)
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_CPI).assertExists()
    }
}