package com.haurbano.presentation.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haurbano.domain.models.Post
import com.haurbano.presentation.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_post.view.*

class PostAdapter: RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    private var items = mutableListOf<Post>()
    private val listeners = mutableListOf<(Post) -> Unit>()


    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(post: Post) {
            itemView.txtAuthor.text = post.authorName
            itemView.txtTitle.text = post.title
            itemView.btnComments.text = post.numberOfComments.toString()

            // Mark as read
            if (post.isRead) {
                itemView.txtNewPost.visibility = View.GONE
            } else {
                itemView.txtNewPost.visibility = View.VISIBLE
            }

            // show image
            Picasso.with(itemView.context)
                .load(post.thumbnail)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(itemView.imgPostList)

            //itemView.txtCreatedTime.text = post.entryDate
            itemView.setOnClickListener {
                listeners.forEach { it(post) }
                notifyItemChanged(items.indexOf(post))
            }
        }
    }

    fun addListener(listener: (Post) -> Unit) {
        listeners.add(listener)
    }

    fun removeListeners() {
        listeners.clear()
    }

    fun removePost(post: Post) {
        items.remove(post)
        val position = items.indexOf(post)
        notifyItemRemoved(position)
    }

    fun markAsRead(post: Post) {
        val position = items.indexOf(post) + 1 // +1 to get position and not index
        notifyItemChanged(position, post)
    }

    fun replaceItems(posts: List<Post>) {
        items.clear()
        items.addAll(posts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_post, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])
}