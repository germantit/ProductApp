package com.example.productapp.domain.usecase

import com.example.productapp.domain.ProductItem
import com.example.productapp.domain.ProductListRepository

class DeleteProductItemUseCase(private val productListRepository: ProductListRepository) {
    suspend fun deleteProductItem(productItem: ProductItem) {
        productListRepository.deleteProductItem(productItem)
    }
}