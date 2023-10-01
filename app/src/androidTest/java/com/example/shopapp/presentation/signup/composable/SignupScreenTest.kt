package com.example.shopapp.presentation.signup.composable

import androidx.activity.compose.setContent
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.assertPositionInRootIsEqualTo
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.assertTopPositionInRootIsEqualTo
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
import com.example.shopapp.util.Constants.SIGNUP_BTN
import com.example.shopapp.util.Constants.SIGNUP_CITY_ERROR
import com.example.shopapp.util.Constants.SIGNUP_CITY_TF
import com.example.shopapp.util.Constants.SIGNUP_COLUMN
import com.example.shopapp.util.Constants.SIGNUP_CONFIRM_PASSWORD_ERROR
import com.example.shopapp.util.Constants.SIGNUP_CONFIRM_PASSWORD_TF
import com.example.shopapp.util.Constants.SIGNUP_CONTENT
import com.example.shopapp.util.Constants.SIGNUP_CPI
import com.example.shopapp.util.Constants.SIGNUP_EMAIL_ERROR
import com.example.shopapp.util.Constants.SIGNUP_EMAIL_TF
import com.example.shopapp.util.Constants.SIGNUP_FIRSTNAME_ERROR
import com.example.shopapp.util.Constants.SIGNUP_FIRSTNAME_TF
import com.example.shopapp.util.Constants.SIGNUP_LASTNAME_ERROR
import com.example.shopapp.util.Constants.SIGNUP_LASTNAME_TF
import com.example.shopapp.util.Constants.SIGNUP_PASSWORD_ERROR
import com.example.shopapp.util.Constants.SIGNUP_PASSWORD_TF
import com.example.shopapp.util.Constants.SIGNUP_STREET_ERROR
import com.example.shopapp.util.Constants.SIGNUP_STREET_TF
import com.example.shopapp.util.Constants.SIGNUP_TOP_BAR
import com.example.shopapp.util.Constants.SIGNUP_ZIP_CODE_ERROR
import com.example.shopapp.util.Constants.SIGNUP_ZIP_CODE_TF
import com.example.shopapp.util.Constants.bottomBarHeight
import com.example.shopapp.util.Constants.cityEmptyError
import com.example.shopapp.util.Constants.confirmPasswordError
import com.example.shopapp.util.Constants.emailEmptyError
import com.example.shopapp.util.Constants.fieldContainsAtLeastOneLetterError
import com.example.shopapp.util.Constants.fieldContainsDigitsError
import com.example.shopapp.util.Constants.fieldEmptyError
import com.example.shopapp.util.Constants.filedContainsSpecialCharsError
import com.example.shopapp.util.Constants.passwordContainsAtLeastOneCapitalLetterError
import com.example.shopapp.util.Constants.passwordContainsAtLeastOneDigitError
import com.example.shopapp.util.Constants.passwordContainsAtLeastOneSpecialCharError
import com.example.shopapp.util.Constants.passwordEmptyError
import com.example.shopapp.util.Constants.shortPasswordError
import com.example.shopapp.util.Constants.streetContainsAtLeastOneDigitError
import com.example.shopapp.util.Constants.streetEmptyError
import com.example.shopapp.util.Constants.zipCodeBadFormat
import com.example.shopapp.util.Constants.zipCodeEmptyError
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
        firstName: String = "",
        firstNameError: String? = null,
        lastName: String = "",
        lastNameError: String? = null,
        street: String = "",
        streetError: String? = null,
        city: String = "",
        cityError: String? = null,
        zipCode: String = "",
        zipCodeError: String? = null,
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
                            scaffoldState = rememberScaffoldState(),
                            scrollState = rememberScrollState(),
                            bottomBarHeight = bottomBarHeight.dp,
                            email = email,
                            emailError = emailError,
                            password = password,
                            passwordError = passwordError,
                            confirmPassword = confirmPassword,
                            confirmPasswordError = confirmPasswordError,
                            firstName = firstName,
                            firstNameError = firstNameError,
                            lastName = lastName,
                            lastNameError = lastNameError,
                            street = street,
                            streetError = streetError,
                            city = city,
                            cityError = cityError,
                            zipCode = zipCode,
                            zipCodeError = zipCodeError,
                            isLoading = isLoading,
                            onEmailChange = {},
                            onPasswordChange = {},
                            onConfirmPasswordChange = {},
                            onFirstNameChange = {},
                            onLastNameChange = {},
                            onStreetChange = {},
                            onCityChange = {},
                            onZipCodeChange = {},
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
    fun signupScreenTopBar_hasCorrectNumberOfItems() {
        setScreenState()

        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(SIGNUP_TOP_BAR).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(2)
    }

    @Test
    fun signupScreenTopBar_topBarIsDisplayedCorrectly() {
        setScreenState()

        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).assertTopPositionInRootIsEqualTo(15.dp)
        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).assertHeightIsEqualTo(36.dp)
        val deviceWidth = composeRule.onNodeWithTag(SIGNUP_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).assertWidthIsEqualTo(deviceWidth-20.dp)
    }

    @Test
    fun signupListScreenTopBar_goBackButtonIsDisplayedCorrectly() {
        setScreenState()

        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).onChildAt(0).assertContentDescriptionContains(GO_BACK_BTN)
        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).onChildAt(0).assertHasClickAction()

        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).onChildAt(0).assertPositionInRootIsEqualTo(10.dp,15.dp)
        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).onChildAt(0).assertHeightIsEqualTo(36.dp)
        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).onChildAt(0).assertWidthIsEqualTo(36.dp)
    }

    @Test
    fun signupScreenTopBar_titleIsDisplayedCorrectly() {
        setScreenState()

        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).onChildAt(1).assertTextEquals("Sign up")
        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).onChildAt(1).assertLeftPositionInRootIsEqualTo(56.dp,)
    }

    @Test
    fun signupScreen_hasCorrectNumberOfItemsWhenErrorsAreNotDisplayed() {
        setScreenState()

        composeRule.onNodeWithTag(SIGNUP_COLUMN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_COLUMN).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(SIGNUP_COLUMN).fetchSemanticsNode().children.size

        assertThat(numberOfChildren).isEqualTo(9)
    }

    @Test
    fun signupScreen_hasCorrectNumberOfItemsWhenErrorsAreDisplayed() {
        setScreenState(
            emailError = emailEmptyError,
            passwordError = passwordEmptyError,
            confirmPasswordError = confirmPasswordError,
            firstNameError = fieldEmptyError,
            lastNameError = fieldEmptyError,
            streetError = streetEmptyError,
            cityError = cityEmptyError,
            zipCodeError = zipCodeEmptyError
        )

        composeRule.onNodeWithTag(SIGNUP_COLUMN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_COLUMN).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(SIGNUP_COLUMN).fetchSemanticsNode().children.size

        assertThat(numberOfChildren).isEqualTo(17)
    }

    @Test
    fun signupScreen_isDisplayedCorrectly() {
        setScreenState()
        val deviceWidth = composeRule.onNodeWithTag(SIGNUP_CONTENT).onParent().getBoundsInRoot().right
        val deviceHeight = composeRule.onNodeWithTag(SIGNUP_CONTENT).onParent().getBoundsInRoot().bottom

        composeRule.onNodeWithTag(SIGNUP_CONTENT).assertPositionInRootIsEqualTo(10.dp,0.dp)
        composeRule.onNodeWithTag(SIGNUP_CONTENT).assertHeightIsEqualTo(deviceHeight-bottomBarHeight.dp)
        composeRule.onNodeWithTag(SIGNUP_CONTENT).assertWidthIsEqualTo(deviceWidth-20.dp)
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

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).assertLeftPositionInRootIsEqualTo(10.dp)
        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).assertWidthIsEqualTo(deviceWidth-20.dp)
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

        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).assertLeftPositionInRootIsEqualTo(10.dp)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).assertWidthIsEqualTo(deviceWidth-20.dp)
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

        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).assertLeftPositionInRootIsEqualTo(10.dp)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).assertWidthIsEqualTo(deviceWidth-20.dp)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        assertThat(textInput).isEqualTo(confirmPassword)
    }

    @Test
    fun signupScreenFirstNameTextField_textIsDisplayedCorrectly() {
        val  firstName= "John"
        setScreenState(
            firstName = firstName
        )
        val deviceWidth = composeRule.onNodeWithTag(SIGNUP_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        val passwordNode = composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()

        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).assertLeftPositionInRootIsEqualTo(10.dp)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).assertWidthIsEqualTo(deviceWidth-20.dp)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        assertThat(textInput).isEqualTo(firstName)
    }

    @Test
    fun signupScreenLastNameTextField_textIsDisplayedCorrectly() {
        val  lastName= "Smith"
        setScreenState(
            lastName = lastName
        )
        val deviceWidth = composeRule.onNodeWithTag(SIGNUP_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        val passwordNode = composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()

        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).assertLeftPositionInRootIsEqualTo(10.dp)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).assertWidthIsEqualTo(deviceWidth-20.dp)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        assertThat(textInput).isEqualTo(lastName)
    }

    @Test
    fun signupScreenStreetTextField_textIsDisplayedCorrectly() {
        val street = "Street 1"
        setScreenState(
            street = street
        )
        val deviceWidth = composeRule.onNodeWithTag(SIGNUP_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        val passwordNode = composeRule.onNodeWithTag(SIGNUP_STREET_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()

        composeRule.onNodeWithTag(SIGNUP_STREET_TF).assertLeftPositionInRootIsEqualTo(10.dp)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).assertWidthIsEqualTo(deviceWidth-20.dp)
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        assertThat(textInput).isEqualTo(street)
    }

    @Test
    fun signupScreenCityTextField_textIsDisplayedCorrectly() {
        val city = "Warsaw"
        setScreenState(
            city = city
        )
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        val passwordNode = composeRule.onNodeWithTag(SIGNUP_CITY_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()

        composeRule.onNodeWithTag(SIGNUP_CITY_TF).assertLeftPositionInRootIsEqualTo(10.dp)
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        assertThat(textInput).isEqualTo(city)
    }

    @Test
    fun signupScreenZipCodeTextField_textIsDisplayedCorrectly() {
        val zipCode = "12-345"
        setScreenState(
            zipCode = zipCode
        )

        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)
        val passwordNode = composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()

        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()
        assertThat(textInput).isEqualTo(zipCode)
    }

    @Test
    fun signupScreenEmailErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            emailError = emailEmptyError
        )

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertTextEquals(emailEmptyError)
        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertLeftPositionInRootIsEqualTo(10.dp)
    }

    @Test
    fun signupScreenPasswordErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            passwordError = passwordEmptyError
        )

        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertTextEquals(passwordEmptyError)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertLeftPositionInRootIsEqualTo(10.dp)
    }

    @Test
    fun signupScreenConfirmPasswordErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            confirmPasswordError = confirmPasswordError
        )

        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertTextEquals(confirmPasswordError)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertLeftPositionInRootIsEqualTo(10.dp)
    }

    @Test
    fun signupScreenFirstNameErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            firstNameError = fieldEmptyError
        )

        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertTextEquals(fieldEmptyError)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertLeftPositionInRootIsEqualTo(10.dp)
    }

    @Test
    fun signupScreenLastNameErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            lastNameError = fieldEmptyError
        )

        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertTextEquals(fieldEmptyError)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertLeftPositionInRootIsEqualTo(10.dp)
    }

    @Test
    fun signupScreenStreetErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            streetError = streetEmptyError
        )

        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertTextEquals(streetEmptyError)
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertLeftPositionInRootIsEqualTo(10.dp)
    }

    @Test
    fun signupScreenCityErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            cityError = cityEmptyError
        )

        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertTextEquals(cityEmptyError)
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertLeftPositionInRootIsEqualTo(10.dp)
    }

    @Test
    fun signupScreenZipCodeErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            zipCodeError = zipCodeEmptyError
        )

        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertTextEquals(zipCodeEmptyError)
    }

    @Test
    fun signupScreenEmailErrorTextField_performClickOnButtonWileEmailTextFieldIsEmpty_errorDisplayedCorrectly() {
        val password = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertTextEquals(emailEmptyError)

        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenPasswordErrorTextField_performClickOnButtonWilePasswordTextFieldIsEmpty_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertTextEquals(passwordEmptyError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenConfirmPasswordErrorTextField_performClickOnButtonWileConfirmPasswordTextFieldIsEmpty_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertTextEquals(confirmPasswordError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenFirstNameErrorTextField_performClickOnButtonWileFirstNameTextFieldIsEmpty_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertTextEquals(fieldEmptyError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenLastNameErrorTextField_performClickOnButtonWileLastNameTextFieldIsEmpty_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val firstName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertTextEquals(fieldEmptyError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenStreetErrorTextField_performClickOnButtonWileStreetTextFieldIsEmpty_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val firstName = "Smith"
        val lastName = "Smith"
        val city = "Warsaw"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertTextEquals(streetEmptyError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenCityErrorTextField_performClickOnButtonWileCityTextFieldIsEmpty_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val firstName = "Smith"
        val lastName = "Smith"
        val street = "Street 1"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertTextEquals(cityEmptyError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenZipCodeErrorTextField_performClickOnButtonWileZipCodeTextFieldIsEmpty_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val firstName = "Smith"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertTextEquals(zipCodeEmptyError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenPasswordTextField_performClickOnButtonWilePasswordIsTooShort_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwe"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertTextEquals(shortPasswordError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenPasswordTextField_performClickOnButtonWilePasswordDoesNotHaveOneDigit_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty++"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertTextEquals(passwordContainsAtLeastOneDigitError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenPasswordTextField_performClickOnButtonWilePasswordDoesNotHaveOneCapitalLetter_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertTextEquals(passwordContainsAtLeastOneCapitalLetterError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenPasswordTextField_performClickOnButtonWilePasswordDoesNotHaveOneSpecialChar_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty11"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertTextEquals(passwordContainsAtLeastOneSpecialCharError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenFirstNameTextField_performClickOnButtonWileFirstNameDoesHaveDigit_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val firstName = "J0hn"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertTextEquals(fieldContainsDigitsError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
        fun signupScreenFirstNameTextField_performClickOnButtonWileFirstNameDoesHaveSpecialChar_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val firstName = "John%"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertTextEquals(filedContainsSpecialCharsError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenLastNameTextField_performClickOnButtonWileLastNameDoesHaveDigit_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith1"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertTextEquals(fieldContainsDigitsError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenLastNameTextField_performClickOnButtonWileLastNameDoesHaveSpecialChar_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith#"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertTextEquals(filedContainsSpecialCharsError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenStreetTextField_performClickOnButtonWileStreetHasNoLetters_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = "61"
        val city = "Warsaw"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertTextEquals(fieldContainsAtLeastOneLetterError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenStreetTextField_performClickOnButtonWileStreetDoesNotHaveOneDigit_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street"
        val city = "Warsaw"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertTextEquals(streetContainsAtLeastOneDigitError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenStreetTextField_performClickOnButtonWileStreetDoesHaveSpecialChar_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1%"
        val city = "Warsaw"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertTextEquals(filedContainsSpecialCharsError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenCityTextField_performClickOnButtonWileCityHasNoLetters_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "34"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertTextEquals(fieldContainsAtLeastOneLetterError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenCityTextField_performClickOnButtonWileCityDoesHaveDigit_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw5"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertTextEquals(fieldContainsDigitsError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenCityTextField_performClickOnButtonWileCityDoesHaveSpecialChar_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw$"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertTextEquals(filedContainsSpecialCharsError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenZipCodeTextField_performClickOnButtonWileZipCodeHasBadFormat_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertTextEquals(zipCodeBadFormat)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenZipCodeTextField_performClickOnButtonWileZipCodeDoesHaveSpecialChar_errorDisplayedCorrectly() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "{2-345"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertTextEquals(filedContainsSpecialCharsError)

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenErrorTextFields_performClickOnButtonWileAllTextFieldsAreEmpty_errorsDisplayedCorrectly() {
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        val errors = listOf(
            SIGNUP_EMAIL_ERROR,
            SIGNUP_PASSWORD_ERROR,
            SIGNUP_FIRSTNAME_ERROR,
            SIGNUP_LASTNAME_ERROR,
            SIGNUP_STREET_ERROR,
            SIGNUP_CITY_ERROR,
            SIGNUP_ZIP_CODE_ERROR
        )

        val constants = listOf(
            emailEmptyError,
            passwordEmptyError,
            fieldEmptyError,
            fieldEmptyError,
            streetEmptyError,
            cityEmptyError,
            zipCodeEmptyError
        )

        errors.zip(constants) { error, constant ->
            composeRule.onNodeWithTag(error).assertExists()
            composeRule.onNodeWithTag(error).assertTextEquals(constant)
        }

        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
    }

    @Test
    fun signupScreenErrorTextFields_noErrorsAfterClickingOnTheButton() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()


        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(confirmPassword)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SIGNUP_BTN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_ERROR).assertDoesNotExist()
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
    fun signupScreenCircularProgressIndicator_isShowingUpWhenAllIsCorrect() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(SIGNUP_CPI).assertDoesNotExist()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(confirmPassword)
        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(SIGNUP_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).performTextInput(zipCode)
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        composeRule.onNodeWithTag(SIGNUP_CPI).assertExists()
    }
}