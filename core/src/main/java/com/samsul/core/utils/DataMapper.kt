package com.samsul.core.utils

import com.samsul.core.data.local.entity.UserEntity
import com.samsul.core.data.remote.response.ListDataResponse
import com.samsul.core.domain.model.Users
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {
    fun mapResponsesToDomain(input: List<ListDataResponse>): Flow<List<Users>> {
        val dataArray = ArrayList<Users>()
        input.map {
            val user = Users(
                it.id,
                it.login,
                it.url,
                it.avatarUrl,
                it.name,
                it.bio,
                it.location,
                it.type,
                it.publicRepos,
                it.followers,
                it.following,
                false
            )
            dataArray.add(user)
        }
        return flowOf(dataArray)
    }

    fun mapResponseToDomain(input: ListDataResponse): Flow<Users> {
        return flowOf(
            Users(
                input.id,
                input.login,
                input.url,
                input.avatarUrl,
                input.name,
                input.bio,
                input.location,
                input.type,
                input.publicRepos,
                input.followers,
                input.following,
                false
            )
        )
    }

    fun mapEntitiesToDomain(input: List<UserEntity>): List<Users> =
        input.map { userEntity ->
            Users(
                userEntity.id,
                userEntity.login,
                userEntity.url,
                userEntity.avatarUrl,
                userEntity.name,
                userEntity.bio,
                userEntity.location,
                userEntity.type,
                userEntity.publicRepos,
                userEntity.followers,
                userEntity.following,
                userEntity.isFavorite
            )
        }

    fun mapEntityToDomain(input: UserEntity?): Users {
        return Users(
            input?.id,
            input?.login,
            input?.url,
            input?.avatarUrl,
            input?.name,
            input?.bio,
            input?.location,
            input?.type,
            input?.publicRepos,
            input?.followers,
            input?.following,
            input?.isFavorite
        )
    }


    fun mapDomainToEntity(input: Users?) = UserEntity(
        input?.id,
        input?.login,
        input?.url,
        input?.avatarUrl,
        input?.name,
        input?.bio,
        input?.location,
        input?.type,
        input?.publicRepos,
        input?.followers,
        input?.following,
        input?.isFavorite
    )
}