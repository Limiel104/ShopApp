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
    val logoutUseCase: LogoutUseCase,
    val addUserUseCase: AddUserUseCase,
    val addProductToFavouritesUseCase: AddProductToFavouritesUseCase,
    val getUserFavouritesUseCase: GetUserFavouritesUseCase,
    val deleteProductFromFavouritesUseCase: DeleteProductFromFavouritesUseCase,
    val setUserFavouritesUseCase: SetUserFavouritesUseCase,
    val getFavouriteIdUseCase: GetFavouriteIdUseCase,
    val filterProductsByUserFavouritesUseCase: FilterProductsByUserFavouritesUseCase,
    val filterProductsUseCase: FilterProductsUseCase,
    val sortProductsUseCase: SortProductsUseCase,
    val toggleCheckBoxUseCase: ToggleCheckBoxUseCase,
    val addProductToCartUseCase: AddProductToCartUseCase,
    val getUserCartItemsUseCase: GetUserCartItemsUseCase,
    val deleteProductFromCartUseCase: DeleteProductFromCartUseCase,
    val updateProductInCartUseCase: UpdateProductInCartUseCase
)