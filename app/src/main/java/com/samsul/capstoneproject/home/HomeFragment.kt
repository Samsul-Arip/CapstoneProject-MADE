package com.samsul.capstoneproject.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.rxbinding2.widget.RxTextView
import com.samsul.capstoneproject.MainActivity
import com.samsul.capstoneproject.databinding.FragmentHomeBinding
import com.samsul.capstoneproject.detail.DetailUserActivity
import com.samsul.core.domain.model.Users
import com.samsul.core.ui.UserAdapter
import com.samsul.core.utils.Helper
import com.samsul.core.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment(), UserAdapter.OnItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userAdapter = UserAdapter(requireContext(), this)
        binding.imgMenu.setOnClickListener {
            (activity as MainActivity).openCloseNavigationDrawer()
        }
        setRecyclerView()
        val searchStream = RxTextView.textChanges(binding.edtSearch)
            .skipInitialValue()
            .map { search ->
                TextUtils.isEmpty(search)
            }

        searchStream.subscribe {
            showIsEmpty(it)
        }
        binding.edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                Helper.hideSoftKeyBoard(requireContext(), view)
                fetchUser(binding.edtSearch.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.timeout.btnTryAgain.setOnClickListener {
            binding.timeout.root.visibility = View.GONE
            fetchUser(binding.edtSearch.text.toString())
        }
    }

    private fun showIsEmpty(isEmpty: Boolean) {
        if(isEmpty) {
            binding.rvUser.visibility = View.GONE
            binding.timeout.root.visibility = View.GONE
            binding.animationView.visibility = View.VISIBLE
        }
    }

    private fun setRecyclerView() {
        binding.rvUser.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = userAdapter
        }
    }

    private fun fetchUser(text: String) {
        stateLoading(true)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSearchUser(text).collect{ response ->
                    when(response) {
                        is Resource.Success -> {
                            stateLoading(false)
                            binding.rvUser.visibility = View.VISIBLE
                            if(response.data != null) {
                                userAdapter.submitList(response.data)
                            }
                        }
                        is Resource.Loading -> {stateLoading(true)}
                        is Resource.Error -> {
                            stateLoading(false)
                            binding.timeout.root.visibility = View.VISIBLE
                            binding.rvUser.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun stateLoading(loading: Boolean) {
        when(loading) {
            true -> {
                binding.rvUser.visibility = View.GONE
                binding.animationView.visibility = View.GONE
                binding.loadSearching.visibility = View.VISIBLE
            }
            false -> {
                binding.animationView.visibility = View.GONE
                binding.loadSearching.visibility = View.GONE
            }
        }
    }

    override fun onItemClicked(user: Users) {
        startActivity(Intent(requireContext(), DetailUserActivity::class.java).putExtra(DetailUserActivity.USERNAME, user.login))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}