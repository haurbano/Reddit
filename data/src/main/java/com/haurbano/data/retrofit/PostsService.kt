package com.haurbano.data.retrofit

import com.haurbano.data.models.PostsRequestResponse
import retrofit2.http.GET

interface PostsService {
    @GET("top/.json?t=all&limit=10")
    suspend fun getPosts(): PostsRequestResponse
}