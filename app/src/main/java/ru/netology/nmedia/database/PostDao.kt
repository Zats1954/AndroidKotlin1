package ru.netology.nmedia.database

import ru.netology.nmedia.dto.Post

interface PostDao {
    fun getAll(): List<Post>
    fun save(post:Post):Post
    fun likeById(id:Long)
//    fun share(id: Long)
    fun removeById(id:Long)
    fun addVideo(post: Post?)
}