package com.samsul.capstoneproject.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.samsul.core.domain.model.Users
import com.samsul.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {
    fun getDetailUser(username: String) = userUseCase.getDetailUser(username)

    fun getStateFavorite(username: String) = userUseCase.getStateFavorite(username)?.asLiveData()

    fun insertFavorite(user: Users) = viewModelScope.launch {
        userUseCase.insertUser(user)
    }

    fun deleteFavorite(user: Users) = viewModelScope.launch {
        userUseCase.deleteUser(user)
    }
}