package com.example.shopapp.presentation.account

import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    private lateinit var user: FirebaseUser

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { shopUseCases.getCurrentUserUseCase() } returns user
        this.accountViewModel = AccountViewModel(shopUseCases)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    private fun getCurrentAccountState(): AccountState {
        return accountViewModel.accountState.value
    }

    @Test
    fun `checkIfUserIsLoggedIn is successful`() {
        accountViewModel.checkIfUserIsLoggedIn()

        val isUserLoggedIn = accountViewModel.accountState.value.isUserLoggedIn

        assertThat(isUserLoggedIn).isTrue()
        verify(exactly = 2) { shopUseCases.getCurrentUserUseCase }
    }

    @Test
    fun `user name is set correctly on init`() {
        val name = getCurrentAccountState().name

        assertThat(name).isEqualTo("John")

        verify(exactly = 1) { shopUseCases.getCurrentUserUseCase() }
    }

    @Test
    fun `logout is successful`() {
        every { shopUseCases.logoutUseCase() } just runs

        accountViewModel.logout()

        verifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.logoutUseCase()
        }
    }

    @Test
    fun `event logout is successful`() {
        every { shopUseCases.logoutUseCase() } just runs

        accountViewModel.onEvent(AccountEvent.OnLogout)

        verifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.logoutUseCase()
        }
    }
}