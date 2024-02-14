package com.atya.firstsubmissiongithubuserapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.atya.firstsubmissiongithubuserapp.R
import com.atya.firstsubmissiongithubuserapp.data.local.entity.FavoriteUser
import com.atya.firstsubmissiongithubuserapp.databinding.ActivityUserDetailBinding
import com.atya.firstsubmissiongithubuserapp.ui.adapter.SectionsPagerAdapter
import com.atya.firstsubmissiongithubuserapp.ui.viewmodel.UserDetailViewModel
import com.atya.firstsubmissiongithubuserapp.ui.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity(){

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var userDetailViewModel : UserDetailViewModel
    private var isUserFavorite: Boolean = false
    private var userFavorite = FavoriteUser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userDetailData = intent.getStringExtra(EXTRA_LOGIN)

        userDetailViewModel = obtainViewModel(this@UserDetailActivity)

        userDetailViewModel.isLoading.observe(this){
            binding.progressBar.isVisible = it
        }

        userDetailViewModel.userFavorite.observe(this){ isFavorite ->
            if(isFavorite){
                isUserFavorite = true
                setFavoriteIcon(true)
            } else{
                isUserFavorite = false
                setFavoriteIcon(false)
            }
            Log.d("UserDetailActivity", "getUserByUsername: $isFavorite")
        }

        userDetailViewModel.getDetailUser(userDetailData.toString())

        userDetailViewModel.detailUser.observe(this){
            with(binding){
                tvFullname.text = it.name
                tvUsername.text = it.login
                tvFollower.text = it.followers.toString()
                tvFollowing.text = it.following.toString()
                Glide.with(binding.root)
                    .load(it.avatarUrl)
                    .into(binding.ivMainAvatar)
                userDetailViewModel.isUserFavorite(it.login)
                userFavorite.username = it.login
                userFavorite.avatarUrl = it.avatarUrl
            }
        }
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager){ tab, position->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        binding.btnFavorite.setOnClickListener {
            if(isUserFavorite){
                setFavoriteIcon(false)
                userDetailViewModel.deleteUserFavorite(userFavorite)
            } else{
                setFavoriteIcon(true)
                userDetailViewModel.setUserFavorite(userFavorite)
            }
        }
    }

    private fun setFavoriteIcon(result: Boolean) {
        val favorite = binding.btnFavorite
        if(result){
            favorite.setImageResource(R.drawable.ic_favorite)
        } else{
            favorite.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): UserDetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(UserDetailViewModel::class.java)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    companion object{
        const val EXTRA_LOGIN = "extra_login"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

}