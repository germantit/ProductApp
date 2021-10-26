package com.example.productapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.productapp.data.ProductItemDbModel

@Dao
interface ProductListDao {

    @Query("SELECT * FROM product_items")
    fun getProductList(): LiveData<List<ProductItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProductItem(productItemDbModel: ProductItemDbModel)

    @Query("DELETE FROM product_items WHERE id=:productItemId")
    suspend fun deleteProductItem(productItemId: Int)

    @Query("SELECT * FROM product_items WHERE id=:productItemId LIMIT 1")
    suspend fun getProductItem(productItemId: Int): ProductItemDbModel
}