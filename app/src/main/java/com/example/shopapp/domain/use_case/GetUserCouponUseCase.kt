package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Coupon
import com.example.shopapp.domain.repository.CouponsRepository
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow

class GetUserCouponUseCase(
    private val couponsRepository: CouponsRepository
) {
    suspend operator fun invoke(userUID: String): Flow<Resource<Coupon>> {
        return couponsRepository.getUserCoupon(userUID)
    }
}