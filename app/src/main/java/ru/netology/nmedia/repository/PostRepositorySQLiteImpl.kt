package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
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