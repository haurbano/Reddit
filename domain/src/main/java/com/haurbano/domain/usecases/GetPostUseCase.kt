package com.haurbano.domain.usecases

import com.haurbano.domain.common.Resource
import com.haurbano.domain.models.Post
import com.haurbano.domain.repositories.PostsRepository

class GetPostUseCase(
    private val postsRepository: PostsRepository
) {
    suspend fun getPosts(): Resource<List<Post>> {
        return postsRepository.getPosts().apply {
            data?.map { post ->
                    val isRead = postsRepository.isPostAlreadyRead(post.id)
                    post.apply { this.isRead = isRead }
                }?.filter { post ->
                    val isPostDismissed = postsRepository.isPostDismissed(post.id)
                    !isPostDismissed
                }
        }
    }
}