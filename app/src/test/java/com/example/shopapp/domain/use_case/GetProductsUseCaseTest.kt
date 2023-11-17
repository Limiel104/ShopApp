package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.repository.ProductRepository
import com.google.common.truth.Truth.assertThat
import com.example.shopapp.util.Constants.productDescription
import com.example.shopapp.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetProductsUseCaseTest {

    @MockK
    private lateinit var productRepository: ProductRepository
    private lateinit var getProductsUseCase: GetProductsUseCase
    private lateinit var productList: List<Product>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getProductsUseCase = GetProductsUseCase(productRepository)

        productList = listOf(
            Product(
                id = 1,
                title = "Shirt",
                price = 195.59,
                description = productDescription,
                category = "women's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 2,
                title = "Shirt",
                price = 195.59,
                description = productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 3,
                title = "Shirt",
                price = 195.59,
                description = productDescription,
                category = "jewelery",
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 4,
                title = "Shirt",
                price = 195.59,
                description = productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            )
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `get products regardless of category id`() {
        runBlocking {
            coEvery { productRepository.getProducts("all")
            } returns flowOf(
                Resource.Success(productList)
            )

            val products = getProductsUseCase("all").first().data

            coVerify(exactly = 1) { getProductsUseCase("all") }
            assertThat(products?.size).isEqualTo(4)
        }
    }

    @Test
    fun `get products from exactly one category`() {
        runBlocking {
            coEvery {
                productRepository.getProducts("men's clothing")
            } returns flowOf(
                Resource.Success(productList.filter { it.category == "men's clothing" })
            )

            val products = getProductsUseCase("men's clothing").first().data

            coVerify(exactly = 1) { getProductsUseCase("men's clothing") }
            assertThat(products).isNotNull()
            assertThat(products?.size).isEqualTo(2)
            if (products != null) {
                for(product in products) {
                    assertThat(product.category).isEqualTo("men's clothing")
                }
            }
        }
    }

    @Test
    fun `return empty list when no product from that category`() {
        runBlocking {
            coEvery {
                productRepository.getProducts("electronics")
            } returns flowOf(
                Resource.Success(productList.filter { it.category == "electronics" })
            )

            val products = getProductsUseCase("electronics").first().data

            coVerify(exactly = 1) { getProductsUseCase("electronics") }
            assertThat(products).isEmpty()
        }
    }

    @Test
    fun `return error and empty list`() {
        runBlocking {
            coEvery { productRepository.getProducts(any()) } returns flowOf(
                Resource.Error("Error")
            )

            val result = getProductsUseCase("all").first()
            val errorMessage = result.message
            val products = result.data

            coVerify { getProductsUseCase("all") }
            assertThat(errorMessage).isEqualTo("Error")
            assertThat(products).isNull()
        }
    }
}