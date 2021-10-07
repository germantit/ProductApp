package com.example.productapp.presentation

import androidx.lifecycle.ViewModel
import com.example.productapp.data.ProductListRepositoryImpl
import com.example.productapp.domain.DeleteProductItemUseCase
import com.example.productapp.domain.EditProductItemUseCase
import com.example.productapp.domain.GetProductListUseCase
import com.example.productapp.domain.ProductItem

class ProductViewModel : ViewModel() {

    private val repository = ProductListRepositoryImpl

    private val getProductListUseCase = GetProductListUseCase(repository)
    private val deleteProductItemUseCase = DeleteProductItemUseCase(repository)
    private val editProductItemUseCase = EditProductItemUseCase(repository)

    val productList = getProductListUseCase.getProductList()

    fun deleteProductItem(productItem: ProductItem) {
        deleteProductItemUseCase.deleteProductItem(productItem)
    }
}