package com.zaidmansuri.filmfusion.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.zaidmansuri.filmfusion.R
import com.zaidmansuri.filmfusion.adapter.WebSeriesAdapter
import com.zaidmansuri.filmfusion.databinding.FragmentWebseriesBinding
import com.zaidmansuri.filmfusion.model.WebSeries


class WebseriesFragment : Fragment() {
    private lateinit var binding: FragmentWebseriesBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var webSeriesAdapter: WebSeriesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_webseries, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWebseriesBinding.bind(view)
        databaseReference = FirebaseDatabase.getInstance().getReference("WebSeries")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val webseriesList = arrayListOf<WebSeries>()
                for (i in snapshot.children) {
                    val data = i.getValue(WebSeries::class.java)
                    webseriesList.add(data!!)
                }
                if (snapshot.exists()) {
                    webSeriesAdapter = WebSeriesAdapter(webseriesList)
                    binding.webseriesRecycle.apply {
                        adapter = webSeriesAdapter
                        layoutManager = LinearLayoutManager(activity)
                    }
                }
                binding.shimmerLayout.stopShimmer()
                binding.shimmerLayout.visibility = View.GONE
                binding.webseriesRecycle.visibility = View.VISIBLE
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}