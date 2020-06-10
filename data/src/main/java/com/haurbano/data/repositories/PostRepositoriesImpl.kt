package com.haurbano.data.repositories

import com.haurbano.data.datasources.ReadPostDataSource
import com.haurbano.data.mappers.PostsMapper
import com.haurbano.data.retrofit.PostsService
import com.haurbano.domain.models.Post
import com.haurbano.domain.repositories.PostsRepository

class PostRepositoriesImpl(
    private val postsService: PostsService,
    private val readPostDataSource: ReadPostDataSource,
    private val postsMapper: PostsMapper
): PostsRepository {
    override suspend fun getPosts(): List<Post> {
        val postsResponse = postsService.getPosts()
        return postsMapper(postsResponse)
    }

    override suspend fun markReadPosts(postList: List<Post>): List<Post> {
        return postList.map { post ->
            val isRead = readPostDataSource.isPostAlreadyRead(post.id)
            post.apply { this.isRead = isRead }
        }
    }
}