package com.example.techbazaar.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.techbazaar.MainActivity
import com.example.techbazaar.R
import com.example.techbazaar.data.database.AppDatabase
import com.example.techbazaar.data.database.dao.ProductDao
import com.example.techbazaar.data.database.entity.ProductEntity
import com.example.techbazaar.databinding.ActivityProductDetailBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding :ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProductDetailBinding.inflate(layoutInflater)

        getProductDetail(intent.getStringExtra("id"))

        setContentView(binding.root)
    }

    private fun getProductDetail(productID: String?) {
        Firebase.firestore.collection("Products")
            .document(productID!!).get().addOnSuccessListener {
                val listOfProductImages = it.get("productImages") as ArrayList<String>
                val productName=it.getString("productName")
                val productSellingPrice=it.getString("productSellingPrice")
                val productMRP=it.getString("productMrp")
                val productDescription=it.getString("productDescription")
                val productCoverImg=it.getString("productCoverImg")
                binding.textViewProductName.text=productName
                binding.textViewProductPrice.text=productSellingPrice
                binding.textViewProductMRP.text=productMRP
                binding.textViewDescription.text=productDescription

                val listSliderImage = ArrayList<SlideModel>()
                for (data in listOfProductImages){
                    listSliderImage.add(SlideModel(data,ScaleTypes.CENTER_CROP))
                }


                cartAction(productID,productName,productSellingPrice,productCoverImg)
                binding.imageSlider.setImageList(listSliderImage)
            }.addOnFailureListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

    }

    private fun cartAction(
        productID: String,
        productName: String?,
        productSellingPrice: String?,
        productCoverImg: String?
    ) {
        val productDao=AppDatabase.getInstance(this).productDao()

        if (productDao.isExist(productID)!= null){
            binding.buttonAddToCart.text = "Go to Cart"

        }else{
            binding.buttonAddToCart.text = "Add Cart"
        }

        binding.buttonAddToCart.setOnClickListener {
            if (productDao.isExist(productID)!= null){
                openCart()

            }else{
                addToCart(productDao,productID,productName,productSellingPrice,productCoverImg)
            }
        }
    }

    private fun addToCart(productDao: ProductDao, productID: String, productName: String?, productSellingPrice: String?, productCoverImg: String?) {
        val data =ProductEntity(productID,productName,productSellingPrice,productCoverImg)
        lifecycleScope.launch (Dispatchers.IO)  {
            productDao.insertProduct(data)
            binding.buttonAddToCart.text = "Go to Cart"
        }

    }

    private fun openCart() {
        val preferences = this.getSharedPreferences("info", MODE_PRIVATE)
        val editor =preferences.edit()
        editor.putBoolean("isCart",true)
        editor.apply()

        startActivity(Intent(this,MainActivity::class.java))
        finish()

    }


}