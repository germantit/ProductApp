package com.example.productapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.productapp.data.ProductItemDbModel
import com.example.productapp.data.UniqueProductDBModel

@Dao
interface ProductListDao {

    @Query("SELECT * FROM product_items ORDER BY enabled DESC")
    fun getProductList(): LiveData<List<ProductItemDbModel>>

    @Query("SELECT * FROM unique_item ORDER BY useCount DESC")
    fun getUniqueProductList(): LiveData<List<UniqueProductDBModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProductItem(productItemDbModel: ProductItemDbModel)

    @Insert
    suspend fun addUniqueProduct(uniqueProductDBModel: UniqueProductDBModel)

    @Update
    suspend fun updateUniqueProduct(uniqueProductDBModel: UniqueProductDBModel)

    @Query("DELETE FROM product_items WHERE id=:productItemId")
    suspend fun deleteProductItem(productItemId: Int)

    @Query("SELECT * FROM product_items WHERE id=:productItemId LIMIT 1")
    suspend fun getProductItem(productItemId: Int): ProductItemDbModel

    @Query("SELECT * FROM unique_item WHERE item LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<UniqueProductDBModel>>


}