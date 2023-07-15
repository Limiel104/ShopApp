package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.repository.ProductRepository
import com.google.common.truth.Truth.assertThat
import com.example.shopapp.util.Category
import com.example.shopapp.util.Constants.productDescription
import com.example.shopapp.util.Constants.productImageUrl
import com.example.shopapp.util.Constants.productPrice
import com.example.shopapp.util.Constants.productTitle
import com.example.shopapp.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
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
        this.getProductsUseCase = GetProductsUseCase(productRepository)

        productList = listOf(
            Product(
                id = 1,
                title = productTitle,
                price = productPrice,
                description = productDescription,
                category = Category.Women.id,
                imageUrl = productImageUrl
            ),
            Product(
                id = 2,
                title = productTitle,
                price = productPrice,
                description = productDescription,
                category = Category.Men.id,
                imageUrl = productImageUrl
            ),
            Product(
                id = 3,
                title = productTitle,
                price = productPrice,
                description = productDescription,
                category = Category.Jewelery.id,
                imageUrl = productImageUrl
            ),
            Product(
                id = 4,
                title = productTitle,
                price = productPrice,
                description = productDescription,
                category = Category.Men.id,
                imageUrl = productImageUrl
            )
        )
    }

    @Test
    fun `get products regardless of category id`() {
        runBlocking {
            coEvery { productRepository.getProducts(Category.All.id)
            } returns flowOf(
                Resource.Success(productList)
            )

            val products = getProductsUseCase(Category.All.id).first().data

            coVerify(exactly = 1) { getProductsUseCase(Category.All.id) }
            assertThat(products?.size).isEqualTo(4)
        }
    }

    @Test
    fun `get products from exactly one category`() {
        runBlocking {
            coEvery {
                productRepository.getProducts(Category.Men.id)
            } returns flowOf(
                Resource.Success(productList.filter { it.category == Category.Men.id })
            )

            val products = getProductsUseCase(Category.Men.id).first().data

            coVerify(exactly = 1) { getProductsUseCase(Category.Men.id) }
            assertThat(products?.size).isEqualTo(2)
        }
    }

    @Test
    fun `return empty list when no product from that category`() {
        runBlocking {
            coEvery {
                productRepository.getProducts(Category.Electronics.id)
            } returns flowOf(
                Resource.Success(productList.filter { it.category == Category.Electronics.id })
            )

            val products = getProductsUseCase(Category.Electronics.id).first().data

            coVerify(exactly = 1) { getProductsUseCase(Category.Electronics.id) }
            assertThat(products).isEmpty()
        }
    }

    @Test
    fun `return error and empty list`() {
        runBlocking {
            coEvery { productRepository.getProducts(any()) } returns flowOf(
                Resource.Error("Error")
            )

            val result = getProductsUseCase(Category.All.id).first()
            val errorMessage = result.message
            val products = result.data

            coVerify { getProductsUseCase(Category.All.id) }
            assertThat(errorMessage).isEqualTo("Error")
            assertThat(products).isNull()
        }
    }
}