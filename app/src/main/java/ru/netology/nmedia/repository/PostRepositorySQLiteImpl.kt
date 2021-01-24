package ru.netology.nmedia.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.database.DbHelper
import ru.netology.nmedia.database.PostDao
import ru.netology.nmedia.dto.Post

class PostRepositorySQLiteImpl(private val dao:PostDao) : PostRepository {
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init{
        posts = dao.getAll()
        data.value = posts
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        dao.likeById(id)
        posts = posts.map {
            if (it.id != id) it
            else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1
                else it.likes + 1
            )
        }
        data.value = posts
    }

    override fun share(id: Long) {
        posts = posts.map {
            if (it.id != id) it
            else it.copy(
                shares = it.shares + 1
            )
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        val saved = dao.save(post)
        val id = post.id
        posts = if(id == 0L){
            listOf(saved) + posts}
        else{posts.map{if(it.id!= id)
                             it else saved}
        }
        data.value = posts
    }

    override fun addVideo(post: Post) {
        posts = posts.map {
            if (it.id != post.id) it else it.copy(
                video = post.video,
                videoVisibility = View.VISIBLE
            ).let{ dao.save(it) }
        }
        data.value = posts
    }
}