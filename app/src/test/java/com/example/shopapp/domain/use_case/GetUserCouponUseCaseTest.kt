package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Coupon
import com.example.shopapp.domain.repository.CouponsRepository
import com.example.shopapp.domain.util.Resource
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetUserCouponUseCaseTest {

    @MockK
    private lateinit var couponsRepository: CouponsRepository
    private lateinit var getUserCouponUseCase: GetUserCouponUseCase
    private lateinit var coupon: Coupon

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getUserCouponUseCase = GetUserCouponUseCase(couponsRepository)

        coupon = Coupon(
            userUID = "userUID",
            amount = 20
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `get user coupon was successful`() {
        runBlocking {
            val result = Resource.Success((coupon))

            coEvery {
                couponsRepository.getUserCoupon("userUID")
            } returns flowOf(result)

            val response = getUserCouponUseCase("userUID").first()

            coVerify(exactly = 1) { getUserCouponUseCase("userUID") }
            assertThat(response).isEqualTo(result)
            assertThat(response.data).isEqualTo(coupon)
            assertThat(response.message).isNull()
        }
    }

    @Test
    fun `get user coupon was not successful and error message was returned`() {
        runBlocking {
            coEvery {
                couponsRepository.getUserCoupon("userUID")
            } returns flowOf(Resource.Error("Error"))

            val response = getUserCouponUseCase("userUID").first()

            coVerify(exactly = 1) { getUserCouponUseCase("userUID") }
            assertThat(response.data).isNull()
            assertThat(response.message).isEqualTo("Error")
        }
    }

    @Test
    fun `get user coupon is loading`() {
        runBlocking {
            coEvery {
                couponsRepository.getUserCoupon("userUID")
            } returns flowOf(Resource.Loading(true))

            val response = getUserCouponUseCase("userUID").first()

            coVerify(exactly = 1) { getUserCouponUseCase("userUID") }
            assertThat(response.data).isNull()
            assertThat(response.message).isNull()
        }
    }
}