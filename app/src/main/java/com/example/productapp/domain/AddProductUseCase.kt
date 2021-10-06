package com.example.productapp.domain

class AddProductUseCase(private val productListRepository: ProductListRepository) {
    fun addProduct(productItem: ProductItem) {
        productListRepository.addProduct(productItem)
    }
}