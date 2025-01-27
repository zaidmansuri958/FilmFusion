package com.zaidmansuri.filmfusion.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.zaidmansuri.filmfusion.adapter.TopRatedAdapter
import com.zaidmansuri.filmfusion.databinding.ActivityMovieBinding
import com.zaidmansuri.filmfusion.model.Movie

class MovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var topRatedAdapter: TopRatedAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        if (binding.adView.isLoading()) {
        } else {
            binding.adView.loadAd(adRequest)
        }

        val category = intent.getStringExtra("category_name").toString()
        binding.categoryName.text = category
        databaseReference = FirebaseDatabase.getInstance().getReference(category)
        databaseReference.addValueEventListener(object : ValueEventListener {
            val movieList = arrayListOf<Movie>()
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {
                    val data = i.getValue(Movie::class.java)
                    movieList.add(data!!)
                }
                movieList.shuffle()
                if (snapshot.exists()) {
                    topRatedAdapter = TopRatedAdapter(movieList,category)
                    binding.movieRecycle.apply {
                        adapter = topRatedAdapter
                        layoutManager = GridLayoutManager(this@MovieActivity, 3)
                    }
                }
                binding.shimmerLayout.stopShimmer()
                binding.shimmerLayout.visibility = View.GONE
                binding.ll.visibility = View.VISIBLE
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }
}