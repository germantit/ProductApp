package com.example.productapp.domain

class GetProductItemUseCase(private val productListRepository: ProductListRepository) {
    fun getProductItem(productItemId: Int) : ProductItem {
        return productListRepository.getProductItem(productItemId)
    }
}