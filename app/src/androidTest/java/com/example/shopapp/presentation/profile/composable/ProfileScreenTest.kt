package com.example.shopapp.presentation.profile.composable

import androidx.activity.compose.setContent
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
import com.example.shopapp.presentation.profil.composable.ProfileContent
import com.example.shopapp.presentation.profil.composable.ProfileScreen
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.GO_BACK_BTN
import com.example.shopapp.util.Constants.PROFILE_CITY_ERROR
import com.example.shopapp.util.Constants.PROFILE_CITY_TF
import com.example.shopapp.util.Constants.PROFILE_COLUMN
import com.example.shopapp.util.Constants.PROFILE_CONTENT
import com.example.shopapp.util.Constants.PROFILE_FIRSTNAME_ERROR
import com.example.shopapp.util.Constants.PROFILE_FIRSTNAME_TF
import com.example.shopapp.util.Constants.PROFILE_LASTNAME_ERROR
import com.example.shopapp.util.Constants.PROFILE_LASTNAME_TF
import com.example.shopapp.util.Constants.PROFILE_STREET_ERROR
import com.example.shopapp.util.Constants.PROFILE_STREET_TF
import com.example.shopapp.util.Constants.PROFILE_TOP_BAR
import com.example.shopapp.util.Constants.PROFILE_ZIP_CODE_ERROR
import com.example.shopapp.util.Constants.PROFILE_ZIP_CODE_TF
import com.example.shopapp.util.Constants.SAVE_BTN
import com.example.shopapp.util.Constants.bottomBarHeight
import com.example.shopapp.util.Constants.cityEmptyError
import com.example.shopapp.util.Constants.fieldEmptyError
import com.example.shopapp.util.Constants.streetEmptyError
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

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
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
                            scaffoldState = rememberScaffoldState(),
                            bottomBarHeight = bottomBarHeight.dp,
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
                    ) {
                        ProfileScreen(
                            bottomBarHeight = bottomBarHeight.dp
                        )
                    }
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

        composeRule.onNodeWithTag(PROFILE_TOP_BAR).assertTopPositionInRootIsEqualTo(15.dp)
        composeRule.onNodeWithTag(PROFILE_TOP_BAR).assertHeightIsEqualTo(36.dp)
        val deviceWidth = composeRule.onNodeWithTag(PROFILE_CONTENT).onParent().getBoundsInRoot().right
        composeRule.onNodeWithTag(PROFILE_TOP_BAR).assertWidthIsEqualTo(deviceWidth-20.dp)
    }

    @Test
    fun profileListScreenTopBar_goBackButtonIsDisplayedCorrectly() {
        setScreenState()

        composeRule.onNodeWithTag(PROFILE_TOP_BAR).assertExists()
        composeRule.onNodeWithTag(PROFILE_TOP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(PROFILE_TOP_BAR).onChildAt(0).assertContentDescriptionContains(GO_BACK_BTN)
        composeRule.onNodeWithTag(PROFILE_TOP_BAR).onChildAt(0).assertHasClickAction()

        composeRule.onNodeWithTag(PROFILE_TOP_BAR).onChildAt(0).assertPositionInRootIsEqualTo(10.dp,15.dp)
        composeRule.onNodeWithTag(PROFILE_TOP_BAR).onChildAt(0).assertHeightIsEqualTo(36.dp)
        composeRule.onNodeWithTag(PROFILE_TOP_BAR).onChildAt(0).assertWidthIsEqualTo(36.dp)
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
        assertThat(numberOfChildren).isEqualTo(11)
    }

    @Test
    fun profileScreen_isDisplayedCorrectly() {
        setScreenState()
        val deviceWidth = composeRule.onNodeWithTag(PROFILE_CONTENT).onParent().getBoundsInRoot().right
        val deviceHeight = composeRule.onNodeWithTag(PROFILE_CONTENT).onParent().getBoundsInRoot().bottom

        composeRule.onNodeWithTag(PROFILE_CONTENT).assertPositionInRootIsEqualTo(10.dp,0.dp)
        composeRule.onNodeWithTag(PROFILE_CONTENT).assertHeightIsEqualTo(deviceHeight-bottomBarHeight.dp)
        composeRule.onNodeWithTag(PROFILE_CONTENT).assertWidthIsEqualTo(deviceWidth-20.dp)
    }

    @Test
    fun profileScreenFirstNameTextField_textIsDisplayedCorrectly() {
        val  firstName= "John"
        setScreenState(
            firstName = firstName
        )
        val deviceWidth = composeRule.onNodeWithTag(PROFILE_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_TF).performTextInput(firstName)
        val passwordNode = composeRule.onNodeWithTag(PROFILE_FIRSTNAME_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()

        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_TF).assertLeftPositionInRootIsEqualTo(10.dp)
        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_TF).assertWidthIsEqualTo(deviceWidth-20.dp)
        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_ERROR).assertDoesNotExist()
        assertThat(textInput).isEqualTo(firstName)
    }

    @Test
    fun profileScreenLastNameTextField_textIsDisplayedCorrectly() {
        val  lastName= "Smith"
        setScreenState(
            lastName = lastName
        )
        val deviceWidth = composeRule.onNodeWithTag(PROFILE_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(PROFILE_LASTNAME_TF).performTextInput(lastName)
        val passwordNode = composeRule.onNodeWithTag(PROFILE_LASTNAME_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()

        composeRule.onNodeWithTag(PROFILE_LASTNAME_TF).assertLeftPositionInRootIsEqualTo(10.dp)
        composeRule.onNodeWithTag(PROFILE_LASTNAME_TF).assertWidthIsEqualTo(deviceWidth-20.dp)
        composeRule.onNodeWithTag(PROFILE_LASTNAME_ERROR).assertDoesNotExist()
        assertThat(textInput).isEqualTo(lastName)
    }

    @Test
    fun profileScreenStreetTextField_textIsDisplayedCorrectly() {
        val street = "Street 1"
        setScreenState(
            street = street
        )
        val deviceWidth = composeRule.onNodeWithTag(PROFILE_CONTENT).onParent().getBoundsInRoot().right

        composeRule.onNodeWithTag(PROFILE_STREET_TF).performTextInput(street)
        val passwordNode = composeRule.onNodeWithTag(PROFILE_STREET_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()

        composeRule.onNodeWithTag(PROFILE_STREET_TF).assertLeftPositionInRootIsEqualTo(10.dp)
        composeRule.onNodeWithTag(PROFILE_STREET_TF).assertWidthIsEqualTo(deviceWidth-20.dp)
        composeRule.onNodeWithTag(PROFILE_STREET_ERROR).assertDoesNotExist()
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

        composeRule.onNodeWithTag(PROFILE_CITY_TF).assertLeftPositionInRootIsEqualTo(10.dp)
        composeRule.onNodeWithTag(PROFILE_CITY_ERROR).assertDoesNotExist()
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

        composeRule.onNodeWithTag(PROFILE_ZIP_CODE_ERROR).assertDoesNotExist()
        assertThat(textInput).isEqualTo(zipCode)
    }

    @Test
    fun profileScreenFirstNameErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            firstNameError = fieldEmptyError
        )

        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_ERROR).assertExists()
        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_ERROR).assertTextEquals(fieldEmptyError)
        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_ERROR).assertLeftPositionInRootIsEqualTo(10.dp)
    }

    @Test
    fun profileScreenLastNameErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            lastNameError = fieldEmptyError
        )

        composeRule.onNodeWithTag(PROFILE_LASTNAME_ERROR).assertExists()
        composeRule.onNodeWithTag(PROFILE_LASTNAME_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(PROFILE_LASTNAME_ERROR).assertTextEquals(fieldEmptyError)
        composeRule.onNodeWithTag(PROFILE_LASTNAME_ERROR).assertLeftPositionInRootIsEqualTo(10.dp)
    }

    @Test
    fun profileScreenStreetErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            streetError = streetEmptyError
        )

        composeRule.onNodeWithTag(PROFILE_STREET_ERROR).assertExists()
        composeRule.onNodeWithTag(PROFILE_STREET_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(PROFILE_STREET_ERROR).assertTextEquals(streetEmptyError)
        composeRule.onNodeWithTag(PROFILE_STREET_ERROR).assertLeftPositionInRootIsEqualTo(10.dp)
    }

    @Test
    fun profileScreenCityErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            cityError = cityEmptyError
        )

        composeRule.onNodeWithTag(PROFILE_CITY_ERROR).assertExists()
        composeRule.onNodeWithTag(PROFILE_CITY_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(PROFILE_CITY_ERROR).assertTextEquals(cityEmptyError)
        composeRule.onNodeWithTag(PROFILE_CITY_ERROR).assertLeftPositionInRootIsEqualTo(10.dp)
    }

    @Test
    fun profileScreenZipCodeErrorTextField_isDisplayedCorrectly() {
        setScreenState(
            zipCodeError = zipCodeEmptyError
        )

        composeRule.onNodeWithTag(PROFILE_ZIP_CODE_ERROR).assertExists()
        composeRule.onNodeWithTag(PROFILE_ZIP_CODE_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(PROFILE_ZIP_CODE_ERROR).assertTextEquals(zipCodeEmptyError)
    }

    @Test
    fun profileScreenFirstNameErrorTextField_performClickOnButtonWileFirstNameTextFieldIsEmpty_errorDisplayedCorrectly() {
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(PROFILE_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(PROFILE_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(PROFILE_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(PROFILE_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SAVE_BTN).assertExists()
        composeRule.onNodeWithTag(SAVE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SAVE_BTN).performClick()

        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_ERROR).assertExists()
        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_ERROR).assertTextEquals(fieldEmptyError)

        composeRule.onNodeWithTag(PROFILE_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
    fun profileScreenLastNameErrorTextField_performClickOnButtonWileLastNameTextFieldIsEmpty_errorDisplayedCorrectly() {
        val firstName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(PROFILE_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(PROFILE_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(PROFILE_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SAVE_BTN).assertExists()
        composeRule.onNodeWithTag(SAVE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SAVE_BTN).performClick()

        composeRule.onNodeWithTag(PROFILE_LASTNAME_ERROR).assertExists()
        composeRule.onNodeWithTag(PROFILE_LASTNAME_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(PROFILE_LASTNAME_ERROR).assertTextEquals(fieldEmptyError)

        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
    fun profileScreenStreetErrorTextField_performClickOnButtonWileStreetTextFieldIsEmpty_errorDisplayedCorrectly() {
        val firstName = "Smith"
        val lastName = "Smith"
        val city = "Warsaw"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(PROFILE_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(PROFILE_CITY_TF).performTextInput(city)
        composeRule.onNodeWithTag(PROFILE_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SAVE_BTN).assertExists()
        composeRule.onNodeWithTag(SAVE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SAVE_BTN).performClick()

        composeRule.onNodeWithTag(PROFILE_STREET_ERROR).assertExists()
        composeRule.onNodeWithTag(PROFILE_STREET_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(PROFILE_STREET_ERROR).assertTextEquals(streetEmptyError)

        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
    fun profileScreenCityErrorTextField_performClickOnButtonWileCityTextFieldIsEmpty_errorDisplayedCorrectly() {
        val firstName = "Smith"
        val lastName = "Smith"
        val street = "Street 1"
        val zipCode = "12-345"
        setScreen()

        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(PROFILE_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(PROFILE_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(PROFILE_ZIP_CODE_TF).performTextInput(zipCode)

        composeRule.onNodeWithTag(SAVE_BTN).assertExists()
        composeRule.onNodeWithTag(SAVE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SAVE_BTN).performClick()

        composeRule.onNodeWithTag(PROFILE_CITY_ERROR).assertExists()
        composeRule.onNodeWithTag(PROFILE_CITY_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(PROFILE_CITY_ERROR).assertTextEquals(cityEmptyError)

        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_ZIP_CODE_ERROR).assertDoesNotExist()
    }

    @Test
    fun profileScreenZipCodeErrorTextField_performClickOnButtonWileZipCodeTextFieldIsEmpty_errorDisplayedCorrectly() {
        val firstName = "Smith"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        setScreen()

        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_CITY_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_ZIP_CODE_ERROR).assertDoesNotExist()

        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_TF).performTextInput(firstName)
        composeRule.onNodeWithTag(PROFILE_LASTNAME_TF).performTextInput(lastName)
        composeRule.onNodeWithTag(PROFILE_STREET_TF).performTextInput(street)
        composeRule.onNodeWithTag(PROFILE_CITY_TF).performTextInput(city)

        composeRule.onNodeWithTag(SAVE_BTN).assertExists()
        composeRule.onNodeWithTag(SAVE_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(SAVE_BTN).performClick()

        composeRule.onNodeWithTag(PROFILE_ZIP_CODE_ERROR).assertExists()
        composeRule.onNodeWithTag(PROFILE_ZIP_CODE_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(PROFILE_ZIP_CODE_ERROR).assertTextEquals(zipCodeEmptyError)

        composeRule.onNodeWithTag(PROFILE_FIRSTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_LASTNAME_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_STREET_ERROR).assertDoesNotExist()
        composeRule.onNodeWithTag(PROFILE_CITY_ERROR).assertDoesNotExist()
    }
}