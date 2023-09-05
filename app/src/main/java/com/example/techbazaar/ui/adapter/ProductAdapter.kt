package com.example.techbazaar.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.techbazaar.databinding.ItemProductLayoutBinding
import com.example.techbazaar.model.AddProduct
import com.example.techbazaar.ui.activity.ProductDetailActivity

class ProductAdapter(val context: Context,val list:ArrayList<AddProduct>)
    :RecyclerView.Adapter<ProductAdapter.productViewHolder>(){

    inner class productViewHolder(val binding : ItemProductLayoutBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): productViewHolder {
        val binding=ItemProductLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return productViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: productViewHolder, position: Int) {
        val data=list[position]
        Glide.with(context).load(data.productCoverImg).into(holder.binding.imageViewProductItem)
        holder.binding.textViewProductName.text=data.productName
        holder.binding.textViewCategory.text=data.productCategory
        holder.binding.textViewMrp.text=data.productMrp

        holder.binding.buttonSellingPrice.text=data.productSellingPrice

        //sending data to product detail activity
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("id",list[position].productId)
            context.startActivity(intent)
        }



    }
}