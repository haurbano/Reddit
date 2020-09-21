package com.haurbano.presentation.posts

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.haurbano.domain.models.Post
import com.haurbano.presentation.R
import com.haurbano.presentation.postdetail.PostDetailsActivity
import com.haurbano.presentation.viewmodelfactory.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_posts.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class PostsActivity : AppCompatActivity() {

    @Inject lateinit var postAdapter: PostAdapter
    @Inject lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: PostsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PostsViewModel::class.java)
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

        postAdapter.addListener(object : PostAdapter.Listener {
            override fun itemClicked(post: Post) {
                checkPostAsRead(post)
                navigateToPostDetails(post)
            }

            override fun itemRemoved(post: Post) {
                removePost(post)
            }

        })
    }

    private fun navigateToPostDetails(post: Post) {
        PostDetailsActivity.start(this, post)
    }

    private fun removePost(post: Post) {
        lifecycleScope.launch {
            val isRemoved = viewModel.dismissPost(post)
            if (isRemoved) postAdapter.dismissPost(post)
        }
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

    private fun removeAllItems() {
        postAdapter.removeAllItems()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.post_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == R.id.removeAllItemsMenu) {
            removeAllItems()
            true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }
}
