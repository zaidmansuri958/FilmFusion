package com.zaidmansuri.filmfusion.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.zaidmansuri.filmfusion.activity.WebseriesActivity
import com.zaidmansuri.filmfusion.databinding.WebseriesCardBinding
import com.zaidmansuri.filmfusion.model.WebSeries

class WebSeriesAdapter(val webSeriesList: ArrayList<WebSeries>) :
    RecyclerView.Adapter<WebSeriesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebSeriesViewHolder {
        val view = WebseriesCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WebSeriesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return webSeriesList.size
    }

    override fun onBindViewHolder(holder: WebSeriesViewHolder, position: Int) {
        val data = webSeriesList.get(position)
        holder.binding.webseriesName.text = data.name
        holder.binding.totalEpisode.text = "Total episode : " + data.episode
        holder.binding.desc.text = data.desc
        holder.binding.webseriesName.setOnClickListener {
            TransitionManager.beginDelayedTransition(holder.binding.root, AutoTransition())
            if(holder.binding.more.isVisible){
                holder.binding.more.visibility= View.GONE
            }else{
                holder.binding.more.visibility= View.VISIBLE
            }
        }
        Glide.with(holder.itemView).load(data.image).into(holder.binding.webseriesImage)
        holder.binding.webseriesImage.setOnClickListener {
            val intent=Intent(holder.itemView.context,WebseriesActivity::class.java)
            intent.putExtra("webseries_name",data.name)
            holder.itemView.context.startActivity(intent)
        }
    }
}

class WebSeriesViewHolder(val binding: WebseriesCardBinding) : ViewHolder(binding.root)