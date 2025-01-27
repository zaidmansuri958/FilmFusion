package com.zaidmansuri.filmfusion.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.zaidmansuri.filmfusion.activity.PlayActivity
import com.zaidmansuri.filmfusion.databinding.SuggestionCardBinding
import com.zaidmansuri.filmfusion.model.Movie

class SuggestionAdapter(private val suggestionList: ArrayList<Movie>) :
    RecyclerView.Adapter<SuggestionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        val view = SuggestionCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SuggestionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        val data = suggestionList.get(position)
        holder.binding.title.text = data.title
        Glide.with(holder.itemView).load(data.image).into(holder.binding.image)
        holder.binding.desc.text = data.desc
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, PlayActivity::class.java)
            intent.putExtra("video", data.video)
            intent.putExtra("title", data.title)
            intent.putExtra("desc", data.desc)
            intent.putExtra("image", data.image)
            holder.itemView.context.startActivity(intent)
        }
    }
}

class SuggestionViewHolder(val binding: SuggestionCardBinding) : ViewHolder(binding.root)