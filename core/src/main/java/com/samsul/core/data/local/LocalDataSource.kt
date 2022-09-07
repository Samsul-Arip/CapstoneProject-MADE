package com.samsul.core.data.local

import com.samsul.core.data.local.entity.UserEntity
import com.samsul.core.data.local.room.UserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val userDao: UserDao){

    fun getFavoriteUser(): Flow<List<UserEntity>> = userDao.getFavoriteUser()

    fun getStateFavorite(username: String): Flow<UserEntity> = userDao.getStateFavorite(username)

    suspend fun insertUser(user: UserEntity) = userDao.insertUser(user)

    suspend fun deleteUser(user: UserEntity) = userDao.deleteUser(user)
}