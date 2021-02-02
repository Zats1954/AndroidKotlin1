package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class NotifyActivity : AppCompatActivity() {
    private val viewModel: PostViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getBundleExtra("post_bundle")?.let { bundle ->
            bundle.getParcelable<Post>("post")?.let { post ->
                viewModel.addPost()
                viewModel.changeContent(post.content)
            }
        }
    }
}




