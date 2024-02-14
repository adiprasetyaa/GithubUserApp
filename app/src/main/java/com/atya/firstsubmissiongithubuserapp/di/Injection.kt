package com.atya.firstsubmissiongithubuserapp.di

import android.content.Context
import com.atya.firstsubmissiongithubuserapp.data.FavoriteRepository
import com.atya.firstsubmissiongithubuserapp.data.local.room.FavoriteUserDatabase
import com.atya.firstsubmissiongithubuserapp.data.remote.retrofit.ApiConfig
import com.atya.firstsubmissiongithubuserapp.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavoriteRepository {
        val database = FavoriteUserDatabase.getDatabase(context)
        val dao = database.favoriteUserDao()
        return FavoriteRepository.getInstance(dao)
    }
}