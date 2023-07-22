package com.example.shopapp.presentation.signup

import com.example.shopapp.domain.model.ValidationResult
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.Constants.confirmPasswordError
import com.example.shopapp.util.Constants.emailEmptyError
import com.example.shopapp.util.Constants.passwordEmptyError
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
    private lateinit var user: FirebaseUser

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.signupViewModel = SignupViewModel(shopUseCases)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    private fun getCurrentSignupState(): SignupState {
        return signupViewModel.signupState.value
    }

    @Test
    fun `event enteredEmail sets email state`() {
        val email = "email@email.com"
        val initialEmailState = getCurrentSignupState().email

        signupViewModel.onEvent(SignupEvent.EnteredEmail(email))

        val resultEmail = getCurrentSignupState().email

        assertThat(initialEmailState).isEqualTo("")
        assertThat(resultEmail).isEqualTo(email)
    }

    @Test
    fun `event enteredPassword sets password state`() {
        val password = "Qwerty1+"
        val initialPasswordState = getCurrentSignupState().password

        signupViewModel.onEvent(SignupEvent.EnteredPassword(password))

        val resultPassword = getCurrentSignupState().password

        assertThat(initialPasswordState).isEqualTo("")
        assertThat(resultPassword).isEqualTo(password)
    }

    @Test
    fun `event enteredPassword sets confirmPassword state`() {
        val password = "Qwerty1+"
        val initialConfirmPasswordState = getCurrentSignupState().password

        signupViewModel.onEvent(SignupEvent.EnteredConfirmPassword(password))

        val resultConfirmPassword = getCurrentSignupState().confirmPassword

        assertThat(initialConfirmPasswordState).isEqualTo("")
        assertThat(resultConfirmPassword).isEqualTo(password)
    }

    @Test
    fun `validation is successful`() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateSignupPasswordUseCase(password)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        } returns ValidationResult(isSuccessful = true)

        val result = signupViewModel.isValidationSuccessful(email,password,confirmPassword)
        val signupState = getCurrentSignupState()

        verifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        }
        assertThat(result).isTrue()
        assertThat(signupState.emailError).isNull()
        assertThat(signupState.passwordError).isNull()
        assertThat(signupState.confirmPasswordError).isNull()
    }

    @Test
    fun `when email is not validated correct error is set in state`() {
        val email = ""
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"

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

        val result = signupViewModel.isValidationSuccessful(email,password,confirmPassword)
        val signupState = getCurrentSignupState()

        verifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        }
        assertThat(result).isFalse()
        assertThat(signupState.emailError).isEqualTo(emailEmptyError)
        assertThat(signupState.passwordError).isNull()
        assertThat(signupState.confirmPasswordError).isNull()
    }

    @Test
    fun `when password is not validated correct error is set in state`() {
        val email = "email@email.com"
        val password = ""
        val confirmPassword = ""

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

        val result = signupViewModel.isValidationSuccessful(email,password,confirmPassword)
        val signupState = getCurrentSignupState()

        verifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        }
        assertThat(result).isFalse()
        assertThat(signupState.emailError).isNull()
        assertThat(signupState.passwordError).isEqualTo(passwordEmptyError)
        assertThat(signupState.confirmPasswordError).isNull()
    }

    @Test
    fun `when confirmPassword is not validated correct error is set in state`() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val confirmPassword = ""

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

        val result = signupViewModel.isValidationSuccessful(email,password,confirmPassword)
        val signupState = getCurrentSignupState()

        verifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        }
        assertThat(result).isFalse()
        assertThat(signupState.emailError).isNull()
        assertThat(signupState.passwordError).isNull()
        assertThat(signupState.confirmPasswordError).isEqualTo(confirmPasswordError)
    }

    @Test
    fun `when all fields are not validated correct errors are set in state`() {
        val email = "email@email.com"
        val password = ""
        val confirmPassword = "Qwerty1+"

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

        val result = signupViewModel.isValidationSuccessful(email,password,confirmPassword)
        val signupState = getCurrentSignupState()

        verifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        }
        assertThat(result).isFalse()
        assertThat(signupState.emailError).isEqualTo(emailEmptyError)
        assertThat(signupState.passwordError).isEqualTo(passwordEmptyError)
        assertThat(signupState.confirmPasswordError).isEqualTo(confirmPasswordError)
    }

    @Test
    fun `addUser is successful`() {
        val userUID = "userUID"

        every { shopUseCases.getCurrentUserUseCase() } returns user
        every { user.uid } returns userUID
        coEvery {
            shopUseCases.addUserUseCase(userUID)
        } returns flowOf(Resource.Success(true))

        signupViewModel.addUser()

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.addUserUseCase(userUID)
        }
    }

    @Test
    fun `signup is successful`() {
        val userUID = "userUID"
        val email = "email@email.com"
        val password = "Qwerty1+"

        coEvery {
            shopUseCases.signupUseCase(email,password)
        } returns flowOf(Resource.Success(user))
        every { shopUseCases.getCurrentUserUseCase() } returns user
        every { user.uid } returns userUID
        coEvery {
            shopUseCases.addUserUseCase(userUID)
        } returns flowOf(Resource.Success(true))

        signupViewModel.signup(email,password)

        coVerifySequence {
            shopUseCases.signupUseCase(email,password)
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.addUserUseCase(userUID)
        }
    }

    @Test
    fun `signup is loading`() {
        val email = "email@email.com"
        val password = "Qwerty1+"

        coEvery {
            shopUseCases.signupUseCase(email,password)
        } returns flowOf(Resource.Loading(true))

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
        val userUID = "userUID"
        val email = "email@email.com"
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateSignupPasswordUseCase(password)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        } returns ValidationResult(isSuccessful = true)
        coEvery {
            shopUseCases.signupUseCase(email,password)
        } returns flowOf(Resource.Success(user))
        every { shopUseCases.getCurrentUserUseCase() } returns user
        every { user.uid } returns userUID
        coEvery {
            shopUseCases.addUserUseCase(userUID)
        } returns flowOf(Resource.Success(true))

        signupViewModel.onEvent(SignupEvent.EnteredEmail(email))
        signupViewModel.onEvent(SignupEvent.EnteredPassword(password))
        signupViewModel.onEvent(SignupEvent.EnteredConfirmPassword(confirmPassword))

        signupViewModel.onEvent(SignupEvent.Signup)
        val signupState = getCurrentSignupState()

        coVerifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
            shopUseCases.signupUseCase(email,password)
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.addUserUseCase(userUID)
        }
        assertThat(signupState.email).isEqualTo(email)
        assertThat(signupState.password).isEqualTo(password)
        assertThat(signupState.confirmPassword).isEqualTo(confirmPassword)
        assertThat(signupState.emailError).isNull()
        assertThat(signupState.passwordError).isNull()
        assertThat(signupState.confirmPasswordError).isNull()
    }

    @Test
    fun `event signup is not successful when auth signup returns error`() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateSignupPasswordUseCase(password)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        } returns ValidationResult(isSuccessful = true)
        coEvery {
            shopUseCases.signupUseCase(email,password)
        } returns flowOf(Resource.Error("Error"))

        signupViewModel.onEvent(SignupEvent.EnteredEmail(email))
        signupViewModel.onEvent(SignupEvent.EnteredPassword(password))
        signupViewModel.onEvent(SignupEvent.EnteredConfirmPassword(confirmPassword))

        signupViewModel.onEvent(SignupEvent.Signup)
        val signupState = getCurrentSignupState()

        coVerifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
            shopUseCases.signupUseCase(email,password)
        }
        assertThat(signupState.email).isEqualTo(email)
        assertThat(signupState.password).isEqualTo(password)
        assertThat(signupState.confirmPassword).isEqualTo(confirmPassword)
        assertThat(signupState.emailError).isNull()
        assertThat(signupState.passwordError).isNull()
        assertThat(signupState.confirmPasswordError).isNull()
    }

    @Test
    fun `event signup is not successful when email is blank`() {
        val email = ""
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"

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

        signupViewModel.onEvent(SignupEvent.EnteredEmail(email))
        signupViewModel.onEvent(SignupEvent.EnteredPassword(password))
        signupViewModel.onEvent(SignupEvent.EnteredConfirmPassword(confirmPassword))

        signupViewModel.onEvent(SignupEvent.Signup)
        val signupState = getCurrentSignupState()

        coVerifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        }
        assertThat(signupState.email).isEqualTo(email)
        assertThat(signupState.password).isEqualTo(password)
        assertThat(signupState.confirmPassword).isEqualTo(confirmPassword)
        assertThat(signupState.emailError).isEqualTo(emailEmptyError)
        assertThat(signupState.passwordError).isNull()
        assertThat(signupState.confirmPasswordError).isNull()
    }

    @Test
    fun `event signup is not successful when password is blank`() {
        val email = "email@email.com"
        val password = ""
        val confirmPassword = "Qwerty1+"

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

        signupViewModel.onEvent(SignupEvent.EnteredEmail(email))
        signupViewModel.onEvent(SignupEvent.EnteredPassword(password))
        signupViewModel.onEvent(SignupEvent.EnteredConfirmPassword(confirmPassword))

        signupViewModel.onEvent(SignupEvent.Signup)
        val signupState = getCurrentSignupState()

        coVerifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        }
        assertThat(signupState.email).isEqualTo(email)
        assertThat(signupState.password).isEqualTo(password)
        assertThat(signupState.confirmPassword).isEqualTo(confirmPassword)
        assertThat(signupState.emailError).isNull()
        assertThat(signupState.passwordError).isEqualTo(passwordEmptyError)
        assertThat(signupState.confirmPasswordError).isNull()
    }

    @Test
    fun `event signup is not successful when confirmPassword is blank`() {
        val email = "email@email.com"
        val password = "Qwerty1+"
        val confirmPassword = ""

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

        signupViewModel.onEvent(SignupEvent.EnteredEmail(email))
        signupViewModel.onEvent(SignupEvent.EnteredPassword(password))
        signupViewModel.onEvent(SignupEvent.EnteredConfirmPassword(confirmPassword))

        signupViewModel.onEvent(SignupEvent.Signup)
        val signupState = getCurrentSignupState()

        coVerifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        }
        assertThat(signupState.email).isEqualTo(email)
        assertThat(signupState.password).isEqualTo(password)
        assertThat(signupState.confirmPassword).isEqualTo(confirmPassword)
        assertThat(signupState.emailError).isNull()
        assertThat(signupState.passwordError).isNull()
        assertThat(signupState.confirmPasswordError).isEqualTo(confirmPasswordError)
    }

    @Test
    fun `event signup is not successful when all fields are incorrect`() {
        val email = ""
        val password = ""
        val confirmPassword = "Qwerty1+"

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

        signupViewModel.onEvent(SignupEvent.EnteredEmail(email))
        signupViewModel.onEvent(SignupEvent.EnteredPassword(password))
        signupViewModel.onEvent(SignupEvent.EnteredConfirmPassword(confirmPassword))

        signupViewModel.onEvent(SignupEvent.Signup)
        val signupState = getCurrentSignupState()

        coVerifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateSignupPasswordUseCase(password)
            shopUseCases.validateConfirmPasswordUseCase(password,confirmPassword)
        }
        assertThat(signupState.email).isEqualTo(email)
        assertThat(signupState.password).isEqualTo(password)
        assertThat(signupState.confirmPassword).isEqualTo(confirmPassword)
        assertThat(signupState.emailError).isEqualTo(emailEmptyError)
        assertThat(signupState.passwordError).isEqualTo(passwordEmptyError)
        assertThat(signupState.confirmPasswordError).isEqualTo(confirmPasswordError)
    }
}