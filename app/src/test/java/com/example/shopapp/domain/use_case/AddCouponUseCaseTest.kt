package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Coupon
import com.example.shopapp.domain.repository.CouponsRepository
import com.example.shopapp.util.Resource
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

class AddCouponUseCaseTest {

    @MockK
    private lateinit var couponsRepository: CouponsRepository
    private lateinit var addCouponUseCase: AddCouponUseCase
    private lateinit var coupon: Coupon

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        addCouponUseCase = AddCouponUseCase(couponsRepository)

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
    fun `coupon was added successfully`() {
        runBlocking {
            val result = Resource.Success(true)

            coEvery { couponsRepository.addCoupon(coupon) } returns flowOf(result)

            val response = addCouponUseCase(coupon).first()

            coVerify(exactly = 1) { addCouponUseCase(coupon) }
            assertThat(response).isEqualTo(result)
            assertThat(response.data).isTrue()
            assertThat(response.message).isNull()
        }
    }

    @Test
    fun `coupon was not added and error was returned`() {
        runBlocking {
            coEvery {
                couponsRepository.addCoupon(coupon)
            } returns flowOf(Resource.Error("Error"))

            val response = addCouponUseCase(coupon).first()

            coVerify(exactly = 1) { addCouponUseCase(coupon) }
            assertThat(response.data).isNull()
            assertThat(response.message).isEqualTo("Error")
        }
    }

    @Test
    fun `add coupon is loading`() {
        runBlocking {
            coEvery {
                couponsRepository.addCoupon(coupon)
            } returns flowOf(Resource.Loading(true))

            val response = addCouponUseCase(coupon).first()

            coVerify(exactly = 1) { addCouponUseCase(coupon) }
            assertThat(response.data).isNull()
            assertThat(response.message).isNull()
        }
    }
}