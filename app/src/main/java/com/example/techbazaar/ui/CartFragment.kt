package com.example.techbazaar.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.techbazaar.R
import com.example.techbazaar.data.database.AppDatabase
import com.example.techbazaar.data.database.entity.ProductEntity
import com.example.techbazaar.databinding.FragmentCartBinding
import com.example.techbazaar.ui.activity.AddressActivity
import com.example.techbazaar.ui.activity.ProductDetailActivity
import com.example.techbazaar.ui.adapter.CartAdapter


class CartFragment : Fragment() {

    private lateinit var binding :FragmentCartBinding
    private lateinit var list:ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater)


        val preferences = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editor =preferences.edit()
        editor.putBoolean("isCart",false)
        editor.apply()

        //getting data from database
        val dao=AppDatabase.getInstance(requireContext()).productDao()

        list=ArrayList()
        dao.getAllProduct().observe(requireActivity()){
            binding.recyclerViewCart.adapter=CartAdapter(requireContext(),it)

            list.clear()
            for (data in it){
                list.add(data.productID)
            }
            totalCost(it)
        }


        return binding.root
    }

    private fun totalCost(data: List<ProductEntity>?) {
        var total = 0
        for (item in data!!){
            total += item.productSellingPrice!!.toInt()
        }
        binding.textViewTotalItem.text = "Total item in Cart: ${data.size}"
        binding.textViewTotalCost.text = "Total Cost: $total"

        binding.buttonCheckOut.setOnClickListener {


//            intent.putExtra("totalCost",total.toString())
//            intent.putStringArrayListExtra("productIds")

            val intent = Intent(context, AddressActivity::class.java)
            val bundle = Bundle()
            bundle.putStringArrayList("productIds", list)
            bundle.putString("totalCost", total.toString())
            intent.putExtras(bundle)
            startActivity(intent)
        }

    }


}