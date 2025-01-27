package com.zaidmansuri.filmfusion.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.zaidmansuri.filmfusion.R
import com.zaidmansuri.filmfusion.adapter.TopRatedAdapter
import com.zaidmansuri.filmfusion.databinding.FragmentFavouriteBinding
import com.zaidmansuri.filmfusion.db.LikeDao
import com.zaidmansuri.filmfusion.db.LikeDataBase
import com.zaidmansuri.filmfusion.model.Movie


class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var favouriteAdapter: TopRatedAdapter
    private lateinit var likeDatabase: LikeDataBase
    private lateinit var likeDao: LikeDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavouriteBinding.bind(view)
        likeDatabase = LikeDataBase.getDatabase(requireContext())
        likeDao = likeDatabase.likeDao()
        likeDao.getLikes().observe(viewLifecycleOwner) {
            if (it.isEmpty() || it.isNullOrEmpty()) {
                binding.emptyScreen.visibility = View.VISIBLE
            } else {
                favouriteAdapter = TopRatedAdapter(it as ArrayList<Movie>, "SouthIndian")
                binding.favouriteRecycle.apply {
                    layoutManager = GridLayoutManager(activity, 3)
                    adapter = favouriteAdapter
                }
                binding.emptyScreen.visibility = View.GONE
            }
        }

    }

}