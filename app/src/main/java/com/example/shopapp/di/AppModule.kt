package com.example.shopapp.di

import android.app.Application
import androidx.room.Room
import com.example.shopapp.data.local.ShopDatabase
import com.example.shopapp.data.remote.FakeShopApi
import com.example.shopapp.data.repository.ProductRepositoryImpl
import com.example.shopapp.domain.repository.ProductRepository
import com.example.shopapp.domain.use_case.GetCategoriesUseCase
import com.example.shopapp.domain.use_case.GetProductUseCase
import com.example.shopapp.domain.use_case.GetProductsUseCase
import com.example.shopapp.domain.use_case.ShopUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFakeShopApi(): FakeShopApi {
        return Retrofit.Builder()
            .baseUrl(FakeShopApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideShopDatabase(application: Application): ShopDatabase {
        return Room.databaseBuilder(
            application,
            ShopDatabase::class.java,
            ShopDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductRepository(api: FakeShopApi, db: ShopDatabase): ProductRepository {
        return ProductRepositoryImpl(api,db.productDao)
    }

    @Provides
    @Singleton
    fun provideShopUseCases(productRepository: ProductRepository): ShopUseCases {
        return ShopUseCases(
            getProductsUseCase = GetProductsUseCase(productRepository),
            getCategoriesUseCase = GetCategoriesUseCase(),
            getProductUseCase = GetProductUseCase(productRepository)
        )
    }
}