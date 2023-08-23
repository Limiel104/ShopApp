package com.example.shopapp.presentation.login.composable

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
import com.example.shopapp.util.Constants.LOGIN_BTN
import com.example.shopapp.util.Constants.LOGIN_CONTENT
import com.example.shopapp.util.Constants.LOGIN_CPI
import com.example.shopapp.util.Constants.LOGIN_EMAIL_ERROR
import com.example.shopapp.util.Constants.LOGIN_EMAIL_TF
import com.example.shopapp.util.Constants.LOGIN_PASSWORD_ERROR
import com.example.shopapp.util.Constants.LOGIN_PASSWORD_TF
import com.example.shopapp.util.Constants.bottomBarHeight
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
    }

    private fun setScreenState(
        email: String = "",
        emailError: String? = null,
        password: String = "",
        passwordError: String? = null,
        isLoading: Boolean = false
    ) {
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
                        LoginContent(
                            bottomBarHeight = bottomBarHeight.dp,
                            email = email,
                            emailError = emailError,
                            password = password,
                            passwordError = passwordError,
                            isLoading = isLoading,
                            onEmailChange = {},
                            onPasswordChange = {},
                            onLogin = {},
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
                    startDestination = Screen.LoginScreen.route
                ) {
                    composable(
                        route = Screen.LoginScreen.route
                    ) {
                        LoginScreen(
                            navController = navController,
                            bottomBarHeight = bottomBarHeight.dp
                        )
                    }
                }
            }
        }
    }

    @Test
    fun loginScreen_hasCorrectNumberOfItemsWhenErrorsAreNotDisplayed() {
        setScreenState()

        composeRule.onNodeWithTag(LOGIN_CONTENT).assertExists()
        composeRule.onNodeWithTag(LOGIN_CONTENT).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(LOGIN_CONTENT).fetchSemanticsNode().children.size

        assertThat(numberOfChildren).isEqualTo(6)
    }

    @Test
    fun loginScreen_hasCorrectNumberOfItemsWhenErrorsAreDisplayed() {
        setScreenState(
            emailError = emailEmptyError,
            passwordError = passwordEmptyError
        )

        composeRule.onNodeWithTag(LOGIN_CONTENT).assertExists()
        composeRule.onNodeWithTag(LOGIN_CONTENT).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(LOGIN_CONTENT).fetchSemanticsNode().children.size

        assertThat(numberOfChildren).isEqualTo(8)
    }

    @Test
    fun loginScreen_isDisplayedCorrectly() {
        setScreenState()
        val deviceWidth = composeRule.onNodeWithTag(LOGIN_CONTENT).onParent().getBoundsInRoot().right
        val deviceHeight = composeRule.onNodeWithTag(LOGIN_CONTENT).onParent().getBoundsInRoot().bottom

        composeRule.onNodeWithTag(LOGIN_CONTENT).assertPositionInRootIsEqualTo(20.dp,0.dp)
        composeRule.onNodeWithTag(LOGIN_CONTENT).assertHeightIsEqualTo(deviceHeight-bottomBarHeight.dp)
        composeRule.onNodeWithTag(LOGIN_CONTENT).assertWidthIsEqualTo(deviceWidth-40.dp)
    }

    @Test
    fun loginScreenEmailTextField_textIsDisplayedCorrectly() {
        val email = "email@email.com"
        setScreenState(
            email = email
        )
        val deviceWidth = composeRule.onNodeWithTag(LOGIN_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(LOGIN_EMAIL_TF).performTextInput(email)
        val emailNode = composeRule.onNodeWithTag(LOGIN_EMAIL_TF).fetchSemanticsNode()
        val textInput = emailNode.config.getOrNull(SemanticsProperties.EditableText).toString()

        composeRule.onNodeWithTag(LOGIN_EMAIL_TF).assertLeftPositionInRootIsEqualTo(20.dp)
        composeRule.onNodeWithTag(LOGIN_EMAIL_TF).assertWidthIsEqualTo(deviceWidth-40.dp)
        composeRule.onNodeWithTag(LOGIN_EMAIL_ERROR).assertDoesNotExist()
        assertThat(textInput).isEqualTo(email)
    }

    @Test
    fun loginScreenPasswordTextField_textIsDisplayedCorrectly() {
        val password = "Qwerty1+"
        setScreenState(
            password = password
        )
        val deviceWidth = composeRule.onNodeWithTag(LOGIN_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(LOGIN_PASSWORD_TF).performTextInput(password)
        val passwordNode = composeRule.onNodeWithTag(LOGIN_PASSWORD_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()

        composeRule.onNodeWithTag(LOGIN_PASSWORD_TF).assertLeftPositionInRootIsEqualTo(20.dp)
        composeRule.onNodeWithTag(LOGIN_PASSWORD_TF).assertWidthIsEqualTo(deviceWidth-40.dp)
        composeRule.onNodeWithTag(LOGIN_PASSWORD_ERROR).assertDoesNotExist()
        assertThat(textInput).isEqualTo("••••••••")
    }

    @Test
    fun loginScreenEmailErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            emailError = emailEmptyError
        )

        composeRule.onNodeWithTag(LOGIN_EMAIL_ERROR).assertExists()
        composeRule.onNodeWithTag(LOGIN_EMAIL_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(LOGIN_EMAIL_ERROR).assertTextEquals(emailEmptyError)
        composeRule.onNodeWithTag(LOGIN_EMAIL_ERROR).assertLeftPositionInRootIsEqualTo(20.dp)
    }

    @Test
    fun loginScreenPasswordErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            passwordError = passwordEmptyError
        )

        composeRule.onNodeWithTag(LOGIN_PASSWORD_ERROR).assertExists()
        composeRule.onNodeWithTag(LOGIN_PASSWORD_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(LOGIN_PASSWORD_ERROR).assertTextEquals(passwordEmptyError)
        composeRule.onNodeWithTag(LOGIN_PASSWORD_ERROR).assertLeftPositionInRootIsEqualTo(20.dp)
    }

    @Test
    fun loginScreenEmailErrorTextField_performClickOnButtonWileEmailTextFieldIsEmpty_errorDisplayedCorrectly() {
        val password = "Qwerty1+"
        setScreen()

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
    fun loginScreenPasswordErrorTextField_performClickOnButtonWilePasswordTextFieldIsEmpty_errorDisplayedCorrectly() {
        val email = "email@email.com"
        setScreen()

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
    fun loginScreenErrorTextFields_performClickOnButtonWileAllTextFieldsAreEmpty_errorsDisplayedCorrectly() {
        setScreen()

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
    fun loginScreenErrorTextFields_noErrorsAfterClickingOnTheButton() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        setScreen()

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
    fun loginScreenCircularProgressIndicator_IsDisplayedCorrectly() {
        setScreenState(
            isLoading = true
        )
        val deviceWidth = composeRule.onNodeWithTag(LOGIN_CONTENT).onParent().getBoundsInRoot().right
        val deviceHeight = composeRule.onNodeWithTag(LOGIN_CONTENT).onParent().getBoundsInRoot().bottom
        val leftPosition = deviceWidth.value/2
        val topPosition = deviceHeight.value/2

        composeRule.onNodeWithTag(LOGIN_CPI).assertExists()
        composeRule.onNodeWithTag(LOGIN_CPI).assertIsDisplayed()
        composeRule.onNodeWithTag(LOGIN_CPI).assertPositionInRootIsEqualTo(0.dp,0.dp)
        composeRule.onNodeWithTag(LOGIN_CPI).assertHeightIsEqualTo(deviceHeight)
        composeRule.onNodeWithTag(LOGIN_CPI).assertWidthIsEqualTo(deviceWidth)

        composeRule.onNodeWithTag(LOGIN_CPI).onChild().assertPositionInRootIsEqualTo(leftPosition.dp-20.dp,topPosition.dp-20.dp)
        composeRule.onNodeWithTag(LOGIN_CPI).onChild().assertWidthIsEqualTo(40.dp)
    }

    @Test
    fun loginScreenCircularProgressIndicator_isDisplayedAfterClickingOnTheButtonWhenThereWasNoErrors() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        setScreen()

        composeRule.onNodeWithTag(LOGIN_CPI).assertDoesNotExist()

        composeRule.onNodeWithTag(LOGIN_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(LOGIN_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(LOGIN_BTN).performClick()

        composeRule.onNodeWithTag(LOGIN_CPI).assertExists()
    }
}