package com.example.shopapp.data.repository

import com.example.shopapp.domain.model.Coupon
import com.example.shopapp.domain.repository.CouponsRepository
import com.example.shopapp.util.Resource
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CouponsRepositoryImpl @Inject constructor(
    private val couponsRef: CollectionReference
): CouponsRepository {
    override suspend fun addCoupon(coupon: Coupon): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                couponsRef.document(coupon.userUID).set(
                    mapOf(
                        "userUID" to coupon.userUID,
                        "amount" to coupon.amount,
                        "activationDate" to coupon.activationDate
                    )
                ).await()
                emit(Resource.Success(true))
            }
            catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage as String))
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getUserCoupon(userUID: String): Flow<Resource<Coupon>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                val userCoupon = couponsRef
                    .whereEqualTo("userUID", userUID)
                    .get()
                    .await()
                    .documents
                    .mapNotNull { snapshot ->
                        snapshot.toObject(Coupon::class.java)
                    }
                if(userCoupon.isEmpty()) {
                    emit(Resource.Success(null))
                }
                else {
                    emit(Resource.Success(userCoupon[0]))
                }
            }
            catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage as String))
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun deleteCoupon(userUID: String): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                couponsRef.document(userUID)
                    .delete()
                    .await()
                emit(Resource.Success(true))
            }
            catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage as String))
            }

            emit(Resource.Loading(false))
        }
    }
}