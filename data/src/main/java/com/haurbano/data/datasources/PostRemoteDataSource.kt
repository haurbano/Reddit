package com.haurbano.data.datasources

import com.haurbano.data.models.PostsRequestResponse
import com.haurbano.data.retrofit.PostsService
import javax.inject.Inject

class PostRemoteDataSource @Inject constructor(
    private val postsService: PostsService
) {
    suspend fun getPosts(): PostsRequestResponse =
        postsService.getPosts()
}