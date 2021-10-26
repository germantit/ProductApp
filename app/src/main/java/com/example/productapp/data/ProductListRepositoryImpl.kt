package com.example.productapp.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.productapp.data.database.AppDatabase
import com.example.productapp.domain.ProductItem
import com.example.productapp.domain.ProductListRepository

class ProductListRepositoryImpl(
    application: Application
): ProductListRepository {

    private val productListDao = AppDatabase.getInstance(application).productListDao()
    private val mapper = ProductListMapper()


    override suspend fun addProduct(productItem: ProductItem) {
        productListDao.addProductItem(mapper.mapEntityToDbModel(productItem))
    }

    override suspend fun deleteProductItem(productItem: ProductItem) {
        productListDao.deleteProductItem(productItem.id)
    }

    override suspend fun editProductItem(productItem: ProductItem) {
        productListDao.addProductItem(mapper.mapEntityToDbModel(productItem))
    }

    override suspend fun getProductItem(productItemId: Int): ProductItem {
        val dbModel = productListDao.getProductItem(productItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getProductList(): LiveData<List<ProductItem>> {
        return MediatorLiveData<List<ProductItem>>().apply {
            addSource(productListDao.getProductList()) {
                value = mapper.mapListDbModelToListEntity(it)
            }
        }
    }
}