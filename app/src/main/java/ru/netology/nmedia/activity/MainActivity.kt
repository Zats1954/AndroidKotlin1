package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.os.Bundle
import android.widget.Toast
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    private var postRequestCode = 1
    private val viewModel: PostViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = PostAdapter(
            onInteractionListener = object : OnInteractionListener {
                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onEdit(post: Post) {
                    val intentEdit = Intent(this@MainActivity, EditActivity::class.java)
                    intentEdit.putExtra("post", post)
                    postRequestCode = 1
                    startActivityForResult(intentEdit, postRequestCode)
                    viewModel.edit(post)
                }

                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onShare(post: Post) {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        type = "text/plain"
                    }
                    val shareIntent =
                        Intent.createChooser(intent, getString(R.string.chooser_share_post))
                    if (intent.resolveActivity(packageManager) != null)
                        startActivity(shareIntent)
                    else {
                        showToast(R.string.app_not_found_error)
                    }
                }

                override fun onVideo(post: Post) {
                    postRequestCode = 2
                    val intentVideo = Intent(this@MainActivity, VideoActivity::class.java)
                    intentVideo.putExtra("post", post)
                    startActivityForResult(intentVideo, postRequestCode)
                }

                override fun playVideo(post: Post) {
                    val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(post.video)
                    val shareIntent =
                        Intent.createChooser(intent, getString(R.string.chooser_share_post))
                    if (intent.resolveActivity(packageManager) != null)
                        startActivity(shareIntent)
                    else {
                        showToast(R.string.app_not_found_error)
                    }
                }
            })

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.add.setOnClickListener {
            val intent = Intent(this, NewActivity::class.java)
            startActivityForResult(intent, postRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            when (requestCode) {
                1 -> {val post = data.getParcelableExtra<Post>(NewActivity.POST_KEY) ?: return
                      viewModel.edit(post)}
                2 -> {val post = data.getParcelableExtra<Post>(VideoActivity.POST_KEY) ?: return
                      viewModel.video(post)
                }
            }
        }
    }

    fun showToast(text: Int, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(
            this,
            getString(text),
            length
        ).show()
    }
}


