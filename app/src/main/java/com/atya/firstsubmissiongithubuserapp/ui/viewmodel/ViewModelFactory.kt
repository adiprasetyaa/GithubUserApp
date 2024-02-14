package com.atya.firstsubmissiongithubuserapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.atya.firstsubmissiongithubuserapp.data.FavoriteRepository
import com.atya.firstsubmissiongithubuserapp.di.Injection

class ViewModelFactory private constructor(private val favoriteRepository: FavoriteRepository):ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(favoriteRepository) as T
        } else if(modelClass.isAssignableFrom(UserDetailViewModel::class.java)){
            return UserDetailViewModel(favoriteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: "+ modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}