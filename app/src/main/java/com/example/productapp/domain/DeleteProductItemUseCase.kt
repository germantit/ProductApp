package com.example.productapp.domain

class DeleteProductItemUseCase(private val productListRepository: ProductListRepository) {
    fun deleteProductItem(productItem: ProductItem) {
        productListRepository.deleteProductItem(productItem)
    }
}