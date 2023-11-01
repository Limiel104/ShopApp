package com.example.shopapp.presentation.signup.composable

import androidx.activity.compose.setContent
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.test.assertTopPositionInRootIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
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
import com.example.shopapp.util.Constants.SIGNUP_CITY_TF
import com.example.shopapp.util.Constants.SIGNUP_COLUMN
import com.example.shopapp.util.Constants.SIGNUP_CONFIRM_PASSWORD_TF
import com.example.shopapp.util.Constants.SIGNUP_CONTENT
import com.example.shopapp.util.Constants.SIGNUP_CPI
import com.example.shopapp.util.Constants.SIGNUP_EMAIL_TF
import com.example.shopapp.util.Constants.SIGNUP_FIRSTNAME_TF
import com.example.shopapp.util.Constants.SIGNUP_LASTNAME_TF
import com.example.shopapp.util.Constants.SIGNUP_PASSWORD_TF
import com.example.shopapp.util.Constants.SIGNUP_STREET_TF
import com.example.shopapp.util.Constants.SIGNUP_TOP_BAR
import com.example.shopapp.util.Constants.SIGNUP_ZIP_CODE_TF
import com.example.shopapp.util.Constants.cityEmptyError
import com.example.shopapp.util.Constants.confirmPasswordError
import com.example.shopapp.util.Constants.emailEmptyError
import com.example.shopapp.util.Constants.fieldContainsAtLeastOneLetterError
import com.example.shopapp.util.Constants.fieldContainsDigitsError
import com.example.shopapp.util.Constants.fieldContainsSpecialCharsError
import com.example.shopapp.util.Constants.fieldEmptyError
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

    private lateinit var nodeList: List<String>
    private lateinit var errors: List<String>
    private lateinit var labels: List<String>

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()

        nodeList = listOf(
            SIGNUP_EMAIL_TF,
            SIGNUP_PASSWORD_TF,
            SIGNUP_CONFIRM_PASSWORD_TF,
            SIGNUP_FIRSTNAME_TF,
            SIGNUP_LASTNAME_TF,
            SIGNUP_STREET_TF,
            SIGNUP_CITY_TF,
            SIGNUP_ZIP_CODE_TF
        )

        errors = listOf(
            emailEmptyError,
            passwordEmptyError,
            "",
            fieldEmptyError,
            fieldEmptyError,
            streetEmptyError,
            cityEmptyError,
            zipCodeEmptyError
        )

        labels = listOf("Email","Password","Confirm Password", "First Name", "Last Name", "Street", "City","Zip Code")
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
                            scrollState = rememberScrollState(),
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
                        SignupScreen(navController = navController)
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

        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).assertPositionInRootIsEqualTo(0.dp,0.dp)
        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).assertHeightIsEqualTo(64.dp)
        val deviceWidth = composeRule.onNodeWithTag(SIGNUP_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).assertWidthIsEqualTo(deviceWidth)
    }

    @Test
    fun signupListScreenTopBar_goBackButtonIsDisplayedCorrectly() {
        setScreenState()

        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).onChildAt(0).assertContentDescriptionContains(GO_BACK_BTN)
        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).onChildAt(0).assertHasClickAction()

        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).onChildAt(0).assertPositionInRootIsEqualTo(8.dp,12.dp)
        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).onChildAt(0).assertHeightIsEqualTo(40.dp)
        composeRule.onNodeWithTag(SIGNUP_TOP_BAR).onChildAt(0).assertWidthIsEqualTo(40.dp)
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
    fun signupScreenColumn_hasCorrectNumberOfItemsWhenErrorsAreNotDisplayed() {
        setScreenState()

        composeRule.onNodeWithTag(SIGNUP_COLUMN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_COLUMN).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(SIGNUP_COLUMN).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(9)
    }

    @Test
    fun signupScreenColumn_hasCorrectNumberOfItemsWhenErrorsAreDisplayed() {
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
        assertThat(numberOfChildren).isEqualTo(9)
    }

    @Test
    fun signupScreenColumn_isDisplayedCorrectly() {
        setScreenState()
        val deviceWidth = composeRule.onNodeWithTag(SIGNUP_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(SIGNUP_COLUMN).assertExists()
        composeRule.onNodeWithTag(SIGNUP_COLUMN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_COLUMN).assertPositionInRootIsEqualTo(0.dp,64.dp)
        composeRule.onNodeWithTag(SIGNUP_COLUMN).assertWidthIsEqualTo(deviceWidth)
    }

    @Test
    fun signupScreenEmailTextField_textIsDisplayedCorrectly() {
        val email = "email@email.com"
        setScreenState(
            email = email
        )

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).performTextInput(email)
        val emailNode = composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).fetchSemanticsNode()
        val textInput = emailNode.config.getOrNull(SemanticsProperties.EditableText).toString()

        composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).assertTopPositionInRootIsEqualTo(64.dp)
        assertThat(textInput).isEqualTo(email)
    }

    @Test
    fun signupScreenPasswordTextField_textIsDisplayedCorrectly() {
        val password = "Qwerty1+"
        setScreenState(
            password = password
        )

        composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).performTextInput(password)
        val passwordNode = composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()
        assertThat(textInput).isEqualTo(password)
    }

    @Test
    fun signupScreenConfirmPasswordTextField_textIsDisplayedCorrectly() {
        val confirmPassword = "Qwerty1+"
        setScreenState(
            confirmPassword = confirmPassword
        )

        composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).performTextInput(confirmPassword)
        val passwordNode = composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()
        assertThat(textInput).isEqualTo(confirmPassword)
    }

    @Test
    fun signupScreenFirstNameTextField_textIsDisplayedCorrectly() {
        val  firstName= "John"
        setScreenState(
            firstName = firstName
        )

        composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).performTextInput(firstName)
        val passwordNode = composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()
        assertThat(textInput).isEqualTo(firstName)
    }

    @Test
    fun signupScreenLastNameTextField_textIsDisplayedCorrectly() {
        val  lastName= "Smith"
        setScreenState(
            lastName = lastName
        )

        composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).performTextInput(lastName)
        val passwordNode = composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()
        assertThat(textInput).isEqualTo(lastName)
    }

    @Test
    fun signupScreenStreetTextField_textIsDisplayedCorrectly() {
        val street = "Street 1"
        setScreenState(
            street = street
        )

        composeRule.onNodeWithTag(SIGNUP_STREET_TF).performTextInput(street)
        val passwordNode = composeRule.onNodeWithTag(SIGNUP_STREET_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()
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
        assertThat(textInput).isEqualTo(zipCode)
    }

    @Test
    fun signupScreenEmailErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            emailError = emailEmptyError
        )

        val emailNode = composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).fetchSemanticsNode()
        val errorLabel = emailNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val errorValue = emailNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()
        assertThat(errorLabel).isEqualTo("Email")
        assertThat(errorValue).isEqualTo(emailEmptyError)
    }

    @Test
    fun signupScreenPasswordErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            passwordError = passwordEmptyError
        )

        val passwordNode = composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).fetchSemanticsNode()
        val errorLabel = passwordNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val errorValue = passwordNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()
        assertThat(errorLabel).isEqualTo("Password")
        assertThat(errorValue).isEqualTo(passwordEmptyError)
    }

    @Test
    fun signupScreenConfirmPasswordErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            confirmPasswordError = confirmPasswordError
        )

        val confirmPasswordNode = composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).fetchSemanticsNode()
        val errorLabel = confirmPasswordNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val errorValue = confirmPasswordNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()
        assertThat(errorLabel).isEqualTo("Confirm Password")
        assertThat(errorValue).isEqualTo(confirmPasswordError)
    }

    @Test
    fun signupScreenFirstNameErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            firstNameError = fieldEmptyError
        )

        val firstNameNode = composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).fetchSemanticsNode()
        val errorLabel = firstNameNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val errorValue = firstNameNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()
        assertThat(errorLabel).isEqualTo("First Name")
        assertThat(errorValue).isEqualTo(fieldEmptyError)
    }

    @Test
    fun signupScreenLastNameErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            lastNameError = fieldEmptyError
        )

        val lastNameNode = composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).fetchSemanticsNode()
        val errorLabel = lastNameNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val errorValue = lastNameNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()
        assertThat(errorLabel).isEqualTo("Last Name")
        assertThat(errorValue).isEqualTo(fieldEmptyError)
    }

    @Test
    fun signupScreenStreetErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            streetError = streetEmptyError
        )

        val streetNode = composeRule.onNodeWithTag(SIGNUP_STREET_TF).fetchSemanticsNode()
        val errorLabel = streetNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val errorValue = streetNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()
        assertThat(errorLabel).isEqualTo("Street")
        assertThat(errorValue).isEqualTo(streetEmptyError)
    }

    @Test
    fun signupScreenCityErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            cityError = cityEmptyError
        )

        val cityNode = composeRule.onNodeWithTag(SIGNUP_CITY_TF).fetchSemanticsNode()
        val errorLabel = cityNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val errorValue = cityNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()
        assertThat(errorLabel).isEqualTo("City")
        assertThat(errorValue).isEqualTo(cityEmptyError)
    }

    @Test
    fun signupScreenZipCodeErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            zipCodeError = zipCodeEmptyError
        )

        val zipCodeNode = composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).fetchSemanticsNode()
        val errorLabel = zipCodeNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val errorValue = zipCodeNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()
        assertThat(errorLabel).isEqualTo("Zip Code")
        assertThat(errorValue).isEqualTo(zipCodeEmptyError)
    }

    @Test
    fun signupScreenEmailErrorTextField_performClickOnButtonWileEmailTextFieldIsEmpty_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
            "",
            "Qwerty1+",
            "Qwerty1+",
            "John",
            "Smith",
            "Street 1",
            "Warsaw",
            "12-345"
        )

        for(node in nodeList) {
            val initialNodeErrorState =
                composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                    SemanticsProperties.Error
                )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        val resultNode = composeRule.onNodeWithTag(SIGNUP_EMAIL_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != SIGNUP_EMAIL_TF) {
                val resultNodeErrorState =
                    composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                        SemanticsProperties.Error
                    )
                assertThat(resultNodeErrorState).isNull()
            }
        }
        assertThat(errorLabel).isEqualTo("Email")
        assertThat(resultErrorValue).isEqualTo(emailEmptyError)
    }

    @Test
    fun signupScreenPasswordErrorTextField_performClickOnButtonWilePasswordTextFieldIsEmpty_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "",
            "",
            "John",
            "Smith",
            "Street 1",
            "Warsaw",
            "12-345"
        )

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        val resultNode = composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != SIGNUP_PASSWORD_TF) {
                val resultNodeErrorState =
                    composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                        SemanticsProperties.Error
                    )
                assertThat(resultNodeErrorState).isNull()
            }
        }
        assertThat(errorLabel).isEqualTo("Password")
        assertThat(resultErrorValue).isEqualTo(passwordEmptyError)
    }

    @Test
    fun signupScreenConfirmPasswordErrorTextField_performClickOnButtonWileConfirmPasswordTextFieldIsEmpty_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "Qwerty1+",
            "",
            "John",
            "Smith",
            "Street 1",
            "Warsaw",
            "12-345"
        )

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != SIGNUP_CONFIRM_PASSWORD_TF) {
                val resultNodeErrorState =
                    composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                        SemanticsProperties.Error
                    )
                assertThat(resultNodeErrorState).isNull()
            }
        }
        assertThat(errorLabel).isEqualTo("Confirm Password")
        assertThat(resultErrorValue).isEqualTo(confirmPasswordError)
    }

    @Test
    fun signupScreenFirstNameErrorTextField_performClickOnButtonWileFirstNameTextFieldIsEmpty_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "Qwerty1+",
            "Qwerty1+",
            "",
            "Smith",
            "Street 1",
            "Warsaw",
            "12-345"
        )

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if (node != SIGNUP_FIRSTNAME_TF) {
                val resultNodeErrorState =
                    composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                        SemanticsProperties.Error
                    )
                assertThat(resultNodeErrorState).isNull()
            }
        }
        assertThat(errorLabel).isEqualTo("First Name")
        assertThat(resultErrorValue).isEqualTo(fieldEmptyError)
    }

    @Test
    fun signupScreenLastNameErrorTextField_performClickOnButtonWileLastNameTextFieldIsEmpty_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "Qwerty1+",
            "Qwerty1+",
            "John",
            "",
            "Street 1",
            "Warsaw",
            "12-345"
        )

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != SIGNUP_LASTNAME_TF) {
                val resultNodeErrorState =
                    composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                        SemanticsProperties.Error
                    )
                assertThat(resultNodeErrorState).isNull()
            }
        }
        assertThat(errorLabel).isEqualTo("Last Name")
        assertThat(resultErrorValue).isEqualTo(fieldEmptyError)
    }

    @Test
    fun signupScreenStreetErrorTextField_performClickOnButtonWileStreetTextFieldIsEmpty_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "Qwerty1+",
            "Qwerty1+",
            "John",
            "Smith",
            "",
            "Warsaw",
            "12-345"
        )

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(SIGNUP_STREET_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != SIGNUP_STREET_TF) {
                val resultNodeErrorState =
                    composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                        SemanticsProperties.Error
                    )
                assertThat(resultNodeErrorState).isNull()
            }
        }
        assertThat(errorLabel).isEqualTo("Street")
        assertThat(resultErrorValue).isEqualTo(streetEmptyError)
    }

    @Test
    fun signupScreenCityErrorTextField_performClickOnButtonWileCityTextFieldIsEmpty_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "Qwerty1+",
            "Qwerty1+",
            "John",
            "Smith",
            "Street 1",
            "",
            "12-345"
        )

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(SIGNUP_CITY_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != SIGNUP_CITY_TF) {
                val resultNodeErrorState =
                    composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                        SemanticsProperties.Error
                    )
                assertThat(resultNodeErrorState).isNull()
            }
        }
        assertThat(errorLabel).isEqualTo("City")
        assertThat(resultErrorValue).isEqualTo(cityEmptyError)
    }

    @Test
    fun signupScreenZipCodeErrorTextField_performClickOnButtonWileZipCodeTextFieldIsEmpty_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "Qwerty1+",
            "Qwerty1+",
            "John",
            "Smith",
            "Street 1",
            "Warsaw",
            ""
        )

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(2).toString()

        for(node in nodeList) {
            if (node != SIGNUP_ZIP_CODE_TF) {
                val resultNodeErrorState =
                    composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                        SemanticsProperties.Error
                    )
                assertThat(resultNodeErrorState).isNull()
            }
        }
        assertThat(errorLabel).isEqualTo("Zip Code")
        assertThat(resultErrorValue).isEqualTo(zipCodeEmptyError)
    }

    @Test
    fun signupScreenPasswordTextField_performClickOnButtonWilePasswordIsTooShort_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "Qwe",
            "Qwe",
            "John",
            "Smith",
            "Street 1",
            "Warsaw",
            "12-345"
        )

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != SIGNUP_PASSWORD_TF) {
                val resultNodeErrorState =
                    composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                        SemanticsProperties.Error
                    )
                assertThat(resultNodeErrorState).isNull()
            }
        }
        assertThat(errorLabel).isEqualTo("Password")
        assertThat(resultErrorValue).isEqualTo(shortPasswordError)
    }

    @Test
    fun signupScreenPasswordTextField_performClickOnButtonWilePasswordDoesNotHaveOneDigit_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "Qwerty++",
            "Qwerty++",
            "John",
            "Smith",
            "Street 1",
            "Warsaw",
            "12-345"
        )

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != SIGNUP_PASSWORD_TF) {
                val resultNodeErrorState =
                    composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                        SemanticsProperties.Error
                    )
                assertThat(resultNodeErrorState).isNull()
            }
        }
        assertThat(errorLabel).isEqualTo("Password")
        assertThat(resultErrorValue).isEqualTo(passwordContainsAtLeastOneDigitError)
    }

    @Test
    fun signupScreenPasswordTextField_performClickOnButtonWilePasswordDoesNotHaveOneCapitalLetter_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "qwerty1+",
            "qwerty1+",
            "John",
            "Smith",
            "Street 1",
            "Warsaw",
            "12-345"
        )

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != SIGNUP_PASSWORD_TF) {
                val resultNodeErrorState =
                    composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                        SemanticsProperties.Error
                    )
                assertThat(resultNodeErrorState).isNull()
            }
        }
        assertThat(errorLabel).isEqualTo("Password")
        assertThat(resultErrorValue).isEqualTo(passwordContainsAtLeastOneCapitalLetterError)
    }

    @Test
    fun signupScreenPasswordTextField_performClickOnButtonWilePasswordDoesNotHaveOneSpecialChar_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "Qwerty11",
            "Qwerty11",
            "John",
            "Smith",
            "Street 1",
            "Warsaw",
            "12-345"
        )

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(SIGNUP_PASSWORD_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != SIGNUP_PASSWORD_TF) {
                val resultNodeErrorState =
                    composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                        SemanticsProperties.Error
                    )
                assertThat(resultNodeErrorState).isNull()
            }
        }
        assertThat(errorLabel).isEqualTo("Password")
        assertThat(resultErrorValue).isEqualTo(passwordContainsAtLeastOneSpecialCharError)
    }

    @Test
    fun signupScreenFirstNameTextField_performClickOnButtonWileFirstNameDoesHaveDigit_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "Qwerty1+",
            "Qwerty1+",
            "J0hn",
            "Smith",
            "Street 1",
            "Warsaw",
            "12-345"
        )

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != SIGNUP_FIRSTNAME_TF) {
                val resultNodeErrorState =
                    composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                        SemanticsProperties.Error
                    )
                assertThat(resultNodeErrorState).isNull()
            }
        }
        assertThat(errorLabel).isEqualTo("First Name")
        assertThat(resultErrorValue).isEqualTo(fieldContainsDigitsError)
    }

    @Test
        fun signupScreenFirstNameTextField_performClickOnButtonWileFirstNameDoesHaveSpecialChar_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "Qwerty1+",
            "Qwerty1+",
            "John%",
            "Smith",
            "Street 1",
            "Warsaw",
            "12-345"
        )

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(SIGNUP_FIRSTNAME_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != SIGNUP_FIRSTNAME_TF) {
                val resultNodeErrorState =
                    composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                        SemanticsProperties.Error
                    )
                assertThat(resultNodeErrorState).isNull()
            }
        }
        assertThat(errorLabel).isEqualTo("First Name")
        assertThat(resultErrorValue).isEqualTo(fieldContainsSpecialCharsError)
    }

    @Test
    fun signupScreenLastNameTextField_performClickOnButtonWileLastNameDoesHaveDigit_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "Qwerty1+",
            "Qwerty1+",
            "John",
            "Smith1",
            "Street 1",
            "Warsaw",
            "12-345"
        )

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != SIGNUP_LASTNAME_TF) {
                val resultNodeErrorState =
                    composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                        SemanticsProperties.Error
                    )
                assertThat(resultNodeErrorState).isNull()
            }
        }
        assertThat(errorLabel).isEqualTo("Last Name")
        assertThat(resultErrorValue).isEqualTo(fieldContainsDigitsError)
    }

    @Test
    fun signupScreenLastNameTextField_performClickOnButtonWileLastNameDoesHaveSpecialChar_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "Qwerty1+",
            "Qwerty1+",
            "John",
            "Smith#",
            "Street 1",
            "Warsaw",
            "12-345"
        )

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(SIGNUP_LASTNAME_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != SIGNUP_LASTNAME_TF) {
                val resultNodeErrorState =
                    composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                        SemanticsProperties.Error
                    )
                assertThat(resultNodeErrorState).isNull()
            }
        }
        assertThat(errorLabel).isEqualTo("Last Name")
        assertThat(resultErrorValue).isEqualTo(fieldContainsSpecialCharsError)
    }

    @Test
    fun signupScreenStreetTextField_performClickOnButtonWileStreetHasNoLetters_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "Qwerty1+",
            "Qwerty1+",
            "John",
            "Smith",
            "61",
            "Warsaw",
            "12-345"
        )

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(SIGNUP_STREET_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != SIGNUP_STREET_TF) {
                val resultNodeErrorState =
                    composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                        SemanticsProperties.Error
                    )
                assertThat(resultNodeErrorState).isNull()
            }
        }
        assertThat(errorLabel).isEqualTo("Street")
        assertThat(resultErrorValue).isEqualTo(fieldContainsAtLeastOneLetterError)
    }

    @Test
    fun signupScreenStreetTextField_performClickOnButtonWileStreetDoesNotHaveOneDigit_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "Qwerty1+",
            "Qwerty1+",
            "John",
            "Smith",
            "Street",
            "Warsaw",
            "12-345"
        )

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(SIGNUP_STREET_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != SIGNUP_STREET_TF) {
                val resultNodeErrorState =
                    composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                        SemanticsProperties.Error
                    )
                assertThat(resultNodeErrorState).isNull()
            }
        }
        assertThat(errorLabel).isEqualTo("Street")
        assertThat(resultErrorValue).isEqualTo(streetContainsAtLeastOneDigitError)
    }

    @Test
    fun signupScreenStreetTextField_performClickOnButtonWileStreetDoesHaveSpecialChar_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "Qwerty1+",
            "Qwerty1+",
            "John",
            "Smith",
            "Street 1%",
            "Warsaw",
            "12-345"
        )

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(SIGNUP_STREET_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != SIGNUP_STREET_TF) {
                val resultNodeErrorState =
                    composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                        SemanticsProperties.Error
                    )
                assertThat(resultNodeErrorState).isNull()
            }
        }
        assertThat(errorLabel).isEqualTo("Street")
        assertThat(resultErrorValue).isEqualTo(fieldContainsSpecialCharsError)
    }

    @Test
    fun signupScreenCityTextField_performClickOnButtonWileCityHasNoLetters_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "Qwerty1+",
            "Qwerty1+",
            "John",
            "Smith",
            "Street 1",
            "34",
            "12-345"
        )

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(SIGNUP_CITY_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != SIGNUP_CITY_TF) {
                val resultNodeErrorState =
                    composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                        SemanticsProperties.Error
                    )
                assertThat(resultNodeErrorState).isNull()
            }
        }
        assertThat(errorLabel).isEqualTo("City")
        assertThat(resultErrorValue).isEqualTo(fieldContainsAtLeastOneLetterError)
    }

    @Test
    fun signupScreenCityTextField_performClickOnButtonWileCityDoesHaveDigit_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "Qwerty1+",
            "Qwerty1+",
            "John",
            "Smith",
            "Street 1",
            "Warsaw5",
            "12-345"
        )

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(SIGNUP_CITY_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != SIGNUP_CITY_TF) {
                val resultNodeErrorState =
                    composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                        SemanticsProperties.Error
                    )
                assertThat(resultNodeErrorState).isNull()
            }
        }
        assertThat(errorLabel).isEqualTo("City")
        assertThat(resultErrorValue).isEqualTo(fieldContainsDigitsError)
    }

    @Test
    fun signupScreenCityTextField_performClickOnButtonWileCityDoesHaveSpecialChar_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "Qwerty1+",
            "Qwerty1+",
            "John",
            "Smith",
            "Street 1",
            "Warsaw$",
            "12-345"
        )

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(SIGNUP_CITY_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != SIGNUP_CITY_TF) {
                val resultNodeErrorState =
                    composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                        SemanticsProperties.Error
                    )
                assertThat(resultNodeErrorState).isNull()
            }
        }
        assertThat(errorLabel).isEqualTo("City")
        assertThat(resultErrorValue).isEqualTo(fieldContainsSpecialCharsError)
    }

    @Test
    fun signupScreenZipCodeTextField_performClickOnButtonWileZipCodeHasBadFormat_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "Qwerty1+",
            "Qwerty1+",
            "John",
            "Smith",
            "Street 1",
            "Warsaw",
            "12"
        )

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != SIGNUP_ZIP_CODE_TF) {
                val resultNodeErrorState =
                    composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                        SemanticsProperties.Error
                    )
                assertThat(resultNodeErrorState).isNull()
            }
        }
        assertThat(errorLabel).isEqualTo("Zip Code")
        assertThat(resultErrorValue).isEqualTo(zipCodeBadFormat)
    }

    @Test
    fun signupScreenZipCodeTextField_performClickOnButtonWileZipCodeDoesHaveSpecialChar_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "Qwerty1+",
            "Qwerty1+",
            "John",
            "Smith",
            "Street 1",
            "Warsaw",
            "{2-345"
        )

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(SIGNUP_ZIP_CODE_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != SIGNUP_ZIP_CODE_TF) {
                val resultNodeErrorState =
                    composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                        SemanticsProperties.Error
                    )
                assertThat(resultNodeErrorState).isNull()
            }
        }
        assertThat(errorLabel).isEqualTo("Zip Code")
        assertThat(resultErrorValue).isEqualTo(fieldContainsSpecialCharsError)
    }

    @Test
    fun signupScreenErrorTextFields_performClickOnButtonWileAllTextFieldsAreEmpty_errorsDisplayedCorrectly() {
        setScreen()

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        for(i in 0 until(nodeList.size-1)) {
            if(nodeList[i] != SIGNUP_CONFIRM_PASSWORD_TF) {
                val resultNode = composeRule.onNodeWithTag(nodeList[i]).fetchSemanticsNode()
                val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
                val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()
                assertThat(errorLabel).isEqualTo(labels[i])
                assertThat(resultErrorValue).isEqualTo(errors[i])
            }
        }

        val resultNodeErrorState =
            composeRule.onNodeWithTag(SIGNUP_CONFIRM_PASSWORD_TF).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
        assertThat(resultNodeErrorState).isNull()
    }

    @Test
    fun signupScreenErrorTextFields_noErrorsAfterClickingOnTheButton() {
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "Qwerty1+",
            "Qwerty1+",
            "John",
            "Smith",
            "Street 1",
            "Warsaw",
            "12-345"
        )

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()

        for(node in nodeList) {
            val resultNodeErrorState =
                composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                    SemanticsProperties.Error
                )
            assertThat(resultNodeErrorState).isNull()
        }
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
        setScreen()

        val inputValues = listOf(
            "email@email.com",
            "Qwerty1+",
            "Qwerty1+",
            "John",
            "Smith",
            "Street 1",
            "Warsaw",
            "12-345"
        )

        composeRule.onNodeWithTag(SIGNUP_CPI).assertDoesNotExist()

        nodeList.zip(inputValues) { node, value ->
            composeRule.onNodeWithTag(node).performTextInput(value)
        }

        composeRule.onNodeWithTag(SIGNUP_BTN).performScrollTo()
        composeRule.onNodeWithTag(SIGNUP_BTN).performClick()
        composeRule.onNodeWithTag(SIGNUP_CPI).assertExists()
    }
}