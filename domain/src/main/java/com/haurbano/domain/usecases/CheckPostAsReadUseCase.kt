package com.haurbano.domain.usecases

import com.haurbano.domain.repositories.PostsRepository
import javax.inject.Inject

class CheckPostAsReadUseCase @Inject constructor(
    private val postsRepository: PostsRepository
) {
    suspend operator fun invoke(id: String): Boolean {
        return postsRepository.checkPostAsRead(id)
    }
}