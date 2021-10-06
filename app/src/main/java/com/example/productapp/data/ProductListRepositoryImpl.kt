package com.example.productapp.data

import com.example.productapp.domain.ProductItem
import com.example.productapp.domain.ProductListRepository
import java.lang.RuntimeException

object ProductListRepositoryImpl: ProductListRepository {

    private val productList = mutableListOf<ProductItem>()

    private var autoIncrementId = 0

    override fun addProduct(productItem: ProductItem) {
        if (productItem.id == ProductItem.UNDEFINED_ID){
            productItem.id = autoIncrementId++
        }
        productItem.id = autoIncrementId++
        productList.add(productItem)
    }

    override fun deleteProductItem(productItem: ProductItem) {
        productList.remove(productItem)
    }

    override fun editProductItem(productItem: ProductItem) {
        val oldElement = getProductItem(productItem.id)
        productList.remove(oldElement)
        addProduct(productItem)
    }

    override fun getProductItem(productItemId: Int): ProductItem {
        return productList.find {
            it.id == productItemId } ?: throw RuntimeException("Element with id $productItemId not found")
    }

    override fun getProductList(): List<ProductItem> {
        return productList.toList()
    }
}