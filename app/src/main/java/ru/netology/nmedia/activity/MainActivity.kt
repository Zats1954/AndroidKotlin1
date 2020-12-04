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
//                tvLikes.text = post.likes.toString()
//                tvShares.text = intToString(post.shares)
//                tvVisibles.text = intToString(post.visibles)
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

//  fun intToString(num: Int): String {
//        var str = num.toString()
//        when (str.length) {
//            1, 2, 3 -> return str                     // 999
//            4 -> return if (str[1] != '0') {
//                           str[0] + "."+ str[1]+"K"    // 1100
//                         } else {
//                           str[0] + "K"        //1000
//                         }
//            5 -> return  str.substring(0,2) +"K"            // 10000
//            6 -> return str.substring(0,3) +"K"        // 100000
//            else -> return str.substring(0,str.length - 6)+"." + str[str.length - 6] +"M"           //1000000
//        }
//    }
}

