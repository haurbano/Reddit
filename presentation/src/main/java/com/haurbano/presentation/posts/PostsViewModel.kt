package com.haurbano.presentation.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haurbano.domain.usecases.GetPostUseCase
import com.haurbano.domain.models.Post
import com.haurbano.domain.usecases.CheckPostAsReadUseCase
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostsViewModel(
    private val getPostUseCase: GetPostUseCase,
    private val checkPostAsReadUseCase: CheckPostAsReadUseCase
): ViewModel() {
    private val _posts: MutableLiveData<List<Post>> = MutableLiveData()

    val posts: LiveData<List<Post>>
        get() = _posts

    private val postChange = CompletableDeferred<Boolean>()

    fun fetchPosts() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val postsList = getPostUseCase.getPosts()
                _posts.postValue(postsList)
            }
        }
    }

    suspend fun checkPostAsRead(post: Post): Boolean {
        viewModelScope.launch {
            val result = checkPostAsReadUseCase(post.id)
            postChange.complete(result)
        }

        return postChange.await()
    }

}