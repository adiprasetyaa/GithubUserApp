package com.atya.firstsubmissiongithubuserapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.atya.firstsubmissiongithubuserapp.data.local.entity.FavoriteUser

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun setUserFavorite(user: FavoriteUser)

    @Delete
    suspend fun deleteUserFavorite(user : FavoriteUser)

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE username = :username)")
    fun isFavorite(username: String): LiveData<Boolean>

    @Query("SELECT * FROM favorite")
    fun getFavoriteList(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM favorite WHERE username = :username")
    fun getUserByUsername(username: String): LiveData<FavoriteUser>
}