package com.example.productapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.productapp.data.ProductListRepositoryImpl
import com.example.productapp.domain.AddProductUseCase
import com.example.productapp.domain.EditProductItemUseCase
import com.example.productapp.domain.GetProductItemUseCase
import com.example.productapp.domain.ProductItem
import kotlinx.coroutines.launch

class ProductItemViewModel(application: Application): AndroidViewModel(application) {

    private val repository = ProductListRepositoryImpl(application)

    private val getProductItemUseCase = GetProductItemUseCase(repository)
    private val addProductUseCase = AddProductUseCase(repository)
    private val editProductItemUseCase = EditProductItemUseCase(repository)

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
                val productItem = ProductItem(name, count)
                addProductUseCase.addProduct(productItem)
                finishWork()
            }
        }
    }

    fun editProductItem(inputName: String?, inputCount: String?, defaultCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val defCount = parseCount(defaultCount)
        val fieldsValid = validateInputDifference(name, count, defCount)
        if (fieldsValid) {
            _productItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(name = name, count = defCount - count)
                    editProductItemUseCase.editProductItem(item)
                    finishWork()
                }
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
            errorCount
        }
    }

    private fun validateInputDifference(name: String, count: Int, defCount: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        if (count > defCount) {
            _errorInputCount.value = true
            result = false
        }
        return result
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
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}