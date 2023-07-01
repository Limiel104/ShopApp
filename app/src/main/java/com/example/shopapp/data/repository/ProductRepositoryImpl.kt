package com.example.shopapp.data.repository

import com.example.shopapp.data.local.ProductDao
import com.example.shopapp.data.mapper.toProduct
import com.example.shopapp.data.mapper.toProductEntity
import com.example.shopapp.data.remote.FakeShopApi
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.repository.ProductRepository
import com.example.shopapp.util.Constants.all
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val api: FakeShopApi,
    private val dao: ProductDao
): ProductRepository {

    override suspend fun getProducts(): Flow<Resource<List<Product>>> {
        return flow {
            emit(Resource.Loading(true))

            val products = dao.getProducts().map { it.toProduct() }
            val loadFromCache = products.isNotEmpty()

            if (loadFromCache) {
                emit(Resource.Success(products))
                emit(Resource.Loading(false))
                return@flow
            }

            val productsFromRemote = try {
                api.getProducts().map { it.toProduct() }
            }
            catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(e.message.toString()))
                null
            }
            catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(e.message()))
                null
            }

            productsFromRemote?.let { productList ->
                dao.deleteProducts()
                dao.insertProducts(productList.map { it.toProductEntity() })
                emit(Resource.Success(dao.getProducts().map { it.toProduct() }))
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getProductsFromCategory(categoryId: String): Flow<Resource<List<Product>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val products = api.getProductsFromCategory(categoryId).map { it.toProduct() }
                emit(Resource.Success(products))
                emit(Resource.Loading(false))
            }
            catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(e.message.toString()))
            }
            catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(e.message()))
            }
        }
    }

    override suspend fun getProduct(productId: Int): Resource<Product> {
        return try {
            val product = api.getProduct(productId)
            Resource.Success(product.toProduct())
        }
        catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(e.message.toString())
        }
        catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(e.message())
        }
    }

    override suspend fun getCategories(): Flow<Resource<List<String>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val categories: MutableList<String> = mutableListOf()
                categories.addAll(api.getCategories())
                categories.add(all)
                emit(Resource.Success(categories))
                emit(Resource.Loading(false))
            }
            catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(e.message.toString()))
            }
            catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(e.message()))
            }
        }
    }
}