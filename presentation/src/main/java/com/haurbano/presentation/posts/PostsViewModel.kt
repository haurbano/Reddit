package com.haurbano.presentation.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haurbano.domain.common.Resource
import com.haurbano.domain.models.Post
import com.haurbano.domain.usecases.CheckPostAsReadUseCase
import com.haurbano.domain.usecases.DismissPostUseCase
import com.haurbano.domain.usecases.GetMorePostsUseCase
import com.haurbano.domain.usecases.GetPostUseCase
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch

class PostsViewModel(
    private val getPostUseCase: GetPostUseCase,
    private val checkPostAsReadUseCase: CheckPostAsReadUseCase,
    private val dismissPostUseCase: DismissPostUseCase,
    private val getMorePostsUseCase: GetMorePostsUseCase
): ViewModel() {
    private var loadingMorePosts = false
    private val _posts: MutableLiveData<Resource<List<Post>>> = MutableLiveData()

    val posts: LiveData<Resource<List<Post>>>
        get() = _posts

    fun fetchPosts() {
        viewModelScope.launch {
            val postsList = getPostUseCase.getPosts()
            _posts.postValue(postsList)
        }
    }

    suspend fun checkPostAsRead(post: Post): Resource<Boolean> {
        val postChange = CompletableDeferred<Resource<Boolean>>()

        viewModelScope.launch {
            val result = checkPostAsReadUseCase(post.id)
            postChange.complete(result)
        }

        return postChange.await()
    }

    suspend fun dismissPost(post: Post): Resource<Boolean> {
        val postRemoved = CompletableDeferred<Resource<Boolean>>()
        viewModelScope.launch {
            val result = dismissPostUseCase(post.id)
            postRemoved.complete(result)
        }

        return postRemoved.await()
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItem: Int, totalItemCount: Int) {
        if (loadingMorePosts) return

        if (visibleItemCount + lastVisibleItem >= totalItemCount) {
            viewModelScope.launch {
                loadingMorePosts = true
                val result = getMorePostsUseCase()
                _posts.postValue(result)
                loadingMorePosts = false
            }
        }
    }

}