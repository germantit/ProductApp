package com.example.productapp.domain.usecase

import androidx.lifecycle.LiveData
import com.example.productapp.domain.ProductItem
import com.example.productapp.domain.ProductListRepository

class GetProductListUseCase(private val productListRepository: ProductListRepository) {

    fun getProductList(): LiveData<List<ProductItem>> {
        return productListRepository.getProductList()
    }
}