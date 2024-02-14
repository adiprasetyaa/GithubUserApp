package com.atya.firstsubmissiongithubuserapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.atya.firstsubmissiongithubuserapp.data.local.entity.FavoriteUser
import com.atya.firstsubmissiongithubuserapp.databinding.ActivityFavoriteUserBinding
import com.atya.firstsubmissiongithubuserapp.ui.adapter.FavoriteAdapter
import com.atya.firstsubmissiongithubuserapp.ui.viewmodel.FavoriteViewModel
import com.atya.firstsubmissiongithubuserapp.ui.viewmodel.ViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUserList.layoutManager = layoutManager

        val favoriteViewModel = obtainViewModel(this@FavoriteUserActivity)
        favoriteViewModel.getFavoriteList().observe(this){ result->
            setUserList(result)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    private fun setUserList(userList : List<FavoriteUser>){
        val adapter = FavoriteAdapter()
        adapter.setUserList(userList)
        binding.rvUserList.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FavoriteUser) {
                showDetailUser(data)
            }

        })
    }

    private fun showDetailUser(user: FavoriteUser){
        val detailIntent = Intent(this@FavoriteUserActivity, UserDetailActivity::class.java)
        detailIntent.putExtra(UserDetailActivity.EXTRA_LOGIN, user.username)

        startActivity(detailIntent)
    }
}