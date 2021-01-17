package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {
    private var postRequestCode = 1
    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding =FragmentFeedBinding.inflate(inflater, container, false)
        val adapter = PostAdapter(
            onInteractionListener = object : OnInteractionListener {
                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onEdit(post: Post) {
                    val intentEdit = Intent(context, EditActivity::class.java)
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
                    if (intent.resolveActivity(requireContext().packageManager) != null)
                        startActivity(shareIntent)
                    else {
                        showToast(R.string.app_not_found_error)
                    }
                }

                override fun onVideo(post: Post) {
                    postRequestCode = 2
                    val intentVideo = Intent(context, VideoActivity::class.java)
                    intentVideo.putExtra("post", post)
                    startActivityForResult(intentVideo, postRequestCode)
                }

                override fun playVideo(post: Post) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(post.video)
                    val shareIntent =
                        Intent.createChooser(intent, getString(R.string.chooser_share_post))
                    if (intent.resolveActivity(requireContext().packageManager) != null)
                        startActivity(shareIntent)
                    else {
                        showToast(R.string.app_not_found_error)
                    }
                }
            })

        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner, { posts ->
            adapter.submitList(posts)
        })

        binding.add.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newFragment)
//            val intent = Intent(context, NewFragment::class.java)
//            startActivityForResult(intent, postRequestCode)
        }
        return binding.root
    }


    fun showToast(text: Int, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(
            context,
            getString(text),
            length
        ).show()
    }
}


