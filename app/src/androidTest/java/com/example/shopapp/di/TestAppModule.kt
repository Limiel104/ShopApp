package com.example.shopapp.di

import com.example.shopapp.data.remote.FakeShopApi
import com.example.shopapp.data.repository.ProductRepositoryImpl
import com.example.shopapp.domain.repository.ProductRepository
import com.example.shopapp.domain.use_case.GetCategoriesUseCase
import com.example.shopapp.domain.use_case.GetProductUseCase
import com.example.shopapp.domain.use_case.GetProductsFromCategory
import com.example.shopapp.domain.use_case.GetProductsUseCase
import com.example.shopapp.domain.use_case.ShopUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    fun provideFakeShopApi(): FakeShopApi {
        return Retrofit.Builder()
            .baseUrl(FakeShopApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    fun provideProductRepository(api: FakeShopApi): ProductRepository {
        return ProductRepositoryImpl(api)
    }

    @Provides
    fun provideShopUseCases(productRepository: ProductRepository): ShopUseCases {
        return ShopUseCases(
            getProductsUseCase = GetProductsUseCase(productRepository),
            getCategoriesUseCase = GetCategoriesUseCase(productRepository),
            getProductsFromCategory = GetProductsFromCategory(productRepository),
            getProductUseCase = GetProductUseCase(productRepository)
        )
    }
}