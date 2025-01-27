package com.zaidmansuri.filmfusion.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.zaidmansuri.filmfusion.activity.MovieActivity
import com.zaidmansuri.filmfusion.activity.PlayActivity
import com.zaidmansuri.filmfusion.databinding.MovieCardBinding
import com.zaidmansuri.filmfusion.model.Category

class CategoryAdapter(private val categoryList: ArrayList<Category>) :
    RecyclerView.Adapter<CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = MovieCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val data=categoryList.get(position)
        Glide.with(holder.itemView).load(data.image).into(holder.binding.movieImg)
        holder.itemView.setOnClickListener {
            val intent= Intent(holder.itemView.context,MovieActivity::class.java)
            intent.putExtra("category_name",data.name)
            holder.itemView.context.startActivity(intent)
        }
    }
}

class CategoryViewHolder(val binding: MovieCardBinding) : ViewHolder(binding.root)