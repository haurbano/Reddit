package com.haurbano.domain.models

data class Post(
    private val id: String,
    private val title: String,
    private val authorName: String,
    private val entryDate: String,
    private val thumbnail: String,
    private val numberOfComments: Int,
    private val isRead: Boolean
)