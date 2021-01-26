package ru.netology.nmedia.entity

import android.view.View
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likedByMe: Boolean,
    val likes: Int,
    val shares:Int,
    val visibles: Int,
    val videoVisibility: Int = View.INVISIBLE,
    var video:String? = " "

) {
    fun toPost(): Post = Post(id,
        author,
        published,
        content,
        likedByMe,
        likes,
        shares,
        visibles,
        videoVisibility,
        video )

    companion object {
        fun fromPost(post: Post): PostEntity =
            PostEntity(post.id,
                post.author,
                post.published,
                post.content,
                post.likedByMe,
                post.likes,
                post.shares,
                post.visibles,
                post.videoVisibility,
                post.video )
    }

}