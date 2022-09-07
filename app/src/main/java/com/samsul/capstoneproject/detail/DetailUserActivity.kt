package com.samsul.capstoneproject.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.samsul.capstoneproject.R
import com.samsul.capstoneproject.databinding.ActivityDetailUserBinding
import com.samsul.core.domain.model.Users
import com.samsul.core.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailUserActivity : AppCompatActivity() {

    private val binding: ActivityDetailUserBinding by lazy {
        ActivityDetailUserBinding.inflate(layoutInflater)
    }
    private val viewModel: DetailViewModel by viewModels()

    private lateinit var bundle: Bundle

    private lateinit var user: Users
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bundle = Bundle()
        setViewPager2()
        val username = intent.getStringExtra(USERNAME)
        if(username!=null) {
            bundle.putString(USERNAME, username)
            fetchDetail(username)
            binding.timeout.btnTryAgain.setOnClickListener {
                binding.timeout.root.visibility = View.GONE
                fetchDetail(username)
            }
        }
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    private fun fetchDetail(username: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.layoutDetail.visibility = View.GONE
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getDetailUser(username).collect{ response ->
                    when(response) {
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.layoutDetail.visibility = View.VISIBLE
                            if(response.data != null) {
                                user = response.data!!
                                viewModel.getStateFavorite(username)?.observe(this@DetailUserActivity) {user ->
                                    isFavorite = user.isFavorite == true
                                    setStatusFavorite(isFavorite)
                                }

                                Glide.with(this@DetailUserActivity)
                                    .load(response.data?.avatarUrl)
                                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_baseline_broken_image_24))
                                    .into(binding.imgUser)
                                binding.tvFollower.text = response.data?.followers.toString()
                                binding.tvFollowing.text = response.data?.following.toString()
                                binding.tvUsername.text = response.data?.login
                                binding.tvName.text = response.data?.name
                                binding.tvBio.text = response.data?.bio ?: "Bio : - "
                                binding.tvAddress.text = response.data?.location ?: getString(R.string.unknown)
                            }
                        }
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.layoutDetail.visibility = View.GONE
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.layoutDetail.visibility = View.GONE
                            binding.timeout.root.visibility = View.VISIBLE
                        }
                    }
                }
                setStatusFavorite(isFavorite)
                binding.fab.setOnClickListener {
                    saveOrDeleteFavorite()
                    setStatusFavorite(isFavorite)
                }
            }
        }
    }

    private fun saveOrDeleteFavorite() {
        if (!isFavorite) {
            user.isFavorite = !isFavorite
            viewModel.insertFavorite(user)
            isFavorite = !isFavorite
        } else {
            user.isFavorite = !isFavorite
            viewModel.deleteFavorite(user)
            isFavorite = !isFavorite
        }
    }

    private fun setViewPager2() {
        val tabTitle = listOf(getString(R.string.followers), getString(R.string.following))
        binding.viewPager2.adapter = SectionPagerAdapter(tabTitle.size, supportFragmentManager, lifecycle, bundle)

        TabLayoutMediator(binding.tabs, binding.viewPager2){tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, com.samsul.core.R.drawable.ic_favorite_solid))
        } else {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, com.samsul.core.R.drawable.ic_favorite_outline))
        }
    }

    companion object {
        const val USERNAME = "username"
    }
}