package com.example.shopapp.domain.use_case

data class ShopUseCases(
    val getProductsUseCase: GetProductsUseCase,
    val getCategoriesUseCase: GetCategoriesUseCase,
    val getProductUseCase: GetProductUseCase,
    val validateEmailUseCase: ValidateEmailUseCase,
    val validateLoginPasswordUseCase: ValidateLoginPasswordUseCase,
    val validateSignupPasswordUseCase: ValidateSignupPasswordUseCase,
    val validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase,
    val getCurrentUserUseCase: GetCurrentUserUseCase,
    val loginUseCase: LoginUseCase,
    val signupUseCase: SignupUseCase,
    val logoutUseCase: LogoutUseCase
)