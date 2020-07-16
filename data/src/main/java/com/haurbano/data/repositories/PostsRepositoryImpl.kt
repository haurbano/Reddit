package com.haurbano.data.repositories

import android.util.Log
import com.haurbano.data.datasources.PostRemoteDataSource
import com.haurbano.data.datasources.PostsLocalDataSource
import com.haurbano.data.mappers.PostsMapper
import com.haurbano.data.repositories.common.ErrorHandler
import com.haurbano.domain.common.Resource
import com.haurbano.domain.models.Post
import com.haurbano.domain.repositories.PostsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostsRepositoryImpl(
    private val postDataSource: PostRemoteDataSource,
    private val postsLocalDataSource: PostsLocalDataSource,
    private val postsMapper: PostsMapper,
    private val errorHandler: ErrorHandler
): PostsRepository, ErrorHandler by errorHandler {

    override suspend fun getPosts(): Resource<List<Post>> = withContext(Dispatchers.IO) {
        handleErrors {
            postsLocalDataSource.memoryCache.clear()
            val postsResponse = postDataSource.getPosts()
            postsLocalDataSource.lastAfterKey = postsResponse.data.after
            val newPosts = postsMapper(postsResponse)
            postsLocalDataSource.memoryCache.addAll(newPosts)
            Resource.Success(postsLocalDataSource.memoryCache.toList())
        }
    }

    override suspend fun getMorePosts(): Resource<List<Post>> = withContext(Dispatchers.IO) {
        handleErrors {
            postsLocalDataSource.lastAfterKey?.let {
                val newPosts = postDataSource.getMorePost(it)
                val mappedNewPosts = postsMapper(newPosts)
                postsLocalDataSource.memoryCache.addAll(mappedNewPosts)
                Log.d("More Posts", "Current post size: ${postsLocalDataSource.memoryCache.size}")
                postsLocalDataSource.lastAfterKey = newPosts.data.after
                Resource.Success(postsLocalDataSource.memoryCache.toList())
            } ?: Resource.Success<List<Post>>(postsLocalDataSource.memoryCache.toList())
        }
    }

    override suspend fun checkPostAsRead(id: String): Resource<Boolean> = withContext(Dispatchers.IO)  {
        handleErrors {
            postsLocalDataSource.addReadPost(id)
        }
    }

    override suspend fun dismissPost(id: String): Resource<Boolean> = withContext(Dispatchers.IO) {
        handleErrors {
            postsLocalDataSource.dismissPost(id)
        }
    }

    override suspend fun isPostDismissed(id: String): Resource<Boolean> = withContext(Dispatchers.IO) {
        handleErrors {
            postsLocalDataSource.isPostDismissed(id)
        }
    }

    override suspend fun isPostAlreadyRead(id: String): Resource<Boolean> = withContext(Dispatchers.IO) {
        handleErrors {
            postsLocalDataSource.isPostAlreadyRead(id)
        }
    }

}