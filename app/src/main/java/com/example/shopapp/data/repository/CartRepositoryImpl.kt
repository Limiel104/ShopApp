package com.example.shopapp.data.repository

import com.example.shopapp.domain.model.CartElement
import com.example.shopapp.domain.repository.CartRepository
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
class CartRepositoryImpl @Inject constructor(
    private val cartsRef: CollectionReference
): CartRepository {
    override suspend fun addProductToCart(
        userUID: String,
        productId: Int,
        amount: Int
    ): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                val documentId = cartsRef.document().id
                cartsRef.document(documentId).set(
                    mapOf(
                        "cartElementId" to documentId,
                        "userUID" to userUID,
                        "productId" to productId,
                        "amount" to amount
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

    override suspend fun getCartItems(userUID: String) = callbackFlow {
        val snapshotListener = cartsRef
            .whereEqualTo("userUID", userUID)
            .addSnapshotListener { snapshot, e ->
                val response = if(snapshot != null) {
                    val userCartElements = snapshot.toObjects(CartElement::class.java)
                    Resource.Success(userCartElements)
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

    override suspend fun deleteProductFromCart(
        cartItemId: String
    ): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                cartsRef.document()
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

    override suspend fun updateProductInCart(cartElement: CartElement): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                cartsRef.document(cartElement.cartElementId).update(
                    mapOf(
                        "cartElementId" to cartElement.cartElementId,
                        "userUID" to cartElement.userUID,
                        "productId" to cartElement.productId,
                        "amount" to cartElement.amount
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
}