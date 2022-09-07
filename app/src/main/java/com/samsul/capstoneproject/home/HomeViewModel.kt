package com.samsul.capstoneproject.home

import androidx.lifecycle.ViewModel
import com.samsul.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {
    fun getSearchUser(text: String) = userUseCase.getSearchUser(text)
}