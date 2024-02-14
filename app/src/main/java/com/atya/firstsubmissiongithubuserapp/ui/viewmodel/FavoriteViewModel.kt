package com.atya.firstsubmissiongithubuserapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.atya.firstsubmissiongithubuserapp.data.FavoriteRepository

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository):ViewModel() {

    fun getFavoriteList() = favoriteRepository.getFavoriteList()




}