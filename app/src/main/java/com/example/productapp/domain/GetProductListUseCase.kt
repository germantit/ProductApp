package com.example.productapp.domain

class GetProductListUseCase(private val productListRepository: ProductListRepository) {

    fun getProductList(): List<ProductItem> {
        return productListRepository.getProductList()
    }
}