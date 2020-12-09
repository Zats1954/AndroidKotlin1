package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun likeById(id:Long)
    fun share(id: Long)
    fun getAll(): LiveData<List<Post>>
}