package com.example.shopapp.presentation.login

import com.example.shopapp.domain.model.ValidationResult
import com.example.shopapp.domain.use_case.ShopUseCases
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

class LoginViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var shopUseCases: ShopUseCases
    private lateinit var loginViewModel: LoginViewModel
    @MockK
    private lateinit var user: FirebaseUser

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.loginViewModel = LoginViewModel(shopUseCases)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    private fun getCurrentLoginState(): LoginState {
        return loginViewModel.loginState.value
    }

    @Test
    fun `event enteredEmail sets email state`() {
        val email = "email@email.com"
        val initialEmailState = getCurrentLoginState().email

        loginViewModel.onEvent(LoginEvent.EnteredEmail(email))

        val resultEmail = getCurrentLoginState().email

        assertThat(initialEmailState).isEqualTo("")
        assertThat(resultEmail).isEqualTo(email)
    }

    @Test
    fun `event enteredPassword sets password state`() {
        val password = "Qwerty1+"
        val initialPasswordState = getCurrentLoginState().password

        loginViewModel.onEvent(LoginEvent.EnteredPassword(password))

        val resultPassword = getCurrentLoginState().password

        assertThat(initialPasswordState).isEqualTo("")
        assertThat(resultPassword).isEqualTo(password)
    }

    @Test
    fun `validation is successful`() {
        val email = "email@email.com"
        val password = "Qwerty1+"

        every { shopUseCases.validateEmailUseCase(email) } returns ValidationResult(isSuccessful = true)
        every { shopUseCases.validateLoginPasswordUseCase(password) } returns ValidationResult(isSuccessful = true)

        val result = loginViewModel.isValidationSuccessful(email,password)
        val loginState = getCurrentLoginState()

        verifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateLoginPasswordUseCase(password)
        }
        assertThat(result).isTrue()
        assertThat(loginState.emailError).isNull()
        assertThat(loginState.passwordError).isNull()
    }

    @Test
    fun `when email is not validated correct error is set in state`() {
        val email = ""
        val password = "Qwerty1+"

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = emailEmptyError
        )
        every { shopUseCases.validateLoginPasswordUseCase(password) } returns ValidationResult(isSuccessful = true)

        val result = loginViewModel.isValidationSuccessful(email,password)
        val loginState = getCurrentLoginState()

        verifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateLoginPasswordUseCase(password)
        }
        assertThat(result).isFalse()
        assertThat(loginState.emailError).isEqualTo(emailEmptyError)
        assertThat(loginState.passwordError).isNull()
    }

    @Test
    fun `when password is not validated correct error is set in state`() {
        val email = "email@email.com"
        val password = ""

        every { shopUseCases.validateEmailUseCase(email) } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateLoginPasswordUseCase(password)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = passwordEmptyError
        )

        val result = loginViewModel.isValidationSuccessful(email,password)
        val loginState = getCurrentLoginState()

        verifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateLoginPasswordUseCase(password)
        }
        assertThat(result).isFalse()
        assertThat(loginState.emailError).isNull()
        assertThat(loginState.passwordError).isEqualTo(passwordEmptyError)
    }

    @Test
    fun `when email and password are not validated correct errors are set in state`() {
        val email = ""
        val password = ""

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = emailEmptyError
        )
        every {
            shopUseCases.validateLoginPasswordUseCase(password)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = passwordEmptyError
        )

        val result = loginViewModel.isValidationSuccessful(email,password)
        val loginState = getCurrentLoginState()

        verifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateLoginPasswordUseCase(password)
        }
        assertThat(result).isFalse()
        assertThat(loginState.emailError).isEqualTo(emailEmptyError)
        assertThat(loginState.passwordError).isEqualTo(passwordEmptyError)
    }

    @Test
    fun `login is successful`() {
        val email = "email@email.com"
        val password = "Qwerty1+"

        coEvery {
            shopUseCases.loginUseCase(email,password)
        } returns flowOf(Resource.Success(user))

        loginViewModel.login(email,password)

        coVerify { shopUseCases.loginUseCase(email,password) }
    }

    @Test
    fun `login is loading`() {
        val email = "email@email.com"
        val password = "Qwerty1+"

        coEvery {
            shopUseCases.loginUseCase(email,password)
        } returns flowOf(Resource.Loading(true))

        val initialLoginState = getCurrentLoginState().isLoading

        loginViewModel.login(email,password)
        val loadingState = getCurrentLoginState().isLoading

        coVerify { shopUseCases.loginUseCase(email,password) }
        assertThat(initialLoginState).isFalse()
        assertThat(loadingState).isTrue()
    }

    @Test
    fun `login is not successful and returned error`() {
        val email = "email@email.com"
        val password = "Qwerty1+"

        coEvery {
            shopUseCases.loginUseCase(email,password)
        } returns flowOf(Resource.Error("Error"))

        loginViewModel.login(email,password)
        val loginState = getCurrentLoginState()

        coVerify { shopUseCases.loginUseCase(email,password) }
        assertThat(loginState.emailError).isNull()
        assertThat(loginState.passwordError).isNull()
    }

    @Test
    fun `event login is successful`() {
        val email = "email@email.com"
        val password = "Qwerty1+"

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateLoginPasswordUseCase(password)
        } returns ValidationResult(isSuccessful = true)
        coEvery {
            shopUseCases.loginUseCase(email,password)
        } returns flowOf(Resource.Success(user))

        loginViewModel.onEvent(LoginEvent.EnteredEmail(email))
        loginViewModel.onEvent(LoginEvent.EnteredPassword(password))

        loginViewModel.onEvent(LoginEvent.Login)
        val loginState = getCurrentLoginState()

        coVerifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateLoginPasswordUseCase(password)
            shopUseCases.loginUseCase(email,password)
        }
        assertThat(loginState.email).isEqualTo(email)
        assertThat(loginState.password).isEqualTo(password)
        assertThat(loginState.emailError).isNull()
        assertThat(loginState.passwordError).isNull()
    }

    @Test
    fun `event login is not successful when authRep login returns error`() {
        val email = "email@email.com"
        val password = "Qwerty1+"

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateLoginPasswordUseCase(password)
        } returns ValidationResult(isSuccessful = true)
        coEvery {
            shopUseCases.loginUseCase(email,password)
        } returns flowOf(Resource.Error("Error"))

        loginViewModel.onEvent(LoginEvent.EnteredEmail(email))
        loginViewModel.onEvent(LoginEvent.EnteredPassword(password))

        loginViewModel.onEvent(LoginEvent.Login)
        val loginState = getCurrentLoginState()

        coVerifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateLoginPasswordUseCase(password)
            shopUseCases.loginUseCase(email,password)
        }
        assertThat(loginState.email).isEqualTo(email)
        assertThat(loginState.password).isEqualTo(password)
        assertThat(loginState.emailError).isNull()
        assertThat(loginState.passwordError).isNull()
    }

    @Test
    fun `event login is not successful when email is blank`() {
        val email = ""
        val password = "Qwerty1+"

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = emailEmptyError
        )
        every {
            shopUseCases.validateLoginPasswordUseCase(password)
        } returns ValidationResult(isSuccessful = true)

        loginViewModel.onEvent(LoginEvent.EnteredEmail(email))
        loginViewModel.onEvent(LoginEvent.EnteredPassword(password))

        loginViewModel.onEvent(LoginEvent.Login)
        val loginState = getCurrentLoginState()

        coVerifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateLoginPasswordUseCase(password)
        }
        assertThat(loginState.email).isEqualTo(email)
        assertThat(loginState.password).isEqualTo(password)
        assertThat(loginState.emailError).isEqualTo(emailEmptyError)
        assertThat(loginState.passwordError).isNull()
    }

    @Test
    fun `event login is not successful when password is blank`() {
        val email = "email@email.com"
        val password = ""

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(isSuccessful = true)
        every {
            shopUseCases.validateLoginPasswordUseCase(password)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = passwordEmptyError
        )

        loginViewModel.onEvent(LoginEvent.EnteredEmail(email))
        loginViewModel.onEvent(LoginEvent.EnteredPassword(password))

        loginViewModel.onEvent(LoginEvent.Login)
        val loginState = getCurrentLoginState()

        coVerifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateLoginPasswordUseCase(password)
        }
        assertThat(loginState.email).isEqualTo(email)
        assertThat(loginState.password).isEqualTo(password)
        assertThat(loginState.emailError).isNull()
        assertThat(loginState.passwordError).isEqualTo(passwordEmptyError)
    }

    @Test
    fun `event login is not successful when email and password are blank`() {
        val email = "email@email.com"
        val password = "Qwerty1+"

        every {
            shopUseCases.validateEmailUseCase(email)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = emailEmptyError
        )
        every {
            shopUseCases.validateLoginPasswordUseCase(password)
        } returns ValidationResult(
            isSuccessful = false,
            errorMessage = passwordEmptyError
        )

        loginViewModel.onEvent(LoginEvent.EnteredEmail(email))
        loginViewModel.onEvent(LoginEvent.EnteredPassword(password))

        loginViewModel.onEvent(LoginEvent.Login)
        val loginState = getCurrentLoginState()

        coVerifySequence {
            shopUseCases.validateEmailUseCase(email)
            shopUseCases.validateLoginPasswordUseCase(password)
        }
        assertThat(loginState.email).isEqualTo(email)
        assertThat(loginState.password).isEqualTo(password)
        assertThat(loginState.emailError).isEqualTo(emailEmptyError)
        assertThat(loginState.passwordError).isEqualTo(passwordEmptyError)
    }
}