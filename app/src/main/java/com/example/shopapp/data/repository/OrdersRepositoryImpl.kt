package com.example.shopapp.data.repository

import com.example.shopapp.domain.model.Order
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
    override suspend fun addOrder(userUID: String, order: Order): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                val documentId = ordersRef.document().id
                ordersRef.document(documentId).set(
                    mapOf(
                        "orderId" to documentId,
                        "userUID" to userUID,
                        "date" to order.date,
                        "amount" to order.amount,
                        "products" to order.products
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
                    val orders = snapshot.toObjects(Order::class.java)
                    Resource.Success(orders)
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