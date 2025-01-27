package com.zaidmansuri.filmfusion.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.firebase.database.*
import com.zaidmansuri.filmfusion.adapter.TopRatedAdapter
import com.zaidmansuri.filmfusion.databinding.ActivityWebseriesBinding
import com.zaidmansuri.filmfusion.model.Movie

class WebseriesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebseriesBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var webSeriesAdapter: TopRatedAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebseriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        if (binding.adView.isLoading()) {
        } else {
            binding.adView.loadAd(adRequest)
        }

        val webSeriesName = intent.getStringExtra("webseries_name").toString()
        databaseReference =
            FirebaseDatabase.getInstance().getReference(webSeriesName)
        databaseReference.addValueEventListener(object : ValueEventListener {
            val episodeList = arrayListOf<Movie>()
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {
                    val data = i.getValue(Movie::class.java)
                    episodeList.add(data!!)
                }
                if (snapshot.exists()) {
                    webSeriesAdapter = TopRatedAdapter(episodeList, webSeriesName)
                    binding.webseriesRecycle.apply {
                        adapter = webSeriesAdapter
                        layoutManager = LinearLayoutManager(this@WebseriesActivity)
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