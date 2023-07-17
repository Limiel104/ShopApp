package com.example.shopapp.di

import android.app.Application
import androidx.room.Room
import com.example.shopapp.data.local.ShopDatabase
import com.example.shopapp.data.remote.FakeShopApi
import com.example.shopapp.data.repository.AuthRepositoryImpl
import com.example.shopapp.data.repository.ProductRepositoryImpl
import com.example.shopapp.domain.repository.AuthRepository
import com.example.shopapp.domain.repository.ProductRepository
import com.example.shopapp.domain.use_case.GetCategoriesUseCase
import com.example.shopapp.domain.use_case.GetCurrentUserUseCase
import com.example.shopapp.domain.use_case.GetProductUseCase
import com.example.shopapp.domain.use_case.GetProductsUseCase
import com.example.shopapp.domain.use_case.LoginUseCase
import com.example.shopapp.domain.use_case.LogoutUseCase
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.domain.use_case.SignupUseCase
import com.example.shopapp.domain.use_case.ValidateConfirmPasswordUseCase
import com.example.shopapp.domain.use_case.ValidateEmailUseCase
import com.example.shopapp.domain.use_case.ValidateLoginPasswordUseCase
import com.example.shopapp.domain.use_case.ValidateSignupPasswordUseCase
import com.google.firebase.auth.FirebaseAuth
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
    fun provideShopDatabase(application: Application): ShopDatabase {
        return Room.inMemoryDatabaseBuilder(
            application,
            ShopDatabase::class.java,
        ).build()
    }

    @Provides
    fun provideProductRepository(api: FakeShopApi, db: ShopDatabase): ProductRepository {
        return ProductRepositoryImpl(api,db.productDao)
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    fun provideShopUseCases(
        productRepository: ProductRepository,
        authRepository: AuthRepository
    ): ShopUseCases {
        return ShopUseCases(
            getProductsUseCase = GetProductsUseCase(productRepository),
            getCategoriesUseCase = GetCategoriesUseCase(),
            getProductUseCase = GetProductUseCase(productRepository),
            validateEmailUseCase = ValidateEmailUseCase(),
            validateLoginPasswordUseCase = ValidateLoginPasswordUseCase(),
            validateSignupPasswordUseCase = ValidateSignupPasswordUseCase(),
            validateConfirmPasswordUseCase = ValidateConfirmPasswordUseCase(),
            getCurrentUserUseCase = GetCurrentUserUseCase(authRepository),
            loginUseCase = LoginUseCase(authRepository),
            signupUseCase = SignupUseCase(authRepository),
            logoutUseCase = LogoutUseCase(authRepository)
        )
    }
}