package com.haurbano.presentation.postdetail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.haurbano.domain.models.Post
import com.haurbano.presentation.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post_details.*
import kotlinx.android.synthetic.main.item_post.*

class PostDetailsActivity : AppCompatActivity() {

    companion object {
        private const val POST_KEY = "post_key"
        fun start(context: Context, post: Post) {
            val intent = Intent(context, PostDetailsActivity::class.java)
            intent.putExtra(POST_KEY, post)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)
        val post = intent.getSerializableExtra(POST_KEY) as Post?
        post?.let {
            txtPostTile.text = it.title
            txtDetailAuthor.text = it.authorName
            txtDetailText.text = it.text
            Picasso.with(this)
                .load(it.thumbnail)
                .into(imgDetailPost)
        }
    }
}
