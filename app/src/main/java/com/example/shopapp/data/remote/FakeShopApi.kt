package com.example.shopapp.data.remote

import com.example.shopapp.domain.model.Product
import retrofit2.http.GET

interface FakeShopApi {

    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products/categories")
    suspend fun getCategories(): List<String>

    companion object {
        const val BASE_URL = "https://fakestoreapi.com/"
    }
}