package com.haurbano.data.repositories

import com.haurbano.data.mappers.PostsMapper
import com.haurbano.data.retrofit.PostsService
import com.haurbano.domain.models.Post
import com.haurbano.domain.repositories.PostsRepository

class PostRepositoriesImpl(
    private val postsService: PostsService,
    private val postsMapper: PostsMapper
): PostsRepository {
    override suspend fun getPosts(): List<Post> {
        val postsResponse = postsService.getPosts()
        return postsMapper(postsResponse)
    }
}