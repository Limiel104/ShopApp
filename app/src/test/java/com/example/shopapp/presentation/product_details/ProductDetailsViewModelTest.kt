package com.example.shopapp.presentation.product_details

import androidx.lifecycle.SavedStateHandle
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.MainDispatcherRule
import com.example.shopapp.util.Resource
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductDetailsViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var shopUseCases: ShopUseCases
    @MockK
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var productDetailsViewModel: ProductDetailsViewModel
    private lateinit var product: Product

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        product = Product(
            id = 1,
            title = "title 1",
            price = "123,99 PLN",
            description = "description of a product 1",
            category = "men's clothing",
            imageUrl = "url"
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    private fun setViewModel(): ProductDetailsViewModel {
        return ProductDetailsViewModel(savedStateHandle, shopUseCases)
    }

    private fun getCurrentProductDetailsState(): ProductDetailsState {
        return productDetailsViewModel.productDetailsState.value
    }

    @Test
    fun `product id is set correctly on init`() {
        every { savedStateHandle.get<Int>(any()) } returns 1
        coEvery {
            shopUseCases.getProductUseCase(any())
        } returns Resource.Success(product)

        productDetailsViewModel = setViewModel()

        val productId = getCurrentProductDetailsState().productId
        assertThat(productId).isEqualTo(1)

        verify { savedStateHandle.get<String>(any()) }
    }

    @Test
    fun `get product result is success`() {
        every { savedStateHandle.get<Int>(any()) } returns 1
        coEvery {
            shopUseCases.getProductUseCase(any())
        } returns Resource.Success(product)

        productDetailsViewModel = setViewModel()

        val productToCheck = Product(
            id = getCurrentProductDetailsState().productId,
            title = getCurrentProductDetailsState().title,
            price = getCurrentProductDetailsState().price,
            description = getCurrentProductDetailsState().description,
            category = getCurrentProductDetailsState().category,
            imageUrl = getCurrentProductDetailsState().imageUrl
        )
        assertThat(productToCheck).isEqualTo(product)

        coVerify { shopUseCases.getProductUseCase(any()) }
    }

    @Test
    fun `get product result is error`() {
        every { savedStateHandle.get<Int>(any()) } returns 1
        coEvery {
            shopUseCases.getProductUseCase(any())
        } returns Resource.Error("Error")

        productDetailsViewModel = setViewModel()

        val productId = getCurrentProductDetailsState().productId
        assertThat(productId).isEqualTo(product.id)

        val title = getCurrentProductDetailsState().title
        assertThat(title).isEqualTo("")

        val price = getCurrentProductDetailsState().price
        assertThat(price).isEqualTo("")

        val description = getCurrentProductDetailsState().description
        assertThat(description).isEqualTo("")

        val category = getCurrentProductDetailsState().category
        assertThat(category).isEqualTo("")

        val imageUrl = getCurrentProductDetailsState().imageUrl
        assertThat(imageUrl).isEqualTo("")

        coVerify { shopUseCases.getProductUseCase(any()) }
    }

    @Test
    fun `get product result is loading`() {
        every { savedStateHandle.get<Int>(any()) } returns 1
        coEvery {
            shopUseCases.getProductUseCase(any())
        } returns Resource.Loading(true)

        productDetailsViewModel = setViewModel()

        val productId = getCurrentProductDetailsState().productId
        assertThat(productId).isEqualTo(product.id)

        val title = getCurrentProductDetailsState().title
        assertThat(title).isEqualTo("")

        val price = getCurrentProductDetailsState().price
        assertThat(price).isEqualTo("")

        val description = getCurrentProductDetailsState().description
        assertThat(description).isEqualTo("")

        val category = getCurrentProductDetailsState().category
        assertThat(category).isEqualTo("")

        val imageUrl = getCurrentProductDetailsState().imageUrl
        assertThat(imageUrl).isEqualTo("")

        coVerify { shopUseCases.getProductUseCase(any()) }
    }
}