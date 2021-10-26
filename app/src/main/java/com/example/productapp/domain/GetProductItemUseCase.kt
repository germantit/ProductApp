package com.example.productapp.domain

class GetProductItemUseCase(private val productListRepository: ProductListRepository) {
    suspend fun getProductItem(productItemId: Int) : ProductItem {
        return productListRepository.getProductItem(productItemId)
    }
}