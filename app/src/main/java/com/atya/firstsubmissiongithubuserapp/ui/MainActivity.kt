package com.atya.firstsubmissiongithubuserapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.atya.firstsubmissiongithubuserapp.R
import com.atya.firstsubmissiongithubuserapp.data.datastore.SettingPreferences
import com.atya.firstsubmissiongithubuserapp.data.datastore.dataStore
import com.atya.firstsubmissiongithubuserapp.data.remote.response.ItemsItem
import com.atya.firstsubmissiongithubuserapp.databinding.ActivityMainBinding
import com.atya.firstsubmissiongithubuserapp.ui.adapter.UserListAdapter
import com.atya.firstsubmissiongithubuserapp.ui.viewmodel.MainViewModel
import com.atya.firstsubmissiongithubuserapp.ui.viewmodel.ThemeViewModel
import com.atya.firstsubmissiongithubuserapp.ui.viewmodel.ThemeViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val themeViewModel = ViewModelProvider(this, ThemeViewModelFactory(pref)).get(
            ThemeViewModel::class.java
        )

        themeViewModel.getThemeSettings().observe(this){isDarkModeActive :Boolean->
            if(isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            } else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUserList.layoutManager = layoutManager

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.userList.observe(this){ userList ->
            setUserList(userList)
        }
        mainViewModel.isLoading.observe(this) {
            binding.progressBar.isVisible = it
        }

        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener{ textView, actionId, event ->
                    searchBar.text = searchView.text
                    mainViewModel.findUserList(searchView.text.toString())
                    searchView.hide()
                    false
                }
        }


    }

    private fun setUserList(userList : List<ItemsItem>){
        val adapter = UserListAdapter()
        adapter.submitList(userList)
        binding.rvUserList.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_favorite ->{
                val intentFavorite = Intent(this@MainActivity, FavoriteUserActivity::class.java)
                startActivity(intentFavorite)
                true
            }
            R.id.menu_settings -> {
                val intentSetting = Intent(this@MainActivity, ThemeActivity::class.java)
                startActivity(intentSetting)
                true
            }
            else -> false
        }
        return super.onOptionsItemSelected(item)
    }

}