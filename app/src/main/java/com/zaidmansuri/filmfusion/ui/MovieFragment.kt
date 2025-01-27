package com.zaidmansuri.filmfusion.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.zaidmansuri.filmfusion.R
import com.zaidmansuri.filmfusion.adapter.CategoryAdapter
import com.zaidmansuri.filmfusion.adapter.TopRatedAdapter
import com.zaidmansuri.filmfusion.databinding.FragmentMovieBinding
import com.zaidmansuri.filmfusion.model.Category
import com.zaidmansuri.filmfusion.model.Movie

class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)
        databaseReference = FirebaseDatabase.getInstance().getReference("movies")
        databaseReference.addValueEventListener(object : ValueEventListener {
            var movieList = arrayListOf<Category>()
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {
                    val data = i.getValue(Category::class.java)
                    movieList.add(data!!)
                }
                movieList.shuffle()
                if (snapshot.exists()) {
                    categoryAdapter = CategoryAdapter(movieList)
                    binding.categoryRecycle.apply {
                        adapter = categoryAdapter
                        layoutManager = LinearLayoutManager(activity)
                    }
                }
                binding.shimmerLayout.stopShimmer()
                binding.shimmerLayout.visibility = View.GONE
                binding.categoryRecycle.visibility = View.VISIBLE
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

}