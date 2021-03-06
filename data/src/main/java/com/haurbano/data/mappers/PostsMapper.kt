package com.haurbano.data.mappers

import com.haurbano.data.models.PostsRequestResponse
import com.haurbano.domain.models.Post
import java.util.*

class PostsMapper {
    private val postList = mutableListOf<Post>()
    operator fun invoke(postsRequestResponse: PostsRequestResponse): List<Post> {
        postList.clear()
        postsRequestResponse.data.children.map { children ->
            val post = children.data
            val newPost = Post(
                id = post.id,
                title = post.title,
                authorName = post.author,
                numberOfComments = post.num_comments,
                isRead = false,
                thumbnail = post.thumbnail,
                entryDate = createdToDisplayDate(post.created_utc),
                image = children.data.preview.images[0].source.url,
                text = children.data.author_flair_text
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