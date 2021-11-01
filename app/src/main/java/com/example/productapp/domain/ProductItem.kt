package com.example.productapp.domain

data class ProductItem(
        val name: String,
        val count: Int,
        var id: Int = UNDEFINED_ID,
        val enabled: Boolean
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}
