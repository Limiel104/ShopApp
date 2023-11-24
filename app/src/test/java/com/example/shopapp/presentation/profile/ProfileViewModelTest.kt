package com.example.shopapp.presentation.profile

import androidx.lifecycle.SavedStateHandle
import com.example.shopapp.domain.model.Address
import com.example.shopapp.domain.model.User
import com.example.shopapp.domain.model.ValidationResult
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.presentation.common.Constants.cityEmptyError
import com.example.shopapp.presentation.common.Constants.fieldEmptyError
import com.example.shopapp.presentation.common.Constants.streetEmptyError
import com.example.shopapp.presentation.common.Constants.zipCodeEmptyError
import com.example.shopapp.util.MainDispatcherRule
import com.example.shopapp.domain.util.Resource
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var shopUseCases: ShopUseCases
    private lateinit var profileViewModel: ProfileViewModel

    @MockK
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var user: User
    private lateinit var updatedUser: User

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { savedStateHandle.get<String>("userUID") } returns "userUID"

        user = User(
            userUID = "userUID",
            firstName = "John",
            lastName = "Smith",
            address = Address(
                street = "Street 1",
                city = "Warsaw",
                zipCode = "12-345"
            ),
            points = 123
        )

        updatedUser = User(
            userUID = "userUID",
            firstName = "Bob",
            lastName = "Hall",
            address = Address(
                street = "Street 2",
                city = "Berlin",
                zipCode = "98-765"
            ),
            points = 123
        )

        coEvery { shopUseCases.getUserUseCase("userUID") } returns flowOf(Resource.Success(listOf(user)))
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    private fun setViewModel(): ProfileViewModel {
        return ProfileViewModel(savedStateHandle, shopUseCases)
    }

    private fun getCurrentProfileState(): ProfileState {
        return profileViewModel.profileState.value
    }

    @Test
    fun `event enteredFirstName sets firstName state`() {
        profileViewModel = setViewModel()

        val firstName = "Bob"
        val initialFirstName = getCurrentProfileState().firstName
        profileViewModel.onEvent(ProfileEvent.EnteredFirstName(firstName))
        val resultFirstName = getCurrentProfileState().firstName

        assertThat(initialFirstName).isEqualTo("John")
        assertThat(resultFirstName).isEqualTo(firstName)
    }

    @Test
    fun `event enteredLastName sets lastName state`() {
        profileViewModel = setViewModel()

        val lastName = "Hall"
        val initialLastName = getCurrentProfileState().lastName
        profileViewModel.onEvent(ProfileEvent.EnteredLastName(lastName))
        val resultLastName = getCurrentProfileState().lastName

        assertThat(initialLastName).isEqualTo("Smith")
        assertThat(resultLastName).isEqualTo(lastName)
    }

    @Test
    fun `event enteredStreet sets street state`() {
        profileViewModel = setViewModel()

        val street = "Street 2"
        val initialStreet = getCurrentProfileState().street
        profileViewModel.onEvent(ProfileEvent.EnteredStreet(street))
        val resultStreet = getCurrentProfileState().street

        assertThat(initialStreet).isEqualTo("Street 1")
        assertThat(resultStreet).isEqualTo(street)
    }

    @Test
    fun `event enteredCity sets city state`() {
        profileViewModel = setViewModel()

        val city = "Berlin"
        val initialCity = getCurrentProfileState().city
        profileViewModel.onEvent(ProfileEvent.EnteredCity(city))
        val resultCity = getCurrentProfileState().city

        assertThat(initialCity).isEqualTo("Warsaw")
        assertThat(resultCity).isEqualTo(city)
    }

    @Test
    fun `event enteredZipCode sets zipCode state`() {
        profileViewModel = setViewModel()

        val zipCode = "98-765"
        val initialZipCode = getCurrentProfileState().zipCode
        profileViewModel.onEvent(ProfileEvent.EnteredZipCode(zipCode))
        val resultZipCode = getCurrentProfileState().zipCode

        assertThat(initialZipCode).isEqualTo("12-345")
        assertThat(resultZipCode).isEqualTo(zipCode)
    }

    @Test
    fun `validation is successful`() {
        val firstName = "Bob"
        val lastName = "Hall"
        val street = "Street 2"
        val city = "Berlin"
        val zipCode = "98-765"

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

        profileViewModel = setViewModel()
        val result = profileViewModel.isValidationSuccessful(firstName,lastName,street,city,zipCode)
        val profileState = getCurrentProfileState()

        coVerifySequence {
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(result).isTrue()
        assertThat(profileState.firstNameError).isNull()
        assertThat(profileState.lastNameError).isNull()
        assertThat(profileState.streetError).isNull()
        assertThat(profileState.cityError).isNull()
        assertThat(profileState.zipCodeError).isNull()
    }

    @Test
    fun `when firstName and lastName are not validated correctly their error states are not null`() {
        val firstName = ""
        val lastName = ""
        val street = "Street 2"
        val city = "Berlin"
        val zipCode = "98-765"

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

        profileViewModel = setViewModel()
        val result = profileViewModel.isValidationSuccessful(firstName,lastName,street,city,zipCode)
        val profileState = getCurrentProfileState()

        coVerifySequence {
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(result).isFalse()
        assertThat(profileState.firstNameError).isEqualTo(fieldEmptyError)
        assertThat(profileState.lastNameError).isEqualTo(fieldEmptyError)
        assertThat(profileState.streetError).isNull()
        assertThat(profileState.cityError).isNull()
        assertThat(profileState.zipCodeError).isNull()
    }

    @Test
    fun `when street is not validated correctly its error state is not null`() {
        val firstName = "Bob"
        val lastName = "Hall"
        val street = ""
        val city = "Berlin"
        val zipCode = "98-765"

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

        profileViewModel = setViewModel()
        val result = profileViewModel.isValidationSuccessful(firstName,lastName,street,city,zipCode)
        val profileState = getCurrentProfileState()

        coVerifySequence {
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(result).isFalse()
        assertThat(profileState.firstNameError).isNull()
        assertThat(profileState.lastNameError).isNull()
        assertThat(profileState.streetError).isEqualTo(streetEmptyError)
        assertThat(profileState.cityError).isNull()
        assertThat(profileState.zipCodeError).isNull()
    }

    @Test
    fun `when city is not validated correctly its error state is not null`() {
        val firstName = "Bob"
        val lastName = "Hall"
        val street = "Street 2"
        val city = ""
        val zipCode = "98-765"

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

        profileViewModel = setViewModel()
        val result = profileViewModel.isValidationSuccessful(firstName,lastName,street,city,zipCode)
        val profileState = getCurrentProfileState()

        coVerifySequence {
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(result).isFalse()
        assertThat(profileState.firstNameError).isNull()
        assertThat(profileState.lastNameError).isNull()
        assertThat(profileState.streetError).isNull()
        assertThat(profileState.cityError).isEqualTo(cityEmptyError)
        assertThat(profileState.zipCodeError).isNull()
    }

    @Test
    fun `when zip code is not validated correctly its error state is not null`() {
        val firstName = "Bob"
        val lastName = "Hall"
        val street = "Street 2"
        val city = "Berlin"
        val zipCode = ""

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

        profileViewModel = setViewModel()
        val result = profileViewModel.isValidationSuccessful(firstName,lastName,street,city,zipCode)
        val profileState = getCurrentProfileState()

        coVerifySequence {
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(result).isFalse()
        assertThat(profileState.firstNameError).isNull()
        assertThat(profileState.lastNameError).isNull()
        assertThat(profileState.streetError).isNull()
        assertThat(profileState.cityError).isNull()
        assertThat(profileState.zipCodeError).isEqualTo(zipCodeEmptyError)
    }

    @Test
    fun `when all fields are not validated correctly their error states are not null`() {
        val firstName = ""
        val lastName = ""
        val street = ""
        val city = ""
        val zipCode = ""

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

        profileViewModel = setViewModel()
        val result = profileViewModel.isValidationSuccessful(firstName,lastName,street,city,zipCode)
        val profileState = getCurrentProfileState()

        coVerifySequence {
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(result).isFalse()
        assertThat(profileState.firstNameError).isEqualTo(fieldEmptyError)
        assertThat(profileState.lastNameError).isEqualTo(fieldEmptyError)
        assertThat(profileState.streetError).isEqualTo(streetEmptyError)
        assertThat(profileState.cityError).isEqualTo(cityEmptyError)
        assertThat(profileState.zipCodeError).isEqualTo(zipCodeEmptyError)
    }

    @Test
    fun `updateUser is successful`() {
        coEvery {
            shopUseCases.updateUserUseCase(updatedUser)
        } returns flowOf(Resource.Success(true))

        profileViewModel = setViewModel()
        profileViewModel.saveChanges(updatedUser)
        val loadingState = getCurrentProfileState().isLoading

        coVerifySequence {
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.updateUserUseCase(updatedUser)
        }
        assertThat(loadingState).isFalse()
    }

    @Test
    fun `updateUser is loading`() {
        coEvery {
            shopUseCases.updateUserUseCase(updatedUser)
        } returns flowOf(Resource.Loading(true))

        profileViewModel = setViewModel()
        val initialLoginState = getCurrentProfileState().isLoading
        profileViewModel.saveChanges(updatedUser)
        val loadingState = getCurrentProfileState().isLoading

        coVerifySequence {
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.updateUserUseCase(updatedUser)
        }
        assertThat(initialLoginState).isFalse()
        assertThat(loadingState).isTrue()
    }

    @Test
    fun `event save is successful`() {
        val firstName = "Bob"
        val lastName = "Hall"
        val street = "Street 2"
        val city = "Berlin"
        val zipCode = "98-765"
        val updateUserSlot = slot<User>()

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
            shopUseCases.updateUserUseCase(capture(updateUserSlot))
        } returns flowOf(Resource.Success(true))

        profileViewModel = setViewModel()
        profileViewModel.onEvent(ProfileEvent.EnteredFirstName(firstName))
        profileViewModel.onEvent(ProfileEvent.EnteredLastName(lastName))
        profileViewModel.onEvent(ProfileEvent.EnteredStreet(street))
        profileViewModel.onEvent(ProfileEvent.EnteredCity(city))
        profileViewModel.onEvent(ProfileEvent.EnteredZipCode(zipCode))

        profileViewModel.onEvent(ProfileEvent.Save)
        val profileState = getCurrentProfileState()

        coVerifySequence {
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
            shopUseCases.updateUserUseCase(any())
        }
        assertThat(profileState.firstName).isEqualTo(firstName)
        assertThat(profileState.lastName).isEqualTo(lastName)
        assertThat(profileState.street).isEqualTo(street)
        assertThat(profileState.city).isEqualTo(city)
        assertThat(profileState.zipCode).isEqualTo(zipCode)
        assertThat(profileState.firstNameError).isNull()
        assertThat(profileState.lastNameError).isNull()
        assertThat(profileState.streetError).isNull()
        assertThat(profileState.cityError).isNull()
        assertThat(profileState.zipCodeError).isNull()
        assertThat(updateUserSlot.captured).isEqualTo(updatedUser)
    }

    @Test
    fun `event save is not successful when firstName and lastName are blank`() {
        val firstName = ""
        val lastName = ""
        val street = "Street 2"
        val city = "Berlin"
        val zipCode = "98-765"

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

        profileViewModel = setViewModel()
        profileViewModel.onEvent(ProfileEvent.EnteredFirstName(firstName))
        profileViewModel.onEvent(ProfileEvent.EnteredLastName(lastName))
        profileViewModel.onEvent(ProfileEvent.EnteredStreet(street))
        profileViewModel.onEvent(ProfileEvent.EnteredCity(city))
        profileViewModel.onEvent(ProfileEvent.EnteredZipCode(zipCode))

        profileViewModel.onEvent(ProfileEvent.Save)
        val profileState = getCurrentProfileState()

        coVerifySequence {
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(profileState.firstName).isEqualTo(firstName)
        assertThat(profileState.lastName).isEqualTo(lastName)
        assertThat(profileState.street).isEqualTo(street)
        assertThat(profileState.city).isEqualTo(city)
        assertThat(profileState.zipCode).isEqualTo(zipCode)
        assertThat(profileState.firstNameError).isEqualTo(fieldEmptyError)
        assertThat(profileState.lastNameError).isEqualTo(fieldEmptyError)
        assertThat(profileState.streetError).isNull()
        assertThat(profileState.cityError).isNull()
        assertThat(profileState.zipCodeError).isNull()
    }

    @Test
    fun `event save is not successful when street is blank`() {
        val firstName = "Bob"
        val lastName = "Hall"
        val street = "Street 2"
        val city = "Berlin"
        val zipCode = "98-765"

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

        profileViewModel = setViewModel()
        profileViewModel.onEvent(ProfileEvent.EnteredFirstName(firstName))
        profileViewModel.onEvent(ProfileEvent.EnteredLastName(lastName))
        profileViewModel.onEvent(ProfileEvent.EnteredStreet(street))
        profileViewModel.onEvent(ProfileEvent.EnteredCity(city))
        profileViewModel.onEvent(ProfileEvent.EnteredZipCode(zipCode))

        profileViewModel.onEvent(ProfileEvent.Save)
        val profileState = getCurrentProfileState()

        coVerifySequence {
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(profileState.firstName).isEqualTo(firstName)
        assertThat(profileState.lastName).isEqualTo(lastName)
        assertThat(profileState.street).isEqualTo(street)
        assertThat(profileState.city).isEqualTo(city)
        assertThat(profileState.zipCode).isEqualTo(zipCode)
        assertThat(profileState.firstNameError).isNull()
        assertThat(profileState.lastNameError).isNull()
        assertThat(profileState.streetError).isEqualTo(streetEmptyError)
        assertThat(profileState.cityError).isNull()
        assertThat(profileState.zipCodeError).isNull()
    }

    @Test
    fun `event save is not successful when city is blank`() {
        val firstName = "Bob"
        val lastName = "Hall"
        val street = "Street 2"
        val city = "Berlin"
        val zipCode = "98-765"

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

        profileViewModel = setViewModel()
        profileViewModel.onEvent(ProfileEvent.EnteredFirstName(firstName))
        profileViewModel.onEvent(ProfileEvent.EnteredLastName(lastName))
        profileViewModel.onEvent(ProfileEvent.EnteredStreet(street))
        profileViewModel.onEvent(ProfileEvent.EnteredCity(city))
        profileViewModel.onEvent(ProfileEvent.EnteredZipCode(zipCode))

        profileViewModel.onEvent(ProfileEvent.Save)
        val profileState = getCurrentProfileState()

        coVerifySequence {
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(profileState.firstName).isEqualTo(firstName)
        assertThat(profileState.lastName).isEqualTo(lastName)
        assertThat(profileState.street).isEqualTo(street)
        assertThat(profileState.city).isEqualTo(city)
        assertThat(profileState.zipCode).isEqualTo(zipCode)
        assertThat(profileState.firstNameError).isNull()
        assertThat(profileState.lastNameError).isNull()
        assertThat(profileState.streetError).isNull()
        assertThat(profileState.cityError).isEqualTo(cityEmptyError)
        assertThat(profileState.zipCodeError).isNull()
    }

    @Test
    fun `event save is not successful when zip code is blank`() {
        val firstName = "Bob"
        val lastName = "Hall"
        val street = "Street 2"
        val city = "Berlin"
        val zipCode = "98-765"

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

        profileViewModel = setViewModel()
        profileViewModel.onEvent(ProfileEvent.EnteredFirstName(firstName))
        profileViewModel.onEvent(ProfileEvent.EnteredLastName(lastName))
        profileViewModel.onEvent(ProfileEvent.EnteredStreet(street))
        profileViewModel.onEvent(ProfileEvent.EnteredCity(city))
        profileViewModel.onEvent(ProfileEvent.EnteredZipCode(zipCode))

        profileViewModel.onEvent(ProfileEvent.Save)
        val profileState = getCurrentProfileState()

        coVerifySequence {
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(profileState.firstName).isEqualTo(firstName)
        assertThat(profileState.lastName).isEqualTo(lastName)
        assertThat(profileState.street).isEqualTo(street)
        assertThat(profileState.city).isEqualTo(city)
        assertThat(profileState.zipCode).isEqualTo(zipCode)
        assertThat(profileState.firstNameError).isNull()
        assertThat(profileState.lastNameError).isNull()
        assertThat(profileState.streetError).isNull()
        assertThat(profileState.cityError).isNull()
        assertThat(profileState.zipCodeError).isEqualTo(zipCodeEmptyError)
    }

    @Test
    fun `event save is not successful when all fields are incorrect`() {
        val firstName = "Bob"
        val lastName = "Hall"
        val street = "Street 2"
        val city = "Berlin"
        val zipCode = "98-765"

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

        profileViewModel = setViewModel()
        profileViewModel.onEvent(ProfileEvent.EnteredFirstName(firstName))
        profileViewModel.onEvent(ProfileEvent.EnteredLastName(lastName))
        profileViewModel.onEvent(ProfileEvent.EnteredStreet(street))
        profileViewModel.onEvent(ProfileEvent.EnteredCity(city))
        profileViewModel.onEvent(ProfileEvent.EnteredZipCode(zipCode))

        profileViewModel.onEvent(ProfileEvent.Save)
        val profileState = getCurrentProfileState()

        coVerifySequence {
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.validateNameUseCase(firstName)
            shopUseCases.validateNameUseCase(lastName)
            shopUseCases.validateStreetUseCase(street)
            shopUseCases.validateCityUseCase(city)
            shopUseCases.validateZipCodeUseCase(zipCode)
        }
        assertThat(profileState.firstName).isEqualTo(firstName)
        assertThat(profileState.lastName).isEqualTo(lastName)
        assertThat(profileState.street).isEqualTo(street)
        assertThat(profileState.city).isEqualTo(city)
        assertThat(profileState.zipCode).isEqualTo(zipCode)
        assertThat(profileState.firstNameError).isEqualTo(fieldEmptyError)
        assertThat(profileState.lastNameError).isEqualTo(fieldEmptyError)
        assertThat(profileState.streetError).isEqualTo(streetEmptyError)
        assertThat(profileState.cityError).isEqualTo(cityEmptyError)
        assertThat(profileState.zipCodeError).isEqualTo(zipCodeEmptyError)
    }
}