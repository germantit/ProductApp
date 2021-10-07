package com.example.productapp.domain

import androidx.lifecycle.LiveData

interface ProductListRepository {

    fun addProduct(productItem: ProductItem)

    fun deleteProductItem(productItem: ProductItem)

    fun editProductItem(productItem: ProductItem)

    fun getProductItem(productItemId: Int) : ProductItem

    fun getProductList(): LiveData<List<ProductItem>>
}