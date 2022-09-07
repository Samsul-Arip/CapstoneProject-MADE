package com.samsul.core.domain.repository

import com.samsul.core.domain.model.Users
import com.samsul.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    fun getSearchUser(text: String): Flow<Resource<List<Users>>>

    fun getDetailUser(username: String): Flow<Resource<Users>>

    fun getFollower(username: String): Flow<Resource<List<Users>>>

    fun getFollowing(username: String): Flow<Resource<List<Users>>>

    fun getFavoriteUser(): Flow<List<Users>>

    fun getStateFavorite(username: String): Flow<Users>

    suspend fun insertUser(user: Users)

    suspend fun deleteUser(user: Users): Int

}