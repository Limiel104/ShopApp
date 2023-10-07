package com.example.shopapp.domain.repository

import com.example.shopapp.domain.model.Coupon
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface CouponsRepository {

    suspend fun addCoupon(coupon: Coupon): Flow<Resource<Boolean>>

    suspend fun getUserCoupon(userUID: String): Flow<Resource<Coupon>>

    suspend fun deleteCoupon(userUID: String): Flow<Resource<Boolean>>
}