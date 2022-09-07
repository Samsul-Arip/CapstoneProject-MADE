package com.samsul.core.domain.usecase

import com.samsul.core.domain.model.Users
import com.samsul.core.domain.repository.IUserRepository
import javax.inject.Inject

class UserInteractor @Inject constructor(private val userRepository: IUserRepository): UserUseCase {

    override fun getSearchUser(text: String) = userRepository.getSearchUser(text)

    override fun getDetailUser(username: String) = userRepository.getDetailUser(username)

    override fun getFollower(username: String) = userRepository.getFollower(username)

    override fun getFollowing(username: String) = userRepository.getFollowing(username)

    override fun getFavoriteUser() = userRepository.getFavoriteUser()

    override fun getStateFavorite(username: String) = userRepository.getStateFavorite(username)

    override suspend fun insertUser(user: Users) = userRepository.insertUser(user)

    override suspend fun deleteUser(user: Users) = userRepository.deleteUser(user)

}