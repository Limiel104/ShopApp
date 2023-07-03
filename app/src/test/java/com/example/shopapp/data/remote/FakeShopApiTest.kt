package com.example.shopapp.data.remote

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FakeShopApiTest {

    private lateinit var api: FakeShopApi
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FakeShopApi::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    private fun enqueueMockResponse(fileName: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun sentRequestForProductsFromApi_receivedExpected() {
        runBlocking {
            enqueueMockResponse("fakeShopResponse.json")
            val products = api.getProducts()
            val request = server.takeRequest()
            assertThat(products).isNotEmpty()
            assertThat(request.path).isEqualTo("/products")
        }
    }

    @Test
    fun returnedQuestionsHaveCorrectContent() {
        runBlocking {
            enqueueMockResponse("fakeShopResponse.json")
            val products = api.getProducts()
            val product = products[0]

            assertThat(product.id).isEqualTo(1)
            assertThat(product.title).isEqualTo("Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops")
            assertThat(product.price).isEqualTo(109.95)
            assertThat(product.description).isEqualTo("Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday")
            assertThat(product.category).isEqualTo("men's clothing")
            assertThat(product.imageUrl).isEqualTo("https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg")
        }
    }
}