package com.haurbano.data.datasources

import android.content.Context
import android.content.SharedPreferences

class ReadPostDataSource(
    private val context: Context
) {
    companion object {
        const val READ_POSTS_PREFERENCES = "ReadPostPreferences"
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(READ_POSTS_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun isPostAlreadyRead(postId: String): Boolean = sharedPreferences.contains(postId)

    fun addReadPost(postId: String): Boolean = sharedPreferences.edit().putBoolean(postId, true).commit()

}