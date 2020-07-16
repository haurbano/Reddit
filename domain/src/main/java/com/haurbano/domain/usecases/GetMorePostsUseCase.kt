package com.haurbano.domain.usecases

import com.haurbano.domain.common.Resource
import com.haurbano.domain.models.Post
import com.haurbano.domain.repositories.PostsRepository

class GetMorePostsUseCase(
    private val postsRepository: PostsRepository
) {
    suspend operator fun invoke(): Resource<List<Post>> {
        return postsRepository.getMorePosts()
    }
}