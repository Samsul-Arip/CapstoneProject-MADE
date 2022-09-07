package com.samsul.core.data

import com.samsul.core.data.remote.network.ApiResponse
import com.samsul.core.utils.Resource
import kotlinx.coroutines.flow.*

abstract class NetworkResource<ResultType, RequestType> {

    private val result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = createCall().first()) {
            is ApiResponse.Success -> {
                emitAll(loadFromNetwork(apiResponse.data).map {
                    Resource.Success(it)
                })
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage.toString()))
            }
        }
    }

    protected abstract fun loadFromNetwork(data: RequestType): Flow<ResultType>

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    fun asFlow(): Flow<Resource<ResultType>> = result
}