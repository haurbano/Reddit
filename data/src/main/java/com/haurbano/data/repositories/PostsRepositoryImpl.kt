package com.haurbano.data.repositories

import com.haurbano.data.datasources.PostRemoteDataSource
import com.haurbano.data.datasources.PostsLocalDataSource
import com.haurbano.data.mappers.PostsMapper
import com.haurbano.domain.models.Post
import com.haurbano.domain.repositories.PostsRepository
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val postDataSource: PostRemoteDataSource,
    private val postsLocalDataSource: PostsLocalDataSource,
    private val postsMapper: PostsMapper
): PostsRepository {
    override suspend fun getPosts(): List<Post> {
        val postsResponse = postDataSource.getPosts()
        return postsMapper(postsResponse)
    }

    override suspend fun checkPostAsRead(id: String): Boolean {
        return postsLocalDataSource.addReadPost(id)
    }

    override suspend fun dismissPost(id: String): Boolean {
        return postsLocalDataSource.dismissPost(id)
    }

    override suspend fun isPostDismissed(id: String): Boolean {
        return postsLocalDataSource.isPostDismissed(id)
    }

    override suspend fun isPostAlreadyRead(id: String): Boolean {
        return postsLocalDataSource.isPostAlreadyRead(id)
    }

}