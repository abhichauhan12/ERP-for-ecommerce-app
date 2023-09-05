package com.example.techbazaar.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat.getCategory
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.techbazaar.R
import com.example.techbazaar.databinding.FragmentHomeBinding
import com.example.techbazaar.databinding.ItemCategoryLayoutBinding
import com.example.techbazaar.model.AddProduct
import com.example.techbazaar.model.CategoryModel
import com.example.techbazaar.ui.adapter.CategoryAdapter
import com.example.techbazaar.ui.adapter.ProductAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(layoutInflater)



        val preferences = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)

        if (preferences.getBoolean("isCart",false))
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)

        getCategory()

        getProduct()

        getSliderImage()
        return binding.root
    }

    private fun getSliderImage() {
        Firebase.firestore.collection("Slider").document("item")
            .get().addOnSuccessListener {
                Glide.with(requireContext()).load(it.get("img")).into(binding.imageViewSlider)
            }

    }

    private fun getProduct() {
        val list = ArrayList<AddProduct>()
        Firebase.firestore.collection("Products")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data = doc.toObject(AddProduct::class.java)
                    list.add(data!!)
                }
                  binding.recyclerViewProducts.adapter = ProductAdapter(requireContext(),list)
            }
    }

    private fun getCategory() {
        val list = ArrayList<CategoryModel>()
        Firebase.firestore.collection("Category")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data = doc.toObject(CategoryModel::class.java)
                    list.add(data!!)
                }
                binding.recyclerViewCategory.adapter = CategoryAdapter(requireContext(),list)
            }
    }

}