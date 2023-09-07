package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.repository.ProductRepository
import com.example.shopapp.util.Category
import com.example.shopapp.util.Constants.productDescription
import com.example.shopapp.util.Resource
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetProductUseCaseTest {

    @MockK
    private lateinit var productRepository: ProductRepository
    private lateinit var getProductUseCase: GetProductUseCase
    private lateinit var product: Product

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getProductUseCase = GetProductUseCase(productRepository)

        product = Product(
            id = 1,
            title = "Shirt",
            price = 195.59,
            description = productDescription,
            category = Category.Women.id,
            imageUrl = "imageUrl",
            isInFavourites = false
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `return product with correct id`() {
        runBlocking {
            coEvery { productRepository.getProduct(1) } returns Resource.Success(product)

            val product = getProductUseCase(1).data

            coVerify(exactly = 1) { getProductUseCase(1) }
            assertThat(product).isNotNull()
            assertThat(product).isEqualTo(product)
        }
    }

    @Test
    fun `return error if no product found`() {
        runBlocking {
            coEvery { productRepository.getProduct(1) } returns Resource.Error("No product found with this id")

            val result = getProductUseCase(1)
            val errorMessage = result.message
            val product = result.data

            coVerify(exactly = 1) { getProductUseCase(1) }
            assertThat(errorMessage).isEqualTo("No product found with this id")
            assertThat(product).isNull()
        }
    }
}