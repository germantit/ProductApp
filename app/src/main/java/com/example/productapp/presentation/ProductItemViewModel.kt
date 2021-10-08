package com.example.productapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.productapp.data.ProductListRepositoryImpl
import com.example.productapp.domain.*
import java.lang.Exception

class ProductItemViewModel: ViewModel() {

    private val repository = ProductListRepositoryImpl

    private val getProductItemUseCase = GetProductItemUseCase(repository)
    private val addProductUseCase = AddProductUseCase(repository)
    private val editProductItemUseCase = EditProductItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _productItem = MutableLiveData<ProductItem>()
    val productItem: LiveData<ProductItem>
        get() = _productItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getProductItem(productItemId: Int) {
        val item = getProductItemUseCase.getProductItem(productItemId)
        _productItem.value = item
    }

    fun addProductItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            val productItem = ProductItem(name, count)
            addProductUseCase.addProduct(productItem)
            finishWork()
        }
    }

    fun editProductItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            _productItem.value?.let {
                val item = it.copy(name = name, count = count)
                editProductItemUseCase.editProductItem(item)
                finishWork()
            }
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?) : Int {
        return try {
            inputCount?.trim()?.toInt()?:0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value =true
    }

    fun resetErrorInputCount() {
        _errorInputCount.value =true
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }

}