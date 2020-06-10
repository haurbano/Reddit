package com.haurbano.domain

import com.haurbano.domain.models.Post
import com.haurbano.domain.repositories.PostsRepository

class GetPostUseCase(
    private val postsRepository: PostsRepository
) {
    suspend fun getPosts(): List<Post> {
        val postList = postsRepository.getPosts()
        return postsRepository.markReadPosts(postList)
    }
}