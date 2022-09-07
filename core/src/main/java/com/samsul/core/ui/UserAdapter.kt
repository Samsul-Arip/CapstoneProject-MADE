package com.samsul.core.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samsul.core.databinding.ItemUserBinding
import com.samsul.core.domain.model.Users

class UserAdapter(private val context: Context,
                  private val itemClicked: OnItemClickListener
                  ): ListAdapter<Users, UserAdapter.UserViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder =
        UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(users: Users) {
            binding.tvDetail.setOnClickListener {
                itemClicked.onItemClicked(users)
            }
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

    interface OnItemClickListener {
        fun onItemClicked(user: Users)
    }
}