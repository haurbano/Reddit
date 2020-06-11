package com.haurbano.data.datasources

import android.content.Context
import android.content.SharedPreferences

class PostsLocalDataSource(
    private val context: Context
) {
    companion object {
        const val READ_POSTS_PREFERENCES = "ReadPostPreferences"
        const val REMOVED_POSTS_PREFERENCES = "RemovedPostPreferences"
    }

    private val readPostSharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(READ_POSTS_PREFERENCES, Context.MODE_PRIVATE)
    }

    private val removedPostSharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(REMOVED_POSTS_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun isPostAlreadyRead(postId: String): Boolean = readPostSharedPreferences.contains(postId)

    fun addReadPost(postId: String): Boolean = readPostSharedPreferences.edit().putBoolean(postId, true).commit()

    fun dismissPost(postId: String): Boolean = removedPostSharedPreferences.edit().putBoolean(postId, true).commit()

    fun isPostDismissed(postId: String): Boolean = removedPostSharedPreferences.contains(postId)

}