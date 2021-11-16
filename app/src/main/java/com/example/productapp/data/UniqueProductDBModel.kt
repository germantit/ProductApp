package com.example.productapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "unique_item", indices = [Index(value = ["item"], unique = true)])
data class UniqueProductDBModel(
    @PrimaryKey
    @ColumnInfo(name = "item") val item: String,
    val useCount: Int
)
