package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.os.Bundle
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        viewModel.data.observe(this,{post ->
            with(binding) {
                tvAuthor.text = post.author
                tvPublished.text = post.published
                tvContent.text = post.content
                tvLikes.text = post.likes.toString()
                tvShares.text =  viewModel.intToString(post.shares)
                tvVisibles.text =  viewModel.intToString(post.visibles)
                ivLike.setImageResource(
                    if (post.likedByMe) {
                        R.drawable.ic_heart2
                    } else {
                        R.drawable.ic_heart
                    })
            }
        })

        binding.ivLike.setOnClickListener { viewModel.like()}
    }
}

