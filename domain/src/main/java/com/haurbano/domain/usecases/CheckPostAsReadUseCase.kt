package com.haurbano.domain.usecases

import com.haurbano.domain.common.Resource
import com.haurbano.domain.repositories.PostsRepository

class CheckPostAsReadUseCase (
    private val postsRepository: PostsRepository
) {
    suspend operator fun invoke(id: String): Resource<Boolean> {
        return postsRepository.checkPostAsRead(id)
    }
}