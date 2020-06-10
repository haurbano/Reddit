package com.haurbano.data.datasources

import com.haurbano.data.models.PostsRequestResponse
import com.haurbano.data.retrofit.PostsService

class PostRemoteDataSource(
    private val postsService: PostsService
) {
    suspend fun getPosts(): PostsRequestResponse =
        postsService.getPosts()
}