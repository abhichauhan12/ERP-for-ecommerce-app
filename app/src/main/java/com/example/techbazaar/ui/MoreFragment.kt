package com.example.techbazaar.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.techbazaar.R
import com.example.techbazaar.databinding.FragmentMoreBinding
import com.example.techbazaar.model.AllOrderModel
import com.example.techbazaar.ui.adapter.AllOrderAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MoreFragment : Fragment() {
    private lateinit var binding: FragmentMoreBinding
    private lateinit var list :ArrayList<AllOrderModel>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMoreBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        list= ArrayList()
        val preference=requireContext().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)

        Firebase.firestore.collection("allOrders")
            .whereEqualTo("userId",preference.getString("number","")!!)
            .get()
            .addOnSuccessListener {
                list.clear()
                for (doc in it){
                    val data =doc.toObject(AllOrderModel::class.java)
                    list.add(data)
                }
                binding.recyclerMoreFragment.adapter= AllOrderAdapter(list,requireContext())

            }
        return binding.root

    }


}