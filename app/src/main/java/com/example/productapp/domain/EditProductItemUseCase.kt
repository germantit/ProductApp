package com.example.productapp.domain

class EditProductItemUseCase(private val productListRepository: ProductListRepository) {
    fun editProductItem(productItem: ProductItem){
        productListRepository.editProductItem(productItem)
    }
}