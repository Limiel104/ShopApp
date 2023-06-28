package com.example.shopapp.domain.use_case

data class ShopUseCases(
    val getProductsUseCase: GetProductsUseCase,
    val getCategoriesUseCase: GetCategoriesUseCase,
    val getProductsFromCategory: GetProductsFromCategory,
    val getProductUseCase: GetProductUseCase
)