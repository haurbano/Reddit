package com.haurbano.domain.usecases

import com.haurbano.domain.repositories.PostsRepository

class GetMorePostsUseCase(
    private val postsRepository: PostsRepository
) {
    suspend operator fun invoke() {
        postsRepository.getMorePosts()
    }
}