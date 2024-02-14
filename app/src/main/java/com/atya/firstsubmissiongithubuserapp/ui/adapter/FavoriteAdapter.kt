package com.atya.firstsubmissiongithubuserapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.atya.firstsubmissiongithubuserapp.data.local.entity.FavoriteUser
import com.atya.firstsubmissiongithubuserapp.databinding.FavoriteItemsBinding
import com.atya.firstsubmissiongithubuserapp.helper.FavoriteUserDiffCallback
import com.bumptech.glide.Glide

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback : OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    private val userList = ArrayList<FavoriteUser>()
    fun setUserList(userList: List<FavoriteUser>){
        val diffcCallback = FavoriteUserDiffCallback(this.userList, userList)
        val diffResult = DiffUtil.calculateDiff(diffcCallback)
        this.userList.clear()
        this.userList.addAll(userList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteAdapter.FavoriteViewHolder {
        val binding = FavoriteItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.FavoriteViewHolder, position: Int) {
        holder.bind(userList[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(userList[position])
        }
    }

    override fun getItemCount() = userList.size

    class FavoriteViewHolder(private val binding : FavoriteItemsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(userFav : FavoriteUser){
            binding.tvUsername.text = userFav.username
            Glide.with(itemView)
                .load(userFav.avatarUrl)
                .into(binding.ivMiniAvatar)
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: FavoriteUser)
    }
}