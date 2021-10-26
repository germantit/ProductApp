package com.example.productapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_items")
data class ProductItemDbModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val name: String,
    val count: Int
)
