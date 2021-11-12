package com.example.productapp.domain.usecase

import androidx.lifecycle.LiveData
import com.example.productapp.domain.ProductItem
import com.example.productapp.domain.ProductListRepository
import com.example.productapp.domain.UniqueProduct

class SearchQueryUseCase(private val productListRepository: ProductListRepository) {

    fun searchQuery(searchQuery: String): LiveData<List<UniqueProduct>> {
        return productListRepository.searchDatabase(searchQuery)
    }
}