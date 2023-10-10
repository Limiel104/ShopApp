package com.example.shopapp.presentation.account

import com.example.shopapp.domain.model.Address
import com.example.shopapp.domain.model.Coupon
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
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date

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
    private lateinit var date: Date
    private lateinit var currentDate: Date
    private lateinit var coupon: Coupon
    private lateinit var expiredCoupon: Coupon

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

        date = Date(2023,7,11)
        currentDate = Date()

        coupon = Coupon(
            userUID = "userUID",
            amount = 20,
            activationDate = currentDate
        )

        expiredCoupon = Coupon(
            userUID = "userUID",
            amount = 20,
            activationDate = date
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
        coEvery {
            shopUseCases.getUserUseCase("userUID")
        } returns flowOf(Resource.Error("Error"))

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
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(coupon.activationDate,any())
        } returns false

        accountViewModel = setViewModel()
        val isUserLoggedIn = getCurrentAccountState().isUserLoggedIn
        val resultUser = getCurrentAccountState().user
        val isLoading = getCurrentAccountState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(coupon.activationDate,any())
        }
        assertThat(isUserLoggedIn).isTrue()
        assertThat(resultUser).isEqualTo(user)
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `if user is logged in get user data - get user result is error`() {
        coEvery {
            shopUseCases.getUserUseCase("userUID")
        } returns flowOf(Resource.Error("Error"))

        accountViewModel = setViewModel()
        val isUserLoggedIn = getCurrentAccountState().isUserLoggedIn
        val resultUser = getCurrentAccountState().user
        val isLoading = getCurrentAccountState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserUseCase("userUID")
        }
        assertThat(isUserLoggedIn).isTrue()
        assertThat(resultUser).isEqualTo(User())
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
    fun `get user coupon is successful - coupon already activated`() {
        coEvery {
            shopUseCases.getUserUseCase("userUID")
        } returns flowOf(Resource.Success(listOf(user)))
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(coupon.activationDate,any())
        } returns false

        accountViewModel = setViewModel()
        val isUserLoggedIn = getCurrentAccountState().isUserLoggedIn
        val resultCoupon = getCurrentAccountState().coupon
        val resultIsCouponActivated = getCurrentAccountState().isCouponActivated
        val isLoading = getCurrentAccountState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(coupon.activationDate,any())
        }
        assertThat(isUserLoggedIn).isTrue()
        assertThat(resultCoupon).isEqualTo(coupon)
        assertThat(resultIsCouponActivated).isTrue()
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `get user coupon is successful - coupon not yet activated`() {
        coEvery {
            shopUseCases.getUserUseCase("userUID")
        } returns flowOf(Resource.Success(listOf(user)))
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(null))

        accountViewModel = setViewModel()
        val isUserLoggedIn = getCurrentAccountState().isUserLoggedIn
        val resultIsCouponActivated = getCurrentAccountState().isCouponActivated
        val isLoading = getCurrentAccountState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.getUserCouponUseCase("userUID")
        }
        assertThat(isUserLoggedIn).isTrue()
        assertThat(resultIsCouponActivated).isFalse()
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `get user coupon is not successful and returns error`() {
        coEvery {
            shopUseCases.getUserUseCase("userUID")
        } returns flowOf(Resource.Success(listOf(user)))
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Error("Error"))

        accountViewModel = setViewModel()
        val isUserLoggedIn = getCurrentAccountState().isUserLoggedIn
        val isLoading = getCurrentAccountState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.getUserCouponUseCase("userUID")
        }
        assertThat(isUserLoggedIn).isTrue()
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `get user coupon is loading`() {
        coEvery {
            shopUseCases.getUserUseCase("userUID")
        } returns flowOf(Resource.Success(listOf(user)))
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Loading(true))

        accountViewModel = setViewModel()
        val isUserLoggedIn = getCurrentAccountState().isUserLoggedIn
        val isLoading = getCurrentAccountState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.getUserCouponUseCase("userUID")
        }
        assertThat(isUserLoggedIn).isTrue()
        assertThat(isLoading).isTrue()
    }

    @Test
    fun `delete coupon is successful - activated coupon is expired`() {
        coEvery {
            shopUseCases.getUserUseCase("userUID")
        } returns flowOf(Resource.Success(listOf(user)))
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(expiredCoupon))
        every {
            shopUseCases.isCouponExpiredUseCase(expiredCoupon.activationDate,any())
        } returns true
        coEvery {
            shopUseCases.deleteCouponUseCase("userUID")
        } returns flowOf(Resource.Success(true))

        accountViewModel = setViewModel()
        val isUserLoggedIn = getCurrentAccountState().isUserLoggedIn
        val resultIsCouponActivated = getCurrentAccountState().isCouponActivated
        val isLoading = getCurrentAccountState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(expiredCoupon.activationDate,any())
            shopUseCases.deleteCouponUseCase("userUID")
        }
        assertThat(isUserLoggedIn).isTrue()
        assertThat(resultIsCouponActivated).isFalse()
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `coupon date is passed correctly`() {
        val dateSlot = slot<Date>()

        coEvery {
            shopUseCases.getUserUseCase("userUID")
        } returns flowOf(Resource.Success(listOf(user)))
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(capture(dateSlot),any())
        } returns false

        accountViewModel = setViewModel()
        val isUserLoggedIn = getCurrentAccountState().isUserLoggedIn
        val resultIsCouponActivated = getCurrentAccountState().isCouponActivated
        val isLoading = getCurrentAccountState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(dateSlot.captured,any())
        }
        assertThat(isUserLoggedIn).isTrue()
        assertThat(resultIsCouponActivated).isTrue()
        assertThat(isLoading).isFalse()
        assertThat(dateSlot.captured).isEqualTo(coupon.activationDate)
    }

    @Test
    fun `delete coupon is not successful and returns error`() {
        coEvery {
            shopUseCases.getUserUseCase("userUID")
        } returns flowOf(Resource.Success(listOf(user)))
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(expiredCoupon))
        every {
            shopUseCases.isCouponExpiredUseCase(expiredCoupon.activationDate,any())
        } returns true
        coEvery {
            shopUseCases.deleteCouponUseCase("userUID")
        } returns flowOf(Resource.Error("Error"))

        accountViewModel = setViewModel()
        val isUserLoggedIn = getCurrentAccountState().isUserLoggedIn
        val resultIsCouponActivated = getCurrentAccountState().isCouponActivated
        val isLoading = getCurrentAccountState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(expiredCoupon.activationDate,any())
            shopUseCases.deleteCouponUseCase("userUID")
        }
        assertThat(isUserLoggedIn).isTrue()
        assertThat(resultIsCouponActivated).isFalse()
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `delete coupon is loading`() {
        coEvery {
            shopUseCases.getUserUseCase("userUID")
        } returns flowOf(Resource.Success(listOf(user)))
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(expiredCoupon))
        every {
            shopUseCases.isCouponExpiredUseCase(expiredCoupon.activationDate,any())
        } returns true
        coEvery {
            shopUseCases.deleteCouponUseCase("userUID")
        } returns flowOf(Resource.Loading(true))

        accountViewModel = setViewModel()
        val isUserLoggedIn = getCurrentAccountState().isUserLoggedIn
        val resultIsCouponActivated = getCurrentAccountState().isCouponActivated
        val isLoading = getCurrentAccountState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(expiredCoupon.activationDate,any())
            shopUseCases.deleteCouponUseCase("userUID")
        }
        assertThat(isUserLoggedIn).isTrue()
        assertThat(resultIsCouponActivated).isFalse()
        assertThat(isLoading).isTrue()
    }

    @Test
    fun `logout is successful`() {
        coEvery {
            shopUseCases.getUserUseCase("userUID")
        } returns flowOf(Resource.Success(listOf(user)))
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(coupon.activationDate,any())
        } returns false
        every { shopUseCases.logoutUseCase() } just runs

        accountViewModel = setViewModel()
        accountViewModel.logout()

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(coupon.activationDate,any())
            shopUseCases.logoutUseCase()
        }
    }

    @Test
    fun `event logout is successful`() {
        coEvery {
            shopUseCases.getUserUseCase("userUID")
        } returns flowOf(Resource.Success(listOf(user)))
        coEvery {
            shopUseCases.getUserCouponUseCase("userUID")
        } returns flowOf(Resource.Success(coupon))
        every {
            shopUseCases.isCouponExpiredUseCase(coupon.activationDate,any())
        } returns false
        every { shopUseCases.logoutUseCase() } just runs

        accountViewModel = setViewModel()
        accountViewModel.onEvent(AccountEvent.OnLogout)

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserUseCase("userUID")
            shopUseCases.getUserCouponUseCase("userUID")
            shopUseCases.isCouponExpiredUseCase(coupon.activationDate,any())
            shopUseCases.logoutUseCase()
        }
    }
}