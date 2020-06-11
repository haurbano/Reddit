package com.haurbano.presentation.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.haurbano.domain.models.Post
import com.haurbano.presentation.R
import kotlinx.android.synthetic.main.activity_posts.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostsActivity : AppCompatActivity() {

    private val viewModel: PostsViewModel by viewModel()
    private val postAdapter: PostAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)
        listenViewModelChanges()
        listenViewListeners()
        setupRecyclerViewPosts()
        loadData()
    }

    private fun listenViewListeners() {
        refreshPosts.setOnRefreshListener {
            viewModel.fetchPosts()
        }
    }

    private fun setupRecyclerViewPosts() {
        val linearLayoutManager = LinearLayoutManager(this)
        rvPosts.apply {
            val dividerItemDecoration = DividerItemDecoration(this.context, linearLayoutManager.orientation)
            layoutManager = linearLayoutManager
            adapter = postAdapter
            addItemDecoration(dividerItemDecoration)
        }

        postAdapter.addListener { post -> checkPostAsRead(post) }
    }

    private fun checkPostAsRead(post: Post) {
        lifecycleScope.launch {
            val isChanged = viewModel.checkPostAsRead(post)
            if (isChanged) postAdapter.markAsRead(post.apply { isRead = true })
        }
    }

    private fun listenViewModelChanges() {
        viewModel.posts.observe(this, Observer { posts ->
            refreshPosts.isRefreshing = false
            postAdapter.replaceItems(posts)
        })
    }

    private fun loadData() {
        viewModel.fetchPosts()
    }
}
