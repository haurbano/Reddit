package com.haurbano.domain.models

data class Post(
    val id: String,
    val title: String,
    val authorName: String,
    val entryDate: String,
    val thumbnail: String,
    val numberOfComments: Int,
    var isRead: Boolean
)