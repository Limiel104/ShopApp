package com.example.shopapp.presentation.account

import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
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
    fun `user name is set correctly on init`() {
        every { shopUseCases.getCurrentUserUseCase() } returns user

        accountViewModel = setViewModel()

        val name = getCurrentAccountState().name
        assertThat(name).isEqualTo("John")
    }
}