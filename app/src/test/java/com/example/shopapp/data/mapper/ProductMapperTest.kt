package com.example.shopapp.data.mapper

import com.example.shopapp.data.local.ProductEntity
import com.example.shopapp.data.remote.ProductDto
import com.example.shopapp.domain.model.Product
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ProductMapperTest {

    private lateinit var productDto: ProductDto
    private lateinit var product: Product
    private lateinit var productEntity: ProductEntity

    @Before
    fun setUp() {

        productDto = ProductDto(
            id = 1,
            title = "Shirt",
            price = 159.99,
            description = "Product description",
            category = "men's clothing",
            imageUrl = "imageUrl"
        )

        product = Product(
            id = 1,
            title = "Shirt",
            price = "159,99 PLN",
            description = "Product description",
            category = "men's clothing",
            imageUrl = "imageUrl",
            isInFavourites = false
        )

        productEntity = ProductEntity(
            id = 1,
            title = "Shirt",
            price = 159.99,
            description = "Product description",
            category = "men's clothing",
            imageUrl = "imageUrl",
            isInFavourites = false
        )
    }

    @Test
    fun `productDto can be mapped to product`() {
        val mappedProduct = productDto.toProduct()
        assertThat(mappedProduct).isEqualTo(product)
    }

    @Test
    fun `productEntity can be mapped to product`() {
        val mappedProduct = productEntity.toProduct()
        assertThat(mappedProduct).isEqualTo(product)
    }

    @Test
    fun `product can be mapped to productEntity`() {
        val mappedProduct = product.toProductEntity()
        assertThat(mappedProduct).isEqualTo(productEntity)
    }
}