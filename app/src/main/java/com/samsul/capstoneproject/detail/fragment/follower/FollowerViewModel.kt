package com.samsul.capstoneproject.detail.fragment.follower

import androidx.lifecycle.ViewModel
import com.samsul.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FollowerViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {
    fun getFollower(username: String) = userUseCase.getFollower(username)
}