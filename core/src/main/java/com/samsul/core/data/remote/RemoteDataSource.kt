package com.samsul.core.data.remote

import com.samsul.core.data.remote.network.ApiResponse
import com.samsul.core.data.remote.network.ApiService
import com.samsul.core.data.remote.response.ListDataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService){

    suspend fun getSearchUser(text: String): Flow<ApiResponse<List<ListDataResponse>>> =
        flow {
            try {
                val response = apiService.searchUsers(text)
                val user = response.items
                if(user.isNotEmpty()) {
                    emit(ApiResponse.Success(user))
                }
                emit(ApiResponse.Error(null))
            } catch (e : Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getDetailUser(username: String): Flow<ApiResponse<ListDataResponse>> =
        flow {
            try {
                val response = apiService.getDetailUser(username)
                if (username.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                }
                emit(ApiResponse.Error(null))
            } catch (e : Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getFollower(username: String): Flow<ApiResponse<List<ListDataResponse>>> =
        flow {
            try {
                val response = apiService.getFollower(username)
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                }
                emit(ApiResponse.Error(null))
            } catch (e : Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getFollowing(username: String): Flow<ApiResponse<List<ListDataResponse>>> =
        flow {
            try {
                val response = apiService.getFollowing(username)
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                }
                emit(ApiResponse.Error(null))
            } catch (e : Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

}