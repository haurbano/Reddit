package com.haurbano.data.repositories

import com.haurbano.data.datasources.PostRemoteDataSource
import com.haurbano.data.datasources.ReadPostDataSource
import com.haurbano.data.mappers.PostsMapper
import com.haurbano.domain.models.Post
import com.haurbano.domain.repositories.PostsRepository

class PostsRepositoryImpl(
    private val postDataSource: PostRemoteDataSource,
    private val readPostDataSource: ReadPostDataSource,
    private val postsMapper: PostsMapper
): PostsRepository {
    override suspend fun getPosts(): List<Post> {
        val postsResponse = postDataSource.getPosts()
        return postsMapper(postsResponse)
    }

    override suspend fun markReadPosts(postList: List<Post>): List<Post> {
        return postList.map { post ->
            val isRead = readPostDataSource.isPostAlreadyRead(post.id)
            post.apply { this.isRead = isRead }
        }
    }

    override suspend fun checkPostAsRead(id: String): Boolean {
        return readPostDataSource.addReadPost(id)
    }
}