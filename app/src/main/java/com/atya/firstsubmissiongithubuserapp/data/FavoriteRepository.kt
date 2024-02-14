package com.atya.firstsubmissiongithubuserapp.data

import androidx.lifecycle.LiveData
import com.atya.firstsubmissiongithubuserapp.data.local.entity.FavoriteUser
import com.atya.firstsubmissiongithubuserapp.data.local.room.FavoriteUserDao
import kotlin.concurrent.Volatile

class FavoriteRepository private constructor(
    private val favoriteUserDao: FavoriteUserDao
) {

    fun getFavoriteList(): LiveData<List<FavoriteUser>> {
        return favoriteUserDao.getFavoriteList()
    }

    fun isUserFavorite(username: String): LiveData<Boolean> {
        return favoriteUserDao.isFavorite(username)
    }

    suspend fun setUserFavorite(user: FavoriteUser) {
        favoriteUserDao.setUserFavorite(user)
    }

    suspend fun deleteUserFavorite(user:FavoriteUser) {
        favoriteUserDao.deleteUserFavorite(user)
    }


    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            favoriteUserDao: FavoriteUserDao
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(favoriteUserDao)
            }.also { instance = it }
    }
}