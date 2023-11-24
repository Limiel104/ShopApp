package com.example.shopapp.domain.use_case

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

class DeleteCouponUseCaseTest {

    @MockK
    private lateinit var couponsRepository: CouponsRepository
    private lateinit var deleteCouponUseCase: DeleteCouponUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        deleteCouponUseCase = DeleteCouponUseCase(couponsRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `coupon was deleted successfully`() {
        runBlocking {
            val result = Resource.Success(true)

            coEvery { couponsRepository.deleteCoupon("userUID") } returns flowOf(result)

            val response = deleteCouponUseCase("userUID").first()

            coVerify(exactly = 1) { deleteCouponUseCase("userUID") }
            assertThat(response).isEqualTo(result)
            assertThat(response.data).isTrue()
            assertThat(response.message).isNull()
        }
    }

    @Test
    fun `coupon was not deleted and error message was returned`() {
        runBlocking {
            coEvery {
                couponsRepository.deleteCoupon("userUID")
            } returns flowOf(Resource.Error("Error"))

            val response = deleteCouponUseCase("userUID").first()

            coVerify(exactly = 1) { deleteCouponUseCase("userUID") }
            assertThat(response.data).isNull()
            assertThat(response.message).isEqualTo("Error")
        }
    }

    @Test
    fun `delete coupon is loading`() {
        runBlocking {
            coEvery {
                couponsRepository.deleteCoupon("userUID")
            } returns flowOf(Resource.Loading(true))

            val response = deleteCouponUseCase("userUID").first()

            coVerify(exactly = 1) { deleteCouponUseCase("userUID") }
            assertThat(response.data).isNull()
            assertThat(response.message).isNull()
        }
    }
}