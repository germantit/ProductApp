package com.example.productapp.domain

import androidx.lifecycle.LiveData

interface ProductListRepository {

    suspend fun addProduct(productItem: ProductItem)

    suspend fun addUniqueProduct(uniqueProduct: UniqueProduct)

    suspend fun deleteProductItem(productItem: ProductItem)

    suspend fun editProductItem(productItem: ProductItem)

    suspend fun getProductItem(productItemId: Int) : ProductItem

    fun searchDatabase(searchQuery: String): LiveData<List<UniqueProduct>>

    fun getProductList(): LiveData<List<ProductItem>>

    fun getUniqueProductList(): LiveData<List<UniqueProduct>>
}