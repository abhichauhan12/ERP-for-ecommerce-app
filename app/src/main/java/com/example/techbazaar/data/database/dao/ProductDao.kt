package com.example.techbazaar.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.techbazaar.data.database.entity.ProductEntity

@Dao
interface ProductDao {

    @Insert
    suspend fun insertProduct(product: ProductEntity)

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    @Query("SELECT * FROm products")
    fun getAllProduct() :LiveData<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE productID = :id ")
    fun isExist(id:String) :ProductEntity
}