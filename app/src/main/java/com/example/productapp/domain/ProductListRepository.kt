package com.example.productapp.domain

import androidx.lifecycle.LiveData

interface ProductListRepository {

    suspend fun addProduct(productItem: ProductItem)

    suspend fun deleteProductItem(productItem: ProductItem)

    suspend fun editProductItem(productItem: ProductItem)

    suspend fun getProductItem(productItemId: Int) : ProductItem

    fun searchDatabase(searchQuery: String): LiveData<List<ProductItem>>

    fun getProductList(): LiveData<List<ProductItem>>
}