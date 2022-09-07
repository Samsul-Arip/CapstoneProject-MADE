package com.samsul.favorite.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.samsul.capstoneproject.detail.DetailUserActivity
import com.samsul.capstoneproject.di.MapsModuleDependencies
import com.samsul.core.domain.model.Users
import com.samsul.core.ui.UserAdapter
import com.samsul.favorite.ViewModelFactory
import com.samsul.favorite.databinding.FragmentFavoriteBinding
import com.samsul.favorite.di.DaggerFavoriteComponent
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : Fragment(), UserAdapter.OnItemClickListener {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: FavoriteViewModel by viewModels {
        factory
    }
    private lateinit var userAdapter: UserAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireContext(),
                    MapsModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        fetchFavorite()
    }

    private fun setRecyclerView() {
        userAdapter = UserAdapter(requireContext(), this)
        binding.rvUser.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = userAdapter
        }
    }

    private fun fetchFavorite() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user.isNotEmpty()) {
                userAdapter.submitList(user)
                binding.rvUser.visibility = View.VISIBLE
                binding.animationView.visibility = View.GONE
            } else {
                binding.rvUser.visibility = View.GONE
                binding.animationView.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(user: Users) {
        startActivity(Intent(requireContext(), DetailUserActivity::class.java).putExtra(DetailUserActivity.USERNAME, user.login))
    }

}