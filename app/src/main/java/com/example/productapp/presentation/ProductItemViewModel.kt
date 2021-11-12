package com.example.productapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.productapp.data.ProductListRepositoryImpl
import com.example.productapp.domain.ProductItem
import com.example.productapp.domain.UniqueProduct
import com.example.productapp.domain.usecase.*
import kotlinx.coroutines.launch

class ProductItemViewModel(application: Application): AndroidViewModel(application) {

    private val repository = ProductListRepositoryImpl(application)

    private val getProductItemUseCase = GetProductItemUseCase(repository)
    private val addProductUseCase = AddProductUseCase(repository)
    private val addUniqueProductUseCase = AddUniqueProductUseCase(repository)
    private val editProductItemUseCase = EditProductItemUseCase(repository)
    private val searchQueryUseCase = SearchQueryUseCase(repository)

    private val errorCount = 0

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
        viewModelScope.launch {
            val item = getProductItemUseCase.getProductItem(productItemId)
            _productItem.value = item
        }
    }

    fun addProductItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            viewModelScope.launch {
                val productItem = ProductItem(name, count, enabled = true)
                addProductUseCase.addProduct(productItem)
                finishWork()
            }
        }
    }

    fun addUniqueProduct(inputName: String?) {
        val name = parseName(inputName)
        val fieldsValid = validateInput(name)
        if (fieldsValid) {
            viewModelScope.launch {
                val uniqueProduct = UniqueProduct(item = name)
                addUniqueProductUseCase.addUniqueProduct(uniqueProduct)
                finishWork()
            }
        }
    }

    fun editProductItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            _productItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(name = name, count = count)
                    editProductItemUseCase.editProductItem(item)
                    finishWork()
                }
            }
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<UniqueProduct>> {
        return searchQueryUseCase.searchQuery(searchQuery)
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?) : Int {
        return try {
            inputCount?.trim()?.toInt()?:0
        } catch (e: Exception) {
            errorCount
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

    private fun validateInput(name: String): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}