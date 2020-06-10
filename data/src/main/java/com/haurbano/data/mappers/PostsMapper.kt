package com.haurbano.data.mappers

import com.haurbano.data.models.PostsRequestResponse
import com.haurbano.domain.models.Post

class PostsMapper {
    private val postList = mutableListOf<Post>()
    operator fun invoke(postsRequestResponse: PostsRequestResponse): List<Post> {
        postsRequestResponse.data.children.map { children ->
            val post = children.data
            val newPost = Post(
                id = post.id,
                title = post.title,
                authorName = post.author,
                numberOfComments = post.num_comments,
                isRead = false,
                thumbnail = post.thumbnail,
                entryDate = ""
            )

            postList.add(newPost)
        }
        return postList
    }
}