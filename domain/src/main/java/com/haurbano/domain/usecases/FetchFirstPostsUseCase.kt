package com.haurbano.domain.usecases

import com.haurbano.domain.repositories.PostsRepository

class FetchFirstPostsUseCase(
    private val postsRepository: PostsRepository
) {
    suspend operator fun invoke() {
        postsRepository.getFirstPosts()
    }
}