package com.example.techbazaar.model

data class AddProduct(
    val productName : String?="",
    val productDescription: String?="",
    val productCoverImg: String?="",
    val productCategory: String?="",
    val productId: String?="",
    val productMrp: String?="",
    val productSellingPrice: String?="",
    val productImages:ArrayList<String> =ArrayList()

    )
