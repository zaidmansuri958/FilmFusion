package com.zaidmansuri.filmfusion.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.zaidmansuri.filmfusion.R
import com.zaidmansuri.filmfusion.databinding.ActivityMainBinding
import com.zaidmansuri.filmfusion.ui.FavouriteFragment
import com.zaidmansuri.filmfusion.ui.HomeFragment
import com.zaidmansuri.filmfusion.ui.MovieFragment
import com.zaidmansuri.filmfusion.ui.WebseriesFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNav: MeowBottomNavigation
    lateinit var mAdView: AdView
    private var mInterstitialAd: InterstitialAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this) {
            mAdView = findViewById(R.id.adView)
            val adRequest = AdRequest.Builder().build()
            mAdView.loadAd(adRequest)
        }

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        if (binding.adView.isLoading()) {
        } else {
            binding.adView.loadAd(adRequest)
        }

        supportFragmentManager.beginTransaction().replace(R.id.frame, HomeFragment()).commit()
        bottomNav = findViewById<MeowBottomNavigation>(R.id.bnv)
        bottomNav.add(MeowBottomNavigation.Model(1, R.drawable.ic_home))
        bottomNav.add(MeowBottomNavigation.Model(2, R.drawable.ic_movie))
        bottomNav.add(MeowBottomNavigation.Model(3, R.drawable.ic_webseries))
        bottomNav.add(MeowBottomNavigation.Model(4, R.drawable.ic_favourite))


        binding.bnv.setOnClickMenuListener { }

        binding.bnv.setOnShowListener() {
            val id = it.id
            var fragment: Fragment? = null
            when (id) {
                1 -> {
                    fragment = HomeFragment()
                }
                2 -> {
                    fragment = MovieFragment()
                    val adRequest = AdRequest.Builder().build()
                    InterstitialAd.load(this@MainActivity,
                        "ca-app-pub-9829502421511765/6640668922",
                        adRequest,
                        object : InterstitialAdLoadCallback() {
                            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                                interstitialAd.show(this@MainActivity)
                            }

                            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                                mInterstitialAd = null
                            }
                        })
                }
                3 -> {
                    fragment = WebseriesFragment()
                    val adRequest = AdRequest.Builder().build()
                    InterstitialAd.load(this@MainActivity,
                        "ca-app-pub-9829502421511765/8137599330",
                        adRequest,
                        object : InterstitialAdLoadCallback() {
                            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                                interstitialAd.show(this@MainActivity)
                            }

                            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                                mInterstitialAd = null
                            }
                        })
                }
                4 -> {
                    fragment = FavouriteFragment()
                    val adRequest = AdRequest.Builder().build()
                    InterstitialAd.load(this@MainActivity,
                        "ca-app-pub-9829502421511765/5000284199",
                        adRequest,
                        object : InterstitialAdLoadCallback() {
                            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                                interstitialAd.show(this@MainActivity)
                            }

                            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                                mInterstitialAd = null
                            }
                        })
                }
            }
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame, fragment!!)
            transaction.commit()

        }
        binding.bnv.show(1, true)
    }
}