package com.samsul.core.data.local.room

import androidx.room.*
import com.samsul.core.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user ORDER by login ASC")
    fun getFavoriteUser(): Flow<List<UserEntity>>

    @Query("SELECT * FROM user WHERE login =:username")
    fun getStateFavorite(username: String): Flow<UserEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity): Int
}