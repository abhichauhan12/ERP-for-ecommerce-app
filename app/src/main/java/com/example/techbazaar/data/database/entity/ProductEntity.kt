package com.example.techbazaar.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "products")
data class ProductEntity(

    @PrimaryKey
    @Nonnull
    @ColumnInfo(name = "productID")
    var productID: String,

    @ColumnInfo(name = "productName")
    val productName : String?="",

    @ColumnInfo(name = "productSellingPrice")
    val productSellingPrice: String?="",

    @ColumnInfo(name = "productCoverImages")
    val productCoverImages:String?=""


    )
