package com.example.productapp.domain

class AddProductUseCase(private val productListRepository: ProductListRepository) {
    suspend fun addProduct(productItem: ProductItem) {
        productListRepository.addProduct(productItem)
    }
}