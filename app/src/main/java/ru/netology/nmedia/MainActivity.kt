package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-знаний",
            published = "23 ноября в 20:00",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likedByMe = false,
            likes = 10,
            shares = 1234,
            visibles = 12340000
        )
        with(binding) {
            tvAuthor.text = post.author
            tvPublished.text = post.published
            tvContent.text = post.content
            tvLikes.text = post.likes.toString()
            tvShares.text = intToString(post.shares)
            tvVisibles.text = intToString(post.visibles)

            if (!post.likedByMe) {
                ivLike?.setImageResource(R.drawable.ic_heart)
            }
            ivLike.setOnClickListener {
                post.likedByMe = !post.likedByMe
                ivLike.setImageResource(
                    if (post.likedByMe) {
                        R.drawable.ic_heart2
                    } else {
                        R.drawable.ic_heart
                    }
                )
                if (post.likedByMe) post.likes++ else post.likes--
                tvLikes.text = intToString(post.likes)
            }

            ivShare.setOnClickListener {
                post.shares++
                tvLikes.text = intToString(post.shares)
            }
        }
    }

  inline  fun intToString(num: Int): String {
        var str = num.toString()
        when (str.length) {
            1, 2, 3 -> return str                     // 999
            4 -> return if (str[1] != '0') {
                           str[0] + "."+ str[1]+"K"    // 1100
                         } else {
                           str[0] + "K"        //1000
                         }
            5 -> return  str.substring(0,2) +"K"            // 10000
            6 -> return str.substring(0,3) +"K"        // 100000
            else -> return str.substring(0,str.length - 6)+"." + str[str.length - 6] +"M"           //1000000
        }
    }
}
