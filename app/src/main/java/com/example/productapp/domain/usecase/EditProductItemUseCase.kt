package com.example.productapp.domain.usecase

import com.example.productapp.domain.ProductItem
import com.example.productapp.domain.ProductListRepository

class EditProductItemUseCase(private val productListRepository: ProductListRepository) {
    suspend fun editProductItem(productItem: ProductItem){
        productListRepository.editProductItem(productItem)
    }
}