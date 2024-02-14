package com.atya.firstsubmissiongithubuserapp.data.remote.retrofit

import com.atya.firstsubmissiongithubuserapp.data.remote.response.DetailUserResponse
import com.atya.firstsubmissiongithubuserapp.data.remote.response.ItemsItem
import com.atya.firstsubmissiongithubuserapp.data.remote.response.UserListResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getListUsers(
        @Query("q") username: String,
    ): Call<UserListResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String,
        ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String,
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String,
    ): Call<List<ItemsItem>>


}