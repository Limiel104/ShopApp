package com.example.shopapp.presentation.login.composable

import androidx.activity.compose.setContent
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
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
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.di.AppModule
import com.example.shopapp.presentation.MainActivity
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.GO_BACK_BTN
import com.example.shopapp.util.Constants.LOGIN_BTN
import com.example.shopapp.util.Constants.LOGIN_COLUMN
import com.example.shopapp.util.Constants.LOGIN_CONTENT
import com.example.shopapp.util.Constants.LOGIN_CPI
import com.example.shopapp.util.Constants.LOGIN_EMAIL_TF
import com.example.shopapp.util.Constants.LOGIN_PASSWORD_TF
import com.example.shopapp.util.Constants.LOGIN_TOP_BAR
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
                            email = email,
                            emailError = emailError,
                            password = password,
                            passwordError = passwordError,
                            isLoading = isLoading,
                            onEmailChange = {},
                            onPasswordChange = {},
                            onLogin = {},
                            onSignup = {},
                            onGoBack = {}
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
                        LoginScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun loginScreenTopBar_hasCorrectNumberOfItems() {
        setScreenState()

        composeRule.onNodeWithTag(LOGIN_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(LOGIN_TOP_BAR).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(LOGIN_TOP_BAR).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(2)
    }

    @Test
    fun loginScreenTopBar_topBarIsDisplayedCorrectly() {
        setScreenState()

        composeRule.onNodeWithTag(LOGIN_TOP_BAR).assertPositionInRootIsEqualTo(0.dp,0.dp)
        composeRule.onNodeWithTag(LOGIN_TOP_BAR).assertHeightIsEqualTo(64.dp)
        val deviceWidth = composeRule.onNodeWithTag(LOGIN_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(LOGIN_TOP_BAR).assertWidthIsEqualTo(deviceWidth)
    }

    @Test
    fun loginScreenTopBar_goBackButtonIsDisplayedCorrectly() {
        setScreenState()

        composeRule.onNodeWithTag(LOGIN_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(LOGIN_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(LOGIN_TOP_BAR).onChildAt(0).assertContentDescriptionContains(GO_BACK_BTN)
        composeRule.onNodeWithTag(LOGIN_TOP_BAR).onChildAt(0).assertHasClickAction()

        composeRule.onNodeWithTag(LOGIN_TOP_BAR).onChildAt(0).assertPositionInRootIsEqualTo(8.dp,12.dp)
        composeRule.onNodeWithTag(LOGIN_TOP_BAR).onChildAt(0).assertHeightIsEqualTo(40.dp)
        composeRule.onNodeWithTag(LOGIN_TOP_BAR).onChildAt(0).assertWidthIsEqualTo(40.dp)
    }

    @Test
    fun loginScreenTopBar_titleIsDisplayedCorrectly() {
        setScreenState()

        composeRule.onNodeWithTag(LOGIN_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(LOGIN_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(LOGIN_TOP_BAR).onChildAt(1).assertTextEquals("Login")
        composeRule.onNodeWithTag(LOGIN_TOP_BAR).onChildAt(1).assertLeftPositionInRootIsEqualTo(56.dp,)
    }

    @Test
    fun loginScreenColumn_hasCorrectNumberOfItemsWhenErrorsAreNotDisplayed() {
        setScreenState()

        composeRule.onNodeWithTag(LOGIN_COLUMN).assertExists()
        composeRule.onNodeWithTag(LOGIN_COLUMN).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(LOGIN_COLUMN).fetchSemanticsNode().children.size

        assertThat(numberOfChildren).isEqualTo(5)
    }

    @Test
    fun loginScreenColumn_hasCorrectNumberOfItemsWhenErrorsAreDisplayed() {
        setScreenState(
            emailError = emailEmptyError,
            passwordError = passwordEmptyError
        )

        composeRule.onNodeWithTag(LOGIN_COLUMN).assertExists()
        composeRule.onNodeWithTag(LOGIN_COLUMN).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(LOGIN_COLUMN).fetchSemanticsNode().children.size

        assertThat(numberOfChildren).isEqualTo(5)
    }

    @Test
    fun loginScreenColumn_isDisplayedCorrectly() {
        setScreenState()
        val deviceWidth = composeRule.onNodeWithTag(LOGIN_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(LOGIN_COLUMN).assertExists()
        composeRule.onNodeWithTag(LOGIN_COLUMN).assertIsDisplayed()
        composeRule.onNodeWithTag(LOGIN_COLUMN).assertPositionInRootIsEqualTo(0.dp,64.dp)
        composeRule.onNodeWithTag(LOGIN_COLUMN).assertWidthIsEqualTo(deviceWidth)
    }

    @Test
    fun loginScreenEmailTextField_textIsDisplayedCorrectly() {
        val email = "email@email.com"
        setScreenState(
            email = email
        )

        composeRule.onNodeWithTag(LOGIN_EMAIL_TF).performTextInput(email)
        val emailNode = composeRule.onNodeWithTag(LOGIN_EMAIL_TF).fetchSemanticsNode()
        val textInput = emailNode.config.getOrNull(SemanticsProperties.EditableText).toString()
        assertThat(textInput).isEqualTo(email)
    }

    @Test
    fun loginScreenPasswordTextField_textIsDisplayedCorrectly() {
        val password = "Qwerty1+"
        setScreenState(
            password = password
        )

        composeRule.onNodeWithTag(LOGIN_PASSWORD_TF).performTextInput(password)
        val passwordNode = composeRule.onNodeWithTag(LOGIN_PASSWORD_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()
        assertThat(textInput).isEqualTo("••••••••")
    }

    @Test
    fun loginScreenEmailErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            emailError = emailEmptyError
        )

        val emailNode = composeRule.onNodeWithTag(LOGIN_EMAIL_TF).fetchSemanticsNode()
        val errorLabel = emailNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val errorValue = emailNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()
        assertThat(errorLabel).isEqualTo("Email")
        assertThat(errorValue).isEqualTo(emailEmptyError)
    }

    @Test
    fun loginScreenPasswordErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            passwordError = passwordEmptyError
        )

        val passwordNode = composeRule.onNodeWithTag(LOGIN_PASSWORD_TF).fetchSemanticsNode()
        val errorLabel = passwordNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val errorValue = passwordNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()
        assertThat(errorLabel).isEqualTo("Password")
        assertThat(errorValue).isEqualTo(passwordEmptyError)
    }

    @Test
    fun loginScreenEmailErrorTextField_performClickOnButtonWileEmailTextFieldIsEmpty_errorDisplayedCorrectly() {
        val password = "Qwerty1+"
        setScreen()

        val initialEmailNode = composeRule.onNodeWithTag(LOGIN_EMAIL_TF).fetchSemanticsNode()
        val initialErrorState = initialEmailNode.config.getOrNull(SemanticsProperties.Error)

        composeRule.onNodeWithTag(LOGIN_PASSWORD_TF).performTextInput(password)

        composeRule.onNodeWithTag(LOGIN_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(LOGIN_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(LOGIN_BTN).performClick()

        val resultEmailNode = composeRule.onNodeWithTag(LOGIN_EMAIL_TF).fetchSemanticsNode()
        val errorLabel = resultEmailNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultEmailNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        assertThat(initialErrorState).isNull()
        assertThat(errorLabel).isEqualTo("Email")
        assertThat(resultErrorValue).isEqualTo(emailEmptyError)
    }

    @Test
    fun loginScreenPasswordErrorTextField_performClickOnButtonWilePasswordTextFieldIsEmpty_errorDisplayedCorrectly() {
        val email = "email@email.com"
        setScreen()

        val initialPasswordNode = composeRule.onNodeWithTag(LOGIN_PASSWORD_TF).fetchSemanticsNode()
        val initialErrorState = initialPasswordNode.config.getOrNull(SemanticsProperties.Error)

        composeRule.onNodeWithTag(LOGIN_EMAIL_TF).performTextInput(email)

        composeRule.onNodeWithTag(LOGIN_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(LOGIN_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(LOGIN_BTN).performClick()

        val resultPasswordNode = composeRule.onNodeWithTag(LOGIN_PASSWORD_TF).fetchSemanticsNode()
        val errorLabel = resultPasswordNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultPasswordNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        assertThat(initialErrorState).isNull()
        assertThat(errorLabel).isEqualTo("Password")
        assertThat(resultErrorValue).isEqualTo(passwordEmptyError)
    }

    @Test
    fun loginScreenErrorTextFields_performClickOnButtonWileAllTextFieldsAreEmpty_errorsDisplayedCorrectly() {
        setScreen()

        val emailNode = composeRule.onNodeWithTag(LOGIN_EMAIL_TF).fetchSemanticsNode()
        val passwordNode = composeRule.onNodeWithTag(LOGIN_PASSWORD_TF).fetchSemanticsNode()
        val initialEmailErrorState = emailNode.config.getOrNull(SemanticsProperties.Error)
        val initialPasswordErrorState = passwordNode.config.getOrNull(SemanticsProperties.Error)

        composeRule.onNodeWithTag(LOGIN_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(LOGIN_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(LOGIN_BTN).performClick()

        val resultEmailNode = composeRule.onNodeWithTag(LOGIN_EMAIL_TF).fetchSemanticsNode()
        val errorEmailLabel = resultEmailNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultEmailErrorValue = resultEmailNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        val resultPasswordNode = composeRule.onNodeWithTag(LOGIN_PASSWORD_TF).fetchSemanticsNode()
        val errorPasswordLabel = resultPasswordNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultPasswordErrorValue = resultPasswordNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()


        assertThat(initialEmailErrorState).isNull()
        assertThat(initialPasswordErrorState).isNull()
        assertThat(errorEmailLabel).isEqualTo("Email")
        assertThat(errorPasswordLabel).isEqualTo("Password")
        assertThat(resultEmailErrorValue).isEqualTo(emailEmptyError)
        assertThat(resultPasswordErrorValue).isEqualTo(passwordEmptyError)
    }

    @Test
    fun loginScreenErrorTextFields_noErrorsAfterClickingOnTheButton() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        setScreen()

        val emailNode = composeRule.onNodeWithTag(LOGIN_EMAIL_TF).fetchSemanticsNode()
        val passwordNode = composeRule.onNodeWithTag(LOGIN_PASSWORD_TF).fetchSemanticsNode()
        val initialEmailErrorState = emailNode.config.getOrNull(SemanticsProperties.Error)
        val initialPasswordErrorState = passwordNode.config.getOrNull(SemanticsProperties.Error)

        composeRule.onNodeWithTag(LOGIN_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(LOGIN_PASSWORD_TF).performTextInput(password)

        composeRule.onNodeWithTag(LOGIN_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(LOGIN_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(LOGIN_BTN).performClick()

        val resultEmailNode = composeRule.onNodeWithTag(LOGIN_EMAIL_TF).fetchSemanticsNode()
        val resultPasswordNode = composeRule.onNodeWithTag(LOGIN_PASSWORD_TF).fetchSemanticsNode()
        val resultEmailErrorState = resultEmailNode.config.getOrNull(SemanticsProperties.Error)
        val resultPasswordErrorState = resultPasswordNode.config.getOrNull(SemanticsProperties.Error)

        assertThat(initialEmailErrorState).isNull()
        assertThat(initialPasswordErrorState).isNull()
        assertThat(resultEmailErrorState).isNull()
        assertThat(resultPasswordErrorState).isNull()
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