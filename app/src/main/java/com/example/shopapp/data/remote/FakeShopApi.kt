package com.example.shopapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface FakeShopApi {

    @GET("products")
    suspend fun getProducts(): List<ProductDto>

    @GET("products/category/{categoryId}")
    suspend fun getProductsFromCategory(@Path("categoryId") categoryId: String): List<ProductDto>

    @GET("products/{productId}")
    suspend fun getProduct(@Path("productId") productId: Int): ProductDto

    companion object {
        const val BASE_URL = "https://fakestoreapi.com/"
    }
}