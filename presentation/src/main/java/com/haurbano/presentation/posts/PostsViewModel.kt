package com.haurbano.presentation.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haurbano.domain.GetPostUseCase
import com.haurbano.domain.models.Post
import kotlinx.coroutines.launch

class PostsViewModel(
    private val getPostUseCase: GetPostUseCase
): ViewModel() {
    private val _posts: MutableLiveData<List<Post>> = MutableLiveData()

    val posts: LiveData<List<Post>>
        get() = _posts

    fun fetchPosts() {
        viewModelScope.launch {
            val postsList = getPostUseCase.getPosts()
            _posts.postValue(postsList)
        }
    }

}