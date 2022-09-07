package com.samsul.capstoneproject.detail.fragment.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.samsul.capstoneproject.databinding.FragmentFollowingBinding
import com.samsul.capstoneproject.detail.DetailUserActivity
import com.samsul.core.ui.FollowerAdapter
import com.samsul.core.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FollowingViewModel by viewModels()
    private var followingAdapter: FollowerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(DetailUserActivity.USERNAME)
        setRecyclerView()
        if(username!=null) fetchFollowing(username)
    }

    private fun setRecyclerView() {
        followingAdapter = FollowerAdapter(requireContext())
        binding.rvFollowing.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = followingAdapter
        }
    }

    private fun fetchFollowing(username: String) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getFollowing(username).collect { response ->
                    when(response) {
                        is Resource.Success -> {
                            if(response.data != null) {
                                followingAdapter?.submitList(response.data)
                            }
                        }
                        is Resource.Loading -> {}
                        is Resource.Error -> {}
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        followingAdapter = null
        super.onDestroyView()
        _binding = null
    }

}