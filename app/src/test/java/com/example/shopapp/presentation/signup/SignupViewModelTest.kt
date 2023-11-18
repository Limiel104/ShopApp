package com.example.shopapp.presentation.signup

import com.example.shopapp.domain.model.Address
import com.example.shopapp.domain.model.User
import com.example.shopapp.domain.model.ValidationResult
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.Constants.cityEmptyError
import com.example.shopapp.util.Constants.confirmPasswordError
import com.example.shopapp.util.Constants.emailEmptyError
import com.example.shopapp.util.Constants.fieldEmptyError
import com.example.shopapp.util.Constants.passwordEmptyError
import com.example.shopapp.util.Constants.streetEmptyError
import com.example.shopapp.util.Constants.zipCodeEmptyError
import com.example.shopapp.util.MainDispatcherRule
import com.example.shopapp.util.Resource
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SignupViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var shopUseCases: ShopUseCases
    private lateinit var signupViewModel: SignupViewModel
    @MockK
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var user: User

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { shopUseCases.getCurrentUserUseCase() } returns firebaseUser
        every { firebaseUser.uid } returns "userUID"

        user = User(
            userUID = "userUID",
            firstName = "John",
            lastName = "Smith",
            address = Address(
                street = "Street 1",
                city = "Warsaw",
                zipCode = "12-345"
            ),
            points = 0
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    private fun setViewModel(): SignupViewModel {
        return SignupViewModel(shopUseCases)
    }

    private fun getCurrentSignupState(): SignupState {
        return signupViewModel.signupState.value
    }

    private fun setSignupState(
        email: String = "",
        password: String = "",
        confirmPassword: String = "",
        firstName: String = "",
        lastName: String = "",
        street: String = "",
        city: String = "",
        zipCode: String = ""
    ) {
        if(email != "") signupViewModel.onEvent(SignupEvent.EnteredEmail(email))
        if(password != "") signupViewModel.onEvent(SignupEvent.EnteredPassword(password))
        if(confirmPassword != "") signupViewModel.onEvent(SignupEvent.EnteredConfirmPassword(confirmPassword))
        if(firstName != "") signupViewModel.onEvent(SignupEvent.EnteredFirstName(firstName))
        if(lastName != "") signupViewModel.onEvent(SignupEvent.EnteredLastName(lastName))
        if(street != "") signupViewModel.onEvent(SignupEvent.EnteredStreet(street))
        if(city != "") signupViewModel.onEvent(SignupEvent.EnteredCity(city))
        if(zipCode != "") signupViewModel.onEvent(SignupEvent.EnteredZipCode(zipCode))
    }

    @Test
    fun `event enteredEmail sets email state`() {
        signupViewModel = setViewModel()

        val email = "email@email.com"
        val initialEmailState = getCurrentSignupState().email
        signupViewModel.onEvent(SignupEvent.EnteredEmail(email))
        val resultEmail = getCurrentSignupState().email

        assertThat(initialEmailState).isEqualTo("")
        assertThat(resultEmail).isEqualTo(email)
    }

    @Test
    fun `event enteredPassword sets password state`() {
        signupViewModel = setViewModel()

        val password = "Qwerty1+"
        val initialPasswordState = getCurrentSignupState().password
        signupViewModel.onEvent(SignupEvent.EnteredPassword(password))
        val resultPassword = getCurrentSignupState().password

        assertThat(initialPasswordState).isEqualTo("")
        assertThat(resultPassword).isEqualTo(password)
    }

    @Test
    fun `event enteredPassword sets confirmPassword state`() {
        signupViewModel = setViewModel()

        val password = "Qwerty1+"
        val initialConfirmPasswordState = getCurrentSignupState().password
        signupViewModel.onEvent(SignupEvent.EnteredConfirmPassword(password))
        val resultConfirmPassword = getCurrentSignupState().confirmPassword

        assertThat(initialConfirmPasswordState).isEqualTo("")
        assertThat(resultConfirmPassword).isEqualTo(password)
    }

    @Test
    fun `event enteredFirstName sets firstName state`() {
        signupViewModel = setViewModel()

        val firstName = "John"
        val initialFirstName = getCurrentSignupState().firstName
        signupViewModel.onEvent(SignupEvent.EnteredFirstName(firstName))
        val resultFirstName = getCurrentSignupState().firstName

        assertThat(initialFirstName).isEqualTo("")
        assertThat(resultFirstName).isEqualTo(firstName)
    }

    @Test
    fun `event enteredLastName sets lastName state`() {
        signupViewModel = setViewModel()

        val lastName = "Smith"
        val initialLastName = getCurrentSignupState().lastName
        signupViewModel.onEvent(SignupEvent.EnteredLastName(lastName))
        val resultLastName = getCurrentSignupState().lastName

        assertThat(initialLastName).isEqualTo("")
        assertThat(resultLastName).isEqualTo(lastName)
    }

    @Test
    fun `event enteredStreet sets street state`() {
        signupViewModel = setViewModel()

        val street = "Street 1"
        val initialStreet = getCurrentSignupState().street
        signupViewModel.onEvent(SignupEvent.EnteredStreet(street))
        val resultStreet = getCurrentSignupState().street

        assertThat(initialStreet).isEqualTo("")
        assertThat(resultStreet).isEqualTo(street)
    }

    @Test
        fun `event enteredCity sets city state`() {
        signupViewModel = setViewModel()

        val city = "Warsaw"
        val initialCity= getCurrentSignupState().city
        signupViewModel.onEvent(SignupEvent.EnteredCity(city))
        val resultCity= getCurrentSignupState().city

        assertThat(initialCity).isEqualTo("")
        assertThat(resultCity).isEqualTo(city)
    }

    @Test
    fun `event enteredZipCode sets zipCode state`() {
        signupViewModel = setViewModel()

        val zipCode = "12-345"
        val initialZipCode= getCurrentSignupState().zipCode
        signupViewModel.onEvent(SignupEvent.EnteredZipCode(zipCode))
        val resultZipCode= getCurrentSignupState().zipCode

        assertThat(initialZipCode).isEqualTo("")
        assertThat(resultZipCode).isEqualTo(zipCode)
    }

    @Test
    fun `validation is successful`() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateSignupPasswordUseCase(password)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateNameUseCase(any())
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateStreetUseCase(street)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateCityUseCase(city)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateZipCodeUseCase(zipCode)
        } returns ValidationResult(isSuccessful = true)

        signupViewModel = setViewModel()
        val result = signupViewModel.isValidationSuccessful(email,password,confirmPassword,firstName,lastName,street,city,zipCode)
        val signupState = getCurrentSignupState()

        verifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(result).isTrue()
        assertThat(signupState.emailError).isNull()
        assertThat(signupState.passwordError).isNull()
        assertThat(signupState.confirmPasswordError).isNull()
        assertThat(signupState.firstNameError).isNull()
        assertThat(signupState.lastNameError).isNull()
        assertThat(signupState.streetError).isNull()
        assertThat(signupState.cityError).isNull()
        assertThat(signupState.zipCodeError).isNull()
    }

    @Test
    fun `when email is not validated correctly its error state is not null`() {
        val email = ""
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = emailEmptyError
        )
        every {
            shopUseCases.validateSignupPasswordUseCase(password)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateNameUseCase(any())
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateStreetUseCase(street)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateCityUseCase(city)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateZipCodeUseCase(zipCode)
        } returns ValidationResult(isSuccessful = true)

        signupViewModel = setViewModel()
        val result = signupViewModel.isValidationSuccessful(email,password,confirmPassword,firstName,lastName,street,city,zipCode)
        val signupState = getCurrentSignupState()

        verifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(result).isFalse()
        assertThat(signupState.emailError).isEqualTo(emailEmptyError)
        assertThat(signupState.passwordError).isNull()
        assertThat(signupState.confirmPasswordError).isNull()
        assertThat(signupState.firstNameError).isNull()
        assertThat(signupState.lastNameError).isNull()
        assertThat(signupState.streetError).isNull()
        assertThat(signupState.cityError).isNull()
        assertThat(signupState.zipCodeError).isNull()
    }

    @Test
    fun `when password is not validated correctly its error state is not null`() {
        val email = "email@email.com"
        val password = ""
        val confirmPassword = ""
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateSignupPasswordUseCase(password)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = passwordEmptyError
        )
        every {
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateNameUseCase(any())
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateStreetUseCase(street)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateCityUseCase(city)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateZipCodeUseCase(zipCode)
        } returns ValidationResult(isSuccessful = true)

        signupViewModel = setViewModel()
        val result = signupViewModel.isValidationSuccessful(email,password,confirmPassword,firstName,lastName,street,city,zipCode)
        val signupState = getCurrentSignupState()

        verifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(result).isFalse()
        assertThat(signupState.emailError).isNull()
        assertThat(signupState.passwordError).isEqualTo(passwordEmptyError)
        assertThat(signupState.confirmPasswordError).isNull()
        assertThat(signupState.firstNameError).isNull()
        assertThat(signupState.lastNameError).isNull()
        assertThat(signupState.streetError).isNull()
        assertThat(signupState.cityError).isNull()
        assertThat(signupState.zipCodeError).isNull()
    }

    @Test
    fun `when confirmPassword is not validated correctly its error state is not null`() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val confirmPassword = ""
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateSignupPasswordUseCase(password)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = confirmPasswordError
        )
        every {
            shopUseCases.validateNameUseCase(any())
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateStreetUseCase(street)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateCityUseCase(city)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateZipCodeUseCase(zipCode)
        } returns ValidationResult(isSuccessful = true)

        signupViewModel = setViewModel()
        val result = signupViewModel.isValidationSuccessful(email,password,confirmPassword,firstName,lastName,street,city,zipCode)
        val signupState = getCurrentSignupState()

        verifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(result).isFalse()
        assertThat(signupState.emailError).isNull()
        assertThat(signupState.passwordError).isNull()
        assertThat(signupState.confirmPasswordError).isEqualTo(confirmPasswordError)
        assertThat(signupState.firstNameError).isNull()
        assertThat(signupState.lastNameError).isNull()
        assertThat(signupState.streetError).isNull()
        assertThat(signupState.cityError).isNull()
        assertThat(signupState.zipCodeError).isNull()
    }

    @Test
    fun `when firstName and lastName are not validated correctly their error states are not null`() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"
        val firstName = ""
        val lastName = ""
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateSignupPasswordUseCase(password)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateNameUseCase(any())
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = fieldEmptyError
        )
        every {
            shopUseCases.validateStreetUseCase(street)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateCityUseCase(city)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateZipCodeUseCase(zipCode)
        } returns ValidationResult(isSuccessful = true)

        signupViewModel = setViewModel()
        val result = signupViewModel.isValidationSuccessful(email,password,confirmPassword,firstName,lastName,street,city,zipCode)
        val signupState = getCurrentSignupState()

        verifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(result).isFalse()
        assertThat(signupState.emailError).isNull()
        assertThat(signupState.passwordError).isNull()
        assertThat(signupState.confirmPasswordError).isNull()
        assertThat(signupState.firstNameError).isEqualTo(fieldEmptyError)
        assertThat(signupState.lastNameError).isEqualTo(fieldEmptyError)
        assertThat(signupState.streetError).isNull()
        assertThat(signupState.cityError).isNull()
        assertThat(signupState.zipCodeError).isNull()
    }

    @Test
    fun `when street is not validated correctly its error state is not null`() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = ""
        val city = "Warsaw"
        val zipCode = "12-345"

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateSignupPasswordUseCase(password)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateNameUseCase(any())
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateStreetUseCase(street)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = streetEmptyError
        )
        every {
            shopUseCases.validateCityUseCase(city)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateZipCodeUseCase(zipCode)
        } returns ValidationResult(isSuccessful = true)

        signupViewModel = setViewModel()
        val result = signupViewModel.isValidationSuccessful(email,password,confirmPassword,firstName,lastName,street,city,zipCode)
        val signupState = getCurrentSignupState()

        verifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(result).isFalse()
        assertThat(signupState.emailError).isNull()
        assertThat(signupState.passwordError).isNull()
        assertThat(signupState.confirmPasswordError).isNull()
        assertThat(signupState.firstNameError).isNull()
        assertThat(signupState.lastNameError).isNull()
        assertThat(signupState.streetError).isEqualTo(streetEmptyError)
        assertThat(signupState.cityError).isNull()
        assertThat(signupState.zipCodeError).isNull()
    }

    @Test
    fun `when city is not validated correctly its error state is not null`() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = ""
        val zipCode = "12-345"

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateSignupPasswordUseCase(password)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateNameUseCase(any())
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateStreetUseCase(street)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateCityUseCase(city)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = cityEmptyError
        )
        every {
            shopUseCases.validateZipCodeUseCase(zipCode)
        } returns ValidationResult(isSuccessful = true)

        signupViewModel = setViewModel()
        val result = signupViewModel.isValidationSuccessful(email,password,confirmPassword,firstName,lastName,street,city,zipCode)
        val signupState = getCurrentSignupState()

        verifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(result).isFalse()
        assertThat(signupState.emailError).isNull()
        assertThat(signupState.passwordError).isNull()
        assertThat(signupState.confirmPasswordError).isNull()
        assertThat(signupState.firstNameError).isNull()
        assertThat(signupState.lastNameError).isNull()
        assertThat(signupState.streetError).isNull()
        assertThat(signupState.cityError).isEqualTo(cityEmptyError)
        assertThat(signupState.zipCodeError).isNull()
    }

    @Test
    fun `when zip code is not validated correctly its error state is not null`() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = ""

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateSignupPasswordUseCase(password)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateNameUseCase(any())
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateStreetUseCase(street)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateCityUseCase(city)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateZipCodeUseCase(zipCode)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = zipCodeEmptyError
        )

        signupViewModel = setViewModel()
        val result = signupViewModel.isValidationSuccessful(email,password,confirmPassword,firstName,lastName,street,city,zipCode)
        val signupState = getCurrentSignupState()

        verifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(result).isFalse()
        assertThat(signupState.emailError).isNull()
        assertThat(signupState.passwordError).isNull()
        assertThat(signupState.confirmPasswordError).isNull()
        assertThat(signupState.firstNameError).isNull()
        assertThat(signupState.lastNameError).isNull()
        assertThat(signupState.streetError).isNull()
        assertThat(signupState.cityError).isNull()
        assertThat(signupState.zipCodeError).isEqualTo(zipCodeEmptyError)
    }

    @Test
    fun `when all fields are not validated correctly their error states are not null`() {
        val email = ""
        val password = ""
        val confirmPassword = "Qwerty1+"
        val firstName = ""
        val lastName = ""
        val street = ""
        val city = ""
        val zipCode = ""

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = emailEmptyError
        )
        every {
            shopUseCases.validateSignupPasswordUseCase(password)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = passwordEmptyError
        )
        every {
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = confirmPasswordError
        )
        every {
            shopUseCases.validateNameUseCase(any())
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = fieldEmptyError
        )
        every {
            shopUseCases.validateStreetUseCase(street)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = streetEmptyError
        )
        every {
            shopUseCases.validateCityUseCase(city)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = cityEmptyError
        )
        every {
            shopUseCases.validateZipCodeUseCase(zipCode)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = zipCodeEmptyError
        )

        signupViewModel = setViewModel()
        val result = signupViewModel.isValidationSuccessful(email,password,confirmPassword,firstName,lastName,street,city,zipCode)
        val signupState = getCurrentSignupState()

        verifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(result).isFalse()
        assertThat(signupState.emailError).isEqualTo(emailEmptyError)
        assertThat(signupState.passwordError).isEqualTo(passwordEmptyError)
        assertThat(signupState.confirmPasswordError).isEqualTo(confirmPasswordError)
        assertThat(signupState.firstNameError).isEqualTo(fieldEmptyError)
        assertThat(signupState.lastNameError).isEqualTo(fieldEmptyError)
        assertThat(signupState.streetError).isEqualTo(streetEmptyError)
        assertThat(signupState.cityError).isEqualTo(cityEmptyError)
        assertThat(signupState.zipCodeError).isEqualTo(zipCodeEmptyError)
    }

    @Test
    fun `addUser is successful`() {
        coEvery {
            shopUseCases.addUserUseCase(user)
        } returns flowOf(Resource.Success(true))

        signupViewModel = setViewModel()
        setSignupState(
            email = "email@email.com",
            password = "Qwerty1+",
            confirmPassword = "Qwerty1+",
            firstName = "John",
            lastName = "Smith",
            street = "Street 1",
            city = "Warsaw",
            zipCode = "12-345"
        )
        signupViewModel.addUser()
        val loadingState = getCurrentSignupState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.addUserUseCase(user)
        }
        assertThat(loadingState).isFalse()
    }

    @Test
    fun `addUser is loading`() {
        coEvery {
            shopUseCases.addUserUseCase(user)
        } returns flowOf(Resource.Loading(true))

        signupViewModel = setViewModel()
        setSignupState(
            email = "email@email.com",
            password = "Qwerty1+",
            confirmPassword = "Qwerty1+",
            firstName = "John",
            lastName = "Smith",
            street = "Street 1",
            city = "Warsaw",
            zipCode = "12-345"
        )
        val initialLoginState = getCurrentSignupState().isLoading
        signupViewModel.addUser()
        val loadingState = getCurrentSignupState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.addUserUseCase(user)
        }
        assertThat(initialLoginState).isFalse()
        assertThat(loadingState).isTrue()
    }

    @Test
    fun `signup is successful`() {
        val email = "email@email.com"
        val password = "Qwerty1+"

        coEvery {
            shopUseCases.signupUseCase(email,password)
        } returns flowOf(Resource.Success(firebaseUser))
        coEvery {
            shopUseCases.addUserUseCase(user)
        } returns flowOf(Resource.Success(true))

        signupViewModel = setViewModel()
        setSignupState(
            email = "email@email.com",
            password = "Qwerty1+",
            confirmPassword = "Qwerty1+",
            firstName = "John",
            lastName = "Smith",
            street = "Street 1",
            city = "Warsaw",
            zipCode = "12-345"
        )
        signupViewModel.signup(email,password)
        val loadingState = getCurrentSignupState().isLoading

        coVerifySequence {
            shopUseCases.signupUseCase(email,password)
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.addUserUseCase(user)
        }
        assertThat(loadingState).isFalse()
    }

    @Test
    fun `signup is loading`() {
        val email = "email@email.com"
        val password = "Qwerty1+"

        coEvery {
            shopUseCases.signupUseCase(email,password)
        } returns flowOf(Resource.Loading(true))

        signupViewModel = setViewModel()
        val initialLoginState = getCurrentSignupState().isLoading
        signupViewModel.signup(email,password)
        val loadingState = getCurrentSignupState().isLoading

        coVerify {
            shopUseCases.signupUseCase(email,password)
        }
        assertThat(initialLoginState).isFalse()
        assertThat(loadingState).isTrue()
    }

    @Test
    fun `signup is not successful and returned error`() {
        val email = "email@email.com"
        val password = "Qwerty1+"

        coEvery {
            shopUseCases.signupUseCase(email,password)
        } returns flowOf(Resource.Error("Error"))

        signupViewModel = setViewModel()
        signupViewModel.signup(email,password)
        val signupState = getCurrentSignupState()

        coVerify {
            shopUseCases.signupUseCase(email,password)
        }
        assertThat(signupState.emailError).isNull()
        assertThat(signupState.passwordError).isNull()
    }

    @Test
    fun `event signup is successful`() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateSignupPasswordUseCase(password)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateNameUseCase(any())
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateStreetUseCase(street)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateCityUseCase(city)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateZipCodeUseCase(zipCode)
        } returns ValidationResult(isSuccessful = true)
        coEvery {
            shopUseCases.signupUseCase(email,password)
        } returns flowOf(Resource.Success(firebaseUser))
        coEvery {
            shopUseCases.addUserUseCase(user)
        } returns flowOf(Resource.Success(true))

        signupViewModel = setViewModel()
        signupViewModel.onEvent(SignupEvent.EnteredEmail(email))
        signupViewModel.onEvent(SignupEvent.EnteredPassword(password))
        signupViewModel.onEvent(SignupEvent.EnteredConfirmPassword(confirmPassword))
        signupViewModel.onEvent(SignupEvent.EnteredFirstName(firstName))
        signupViewModel.onEvent(SignupEvent.EnteredLastName(lastName))
        signupViewModel.onEvent(SignupEvent.EnteredStreet(street))
        signupViewModel.onEvent(SignupEvent.EnteredCity(city))
        signupViewModel.onEvent(SignupEvent.EnteredZipCode(zipCode))

        signupViewModel.onEvent(SignupEvent.Signup)
        val signupState = getCurrentSignupState()

        coVerifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
            shopUseCases.signupUseCase(email,password)
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.addUserUseCase(user)
        }
        assertThat(signupState.email).isEqualTo(email)
        assertThat(signupState.password).isEqualTo(password)
        assertThat(signupState.confirmPassword).isEqualTo(confirmPassword)
        assertThat(signupState.firstName).isEqualTo(firstName)
        assertThat(signupState.lastName).isEqualTo(lastName)
        assertThat(signupState.street).isEqualTo(street)
        assertThat(signupState.city).isEqualTo(city)
        assertThat(signupState.zipCode).isEqualTo(zipCode)
        assertThat(signupState.emailError).isNull()
        assertThat(signupState.passwordError).isNull()
        assertThat(signupState.confirmPasswordError).isNull()
        assertThat(signupState.firstNameError).isNull()
        assertThat(signupState.lastNameError).isNull()
        assertThat(signupState.streetError).isNull()
        assertThat(signupState.cityError).isNull()
        assertThat(signupState.zipCodeError).isNull()
    }

    @Test
    fun `event signup is not successful when auth signup returns error`() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateSignupPasswordUseCase(password)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateNameUseCase(any())
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateStreetUseCase(street)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateCityUseCase(city)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateZipCodeUseCase(zipCode)
        } returns ValidationResult(isSuccessful = true)
        coEvery {
            shopUseCases.signupUseCase(email,password)
        } returns flowOf(Resource.Error("Error"))

        signupViewModel = setViewModel()
        signupViewModel.onEvent(SignupEvent.EnteredEmail(email))
        signupViewModel.onEvent(SignupEvent.EnteredPassword(password))
        signupViewModel.onEvent(SignupEvent.EnteredConfirmPassword(confirmPassword))
        signupViewModel.onEvent(SignupEvent.EnteredFirstName(firstName))
        signupViewModel.onEvent(SignupEvent.EnteredLastName(lastName))
        signupViewModel.onEvent(SignupEvent.EnteredStreet(street))
        signupViewModel.onEvent(SignupEvent.EnteredCity(city))
        signupViewModel.onEvent(SignupEvent.EnteredZipCode(zipCode))
        signupViewModel.onEvent(SignupEvent.Signup)
        val signupState = getCurrentSignupState()

        coVerifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
            shopUseCases.signupUseCase(email,password)
        }
        assertThat(signupState.email).isEqualTo(email)
        assertThat(signupState.password).isEqualTo(password)
        assertThat(signupState.confirmPassword).isEqualTo(confirmPassword)
        assertThat(signupState.firstName).isEqualTo(firstName)
        assertThat(signupState.lastName).isEqualTo(lastName)
        assertThat(signupState.street).isEqualTo(street)
        assertThat(signupState.city).isEqualTo(city)
        assertThat(signupState.zipCode).isEqualTo(zipCode)
        assertThat(signupState.emailError).isNull()
        assertThat(signupState.passwordError).isNull()
        assertThat(signupState.confirmPasswordError).isNull()
        assertThat(signupState.firstNameError).isNull()
        assertThat(signupState.lastNameError).isNull()
        assertThat(signupState.streetError).isNull()
        assertThat(signupState.cityError).isNull()
        assertThat(signupState.zipCodeError).isNull()
    }

    @Test
    fun `event signup is not successful when email is blank`() {
        val email = ""
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = emailEmptyError
        )
        every {
            shopUseCases.validateSignupPasswordUseCase(password)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateNameUseCase(any())
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateStreetUseCase(street)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateCityUseCase(city)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateZipCodeUseCase(zipCode)
        } returns ValidationResult(isSuccessful = true)

        signupViewModel = setViewModel()
        signupViewModel.onEvent(SignupEvent.EnteredEmail(email))
        signupViewModel.onEvent(SignupEvent.EnteredPassword(password))
        signupViewModel.onEvent(SignupEvent.EnteredConfirmPassword(confirmPassword))
        signupViewModel.onEvent(SignupEvent.EnteredFirstName(firstName))
        signupViewModel.onEvent(SignupEvent.EnteredLastName(lastName))
        signupViewModel.onEvent(SignupEvent.EnteredStreet(street))
        signupViewModel.onEvent(SignupEvent.EnteredCity(city))
        signupViewModel.onEvent(SignupEvent.EnteredZipCode(zipCode))
        signupViewModel.onEvent(SignupEvent.Signup)
        val signupState = getCurrentSignupState()

        coVerifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(signupState.email).isEqualTo(email)
        assertThat(signupState.password).isEqualTo(password)
        assertThat(signupState.confirmPassword).isEqualTo(confirmPassword)
        assertThat(signupState.firstName).isEqualTo(firstName)
        assertThat(signupState.lastName).isEqualTo(lastName)
        assertThat(signupState.street).isEqualTo(street)
        assertThat(signupState.city).isEqualTo(city)
        assertThat(signupState.zipCode).isEqualTo(zipCode)
        assertThat(signupState.emailError).isEqualTo(emailEmptyError)
        assertThat(signupState.passwordError).isNull()
        assertThat(signupState.confirmPasswordError).isNull()
        assertThat(signupState.firstNameError).isNull()
        assertThat(signupState.lastNameError).isNull()
        assertThat(signupState.streetError).isNull()
        assertThat(signupState.cityError).isNull()
        assertThat(signupState.zipCodeError).isNull()
    }

    @Test
    fun `event signup is not successful when password is blank`() {
        val email = "email@email.com"
        val password = ""
        val confirmPassword = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateSignupPasswordUseCase(password)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = passwordEmptyError
        )
        every {
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateNameUseCase(any())
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateStreetUseCase(street)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateCityUseCase(city)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateZipCodeUseCase(zipCode)
        } returns ValidationResult(isSuccessful = true)

        signupViewModel = setViewModel()
        signupViewModel.onEvent(SignupEvent.EnteredEmail(email))
        signupViewModel.onEvent(SignupEvent.EnteredPassword(password))
        signupViewModel.onEvent(SignupEvent.EnteredConfirmPassword(confirmPassword))
        signupViewModel.onEvent(SignupEvent.EnteredFirstName(firstName))
        signupViewModel.onEvent(SignupEvent.EnteredLastName(lastName))
        signupViewModel.onEvent(SignupEvent.EnteredStreet(street))
        signupViewModel.onEvent(SignupEvent.EnteredCity(city))
        signupViewModel.onEvent(SignupEvent.EnteredZipCode(zipCode))
        signupViewModel.onEvent(SignupEvent.Signup)
        val signupState = getCurrentSignupState()

        coVerifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(signupState.email).isEqualTo(email)
        assertThat(signupState.password).isEqualTo(password)
        assertThat(signupState.confirmPassword).isEqualTo(confirmPassword)
        assertThat(signupState.firstName).isEqualTo(firstName)
        assertThat(signupState.lastName).isEqualTo(lastName)
        assertThat(signupState.street).isEqualTo(street)
        assertThat(signupState.city).isEqualTo(city)
        assertThat(signupState.zipCode).isEqualTo(zipCode)
        assertThat(signupState.emailError).isNull()
        assertThat(signupState.passwordError).isEqualTo(passwordEmptyError)
        assertThat(signupState.confirmPasswordError).isNull()
        assertThat(signupState.firstNameError).isNull()
        assertThat(signupState.lastNameError).isNull()
        assertThat(signupState.streetError).isNull()
        assertThat(signupState.cityError).isNull()
        assertThat(signupState.zipCodeError).isNull()
    }

    @Test
    fun `event signup is not successful when confirmPassword is blank`() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val confirmPassword = ""
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateSignupPasswordUseCase(password)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = confirmPasswordError
        )
        every {
            shopUseCases.validateNameUseCase(any())
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateStreetUseCase(street)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateCityUseCase(city)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateZipCodeUseCase(zipCode)
        } returns ValidationResult(isSuccessful = true)

        signupViewModel = setViewModel()
        signupViewModel.onEvent(SignupEvent.EnteredEmail(email))
        signupViewModel.onEvent(SignupEvent.EnteredPassword(password))
        signupViewModel.onEvent(SignupEvent.EnteredConfirmPassword(confirmPassword))
        signupViewModel.onEvent(SignupEvent.EnteredFirstName(firstName))
        signupViewModel.onEvent(SignupEvent.EnteredLastName(lastName))
        signupViewModel.onEvent(SignupEvent.EnteredStreet(street))
        signupViewModel.onEvent(SignupEvent.EnteredCity(city))
        signupViewModel.onEvent(SignupEvent.EnteredZipCode(zipCode))
        signupViewModel.onEvent(SignupEvent.Signup)
        val signupState = getCurrentSignupState()

        coVerifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(signupState.email).isEqualTo(email)
        assertThat(signupState.password).isEqualTo(password)
        assertThat(signupState.confirmPassword).isEqualTo(confirmPassword)
        assertThat(signupState.firstName).isEqualTo(firstName)
        assertThat(signupState.lastName).isEqualTo(lastName)
        assertThat(signupState.street).isEqualTo(street)
        assertThat(signupState.city).isEqualTo(city)
        assertThat(signupState.zipCode).isEqualTo(zipCode)
        assertThat(signupState.emailError).isNull()
        assertThat(signupState.passwordError).isNull()
        assertThat(signupState.confirmPasswordError).isEqualTo(confirmPasswordError)
        assertThat(signupState.firstNameError).isNull()
        assertThat(signupState.lastNameError).isNull()
        assertThat(signupState.streetError).isNull()
        assertThat(signupState.cityError).isNull()
        assertThat(signupState.zipCodeError).isNull()
    }

    @Test
    fun `event signup is not successful when firstName and lastName are blank`() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"
        val firstName = ""
        val lastName = ""
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = "12-345"

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateSignupPasswordUseCase(password)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateNameUseCase(any())
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = fieldEmptyError
        )
        every {
            shopUseCases.validateStreetUseCase(street)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateCityUseCase(city)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateZipCodeUseCase(zipCode)
        } returns ValidationResult(isSuccessful = true)

        signupViewModel = setViewModel()
        signupViewModel.onEvent(SignupEvent.EnteredEmail(email))
        signupViewModel.onEvent(SignupEvent.EnteredPassword(password))
        signupViewModel.onEvent(SignupEvent.EnteredConfirmPassword(confirmPassword))
        signupViewModel.onEvent(SignupEvent.EnteredFirstName(firstName))
        signupViewModel.onEvent(SignupEvent.EnteredLastName(lastName))
        signupViewModel.onEvent(SignupEvent.EnteredStreet(street))
        signupViewModel.onEvent(SignupEvent.EnteredCity(city))
        signupViewModel.onEvent(SignupEvent.EnteredZipCode(zipCode))
        signupViewModel.onEvent(SignupEvent.Signup)
        val signupState = getCurrentSignupState()

        coVerifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(signupState.email).isEqualTo(email)
        assertThat(signupState.password).isEqualTo(password)
        assertThat(signupState.confirmPassword).isEqualTo(confirmPassword)
        assertThat(signupState.firstName).isEqualTo(firstName)
        assertThat(signupState.lastName).isEqualTo(lastName)
        assertThat(signupState.street).isEqualTo(street)
        assertThat(signupState.city).isEqualTo(city)
        assertThat(signupState.zipCode).isEqualTo(zipCode)
        assertThat(signupState.emailError).isNull()
        assertThat(signupState.passwordError).isNull()
        assertThat(signupState.confirmPasswordError).isNull()
        assertThat(signupState.firstNameError).isEqualTo(fieldEmptyError)
        assertThat(signupState.lastNameError).isEqualTo(fieldEmptyError)
        assertThat(signupState.streetError).isNull()
        assertThat(signupState.cityError).isNull()
        assertThat(signupState.zipCodeError).isNull()
    }

    @Test
    fun `event signup is not successful when street is blank`() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = ""
        val city = "Warsaw"
        val zipCode = "12-345"

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateSignupPasswordUseCase(password)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateNameUseCase(any())
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateStreetUseCase(street)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = streetEmptyError
        )
        every {
            shopUseCases.validateCityUseCase(city)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateZipCodeUseCase(zipCode)
        } returns ValidationResult(isSuccessful = true)

        signupViewModel = setViewModel()
        signupViewModel.onEvent(SignupEvent.EnteredEmail(email))
        signupViewModel.onEvent(SignupEvent.EnteredPassword(password))
        signupViewModel.onEvent(SignupEvent.EnteredConfirmPassword(confirmPassword))
        signupViewModel.onEvent(SignupEvent.EnteredFirstName(firstName))
        signupViewModel.onEvent(SignupEvent.EnteredLastName(lastName))
        signupViewModel.onEvent(SignupEvent.EnteredStreet(street))
        signupViewModel.onEvent(SignupEvent.EnteredCity(city))
        signupViewModel.onEvent(SignupEvent.EnteredZipCode(zipCode))
        signupViewModel.onEvent(SignupEvent.Signup)
        val signupState = getCurrentSignupState()

        coVerifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(signupState.email).isEqualTo(email)
        assertThat(signupState.password).isEqualTo(password)
        assertThat(signupState.confirmPassword).isEqualTo(confirmPassword)
        assertThat(signupState.firstName).isEqualTo(firstName)
        assertThat(signupState.lastName).isEqualTo(lastName)
        assertThat(signupState.street).isEqualTo(street)
        assertThat(signupState.city).isEqualTo(city)
        assertThat(signupState.zipCode).isEqualTo(zipCode)
        assertThat(signupState.emailError).isNull()
        assertThat(signupState.passwordError).isNull()
        assertThat(signupState.confirmPasswordError).isNull()
        assertThat(signupState.firstNameError).isNull()
        assertThat(signupState.lastNameError).isNull()
        assertThat(signupState.streetError).isEqualTo(streetEmptyError)
        assertThat(signupState.cityError).isNull()
        assertThat(signupState.zipCodeError).isNull()
    }

    @Test
    fun `event signup is not successful when city is blank`() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = ""
        val zipCode = "12-345"

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateSignupPasswordUseCase(password)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateNameUseCase(any())
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateStreetUseCase(street)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateCityUseCase(city)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = cityEmptyError
        )
        every {
            shopUseCases.validateZipCodeUseCase(zipCode)
        } returns ValidationResult(isSuccessful = true)

        signupViewModel = setViewModel()
        signupViewModel.onEvent(SignupEvent.EnteredEmail(email))
        signupViewModel.onEvent(SignupEvent.EnteredPassword(password))
        signupViewModel.onEvent(SignupEvent.EnteredConfirmPassword(confirmPassword))
        signupViewModel.onEvent(SignupEvent.EnteredFirstName(firstName))
        signupViewModel.onEvent(SignupEvent.EnteredLastName(lastName))
        signupViewModel.onEvent(SignupEvent.EnteredStreet(street))
        signupViewModel.onEvent(SignupEvent.EnteredCity(city))
        signupViewModel.onEvent(SignupEvent.EnteredZipCode(zipCode))
        signupViewModel.onEvent(SignupEvent.Signup)
        val signupState = getCurrentSignupState()

        coVerifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(signupState.email).isEqualTo(email)
        assertThat(signupState.password).isEqualTo(password)
        assertThat(signupState.confirmPassword).isEqualTo(confirmPassword)
        assertThat(signupState.firstName).isEqualTo(firstName)
        assertThat(signupState.lastName).isEqualTo(lastName)
        assertThat(signupState.street).isEqualTo(street)
        assertThat(signupState.city).isEqualTo(city)
        assertThat(signupState.zipCode).isEqualTo(zipCode)
        assertThat(signupState.emailError).isNull()
        assertThat(signupState.passwordError).isNull()
        assertThat(signupState.confirmPasswordError).isNull()
        assertThat(signupState.firstNameError).isNull()
        assertThat(signupState.lastNameError).isNull()
        assertThat(signupState.streetError).isNull()
        assertThat(signupState.cityError).isEqualTo(cityEmptyError)
        assertThat(signupState.zipCodeError).isNull()
    }

    @Test
    fun `event signup is not successful when zip code is blank`() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"
        val street = "Street 1"
        val city = "Warsaw"
        val zipCode = ""

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateSignupPasswordUseCase(password)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateNameUseCase(any())
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateStreetUseCase(street)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateCityUseCase(city)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateZipCodeUseCase(zipCode)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = zipCodeEmptyError
        )

        signupViewModel = setViewModel()
        signupViewModel.onEvent(SignupEvent.EnteredEmail(email))
        signupViewModel.onEvent(SignupEvent.EnteredPassword(password))
        signupViewModel.onEvent(SignupEvent.EnteredConfirmPassword(confirmPassword))
        signupViewModel.onEvent(SignupEvent.EnteredFirstName(firstName))
        signupViewModel.onEvent(SignupEvent.EnteredLastName(lastName))
        signupViewModel.onEvent(SignupEvent.EnteredStreet(street))
        signupViewModel.onEvent(SignupEvent.EnteredCity(city))
        signupViewModel.onEvent(SignupEvent.EnteredZipCode(zipCode))
        signupViewModel.onEvent(SignupEvent.Signup)
        val signupState = getCurrentSignupState()

        coVerifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(signupState.email).isEqualTo(email)
        assertThat(signupState.password).isEqualTo(password)
        assertThat(signupState.confirmPassword).isEqualTo(confirmPassword)
        assertThat(signupState.firstName).isEqualTo(firstName)
        assertThat(signupState.lastName).isEqualTo(lastName)
        assertThat(signupState.street).isEqualTo(street)
        assertThat(signupState.city).isEqualTo(city)
        assertThat(signupState.zipCode).isEqualTo(zipCode)
        assertThat(signupState.emailError).isNull()
        assertThat(signupState.passwordError).isNull()
        assertThat(signupState.confirmPasswordError).isNull()
        assertThat(signupState.firstNameError).isNull()
        assertThat(signupState.lastNameError).isNull()
        assertThat(signupState.streetError).isNull()
        assertThat(signupState.cityError).isNull()
        assertThat(signupState.zipCodeError).isEqualTo(zipCodeEmptyError)
    }

    @Test
    fun `event signup is not successful when all fields are incorrect`() {
        val email = ""
        val password = ""
        val confirmPassword = "Qwerty1+"
        val firstName = ""
        val lastName = ""
        val street = ""
        val city = ""
        val zipCode = ""

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = emailEmptyError
        )
        every {
            shopUseCases.validateSignupPasswordUseCase(password)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = passwordEmptyError
        )
        every {
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = confirmPasswordError
        )
        every {
            shopUseCases.validateNameUseCase(any())
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = fieldEmptyError
        )
        every {
            shopUseCases.validateStreetUseCase(street)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = streetEmptyError
        )
        every {
            shopUseCases.validateCityUseCase(city)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = cityEmptyError
        )
        every {
            shopUseCases.validateZipCodeUseCase(zipCode)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = zipCodeEmptyError
        )

        signupViewModel = setViewModel()
        signupViewModel.onEvent(SignupEvent.EnteredEmail(email))
        signupViewModel.onEvent(SignupEvent.EnteredPassword(password))
        signupViewModel.onEvent(SignupEvent.EnteredConfirmPassword(confirmPassword))
        signupViewModel.onEvent(SignupEvent.EnteredFirstName(firstName))
        signupViewModel.onEvent(SignupEvent.EnteredLastName(lastName))
        signupViewModel.onEvent(SignupEvent.EnteredStreet(street))
        signupViewModel.onEvent(SignupEvent.EnteredCity(city))
        signupViewModel.onEvent(SignupEvent.EnteredZipCode(zipCode))
        signupViewModel.onEvent(SignupEvent.Signup)
        val signupState = getCurrentSignupState()

        coVerifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(signupState.email).isEqualTo(email)
        assertThat(signupState.password).isEqualTo(password)
        assertThat(signupState.confirmPassword).isEqualTo(confirmPassword)
        assertThat(signupState.firstName).isEqualTo(firstName)
        assertThat(signupState.lastName).isEqualTo(lastName)
        assertThat(signupState.street).isEqualTo(street)
        assertThat(signupState.city).isEqualTo(city)
        assertThat(signupState.zipCode).isEqualTo(zipCode)
        assertThat(signupState.emailError).isEqualTo(emailEmptyError)
        assertThat(signupState.passwordError).isEqualTo(passwordEmptyError)
        assertThat(signupState.confirmPasswordError).isEqualTo(confirmPasswordError)
        assertThat(signupState.firstNameError).isEqualTo(fieldEmptyError)
        assertThat(signupState.lastNameError).isEqualTo(fieldEmptyError)
        assertThat(signupState.streetError).isEqualTo(streetEmptyError)
        assertThat(signupState.cityError).isEqualTo(cityEmptyError)
        assertThat(signupState.zipCodeError).isEqualTo(zipCodeEmptyError)
    }
}