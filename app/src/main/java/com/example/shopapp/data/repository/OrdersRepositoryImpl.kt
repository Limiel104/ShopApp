package com.example.shopapp.data.repository

import com.example.shopapp.domain.model.FirebaseOrder
import com.example.shopapp.domain.repository.OrdersRepository
import com.example.shopapp.util.Resource
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrdersRepositoryImpl @Inject constructor(
    private val ordersRef: CollectionReference
): OrdersRepository {
    override suspend fun addOrder(firebaseOrder: FirebaseOrder): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                val documentId = ordersRef.document().id
                ordersRef.document(documentId).set(
                    mapOf(
                        "orderId" to documentId,
                        "userUID" to firebaseOrder.userUID,
                        "date" to firebaseOrder.date,
                        "amount" to firebaseOrder.totalAmount,
                        "products" to firebaseOrder.products
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

    override suspend fun getUserOrders(userUID: String) = callbackFlow {
        val snapshotListener = ordersRef
            .whereEqualTo("userUID",userUID)
            .addSnapshotListener { snapshot, e ->
                val response = if(snapshot != null) {
                    val firebaseOrders = snapshot.toObjects(FirebaseOrder::class.java)
                    Resource.Success(firebaseOrders)
                }
                else {
                    Resource.Error(e!!.localizedMessage as String)
                }
                trySend(response)
            }

        awaitClose {
            snapshotListener.remove()
        }
    }
}