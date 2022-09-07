package com.samsul.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.samsul.core.domain.usecase.UserUseCase

class FavoriteViewModel(userUseCase: UserUseCase): ViewModel() {
    val user = userUseCase.getFavoriteUser().asLiveData()
}