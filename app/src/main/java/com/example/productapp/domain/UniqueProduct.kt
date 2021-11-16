package com.example.productapp.domain

data class UniqueProduct(
    val item: String,
    val useCount: Int = DEFAULT_COUNT
) : Comparable<UniqueProduct> {
    companion object {
        const val DEFAULT_COUNT = 1
    }

    override fun compareTo(other: UniqueProduct): Int {
        return other.useCount - useCount
    }
}
