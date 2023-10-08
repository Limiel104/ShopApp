package com.example.shopapp.domain.use_case

import java.util.Date
import java.util.concurrent.TimeUnit

class IsCouponExpiredUseCase {
    operator fun invoke(couponActivationDate: Date, currentDate: Date): Boolean {
        val currentTimeLong = currentDate.time
        val couponDateLong = couponActivationDate.time
        val timeDifference = currentTimeLong - couponDateLong
        val numberOfDaysPassedSinceActivation = TimeUnit.MILLISECONDS.toDays(timeDifference)
        return (numberOfDaysPassedSinceActivation > 14)
    }
}