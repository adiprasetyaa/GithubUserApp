package com.atya.firstsubmissiongithubuserapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.atya.firstsubmissiongithubuserapp.data.local.entity.FavoriteUser

class FavoriteUserDiffCallback (private val oldListUserFavorite : List<FavoriteUser>, private val newListUserFavorite:List<FavoriteUser>):DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldListUserFavorite.size

    override fun getNewListSize(): Int = newListUserFavorite.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldListUserFavorite[oldItemPosition].username == newListUserFavorite[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldListUserFavorite[oldItemPosition].avatarUrl == newListUserFavorite[newItemPosition].avatarUrl
    }

}