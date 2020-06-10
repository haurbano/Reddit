package com.haurbano.presentation.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.haurbano.presentation.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostsActivity : AppCompatActivity() {

    private val viewModel: PostsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)
        listenViewModelChanges()
        loadData()
    }

    private fun listenViewModelChanges() {
        viewModel.posts.observe(this, Observer { posts ->
            Log.e("--haur", "${posts.size}")
        })
    }

    private fun loadData() {
        viewModel.fetchPosts()
    }
}
