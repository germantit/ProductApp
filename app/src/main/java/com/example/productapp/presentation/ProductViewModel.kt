package com.example.productapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.productapp.data.ProductListRepositoryImpl
import com.example.productapp.domain.usecase.DeleteProductItemUseCase
import com.example.productapp.domain.usecase.EditProductItemUseCase
import com.example.productapp.domain.usecase.GetProductListUseCase
import com.example.productapp.domain.ProductItem
import kotlinx.coroutines.*

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProductListRepositoryImpl(application)

    private val getProductListUseCase = GetProductListUseCase(repository)
    private val deleteProductItemUseCase = DeleteProductItemUseCase(repository)
    private val editProductItemUseCase = EditProductItemUseCase(repository)

    val productList = getProductListUseCase.getProductList()

    fun deleteProductItem(productItem: ProductItem) {
        viewModelScope.launch {
            deleteProductItemUseCase.deleteProductItem(productItem)
        }
    }

    fun changeEnableState(productItem: ProductItem) {
        viewModelScope.launch {
            val newItem = productItem.copy(enabled = !productItem.enabled)
            editProductItemUseCase.editProductItem(newItem)
        }
    }
}