package com.atya.firstsubmissiongithubuserapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atya.firstsubmissiongithubuserapp.data.remote.response.ItemsItem
import com.atya.firstsubmissiongithubuserapp.databinding.UserItemsBinding
import com.atya.firstsubmissiongithubuserapp.ui.UserDetailActivity
import com.bumptech.glide.Glide

class UserListAdapter : ListAdapter<ItemsItem, UserListAdapter.MyViewHolder> (DIFF_CALLBACK) {

    class MyViewHolder(val binding : UserItemsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(userList : ItemsItem){
            binding.tvUsername.text = userList.login
            Glide.with(binding.root)
                .load(userList.avatarUrl)
                .into(binding.ivMiniAvatar)
                .clearOnDetach()
            itemView.setOnClickListener{
                val intentToDetail = Intent(itemView.context, UserDetailActivity::class.java )
                intentToDetail.putExtra(UserDetailActivity.EXTRA_LOGIN, userList.login)
                itemView.context.startActivity(intentToDetail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = UserItemsBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userList = getItem(position)
        holder.bind(userList)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}