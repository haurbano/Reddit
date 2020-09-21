package com.haurbano.data.mappers

import com.haurbano.data.models.PostsRequestResponse
import com.haurbano.domain.models.Post
import java.util.*
import javax.inject.Inject

class PostsMapper @Inject constructor() {
    private val postList = mutableListOf<Post>()
    operator fun invoke(postsRequestResponse: PostsRequestResponse): List<Post> {
        postsRequestResponse.data.children.map { children ->
            val post = children.data
            val newPost = Post(
                id = post.id,
                title = post.title,
                authorName = post.author_fullname,
                numberOfComments = post.num_comments,
                isRead = false,
                thumbnail = post.thumbnail,
                entryDate = createdToDisplayDate(post.created_utc)
            )

            postList.add(newPost)
        }
        return postList
    }

    private fun createdToDisplayDate(timeStamp: Double): String {
        val date = Date(timeStamp.toLong())
        return date.toString()
    }
}