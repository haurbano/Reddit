package com.haurbano.domain.repositories

import com.haurbano.domain.common.Resource
import com.haurbano.domain.models.Post

interface PostsRepository {
    suspend fun getPosts(): Resource<List<Post>>
    suspend fun checkPostAsRead(id: String): Boolean
    suspend fun dismissPost(id: String): Boolean
    suspend fun isPostDismissed(id: String): Boolean
    suspend fun isPostAlreadyRead(id: String): Boolean
}