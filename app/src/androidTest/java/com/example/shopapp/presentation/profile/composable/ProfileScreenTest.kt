package com.example.shopapp.presentation.profile.composable

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
import com.example.shopapp.util.Constants.PROFILE_CITY_TF
import com.example.shopapp.util.Constants.PROFILE_COLUMN
import com.example.shopapp.util.Constants.PROFILE_CONTENT
import com.example.shopapp.util.Constants.PROFILE_CPI
import com.example.shopapp.util.Constants.PROFILE_FIRSTNAME_TF
import com.example.shopapp.util.Constants.PROFILE_LASTNAME_TF
import com.example.shopapp.util.Constants.PROFILE_STREET_TF
import com.example.shopapp.util.Constants.PROFILE_TOP_BAR
import com.example.shopapp.util.Constants.PROFILE_ZIP_CODE_TF
import com.example.shopapp.util.Constants.SAVE_BTN
import com.example.shopapp.util.Constants.cityEmptyError
import com.example.shopapp.util.Constants.fieldContainsAtLeastOneLetterError
import com.example.shopapp.util.Constants.fieldContainsDigitsError
import com.example.shopapp.util.Constants.fieldContainsSpecialCharsError
import com.example.shopapp.util.Constants.fieldEmptyError
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
class ProfileScreenTest {
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
            PROFILE_FIRSTNAME_TF,
            PROFILE_LASTNAME_TF,
            PROFILE_STREET_TF,
            PROFILE_CITY_TF,
            PROFILE_ZIP_CODE_TF
        )

        errors = listOf(
            fieldEmptyError,
            fieldEmptyError,
            streetEmptyError,
            cityEmptyError,
            zipCodeEmptyError
        )

        labels = listOf("First Name", "Last Name", "Street", "City", "Zip Code")
    }

    private fun setScreenState(
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
                    startDestination = Screen.ProfileScreen.route
                ) {
                    composable(
                        route = Screen.ProfileScreen.route
                    ) {
                        ProfileContent(
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
                            onFirstNameChange = {},
                            onLastNameChange = {},
                            onStreetChange = {},
                            onCityChange = {},
                            onZipCodeChange = {},
                            onGoBack = {},
                            onSave = {}
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
                    startDestination = Screen.ProfileScreen.route
                ) {
                    composable(
                        route = Screen.ProfileScreen.route
                    ) { ProfileScreen(navController = navController) }
                }
            }
        }
    }

    @Test
    fun profileScreenTopBar_hasCorrectNumberOfItems() {
        setScreenState()

        composeRule.onNodeWithTag(PROFILE_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(PROFILE_TOP_BAR).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(PROFILE_TOP_BAR).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(2)
    }

    @Test
    fun profileScreenTopBar_topBarIsDisplayedCorrectly() {
        setScreenState()

        composeRule.onNodeWithTag(PROFILE_TOP_BAR).assertPositionInRootIsEqualTo(0.dp,0.dp)
        composeRule.onNodeWithTag(PROFILE_TOP_BAR).assertHeightIsEqualTo(64.dp)
        val deviceWidth = composeRule.onNodeWithTag(PROFILE_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(PROFILE_TOP_BAR).assertWidthIsEqualTo(deviceWidth)
    }

    @Test
    fun profileListScreenTopBar_goBackButtonIsDisplayedCorrectly() {
        setScreenState()

        composeRule.onNodeWithTag(PROFILE_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(PROFILE_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(PROFILE_TOP_BAR).onChildAt(0).assertContentDescriptionContains(GO_BACK_BTN)
        composeRule.onNodeWithTag(PROFILE_TOP_BAR).onChildAt(0).assertHasClickAction()

        composeRule.onNodeWithTag(PROFILE_TOP_BAR).onChildAt(0).assertPositionInRootIsEqualTo(8.dp,12.dp)
        composeRule.onNodeWithTag(PROFILE_TOP_BAR).onChildAt(0).assertHeightIsEqualTo(40.dp)
        composeRule.onNodeWithTag(PROFILE_TOP_BAR).onChildAt(0).assertWidthIsEqualTo(40.dp)
    }

    @Test
    fun profileScreenTopBar_titleIsDisplayedCorrectly() {
        setScreenState()

        composeRule.onNodeWithTag(PROFILE_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(PROFILE_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(PROFILE_TOP_BAR).onChildAt(1).assertTextEquals("My Profile")
        composeRule.onNodeWithTag(PROFILE_TOP_BAR).onChildAt(1).assertLeftPositionInRootIsEqualTo(56.dp,)
    }

    @Test
    fun profileScreen_hasCorrectNumberOfItemsWhenErrorsAreNotDisplayed() {
        setScreenState()

        composeRule.onNodeWithTag(PROFILE_COLUMN).assertExists()
        composeRule.onNodeWithTag(PROFILE_COLUMN).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(PROFILE_COLUMN).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(6)
    }

    @Test
    fun profileScreen_hasCorrectNumberOfItemsWhenErrorsAreDisplayed() {
        setScreenState(
            firstNameError = fieldEmptyError,
            lastNameError = fieldEmptyError,
            streetError = streetEmptyError,
            cityError = cityEmptyError,
            zipCodeError = zipCodeEmptyError
        )

        composeRule.onNodeWithTag(PROFILE_COLUMN).assertExists()
        composeRule.onNodeWithTag(PROFILE_COLUMN).assertIsDisplayed()
        val numberOfChildren = composeRule.onNodeWithTag(PROFILE_COLUMN).fetchSemanticsNode().children.size
        assertThat(numberOfChildren).isEqualTo(6)
    }

    @Test
    fun profileScreenColumn_isDisplayedCorrectly() {
        setScreenState()
        val deviceWidth = composeRule.onNodeWithTag(PROFILE_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(PROFILE_COLUMN).assertExists()
        composeRule.onNodeWithTag(PROFILE_COLUMN).assertIsDisplayed()
        composeRule.onNodeWithTag(PROFILE_COLUMN).assertPositionInRootIsEqualTo(0.dp,64.dp)
        composeRule.onNodeWithTag(PROFILE_COLUMN).assertWidthIsEqualTo(deviceWidth)
    }

    @Test
    fun profileScreenFirstNameTextField_textIsDisplayedCorrectly() {
        val  firstName= "John"
        setScreenState(
            firstName = firstName
        )

        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_TF).performTextInput(firstName)
        val passwordNode = composeRule.onNodeWithTag(PROFILE_FIRSTNAME_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()
        assertThat(textInput).isEqualTo(firstName)
    }

    @Test
    fun profileScreenLastNameTextField_textIsDisplayedCorrectly() {
        val  lastName= "Smith"
        setScreenState(
            lastName = lastName
        )

        composeRule.onNodeWithTag(PROFILE_LASTNAME_TF).performTextInput(lastName)
        val passwordNode = composeRule.onNodeWithTag(PROFILE_LASTNAME_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()
        assertThat(textInput).isEqualTo(lastName)
    }

    @Test
    fun profileScreenStreetTextField_textIsDisplayedCorrectly() {
        val street = "Street 1"
        setScreenState(
            street = street
        )

        composeRule.onNodeWithTag(PROFILE_STREET_TF).performTextInput(street)
        val passwordNode = composeRule.onNodeWithTag(PROFILE_STREET_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()
        assertThat(textInput).isEqualTo(street)
    }

    @Test
    fun profileScreenCityTextField_textIsDisplayedCorrectly() {
        val city = "Warsaw"
        setScreenState(
            city = city
        )
        composeRule.onNodeWithTag(PROFILE_CITY_TF).performTextInput(city)
        val passwordNode = composeRule.onNodeWithTag(PROFILE_CITY_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()
        assertThat(textInput).isEqualTo(city)
    }

    @Test
    fun profileScreenZipCodeTextField_textIsDisplayedCorrectly() {
        val zipCode = "12-345"
        setScreenState(
            zipCode = zipCode
        )

        composeRule.onNodeWithTag(PROFILE_ZIP_CODE_TF).performTextInput(zipCode)
        val passwordNode = composeRule.onNodeWithTag(PROFILE_ZIP_CODE_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()
        assertThat(textInput).isEqualTo(zipCode)
    }

    @Test
    fun profileScreenFirstNameErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            firstNameError = fieldEmptyError
        )

        val firstNameNode = composeRule.onNodeWithTag(PROFILE_FIRSTNAME_TF).fetchSemanticsNode()
        val errorLabel = firstNameNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val errorValue = firstNameNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()
        assertThat(errorLabel).isEqualTo("First Name")
        assertThat(errorValue).isEqualTo(fieldEmptyError)
    }

    @Test
    fun profileScreenLastNameErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            lastNameError = fieldEmptyError
        )

        val lastNameNode = composeRule.onNodeWithTag(PROFILE_LASTNAME_TF).fetchSemanticsNode()
        val errorLabel = lastNameNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val errorValue = lastNameNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()
        assertThat(errorLabel).isEqualTo("Last Name")
        assertThat(errorValue).isEqualTo(fieldEmptyError)
    }

    @Test
    fun profileScreenStreetErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            streetError = streetEmptyError
        )

        val streetNode = composeRule.onNodeWithTag(PROFILE_STREET_TF).fetchSemanticsNode()
        val errorLabel = streetNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val errorValue = streetNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()
        assertThat(errorLabel).isEqualTo("Street")
        assertThat(errorValue).isEqualTo(streetEmptyError)
    }

    @Test
    fun profileScreenCityErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            cityError = cityEmptyError
        )

        val cityNode = composeRule.onNodeWithTag(PROFILE_CITY_TF).fetchSemanticsNode()
        val errorLabel = cityNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val errorValue = cityNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()
        assertThat(errorLabel).isEqualTo("City")
        assertThat(errorValue).isEqualTo(cityEmptyError)
    }

    @Test
    fun profileScreenZipCodeErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            zipCodeError = zipCodeEmptyError
        )

        val zipCodeNode = composeRule.onNodeWithTag(PROFILE_ZIP_CODE_TF).fetchSemanticsNode()
        val errorLabel = zipCodeNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val errorValue = zipCodeNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()
        assertThat(errorLabel).isEqualTo("Zip Code")
        assertThat(errorValue).isEqualTo(zipCodeEmptyError)
    }

    @Test
    fun profileScreenFirstNameErrorTextField_performClickOnButtonWileFirstNameTextFieldIsEmpty_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
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

        composeRule.onNodeWithTag(SAVE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SAVE_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SAVE_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(PROFILE_FIRSTNAME_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if (node != PROFILE_FIRSTNAME_TF) {
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
    fun profileScreenLastNameErrorTextField_performClickOnButtonWileLastNameTextFieldIsEmpty_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
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

        composeRule.onNodeWithTag(SAVE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SAVE_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SAVE_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(PROFILE_LASTNAME_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != PROFILE_LASTNAME_TF) {
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
    fun profileScreenStreetErrorTextField_performClickOnButtonWileStreetTextFieldIsEmpty_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
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

        composeRule.onNodeWithTag(SAVE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SAVE_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SAVE_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(PROFILE_STREET_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != PROFILE_STREET_TF) {
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
    fun profileScreenCityErrorTextField_performClickOnButtonWileCityTextFieldIsEmpty_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
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

        composeRule.onNodeWithTag(SAVE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SAVE_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SAVE_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(PROFILE_CITY_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != PROFILE_CITY_TF) {
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
    fun profileScreenZipCodeErrorTextField_performClickOnButtonWileZipCodeTextFieldIsEmpty_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
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

        composeRule.onNodeWithTag(SAVE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SAVE_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SAVE_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(PROFILE_ZIP_CODE_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(2).toString()

        for(node in nodeList) {
            if (node != PROFILE_ZIP_CODE_TF) {
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
    fun profileScreenFirstNameTextField_performClickOnButtonWileFirstNameDoesHaveDigit_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
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

        composeRule.onNodeWithTag(SAVE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SAVE_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SAVE_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(PROFILE_FIRSTNAME_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != PROFILE_FIRSTNAME_TF) {
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
    fun profileScreenFirstNameTextField_performClickOnButtonWileFirstNameDoesHaveSpecialChar_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
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

        composeRule.onNodeWithTag(SAVE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SAVE_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SAVE_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(PROFILE_FIRSTNAME_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != PROFILE_FIRSTNAME_TF) {
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
    fun profileScreenLastNameTextField_performClickOnButtonWileLastNameDoesHaveDigit_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
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

        composeRule.onNodeWithTag(SAVE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SAVE_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SAVE_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(PROFILE_LASTNAME_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != PROFILE_LASTNAME_TF) {
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
    fun profileScreenLastNameTextField_performClickOnButtonWileLastNameDoesHaveSpecialChar_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
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

        composeRule.onNodeWithTag(SAVE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SAVE_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SAVE_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(PROFILE_LASTNAME_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != PROFILE_LASTNAME_TF) {
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
    fun profileScreenStreetTextField_performClickOnButtonWileStreetHasNoLetters_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
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

        composeRule.onNodeWithTag(SAVE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SAVE_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SAVE_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(PROFILE_STREET_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != PROFILE_STREET_TF) {
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
    fun profileScreenStreetTextField_performClickOnButtonWileStreetDoesNotHaveOneDigit_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
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

        composeRule.onNodeWithTag(SAVE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SAVE_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SAVE_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(PROFILE_STREET_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != PROFILE_STREET_TF) {
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
    fun profileScreenStreetTextField_performClickOnButtonWileStreetDoesHaveSpecialChar_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
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

        composeRule.onNodeWithTag(SAVE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SAVE_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SAVE_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(PROFILE_STREET_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != PROFILE_STREET_TF) {
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
    fun profileScreenCityTextField_performClickOnButtonWileCityHasNoLetters_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
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

        composeRule.onNodeWithTag(SAVE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SAVE_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SAVE_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(PROFILE_CITY_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != PROFILE_CITY_TF) {
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
    fun profileScreenCityTextField_performClickOnButtonWileCityDoesHaveDigit_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
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

        composeRule.onNodeWithTag(SAVE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SAVE_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SAVE_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(PROFILE_CITY_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != PROFILE_CITY_TF) {
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
    fun profileScreenCityTextField_performClickOnButtonWileCityDoesHaveSpecialChar_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
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

        composeRule.onNodeWithTag(SAVE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SAVE_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SAVE_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(PROFILE_CITY_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != PROFILE_CITY_TF) {
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
    fun profileScreenZipCodeTextField_performClickOnButtonWileZipCodeHasBadFormat_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
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

        composeRule.onNodeWithTag(SAVE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SAVE_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SAVE_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(PROFILE_ZIP_CODE_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != PROFILE_ZIP_CODE_TF) {
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
    fun profileScreenZipCodeTextField_performClickOnButtonWileZipCodeDoesHaveSpecialChar_errorDisplayedCorrectly() {
        setScreen()

        val inputValues = listOf(
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

        composeRule.onNodeWithTag(SAVE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SAVE_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SAVE_BTN).performClick()


        val resultNode = composeRule.onNodeWithTag(PROFILE_ZIP_CODE_TF).fetchSemanticsNode()
        val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
        val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()

        for(node in nodeList) {
            if(node != PROFILE_ZIP_CODE_TF) {
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
    fun profileScreenErrorTextFields_performClickOnButtonWileAllTextFieldsAreEmpty_errorsDisplayedCorrectly() {
        setScreen()

        for(node in nodeList) {
            val initialNodeErrorState =  composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                SemanticsProperties.Error
            )
            assertThat(initialNodeErrorState).isNull()
        }

        composeRule.onNodeWithTag(SAVE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SAVE_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SAVE_BTN).performClick()

        for(i in 0 until(nodeList.size-1)) {
            val resultNode = composeRule.onNodeWithTag(nodeList[i]).fetchSemanticsNode()
            val errorLabel = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(0).toString()
            val resultErrorValue = resultNode.config.getOrNull(SemanticsProperties.Text)?.get(1).toString()
            assertThat(errorLabel).isEqualTo(labels[i])
            assertThat(resultErrorValue).isEqualTo(errors[i])
        }
    }

    @Test
    fun profileScreenErrorTextFields_noErrorsAfterClickingOnTheButton() {
        setScreen()

        val inputValues = listOf(
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

        composeRule.onNodeWithTag(SAVE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SAVE_BTN).assertIsEnabled()
        composeRule.onNodeWithTag(SAVE_BTN).performClick()

        for(node in nodeList) {
            val resultNodeErrorState =
                composeRule.onNodeWithTag(node).fetchSemanticsNode().config.getOrNull(
                    SemanticsProperties.Error
                )
            assertThat(resultNodeErrorState).isNull()
        }
    }

    @Test
    fun profileScreenCircularProgressIndicator_IsDisplayedCorrectly() {
        setScreenState(
            isLoading = true
        )
        val deviceWidth = composeRule.onNodeWithTag(PROFILE_CONTENT).onParent().getBoundsInRoot().right
        val deviceHeight = composeRule.onNodeWithTag(PROFILE_CONTENT).onParent().getBoundsInRoot().bottom
        val leftPosition = deviceWidth.value/2
        val topPosition = deviceHeight.value/2

        composeRule.onNodeWithTag(PROFILE_CPI).assertExists()
        composeRule.onNodeWithTag(PROFILE_CPI).assertIsDisplayed()
        composeRule.onNodeWithTag(PROFILE_CPI).assertPositionInRootIsEqualTo(0.dp,0.dp)
        composeRule.onNodeWithTag(PROFILE_CPI).assertHeightIsEqualTo(deviceHeight)
        composeRule.onNodeWithTag(PROFILE_CPI).assertWidthIsEqualTo(deviceWidth)

        composeRule.onNodeWithTag(PROFILE_CPI).onChild().assertPositionInRootIsEqualTo(leftPosition.dp-20.dp,topPosition.dp-20.dp)
        composeRule.onNodeWithTag(PROFILE_CPI).onChild().assertWidthIsEqualTo(40.dp)
    }
}