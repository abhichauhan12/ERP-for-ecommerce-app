package com.example.techbazaar.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat.getCategory
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.techbazaar.R
import com.example.techbazaar.databinding.ActivityCategoryProductBinding
import com.example.techbazaar.databinding.ActivityMainBinding
import com.example.techbazaar.model.AddProduct
import com.example.techbazaar.ui.adapter.CategoryProductAdapter
import com.example.techbazaar.ui.adapter.ProductAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


//using item_category_product_layout in recyclerView of categoryActivity
class CategoryProductActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCategoryProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityCategoryProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getProducts(intent.getStringExtra("cat"))

    }

    private fun getProducts(category: String?) {
        val list = ArrayList<AddProduct>()
        Firebase.firestore.collection("Products").whereEqualTo("productCategory",category)
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data = doc.toObject(AddProduct::class.java)
                    list.add(data!!)
                }
                binding.recyclerViewCategoryActivity.adapter = CategoryProductAdapter(this,list)
            }

    }
}