package com.haurbano.data.repositories

import com.haurbano.data.datasources.PostRemoteDataSource
import com.haurbano.data.datasources.PostsLocalDataSource
import com.haurbano.data.mappers.PostsMapper
import com.haurbano.data.repositories.common.ErrorHandlerRepository
import com.haurbano.data.repositories.common.IOErrorHandlerRepository
import com.haurbano.domain.common.ErrorEntity
import com.haurbano.domain.common.Resource
import com.haurbano.domain.models.Post
import com.haurbano.domain.repositories.PostsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception

class PostsRepositoryImpl(
    private val postDataSource: PostRemoteDataSource,
    private val postsLocalDataSource: PostsLocalDataSource,
    private val postsMapper: PostsMapper
): PostsRepository, ErrorHandlerRepository by IOErrorHandlerRepository() {

    override suspend fun getPosts(): Resource<List<Post>> = withContext(Dispatchers.IO) {
        handlerErrors {
            val postsResponse = postDataSource.getPosts()
            Resource.Success(postsMapper(postsResponse))
        }
    }

    override suspend fun checkPostAsRead(id: String): Boolean = withContext(Dispatchers.IO)  {
        postsLocalDataSource.addReadPost(id)
    }

    override suspend fun dismissPost(id: String): Boolean = withContext(Dispatchers.IO) {
        postsLocalDataSource.dismissPost(id)
    }

    override suspend fun isPostDismissed(id: String): Boolean = withContext(Dispatchers.IO) {
        postsLocalDataSource.isPostDismissed(id)
    }

    override suspend fun isPostAlreadyRead(id: String): Boolean = withContext(Dispatchers.IO) {
        postsLocalDataSource.isPostAlreadyRead(id)
    }

}