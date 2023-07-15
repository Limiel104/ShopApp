package com.example.shopapp.presentation.account

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class AccountViewModelTest {

    private lateinit var accountViewModel: AccountViewModel

    @Before
    fun setUp() {
        accountViewModel = AccountViewModel()
    }

    private fun getCurrentAccountState(): AccountState {
        return accountViewModel.accountState.value
    }

    @Test
    fun `user name is set correctly on init`() {
        val name = getCurrentAccountState().name
        assertThat(name).isEqualTo("John")
    }
}