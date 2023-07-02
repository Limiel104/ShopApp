package com.example.shopapp.data.remote

import retrofit2.http.GET

interface FakeShopApi {

    @GET("products")
    suspend fun getProducts(): List<ProductDto>

    companion object {
        const val BASE_URL = "https://fakestoreapi.com/"
    }
}