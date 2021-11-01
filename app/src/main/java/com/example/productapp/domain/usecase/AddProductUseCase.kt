package com.example.productapp.domain.usecase

import com.example.productapp.domain.ProductItem
import com.example.productapp.domain.ProductListRepository

class AddProductUseCase(private val productListRepository: ProductListRepository) {
    suspend fun addProduct(productItem: ProductItem) {
        productListRepository.addProduct(productItem)
    }
}