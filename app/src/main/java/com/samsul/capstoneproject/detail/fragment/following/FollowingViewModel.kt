package com.samsul.capstoneproject.detail.fragment.following

import androidx.lifecycle.ViewModel
import com.samsul.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {
    fun getFollowing(username: String) = userUseCase.getFollowing(username)
}