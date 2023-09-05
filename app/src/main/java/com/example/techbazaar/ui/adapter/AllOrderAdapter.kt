package com.example.techbazaar.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.techbazaar.databinding.ItemAllOrdersLayoutBinding
import com.example.techbazaar.model.AllOrderModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AllOrderAdapter(val list :ArrayList<AllOrderModel>, val context: Context)
    :RecyclerView.Adapter<AllOrderAdapter.AllOrderViewHolder>(){

    inner class AllOrderViewHolder (val binding: ItemAllOrdersLayoutBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrderViewHolder {
        return AllOrderViewHolder(
            ItemAllOrdersLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: AllOrderViewHolder, position: Int) {
        holder.binding.textViewProductName.text=list[position].name
        holder.binding.textViewProductPrice.text=list[position].price



        when(list[position].status){
            "Ordered" -> {

                holder.binding.textViewProductStatus.text="Ordered"

            }

            "Dispatched" -> {
                holder.binding.textViewProductStatus.text="Dispatched"


            }

            "Delivered" -> {
                holder.binding.textViewProductStatus.text="Delivered"

            }

            "Cancelled" -> {
                holder.binding.textViewProductStatus.text="Cancelled"

            }

        }


    }

}