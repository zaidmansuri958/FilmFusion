package com.zaidmansuri.filmfusion.ui

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.zaidmansuri.filmfusion.R
import com.zaidmansuri.filmfusion.activity.PlayActivity
import com.zaidmansuri.filmfusion.adapter.TopRatedAdapter
import com.zaidmansuri.filmfusion.databinding.FragmentHomeBinding
import com.zaidmansuri.filmfusion.db.LikeDao
import com.zaidmansuri.filmfusion.db.LikeDataBase
import com.zaidmansuri.filmfusion.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var topRatedAdapter: TopRatedAdapter
    private lateinit var likeDao: LikeDao
    private lateinit var likeDatabase: LikeDataBase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        likeDatabase = LikeDataBase.getDatabase(requireActivity().applicationContext)
        likeDao = likeDatabase.likeDao()
        databaseReference = FirebaseDatabase.getInstance().getReference()
        databaseReference.child("TopRated").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = arrayListOf<Movie>()
                for (i in snapshot.children) {
                    val data = i.getValue(Movie::class.java)
                    list.add(data!!)
                }
                list.shuffle()
                if (snapshot.exists()) {
                    topRatedAdapter = TopRatedAdapter(list, "TopRated")
                    binding.topRatedRecycle.apply {
                        adapter = topRatedAdapter
                        layoutManager = GridLayoutManager(activity, 3)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        databaseReference.child("PosterMovie")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val data = snapshot.child("1").getValue(Movie::class.java)
                        Glide.with(binding.poster).load(data!!.image).into(binding.poster)
                        binding.btnPlay.setOnClickListener {
                            val intent = Intent(activity, PlayActivity::class.java)
                            intent.putExtra("video", data.video)
                            intent.putExtra("image", data.image)
                            intent.putExtra("desc", data.desc)
                            intent.putExtra("title", data.title)
                            intent.putExtra("category", "TopRated")
                            startActivity(intent)
                        }
                        binding.btnLike.setOnClickListener {
                            GlobalScope.launch(Dispatchers.Default) {
                                if (!likeDao.likedOrNot(data.image!!)) {
                                    likeDao.insertLike(
                                        Movie(
                                            null,
                                            data!!.image,
                                            data!!.title,
                                            data!!.video,
                                            data!!.desc
                                        )
                                    )
                                    setLikeIcon(data.image)
                                } else {
                                    likeDao.removeLike(data.image)
                                    setLikeIcon(data.image)
                                }
                            }

                        }

                    }
                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.visibility = View.GONE
                    binding.nestedView.visibility = View.VISIBLE
                }


                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    fun setLikeIcon(image: String) {
        GlobalScope.launch(Dispatchers.Main) {
            if (likeDao.likedOrNot(image)) {
                binding.btnLike.setImageResource(R.drawable.ic_favourite)
            } else {
                binding.btnLike.setImageResource(R.drawable.ic_favorite_border)
            }
        }
    }

}