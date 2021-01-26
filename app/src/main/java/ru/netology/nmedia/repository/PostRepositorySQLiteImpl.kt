package ru.netology.nmedia.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.database.DbHelper
import ru.netology.nmedia.database.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity

class PostRepositorySQLiteImpl(private val dao:PostDao) : PostRepository {

    override fun getAll(): LiveData<List<Post>> =
        Transformations.map(dao.getAll()){
                      list -> list.map{entity -> entity.toPost()}
        }

    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun share(id: Long) {
//        posts = posts.map {
//            if (it.id != id) it
//            else it.copy(
//                shares = it.shares + 1
//            )
//        }
//        data.value = posts
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }

    override fun save(post: Post) {
        dao.save(PostEntity.fromPost(post))
    }

    override fun addVideo(post: Post) {
        dao.addVideo(post)
    }
}