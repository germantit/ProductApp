package com.example.productapp.domain.usecase

import com.example.productapp.domain.ProductItem
import com.example.productapp.domain.ProductListRepository

class GetProductItemUseCase(private val productListRepository: ProductListRepository) {
    suspend fun getProductItem(productItemId: Int) : ProductItem {
        return productListRepository.getProductItem(productItemId)
    }
}