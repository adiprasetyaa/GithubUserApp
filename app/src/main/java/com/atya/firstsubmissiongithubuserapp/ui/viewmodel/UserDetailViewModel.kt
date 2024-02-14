package com.atya.firstsubmissiongithubuserapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atya.firstsubmissiongithubuserapp.data.FavoriteRepository
import com.atya.firstsubmissiongithubuserapp.data.local.entity.FavoriteUser
import com.atya.firstsubmissiongithubuserapp.data.remote.response.DetailUserResponse
import com.atya.firstsubmissiongithubuserapp.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel(private val favoriteRepository: FavoriteRepository): ViewModel() {

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userFavorite = MediatorLiveData<Boolean>()
    val userFavorite: LiveData<Boolean> = _userFavorite

    companion object{
        private const val TAG = "UserDetailViewModel"
    }

    fun getDetailUser(username: String){
        _isLoading.value = true
        val  client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _detailUser.value = response.body()
                } else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun setUserFavorite(favoriteUser: FavoriteUser){
        viewModelScope.launch {
            favoriteRepository.setUserFavorite(favoriteUser)
        }
    }

    fun deleteUserFavorite(favoriteUser: FavoriteUser){
        viewModelScope.launch {
            favoriteRepository.deleteUserFavorite(favoriteUser)
        }
    }

    fun isUserFavorite(username: String){
        val userFavorite = favoriteRepository.isUserFavorite(username)
        _userFavorite.addSource(userFavorite){ isFavorite ->
            _userFavorite.postValue(isFavorite)
        }
    }
}