package com.haurbano.domain.usecases

import com.haurbano.domain.repositories.PostsRepository

class CheckPostAsReadUseCase (
    private val postsRepository: PostsRepository
) {
    suspend operator fun invoke(id: String): Boolean {
        return postsRepository.checkPostAsRead(id)
    }
}