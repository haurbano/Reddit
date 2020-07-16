package com.haurbano.data.datasources

import android.content.Context
import android.content.SharedPreferences
import com.haurbano.domain.common.Resource
import com.haurbano.domain.models.Post

class PostsLocalDataSource(
    private val context: Context
) {
    companion object {
        const val READ_POSTS_PREFERENCES = "ReadPostPreferences"
        const val REMOVED_POSTS_PREFERENCES = "RemovedPostPreferences"
    }


    val memoryCache = mutableListOf<Post>()
    var lastAfterKey: String? = null


    private val readPostSharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(READ_POSTS_PREFERENCES, Context.MODE_PRIVATE)
    }

    private val removedPostSharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(REMOVED_POSTS_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun isPostAlreadyRead(postId: String): Resource<Boolean> = Resource.Success(
        readPostSharedPreferences.contains(postId)
    )

    fun addReadPost(postId: String): Resource<Boolean> = Resource.Success(
        readPostSharedPreferences.edit().putBoolean(postId, true).commit()
    )

    fun dismissPost(postId: String): Resource<Boolean> = Resource.Success(
        removedPostSharedPreferences.edit().putBoolean(postId, true).commit()
    )

    fun isPostDismissed(postId: String): Resource<Boolean> = Resource.Success(
        removedPostSharedPreferences.contains(postId)
    )
}