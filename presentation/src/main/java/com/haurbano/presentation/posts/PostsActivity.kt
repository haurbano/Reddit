package com.haurbano.presentation.posts

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haurbano.domain.common.Resource
import com.haurbano.domain.models.Post
import com.haurbano.presentation.R
import com.haurbano.presentation.postdetail.PostDetailsActivity
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

        rvPosts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = linearLayoutManager.itemCount
                val visibleItemCount = linearLayoutManager.childCount
                val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()

                viewModel.listScrolled(visibleItemCount, lastVisibleItemPosition, totalItemCount)
            }
        })

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
            if (isRemoved.data == true) postAdapter.dismissPost(post)
        }
    }

    private fun checkPostAsRead(post: Post) {
        lifecycleScope.launch {
            val isChanged = viewModel.checkPostAsRead(post)
            if (isChanged.data == true) postAdapter.markAsRead(post.apply { isRead = true })
        }
    }

    private fun listenViewModelChanges() {
        viewModel.posts.observe(this, Observer { resource ->
            when(resource) {
                is Resource.Success -> {
                    refreshPosts.isRefreshing = false
                    postAdapter.replaceItems(resource.successData)
                }
                is Resource.Error -> {
                    refreshPosts.isRefreshing = false
                    showMessage(R.string.msg_error_loading_posts)
                }
                is Resource.Progress -> {
                    refreshPosts.isRefreshing = true
                }
            }
        })
    }

    private fun showMessage(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
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
