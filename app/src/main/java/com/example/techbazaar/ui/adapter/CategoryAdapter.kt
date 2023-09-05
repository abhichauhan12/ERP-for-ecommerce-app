package com.example.techbazaar.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.techbazaar.R
import com.example.techbazaar.databinding.ItemCategoryLayoutBinding
import com.example.techbazaar.model.CategoryModel
import com.example.techbazaar.ui.activity.CategoryProductActivity

class CategoryAdapter(var context: Context, var list:ArrayList<CategoryModel>):
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(view:View):RecyclerView.ViewHolder(view){
        var binding = ItemCategoryLayoutBinding.bind(view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category_layout, parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.textViewItemCategory.text=list[position].cat
        Glide.with(context).load(list[position].img).into(holder.binding.imageViewItemCategory)

        //on click of particular category that will open CategoryProductActivity and pass that category name
        holder.itemView.setOnClickListener{
            val intent=Intent(context,CategoryProductActivity::class.java)
            intent.putExtra("cat",list[position].cat)
            context.startActivity(intent)
        }

    }
}