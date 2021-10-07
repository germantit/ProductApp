package com.example.productapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.productapp.domain.ProductItem
import com.example.productapp.domain.ProductListRepository
import java.lang.RuntimeException

object ProductListRepositoryImpl: ProductListRepository {

    private val productList = mutableListOf<ProductItem>()
    private val productListLD = MutableLiveData<List<ProductItem>>()

    private var autoIncrementId = 0

    init {
        for (i in 0 until 10) {
            val item = ProductItem("Name$i", i)
            addProduct(item)
        }
    }

    override fun addProduct(productItem: ProductItem) {
        if (productItem.id == ProductItem.UNDEFINED_ID){
            productItem.id = autoIncrementId++
        }
        productList.add(productItem)
        updateList()
    }

    override fun deleteProductItem(productItem: ProductItem) {
        productList.remove(productItem)
        updateList()
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

    override fun getProductList(): LiveData<List<ProductItem>> {
        return productListLD
    }

    private fun updateList() {
        productListLD.value = productList.toList()
    }
}