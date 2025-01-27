package com.zaidmansuri.filmfusion.activity

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.firebase.database.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.zaidmansuri.filmfusion.R
import com.zaidmansuri.filmfusion.adapter.SuggestionAdapter
import com.zaidmansuri.filmfusion.databinding.ActivityPlayBinding
import com.zaidmansuri.filmfusion.db.LikeDao
import com.zaidmansuri.filmfusion.db.LikeDataBase
import com.zaidmansuri.filmfusion.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PlayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var suggestionAdapter: SuggestionAdapter
    private lateinit var likeDao: LikeDao
    private lateinit var likeDatabase: LikeDataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adRequest1 = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest1)
        if (binding.adView.isLoading()) {
        } else {
            binding.adView.loadAd(adRequest1)
        }

        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(this@PlayActivity,
            "ca-app-pub-9829502421511765/2429977080",
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.d(ContentValues.TAG, loadAdError.toString())
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    rewardedAd.show(this@PlayActivity) {

                    }
                }
            })


        val video = intent.getStringExtra("video").toString()
        val desc = intent.getStringExtra("desc").toString()
        val title = intent.getStringExtra("title").toString()
        val image = intent.getStringExtra("image").toString()
        val category = intent.getStringExtra("category").toString()
        likeDatabase = LikeDataBase.getDatabase(applicationContext)
        likeDao = likeDatabase.likeDao()
        binding.title.text = title
        binding.desc.text = desc
        setLikeIcon()
        val youTubePlayerView: YouTubePlayerView = findViewById(R.id.yt_player);
        youTubePlayerView.enterFullScreen();
        youTubePlayerView.toggleFullScreen();
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(video, 0F);
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                super.onStateChange(youTubePlayer, state)
            }
        })

        databaseReference = FirebaseDatabase.getInstance().getReference(category)
        databaseReference.addValueEventListener(object : ValueEventListener {
            val suggestionList = arrayListOf<Movie>()
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {
                    val data = i.getValue(Movie::class.java)
                    suggestionList.add(data!!)
                }
                suggestionList.shuffle()
                if (snapshot.exists()) {
                    suggestionAdapter = SuggestionAdapter(suggestionList)
                    binding.suggestionRecycle.apply {
                        adapter = suggestionAdapter
                        layoutManager = LinearLayoutManager(this@PlayActivity)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        binding.like.setOnClickListener {
            GlobalScope.launch(Dispatchers.Default) {
                if (!likeDao.likedOrNot(intent.getStringExtra("image").toString())) {
                    likeDao.insertLike(
                        Movie(null,image,title,video,desc)
                    )
                    setLikeIcon()
                } else {
                    likeDao.removeLike(intent.getStringExtra("image").toString())
                    setLikeIcon()
                }
            }

        }
    }

    fun setLikeIcon() {
        GlobalScope.launch(Dispatchers.Main) {
            if (likeDao.likedOrNot(intent.getStringExtra("image").toString())) {
                binding.like.setImageResource(R.drawable.ic_favourite)
            } else {
                binding.like.setImageResource(R.drawable.ic_favorite_border)
            }
        }
    }
}