package com.example.productapp.domain.usecase

import androidx.lifecycle.LiveData
import com.example.productapp.domain.ProductListRepository
import com.example.productapp.domain.UniqueProduct

class GetUniqueProductListUseCase(private val productListRepository: ProductListRepository) {

    fun getUniqueProductList() : LiveData<List<UniqueProduct>> {
        return productListRepository.getUniqueProductList()
    }
}