package com.example.techbazaar.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.techbazaar.data.database.AppDatabase
import com.example.techbazaar.data.database.entity.ProductEntity
import com.example.techbazaar.databinding.ItemCartLayoutBinding
import com.example.techbazaar.ui.activity.ProductDetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartAdapter(val context: Context,val list:List<ProductEntity>)
    :RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    inner class CartViewHolder(val binding:ItemCartLayoutBinding):
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding=ItemCartLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        Glide.with(context).load(list[position].productCoverImages).into(holder.binding.imageViewProductImage)

        holder.binding.textViewProductName.text=list[position].productName
        holder.binding.textViewProductPrice.text=list[position].productSellingPrice

        //opening detail page of product
        holder.itemView.setOnClickListener {
            val intent = Intent(context,ProductDetailActivity::class.java)
            intent.putExtra("id",list[position].productID)
            context.startActivity(intent)

        }

        //deleting item from cart
        val dao =AppDatabase.getInstance(context).productDao()
        holder.binding.imageViewDelete.setOnClickListener {
            GlobalScope.launch (Dispatchers.IO){

            dao.deleteProduct(ProductEntity(list[position].productID,list[position].productName,list[position].productSellingPrice,list[position].productCoverImages))
            }
        }


    }
}