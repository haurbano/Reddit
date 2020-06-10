package com.haurbano.domain.repositories

import com.haurbano.domain.models.Post

interface PostsRepository {
    suspend fun getPosts(): List<Post>
}