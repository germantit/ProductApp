package com.example.productapp.domain.usecase

import com.example.productapp.domain.ProductListRepository
import com.example.productapp.domain.UniqueProduct

class AddUniqueProductUseCase(private val productListRepository: ProductListRepository) {
    suspend fun addUniqueProduct(uniqueProduct: UniqueProduct) {
        productListRepository.addUniqueProduct(uniqueProduct)
    }
}