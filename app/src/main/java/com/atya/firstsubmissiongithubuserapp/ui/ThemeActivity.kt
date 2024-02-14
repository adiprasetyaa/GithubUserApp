package com.atya.firstsubmissiongithubuserapp.ui

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.atya.firstsubmissiongithubuserapp.data.datastore.SettingPreferences
import com.atya.firstsubmissiongithubuserapp.data.datastore.dataStore
import com.atya.firstsubmissiongithubuserapp.databinding.ActivityThemeBinding
import com.atya.firstsubmissiongithubuserapp.ui.viewmodel.ThemeViewModel
import com.atya.firstsubmissiongithubuserapp.ui.viewmodel.ThemeViewModelFactory

class ThemeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThemeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val switchTheme = binding.switchTheme

        val pref = SettingPreferences.getInstance(application.dataStore)
        val themeViewModel = ViewModelProvider(this, ThemeViewModelFactory(pref)).get(
            ThemeViewModel::class.java
        )

        themeViewModel.getThemeSettings().observe(this){isDarkModeActive :Boolean->
            if(isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }
        switchTheme.setOnCheckedChangeListener{_:CompoundButton?, isChecked:Boolean ->
            themeViewModel.saveThemeSetting(isChecked)
        }

    }
}