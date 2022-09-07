package com.samsul.core.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samsul.core.databinding.ItemUserBinding
import com.samsul.core.domain.model.Users

class FollowerAdapter(private val context: Context): ListAdapter<Users, FollowerAdapter.FollowerViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder =
        FollowerViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FollowerViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(users: Users) {
            binding.tvDetail.visibility = View.GONE
            binding.tvName.text = users.login
            Glide.with(context)
                .load(users.avatarUrl)
                .into(binding.imgUser)
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<Users>() {
        override fun areItemsTheSame(
            oldItem: Users,
            newItem: Users
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Users,
            newItem: Users
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
}