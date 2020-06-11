package com.haurbano.domain.repositories

import com.haurbano.domain.models.Post

interface PostsRepository {
    suspend fun getPosts(): List<Post>
    suspend fun markReadPosts(postList: List<Post>): List<Post>
    suspend fun checkPostAsRead(id: String): Boolean
}