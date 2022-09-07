package com.samsul.core.data

import com.samsul.core.data.local.LocalDataSource
import com.samsul.core.data.remote.RemoteDataSource
import com.samsul.core.data.remote.network.ApiResponse
import com.samsul.core.data.remote.response.ListDataResponse
import com.samsul.core.domain.model.Users
import com.samsul.core.domain.repository.IUserRepository
import com.samsul.core.utils.DataMapper
import com.samsul.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): IUserRepository {

    override fun getSearchUser(text: String): Flow<Resource<List<Users>>> {
        return object : NetworkResource<List<Users>, List<ListDataResponse>>() {
            override fun loadFromNetwork(data: List<ListDataResponse>): Flow<List<Users>> {
                return DataMapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ListDataResponse>>> {
                return remoteDataSource.getSearchUser(text)
            }

        }.asFlow()
    }

    override fun getDetailUser(username: String): Flow<Resource<Users>> {
        return object : NetworkResource<Users, ListDataResponse>() {
            override fun loadFromNetwork(data: ListDataResponse): Flow<Users> {
                return DataMapper.mapResponseToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<ListDataResponse>> {
                return remoteDataSource.getDetailUser(username)
            }

        }.asFlow()
    }

    override fun getFollower(username: String): Flow<Resource<List<Users>>> {
        return object : NetworkResource<List<Users>, List<ListDataResponse>>() {
            override fun loadFromNetwork(data: List<ListDataResponse>): Flow<List<Users>> {
                return DataMapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ListDataResponse>>> {
                return remoteDataSource.getFollower(username)
            }

        }.asFlow()
    }

    override fun getFollowing(username: String): Flow<Resource<List<Users>>> {
        return object : NetworkResource<List<Users>, List<ListDataResponse>>() {
            override fun loadFromNetwork(data: List<ListDataResponse>): Flow<List<Users>> {
                return DataMapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ListDataResponse>>> {
                return remoteDataSource.getFollowing(username)
            }

        }.asFlow()
    }

    override fun getFavoriteUser(): Flow<List<Users>> {
        return localDataSource.getFavoriteUser().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getStateFavorite(username: String): Flow<Users> {
        return localDataSource.getStateFavorite(username).map {
            DataMapper.mapEntityToDomain(it)
        }
    }

    override suspend fun insertUser(user: Users) = localDataSource.insertUser(DataMapper.mapDomainToEntity(user))

    override suspend fun deleteUser(user: Users) = localDataSource.deleteUser(DataMapper.mapDomainToEntity(user))
}