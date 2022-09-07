package com.samsul.core.data.remote.network

import com.samsul.core.BuildConfig
import com.samsul.core.data.remote.response.ListDataResponse
import com.samsul.core.data.remote.response.ListResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization:${BuildConfig.API_KEY}")
    suspend fun searchUsers(
        @Query("q") textSearch: String
    ) : ListResponse

    @GET("users/{username}")
    @Headers("Authorization:${BuildConfig.API_KEY}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ) : ListDataResponse

    @GET("users/{username}/followers")
    @Headers("Authorization:${BuildConfig.API_KEY}")
    suspend fun getFollower(
        @Path("username") username: String
    ) : List<ListDataResponse>

    @GET("users/{username}/following")
    @Headers("Authorization:${BuildConfig.API_KEY}")
    suspend fun getFollowing(
        @Path("username") username: String
    ) : List<ListDataResponse>

}