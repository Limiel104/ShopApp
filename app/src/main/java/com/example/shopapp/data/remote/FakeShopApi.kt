package com.example.shopapp.data.remote

import retrofit2.http.GET

interface FakeShopApi {

    @GET("products")
    suspend fun getProducts(): List<ProductDto>

    @GET("products/categories")
    suspend fun getCategories(): List<String>

    companion object {
        const val BASE_URL = "https://fakestoreapi.com/"
    }
}