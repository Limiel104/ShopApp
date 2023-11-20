package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.repository.CouponsRepository
import com.example.shopapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class DeleteCouponUseCase(
    private val couponsRepository: CouponsRepository
) {
    suspend operator fun invoke(userUID: String): Flow<Resource<Boolean>> {
        return couponsRepository.deleteCoupon(userUID)
    }
}