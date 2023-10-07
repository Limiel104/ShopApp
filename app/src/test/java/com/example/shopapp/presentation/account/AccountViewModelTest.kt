package com.example.shopapp.presentation.account

import com.example.shopapp.domain.model.Address
import com.example.shopapp.domain.model.User
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.MainDispatcherRule
import com.example.shopapp.util.Resource
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AccountViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var shopUseCases: ShopUseCases
    private lateinit var accountViewModel: AccountViewModel
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

    private fun setViewModel(): AccountViewModel {
        return AccountViewModel(shopUseCases)
    }

    private fun getCurrentAccountState(): AccountState {
        return accountViewModel.accountState.value
    }

    @Test
    fun `check if current user is logged in and set state correctly`() {
        accountViewModel = setViewModel()
        val isUserLoggedIn = getCurrentAccountState().isUserLoggedIn

        verify { shopUseCases.getCurrentUserUseCase }
        assertThat(isUserLoggedIn).isTrue()
    }

    @Test
    fun `if user is logged in get user data - get user is successful`() {
        coEvery {
            shopUseCases.getUserUseCase("userUID")
        } returns flowOf(Resource.Success(listOf(user)))

        accountViewModel = setViewModel()
        val isUserLoggedIn = getCurrentAccountState().isUserLoggedIn
        val resultUser = getCurrentAccountState().user
        val isLoading = getCurrentAccountState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserUseCase("userUID")
        }
        assertThat(isUserLoggedIn).isTrue()
        assertThat(resultUser).isEqualTo(user)
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `if user is logged in get user data - get user result is error`() {
        coEvery {
            shopUseCases.getUserUseCase("userUID")
        } returns flowOf(Resource.Success(listOf(user)))

        accountViewModel = setViewModel()
        val isUserLoggedIn = getCurrentAccountState().isUserLoggedIn
        val isLoading = getCurrentAccountState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserUseCase("userUID")
        }
        assertThat(isUserLoggedIn).isTrue()
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `if user is logged in get user data - get user is loading`() {
        coEvery {
            shopUseCases.getUserUseCase("userUID")
        } returns flowOf(Resource.Loading(true))

        accountViewModel = setViewModel()
        val isUserLoggedIn = getCurrentAccountState().isUserLoggedIn
        val isLoading = getCurrentAccountState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserUseCase("userUID")
        }
        assertThat(isUserLoggedIn).isTrue()
        assertThat(isLoading).isTrue()
    }

    @Test
    fun `logout is successful`() {
        coEvery {
            shopUseCases.getUserUseCase("userUID")
        } returns flowOf(Resource.Success(listOf(user)))
        every { shopUseCases.logoutUseCase() } just runs

        accountViewModel = setViewModel()
        accountViewModel.logout()

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.logoutUseCase()
        }
    }

    @Test
    fun `event logout is successful`() {
        coEvery {
            shopUseCases.getUserUseCase("userUID")
        } returns flowOf(Resource.Success(listOf(user)))
        every { shopUseCases.logoutUseCase() } just runs

        accountViewModel = setViewModel()
        accountViewModel.onEvent(AccountEvent.OnLogout)

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.logoutUseCase()
        }
    }
}