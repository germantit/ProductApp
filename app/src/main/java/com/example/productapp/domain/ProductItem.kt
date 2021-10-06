package com.example.productapp.domain

data class ProductItem(
        val name: String,
        val count: Int,
        var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}
