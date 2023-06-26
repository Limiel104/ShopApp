package com.example.shopapp.domain.repository

import com.example.shopapp.domain.model.Product

interface ProductRepository {

    suspend fun getProducts(): List<Product>
}