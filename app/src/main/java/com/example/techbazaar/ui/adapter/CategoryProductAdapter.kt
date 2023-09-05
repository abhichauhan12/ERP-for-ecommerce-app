package com.example.techbazaar.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.techbazaar.databinding.ItemCategoryProductLayoutBinding
import com.example.techbazaar.databinding.ItemProductLayoutBinding
import com.example.techbazaar.model.AddProduct
import com.example.techbazaar.ui.activity.ProductDetailActivity


//this adpater is use for category products
class CategoryProductAdapter (val context: Context, val list:ArrayList<AddProduct>)
    :RecyclerView.Adapter<CategoryProductAdapter.CategoryProductViewHolder>(){

    inner class CategoryProductViewHolder(val binding : ItemCategoryProductLayoutBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryProductViewHolder {
        val binding= ItemCategoryProductLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return CategoryProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategoryProductViewHolder, position: Int) {
        Glide.with(context).load(list[position].productCoverImg).into(holder.binding.imageViewItemCategoryProduct)
        holder.binding.textViewProductName.text=list[position].productName
        holder.binding.textViewSellingPrice.text=list[position].productSellingPrice

        //sending data to product detail activity
        holder.itemView.setOnClickListener {
            val intent =Intent(context,ProductDetailActivity::class.java)
            intent.putExtra("id",list[position].productId)
            context.startActivity(intent)
        }

    }
}