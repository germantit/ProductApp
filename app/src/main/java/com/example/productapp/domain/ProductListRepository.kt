package com.example.productapp.domain

interface ProductListRepository {

    fun addProduct(productItem: ProductItem)

    fun deleteProductItem(productItem: ProductItem)

    fun editProductItem(productItem: ProductItem)

    fun getProductItem(productItemId: Int) : ProductItem

    fun getProductList(): List<ProductItem>
}