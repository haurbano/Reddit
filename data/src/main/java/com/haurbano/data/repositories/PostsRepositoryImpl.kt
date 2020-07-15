package com.haurbano.data.repositories

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
            val postsResponse = postDataSource.getPosts()
            Resource.Success(postsMapper(postsResponse))
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