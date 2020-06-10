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

    fun isPostAlreadyRead(postId: Int): Boolean = sharedPreferences.contains(postId.toString())

    fun addReadPost(postId: Int) = sharedPreferences.edit().putBoolean(postId.toString(), true)

}