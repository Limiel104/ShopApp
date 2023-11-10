package com.example.shopapp.di

import android.app.Application
import androidx.room.Room
import com.example.shopapp.data.local.ShopDatabase
import com.example.shopapp.data.remote.FakeShopApi
import com.example.shopapp.data.repository.AuthRepositoryImpl
import com.example.shopapp.data.repository.CartRepositoryImpl
import com.example.shopapp.data.repository.CouponsRepositoryImpl
import com.example.shopapp.data.repository.FavouritesRepositoryImpl
import com.example.shopapp.data.repository.OrdersRepositoryImpl
import com.example.shopapp.data.repository.ProductRepositoryImpl
import com.example.shopapp.data.repository.UserStorageRepositoryImpl
import com.example.shopapp.domain.repository.AuthRepository
import com.example.shopapp.domain.repository.CartRepository
import com.example.shopapp.domain.repository.CouponsRepository
import com.example.shopapp.domain.repository.FavouritesRepository
import com.example.shopapp.domain.repository.OrdersRepository
import com.example.shopapp.domain.repository.ProductRepository
import com.example.shopapp.domain.repository.UserStorageRepository
import com.example.shopapp.domain.use_case.AddCouponUseCase
import com.example.shopapp.domain.use_case.AddOrderUseCase
import com.example.shopapp.domain.use_case.AddProductToCartUseCase
import com.example.shopapp.domain.use_case.AddProductToFavouritesUseCase
import com.example.shopapp.domain.use_case.AddUserUseCase
import com.example.shopapp.domain.use_case.DeleteCouponUseCase
import com.example.shopapp.domain.use_case.DeleteProductFromCartUseCase
import com.example.shopapp.domain.use_case.DeleteProductFromFavouritesUseCase
import com.example.shopapp.domain.use_case.FilterProductsByUserFavouritesUseCase
import com.example.shopapp.domain.use_case.FilterProductsUseCase
import com.example.shopapp.domain.use_case.GetCategoriesUseCase
import com.example.shopapp.domain.use_case.GetCurrentUserUseCase
import com.example.shopapp.domain.use_case.GetFavouriteIdUseCase
import com.example.shopapp.domain.use_case.GetProductUseCase
import com.example.shopapp.domain.use_case.GetProductsUseCase
import com.example.shopapp.domain.use_case.GetUserCartItemUseCase
import com.example.shopapp.domain.use_case.GetUserCartItemsUseCase
import com.example.shopapp.domain.use_case.GetUserCouponUseCase
import com.example.shopapp.domain.use_case.GetUserFavouriteUseCase
import com.example.shopapp.domain.use_case.GetUserFavouritesUseCase
import com.example.shopapp.domain.use_case.GetUserOrdersUseCase
import com.example.shopapp.domain.use_case.GetUserPointsUseCase
import com.example.shopapp.domain.use_case.GetUserUseCase
import com.example.shopapp.domain.use_case.IsCouponExpiredUseCase
import com.example.shopapp.domain.use_case.LoginUseCase
import com.example.shopapp.domain.use_case.LogoutUseCase
import com.example.shopapp.domain.use_case.SetOrdersUseCase
import com.example.shopapp.domain.use_case.SetUserCartProductsUseCase
import com.example.shopapp.domain.use_case.SetUserFavouritesUseCase
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.domain.use_case.SignupUseCase
import com.example.shopapp.domain.use_case.SortOrdersUseCase
import com.example.shopapp.domain.use_case.SortProductsUseCase
import com.example.shopapp.domain.use_case.ToggleCheckBoxUseCase
import com.example.shopapp.domain.use_case.UpdateProductInCartUseCase
import com.example.shopapp.domain.use_case.UpdateUserPointsUseCase
import com.example.shopapp.domain.use_case.UpdateUserUseCase
import com.example.shopapp.domain.use_case.ValidateCityUseCase
import com.example.shopapp.domain.use_case.ValidateConfirmPasswordUseCase
import com.example.shopapp.domain.use_case.ValidateEmailUseCase
import com.example.shopapp.domain.use_case.ValidateLoginPasswordUseCase
import com.example.shopapp.domain.use_case.ValidateNameUseCase
import com.example.shopapp.domain.use_case.ValidateSignupPasswordUseCase
import com.example.shopapp.domain.use_case.ValidateStreetUseCase
import com.example.shopapp.domain.use_case.ValidateZipCodeUseCase
import com.example.shopapp.util.Constants
import com.example.shopapp.util.Constants.CARTS_COLLECTION
import com.example.shopapp.util.Constants.FAVOURITES_COLLECTION
import com.example.shopapp.util.Constants.ORDERS_COLLECTION
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
    fun provideCartRepository(): CartRepository {
        val cartsRef = Firebase.firestore.collection(CARTS_COLLECTION)
        return CartRepositoryImpl(cartsRef)
    }

    @Provides
    @Singleton
    fun provideOrdersRepository(): OrdersRepository {
        val ordersRef = Firebase.firestore.collection(ORDERS_COLLECTION)
        return OrdersRepositoryImpl(ordersRef)
    }

    @Provides
    @Singleton
    fun provideCouponsRepository(): CouponsRepository {
        val couponsRef = Firebase.firestore.collection(Constants.COUPONS_COLLECTION)
        return CouponsRepositoryImpl(couponsRef)
    }

    @Provides
    fun provideShopUseCases(
        productRepository: ProductRepository,
        authRepository: AuthRepository,
        userStorageRepository: UserStorageRepository,
        favouritesRepository: FavouritesRepository,
        cartRepository: CartRepository,
        ordersRepository: OrdersRepository,
        couponsRepository: CouponsRepository
    ): ShopUseCases {
        return ShopUseCases(
            getProductsUseCase = GetProductsUseCase(productRepository),
            getCategoriesUseCase = GetCategoriesUseCase(),
            getProductUseCase = GetProductUseCase(productRepository),
            validateEmailUseCase = ValidateEmailUseCase(),
            validateLoginPasswordUseCase = ValidateLoginPasswordUseCase(),
            validateSignupPasswordUseCase = ValidateSignupPasswordUseCase(),
            validateConfirmPasswordUseCase = ValidateConfirmPasswordUseCase(),
            validateNameUseCase = ValidateNameUseCase(),
            validateStreetUseCase = ValidateStreetUseCase(),
            validateCityUseCase = ValidateCityUseCase(),
            validateZipCodeUseCase = ValidateZipCodeUseCase(),
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
            sortProductsUseCase = SortProductsUseCase(),
            toggleCheckBoxUseCase = ToggleCheckBoxUseCase(),
            addProductToCartUseCase = AddProductToCartUseCase(cartRepository),
            getUserCartItemsUseCase = GetUserCartItemsUseCase(cartRepository),
            deleteProductFromCartUseCase = DeleteProductFromCartUseCase(cartRepository),
            updateProductInCartUseCase = UpdateProductInCartUseCase(cartRepository),
            getUserCartItemUseCase = GetUserCartItemUseCase(cartRepository),
            setUserCartProductsUseCase = SetUserCartProductsUseCase(),
            addOrderUseCase = AddOrderUseCase(ordersRepository),
            getUserOrdersUseCase = GetUserOrdersUseCase(ordersRepository),
            setOrdersUseCase = SetOrdersUseCase(),
            sortOrdersUseCase = SortOrdersUseCase(),
            getUserUseCase = GetUserUseCase(userStorageRepository),
            updateUserUseCase = UpdateUserUseCase(userStorageRepository),
            getUserPointsUseCase = GetUserPointsUseCase(userStorageRepository),
            updateUserPointsUseCase = UpdateUserPointsUseCase(userStorageRepository),
            addCouponUseCase = AddCouponUseCase(couponsRepository),
            getUserCouponUseCase = GetUserCouponUseCase(couponsRepository),
            deleteCouponUseCase = DeleteCouponUseCase(couponsRepository),
            isCouponExpiredUseCase = IsCouponExpiredUseCase(),
            getUserFavouriteUseCase = GetUserFavouriteUseCase(favouritesRepository)
        )
    }
}