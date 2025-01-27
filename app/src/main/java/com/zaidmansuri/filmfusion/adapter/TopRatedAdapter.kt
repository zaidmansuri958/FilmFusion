package com.zaidmansuri.filmfusion.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.zaidmansuri.filmfusion.activity.PlayActivity
import com.zaidmansuri.filmfusion.databinding.MovieCardBinding
import com.zaidmansuri.filmfusion.model.Movie

class TopRatedAdapter(private val movieList: ArrayList<Movie>,val category:String) :
    RecyclerView.Adapter<TopRatedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedViewHolder {
        val view = MovieCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopRatedViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: TopRatedViewHolder, position: Int) {
        val data = movieList.get(position)
        Glide.with(holder.itemView).load(data.image).into(holder.binding.movieImg)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, PlayActivity::class.java)
            intent.putExtra("video",data.video)
            intent.putExtra("title",data.title)
            intent.putExtra("desc",data.desc)
            intent.putExtra("image",data.image)
            intent.putExtra("category",category)
            holder.itemView.context.startActivity(intent)
        }
    }
}

class TopRatedViewHolder(val binding: MovieCardBinding) : ViewHolder(binding.root)