package ru.netology.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import ru.netology.nmedia.databinding.ActivityVideoBinding
import ru.netology.nmedia.dto.Post

class VideoActivity : AppCompatActivity() {

    companion object {
        const val POST_KEY = "post"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityVideoBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        var post = getIntent().getParcelableExtra<Post>("post")
        binding.ok.setOnClickListener {
            val text = binding.edit.text.toString()
            if (text.isNullOrBlank()) {
                setResult(RESULT_CANCELED)
            } else {
                post = post?.copy(video = text)
                val intent = Intent()
                    .putExtra(
                        POST_KEY, post
                    )
                setResult(RESULT_OK, intent)
            }
            finish()
        }

    }
}