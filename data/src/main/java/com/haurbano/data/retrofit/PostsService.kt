package com.haurbano.data.retrofit

import com.haurbano.data.models.PostsRequestResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PostsService {
    @GET("top/.json?t=all&limit=10")
    suspend fun getPosts(): PostsRequestResponse

    @GET("top/.json?")
    suspend fun getMorePosts(
        @Query("t") t: String = "all",
        @Query("limit") limit: String = "10",
        @Query("after") afterKey: String
    ): PostsRequestResponse
}