package com.example.productapp.domain

data class UniqueProduct(
    var id: Int = UNDEFINED_ID,
    val item: String,
    val useCount: Int = DEFAULT_COUNT
) {
    companion object {
        const val UNDEFINED_ID = 0
        const val DEFAULT_COUNT = 1
    }
}
