package com.example.shopapp.di

import android.app.Application
import androidx.room.Room
import com.example.shopapp.data.local.ShopDatabase
import com.example.shopapp.data.remote.FakeShopApi
import com.example.shopapp.data.repository.AuthRepositoryImpl
import com.example.shopapp.data.repository.FavouritesRepositoryImpl
import com.example.shopapp.data.repository.ProductRepositoryImpl
import com.example.shopapp.data.repository.UserStorageRepositoryImpl
import com.example.shopapp.domain.repository.AuthRepository
import com.example.shopapp.domain.repository.FavouritesRepository
import com.example.shopapp.domain.repository.ProductRepository
import com.example.shopapp.domain.repository.UserStorageRepository
import com.example.shopapp.domain.use_case.AddProductToFavouritesUseCase
import com.example.shopapp.domain.use_case.AddUserUseCase
import com.example.shopapp.domain.use_case.DeleteProductFromFavouritesUseCase
import com.example.shopapp.domain.use_case.FilterProductsByUserFavouritesUseCase
import com.example.shopapp.domain.use_case.GetCategoriesUseCase
import com.example.shopapp.domain.use_case.GetCurrentUserUseCase
import com.example.shopapp.domain.use_case.GetFavouriteIdUseCase
import com.example.shopapp.domain.use_case.GetProductUseCase
import com.example.shopapp.domain.use_case.GetProductsUseCase
import com.example.shopapp.domain.use_case.GetUserFavouritesUseCase
import com.example.shopapp.domain.use_case.LoginUseCase
import com.example.shopapp.domain.use_case.LogoutUseCase
import com.example.shopapp.domain.use_case.SetUserFavouritesUseCase
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.domain.use_case.SignupUseCase
import com.example.shopapp.domain.use_case.FilterProductsUseCase
import com.example.shopapp.domain.use_case.SortProductsUseCase
import com.example.shopapp.domain.use_case.ValidateConfirmPasswordUseCase
import com.example.shopapp.domain.use_case.ValidateEmailUseCase
import com.example.shopapp.domain.use_case.ValidateLoginPasswordUseCase
import com.example.shopapp.domain.use_case.ValidateSignupPasswordUseCase
import com.example.shopapp.util.Constants.FAVOURITES_COLLECTION
import com.example.shopapp.util.Constants.USERS_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideUserStorageRepository(): UserStorageRepository {
        val usersRef = Firebase.firestore.collection(USERS_COLLECTION)
        return UserStorageRepositoryImpl(usersRef)
    }

    @Provides
    @Singleton
    fun provideFavouritesStorageRepository(): FavouritesRepository {
        val usersRef = Firebase.firestore.collection(FAVOURITES_COLLECTION)
        return FavouritesRepositoryImpl(usersRef)
    }

    @Provides
    @Singleton
    fun provideShopUseCases(
        productRepository: ProductRepository,
        authRepository: AuthRepository,
        userStorageRepository: UserStorageRepository,
        favouritesRepository: FavouritesRepository
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
            logoutUseCase = LogoutUseCase(authRepository),
            addUserUseCase = AddUserUseCase(userStorageRepository),
            addProductToFavouritesUseCase = AddProductToFavouritesUseCase(favouritesRepository),
            getUserFavouritesUseCase = GetUserFavouritesUseCase(favouritesRepository),
            deleteProductFromFavouritesUseCase = DeleteProductFromFavouritesUseCase(favouritesRepository),
            setUserFavouritesUseCase = SetUserFavouritesUseCase(),
            getFavouriteIdUseCase = GetFavouriteIdUseCase(),
            filterProductsByUserFavouritesUseCase = FilterProductsByUserFavouritesUseCase(),
            filterProductsUseCase = FilterProductsUseCase(),
            sortProductsUseCase = SortProductsUseCase()
        )
    }
}