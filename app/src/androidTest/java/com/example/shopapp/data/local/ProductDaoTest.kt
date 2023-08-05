package com.example.shopapp.data.local

import com.example.shopapp.di.AppModule
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(AppModule::class)
class ProductDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var db: ShopDatabase
    private lateinit var dao: ProductDao
    private lateinit var productList: List<ProductEntity>

    @Before
    fun setUp() {
        hiltRule.inject()
        dao = db.productDao

        productList = listOf(
            ProductEntity(
                id = 1,
                title = "title 1",
                price = 123.99,
                description = "description of a product 1",
                category = "men's clothing",
                imageUrl = "url",
                isInFavourites = false
            ),
            ProductEntity(
                id = 2,
                title = "title 2",
                price = 41.99,
                description = "description of a product 2",
                category = "women's clothing",
                imageUrl = "url",
                isInFavourites = false
            ),
            ProductEntity(
                id = 3,
                title = "title 3",
                price = 34.99,
                description = "description of a product 3",
                category = "men's clothing",
                imageUrl = "url",
                isInFavourites = false
            ),
            ProductEntity(
                id = 4,
                title = "title 4",
                price = 100.12,
                description = "description of a product 4",
                category = "men's clothing",
                imageUrl = "url",
                isInFavourites = false
            ),
            ProductEntity(
                id = 5,
                title = "title 5",
                price = 44.38,
                description = "description of a product 5",
                category = "jewelery",
                imageUrl = "url",
                isInFavourites = false
            )
        )
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertProducts_productsAddedCorrectly() {
        runBlocking {
            dao.insertProducts(productList)
            val products = dao.getProducts()

            assertThat(products).isEqualTo(products)
            assertThat(products.size).isEqualTo(5)
        }
    }

    @Test
    fun getProductsFromCategory_returnOnlyProductsFromSelectedCategory() {
        runBlocking {
            dao.insertProducts(productList)
            val products = dao.getProductsFromCategory("men's clothing")

            assertThat(products).containsExactly(productList[0],productList[2],productList[3])
            assertThat(products.size).isEqualTo(3)
        }
    }

    @Test
    fun getProductsFromCategory_returnEmptyListWhenNoProductsFoundFromSelectedCategory() {
        runBlocking {
            dao.insertProducts(productList)
            val products = dao.getProductsFromCategory("electronics")

            assertThat(products).isEmpty()
        }
    }

    @Test
    fun getProduct_returnSelectedProduct() {
        runBlocking {
            dao.insertProducts(productList)
            val product = dao.getProduct(1)

            assertThat(product).isEqualTo(productList[0])
            assertThat(product).isInstanceOf(ProductEntity::class.java)
        }
    }

    @Test
    fun getProduct_returnNothingIfSelectedProductNotFound() {
        runBlocking {
            dao.insertProducts(productList)
            val product = dao.getProduct(6)

            assertThat(product).isNull()
        }
    }

    @Test
    fun deleteProducts_deletesAllProductsFromDbCorrectly() {
        runBlocking {
            dao.insertProducts(productList)
            val initialProducts = dao.getProducts()

            assertThat(initialProducts).isNotEmpty()
            assertThat(initialProducts.size).isEqualTo(5)

            dao.deleteProducts()
            val resultProducts = dao.getProducts()

            assertThat(resultProducts).isEmpty()
        }
    }
}