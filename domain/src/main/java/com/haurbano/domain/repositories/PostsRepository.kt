package com.haurbano.domain.repositories

import com.haurbano.domain.common.Resource
import com.haurbano.domain.models.Post
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    fun getPostsFlow(): Flow<Resource<List<Post>>>
    suspend fun getFirstPosts()
    suspend fun getMorePosts()
    suspend fun checkPostAsRead(id: String): Resource<Boolean>
    suspend fun dismissPost(id: String): Resource<Boolean>
    suspend fun isPostDismissed(id: String): Resource<Boolean>
    suspend fun isPostAlreadyRead(id: String): Resource<Boolean>
}