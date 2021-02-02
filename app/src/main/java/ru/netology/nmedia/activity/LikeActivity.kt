package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.dto.Like
import ru.netology.nmedia.viewmodel.PostViewModel

class LikeActivity: AppCompatActivity() {
    private val viewModel: PostViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getBundleExtra("post_bundle")?.let { bundle ->
                bundle.getParcelable<Like>("like")?.let {
                viewModel.likeById(it.postId)
            }
        }
    }
}