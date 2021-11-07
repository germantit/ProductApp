package com.example.productapp.domain.usecase

import androidx.lifecycle.LiveData
import com.example.productapp.domain.ProductItem
import com.example.productapp.domain.ProductListRepository

class SearchQueryUseCase(private val productListRepository: ProductListRepository) {

    fun searchQuery(searchQuery: String): LiveData<List<ProductItem>> {
        return productListRepository.searchDatabase(searchQuery)
    }
}